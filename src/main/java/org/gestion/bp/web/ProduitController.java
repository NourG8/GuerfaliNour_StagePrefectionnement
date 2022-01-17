package org.gestion.bp.web;

//import static org.hamcrest.CoreMatchers.instanceOf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.FileInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.apache.commons.io.IOUtils;
import org.gestion.bp.dao.ArticleCRepository;
import org.gestion.bp.dao.MaterielRepository;
import org.gestion.bp.dao.OpProduitRepository;
import org.gestion.bp.dao.OperationRepository;
import org.gestion.bp.dao.ProduitRepository;
import org.gestion.bp.entities.ArticleConsomme;
import org.gestion.bp.entities.Categorie;
import org.gestion.bp.entities.Magazin;
import org.gestion.bp.entities.Materiel;
import org.gestion.bp.entities.NvModel;
import org.gestion.bp.entities.Operation;
import org.gestion.bp.entities.OperationProduit;
import org.gestion.bp.entities.Produit;
import org.gestion.bp.entities.User;
import org.gestion.bp.service.ArticleCService;
import org.gestion.bp.service.CategorieService;
import org.gestion.bp.service.MagazinService;
import org.gestion.bp.service.MaterielService;
import org.gestion.bp.service.OperationService;
import org.gestion.bp.service.ProduitService;
import org.gestion.bp.service.RoleService;
import org.gestion.bp.service.ServiceIncrement;
import org.gestion.bp.service.ServiceOpPDF;
import org.gestion.bp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProduitController {
	@Autowired
    RoleService roleService;
	

    @Autowired
    UserService userService;
    
    @Autowired
    CategorieService categorieService;
    
    @Autowired
    ProduitService produitService;
    
    @Autowired
    ProduitRepository produitRepository;
    
    @Autowired
    OperationRepository operationRepository;
    
    @Autowired
    MagazinService magazinService;
    
    @Autowired
    OperationService operationService;
    
    @Autowired
    MaterielService materielService;
    
    @Autowired
    MaterielRepository materielRepository;
    
    @Autowired
    ArticleCRepository articleCRepository;

	@Autowired
	OpProduitRepository opProduitRepository;
	
	@Autowired
	ArticleCService articleCService;
	
	@Autowired
	ServiceIncrement serviceIncrement;
	
	 @Autowired
	    private ServiceOpPDF service;
	 
	 @Value("${dir.images}")
	 private String imageDir;
	 
	 
	 
	 @RequestMapping("/gestionStock")
	 public String gestionStock(Model model) {
	     return "/gestionStock";
	 }
	 
	 
	 @RequestMapping("/AjouterProdMateriel")
	 public String AjouterMaterial(Model model) {
	     model.addAttribute("NvModel",new NvModel());
	     model.addAttribute("users",userService.findUsersV2());
	     model.addAttribute("categories",categorieService.findAllCategories());
	     model.addAttribute("magazins",magazinService.findAllMagazins());
	     User user = userService.getLoggedUser();
	     model.addAttribute("user",user);
	     System.out.println("$$$ l utilisateur : "+user.getUsername());
	     return "/insertMateriel";
	 }


	 @RequestMapping(value="/InsertProdMaterial", method= RequestMethod.POST)
	 public String insertProdMateriel(Model model,RedirectAttributes redirAttrs, @ModelAttribute("NvModel") NvModel nvModel,@ModelAttribute("user")User user) {
	     try {
	     	 Authentication u = SecurityContextHolder.getContext().getAuthentication();
	          String nom = u.getName();
	          System.out.println("$$$ l utilisateur : "+nom);
	     	
	     	OperationProduit opProd=new OperationProduit();
	 		opProduitRepository.save(opProd);
	 		
	     	Materiel materiel=new Materiel();
	     	materiel.setCategorie(nvModel.getProduit().getCategorie());
	     	materiel.setIntitule(nvModel.getProduit().getIntitule());
	     	materiel.setMagazin(nvModel.getProduit().getMagazin());
	     	materiel.setMatricule(nvModel.getProduit().getMatricule());
	     	materiel.setTest(0);
	     	 Set<OperationProduit> opProds= new HashSet<OperationProduit>();
	          opProds.add(opProd);
	          materiel.setOperationProduits(opProds);
	          produitService.insertProduit(materiel);
	          
	     	System.out.println("$$$$$$$$$$$$$$$$$$$$====> "+materiel.toString());

	     	DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	 	    Date date = new Date();
	     	
	 	    Operation op2=new Operation();
	 	    op2.setUser(nvModel.getUser());
	 	    op2.setNatureOp("insertion");
	 	   
	 	    op2.setNomOp("operation num°"+serviceIncrement.increment());
	 	    op2.setDateOP(date.toLocaleString());
	 	    op2.setNomResp(nom);
	 	    op2.setOperationProduits(opProds);
	 	    operationService.insertOperation(op2);
	 	    
	 	    opProd.setOperation(op2);
	         opProd.setProduit(materiel);
	         opProd.setTest(1);
	         opProd.setDatePrise(date.toLocaleString());
	     
	 		   opProduitRepository.save(opProd);
	 		    redirAttrs.addFlashAttribute("successInsertion", "insertion avec succees");
	 		    return "redirect:/index";  
	 	    
	     }
	     catch(Exception e) {
	         e.printStackTrace();
	     }
	     return "redirect:/index";
	 }



	 /* hedhy c bon */
	 @RequestMapping("/AjouterProdArticleC")
	 public String AjouterArticleC(Model model) {
	     model.addAttribute("NvModel",new NvModel());
	     model.addAttribute("users",userService.findUsersV2());
	     model.addAttribute("categories",categorieService.findAllCategories());
	     model.addAttribute("magazins",magazinService.findAllMagazins());
	     User user = userService.getLoggedUser();
	     model.addAttribute("user",user);
	     System.out.println("$$$ l utilisateur : "+user.getUsername());
	     return "/insertArticleC";
	 }

	 @RequestMapping(value="/InsertProdArticleC", method= RequestMethod.POST)
	 public String insertProdArticleC(Model model, @ModelAttribute("NvModel") NvModel nvModel,RedirectAttributes redirAttrs,@ModelAttribute("user")User user) {
	     try {
	     	 Authentication u = SecurityContextHolder.getContext().getAuthentication();
	          String nom = u.getName();
	          System.out.println("$$$ l utilisateur : "+nom);
	          
	     	OperationProduit opProd=new OperationProduit();
	 		opProduitRepository.save(opProd);
	 		
	 		
	     	ArticleConsomme article=new ArticleConsomme();
	     	article.setQteMin(0);
	     	article.setCategorie(nvModel.getArticleConsomme().getCategorie());
	     	article.setIntitule(nvModel.getArticleConsomme().getIntitule());
	     	article.setMagazin(nvModel.getArticleConsomme().getMagazin());
	     	article.setMatricule(nvModel.getArticleConsomme().getMatricule());
	     	article.setQte(nvModel.getArticleConsomme().getQte());
	     	
	     	
	     	// photo hedhy choufha 
	     	
	     	 Set<OperationProduit> opProds= new HashSet<OperationProduit>();
	          opProds.add(opProd);
	          article.setOperationProduits(opProds);
	          produitService.insertProduit(article);
	          
	          
	       /*   if(!photo.isEmpty()) {
	 			 article.setPhoto(photo.getOriginalFilename());
	 		    	photo.transferTo(new File(imageDir+article.getCode()));
	 		    }*/
	     	
	          
	     	System.out.println("$$$$$$$$$$$$$$$$$$$$====> "+article.toString());

	 	    Operation op2=new Operation();
	 	    op2.setUser(nvModel.getUser());
	 	    op2.setNatureOp("insertion");
	 	    op2.setNomOp("operation num°"+serviceIncrement.increment());
	 	    op2.setNomResp(nom);
	 	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	 	    Date date = new Date();
	 	    op2.setDateOP(date.toLocaleString());
	 	    op2.setOperationProduits(opProds);
	 	   
	 	    operationService.insertOperation(op2);
	 	    
	 	    opProd.setOperation(op2);
	         opProd.setProduit(article);
	         opProd.setTest(1);
	         opProd.setDatePrise(date.toLocaleString());
	         opProd.setQte(nvModel.getArticleConsomme().getQte());
	         
	 	    opProduitRepository.save(opProd);
	 	    
	 	   redirAttrs.addFlashAttribute("successInsertion", "insertion avec succees");
		    return "redirect:/ListArticleC"; 
	 	    
	 	   
	      
	     }
	     catch(Exception e) {
	         e.printStackTrace();
	     }
	     return "redirect:/ListArticleC";
	 }
	 
	 
	 @RequestMapping(value="/getPhoto",produces=MediaType.IMAGE_JPEG_VALUE)
	 @ResponseBody
	 public byte[] getPhoto(int code) throws Exception{
	 	File f=new File(imageDir+code);
	 	return IOUtils.toByteArray(new FileInputStream(f));
	 }
	 
	 
/*	 @RequestMapping("/")
	 public String getAllProduits(Model model) {
	     User user = userService.getLoggedUser();
	     model.addAttribute("categories",categorieService.findAllCategories());
	     model.addAttribute("user",user);
	     model.addAttribute("produits",produitService.findAllProduits());
	     return "/index";
	 }*/
	 
	 
	 
/*	 @GetMapping("/")
	public String findPaginated(@RequestParam(name="page",defaultValue="1") int pageNo,Model model) {
			int pageSize=2;
			Page<Produit> page=produitService.findPaginatedO(pageNo, pageSize);
            List<Produit> listUtilisateur=page.getContent();
			
			int pagesCount=page.getTotalPages(); 
			int [] pages=new int [pagesCount];
			for(int i=0;i<pagesCount;i++) pages[i]=i;
			model.addAttribute("currentPage",pageNo);
			model.addAttribute("pages",pages);
			model.addAttribute("pageUtilisateurs",page);
			model.addAttribute("listAdmins",listUtilisateur);
			model.addAttribute("totalPages",page.getTotalPages());
			model.addAttribute("totalItems",page.getTotalElements());
			return "/index";
			
	}*/
	 
	 
	 
	 
	 @GetMapping("/index")
		public String findPaginated(@RequestParam(name="page",defaultValue="1") int pageNo,Model model,
               @RequestParam(name="motCle",defaultValue="")String mc) {
		 		int pageSize=8;
				Page<Materiel> pageU=produitRepository.RechercheProduit("%"+mc+"%", PageRequest.of(pageNo-1,pageSize));
				List<Materiel> listOuvriers=pageU.getContent();
				
				int pagesCount=pageU.getTotalPages(); 
				int [] pages=new int [pagesCount];
				for(int i=0;i<pagesCount;i++) pages[i]=i;
				model.addAttribute("currentPage",pageNo);
				model.addAttribute("pages",pages);
				model.addAttribute("pageUtilisateurs",pageU);
				model.addAttribute("motCle",mc);
				model.addAttribute("totalPages",pageU.getTotalPages());
				model.addAttribute("totalItems",pageU.getTotalElements());
				model.addAttribute("produits",produitService.findAllProduits());
				//model.addAttribute("listAdmins",listUtilisateur);
				model.addAttribute("listOuvriers",listOuvriers);
				return "/index";
	 }
	 
	 
	 
	 @GetMapping("/ListMat")
		public String findPaginatedMat(@RequestParam(name="page",defaultValue="1") int pageNo,Model model,
            @RequestParam(name="motCle",defaultValue="")String mc) {
		 		int pageSize=8;
				Page<Materiel> pageU=produitRepository.RechercheProduit("%"+mc+"%", PageRequest.of(pageNo-1,pageSize));
				List<Materiel> listOuvriers=pageU.getContent();
				
				int pagesCount=pageU.getTotalPages(); 
				int [] pages=new int [pagesCount];
				for(int i=0;i<pagesCount;i++) pages[i]=i;
				model.addAttribute("currentPage",pageNo);
				model.addAttribute("pages",pages);
				model.addAttribute("pageUtilisateurs",pageU);
				model.addAttribute("motCle",mc);
				model.addAttribute("totalPages",pageU.getTotalPages());
				model.addAttribute("totalItems",pageU.getTotalElements());
				model.addAttribute("produits",produitService.findAllProduits());
				//model.addAttribute("listAdmins",listUtilisateur);
				model.addAttribute("listOuvriers",listOuvriers);
				 return "/DashboardAdmin";
	 }
	 
	 
	 @GetMapping("/ListArticleC")
		public String findPaginatedArticle(@RequestParam(name="page",defaultValue="1") int pageNo,Model model,
               @RequestParam(name="motCle",defaultValue="")String mc) {
		 		int pageSize=8;
				Page<ArticleConsomme> pageU=produitRepository.RechercheArticle("%"+mc+"%", PageRequest.of(pageNo-1,pageSize));
				List<ArticleConsomme> listOuvriers=pageU.getContent();
				
				int pagesCount=pageU.getTotalPages(); 
				int [] pages=new int [pagesCount];
				for(int i=0;i<pagesCount;i++) pages[i]=i;
				model.addAttribute("currentPage",pageNo);
				model.addAttribute("pages",pages);
				model.addAttribute("pageUtilisateurs",pageU);
				model.addAttribute("motCle",mc);
				model.addAttribute("totalPages",pageU.getTotalPages());
				model.addAttribute("totalItems",pageU.getTotalElements());
				//model.addAttribute("listAdmins",listUtilisateur);
				model.addAttribute("listOuvriers",listOuvriers);
				return "/ListArticleC";
	 }
	 
//	 @GetMapping("/ListArticleC")
//		public String find(@RequestParam(name="page",defaultValue="1") int pageNo,Model model,
//            @RequestParam(name="motCle",defaultValue="")String mc) {
//		 if(mc.getClass().)
//	 findPaginated(pageNo, model, mc);
//	 findPaginatedArticle(pageNo, model, mc);
//	 }

/*	 @RequestMapping("/ListArticleC")
	 public String getAllArticleC(Model model) {
	     User user = userService.getLoggedUser();
	     model.addAttribute("categories",categorieService.findAllCategories());
	     model.addAttribute("user",user);
	     model.addAttribute("produits",produitService.findAllProduits());
//	     model.addAttribute("search",new Search());
	     return "/ListArticleC";
	 }*/
	 
	 
	 @RequestMapping("/updateMateriel/{code}")
	 public String getUpdateMaterielForm(Model model,@PathVariable int code){
	 Materiel m=materielService.findById(code);
	 model.addAttribute("categories",categorieService.findAllCategories());
	 model.addAttribute("magazins",magazinService.findAllMagazins());
	 model.addAttribute("materiel",m);
	 return "/updateMateriel";
	 }

	 @RequestMapping(value="/updateMaterielIntoDB", method= RequestMethod.POST)
	 public String updateMaterielIntoDB(Model model, @ModelAttribute("materiel") Materiel materiel,RedirectAttributes redirAttrs) {
	 try {
	 	List<Categorie> c=categorieService.findAllCategories();
	 	List<Magazin> m=magazinService.findAllMagazins();
	 	Materiel mat = materielService.findById(materiel.getCode());
	   mat.setIntitule(materiel.getIntitule());
	   mat.setMatricule(materiel.getMatricule());
	   mat.setMagazin(materiel.getMagazin());
	   mat.setCategorie(materiel.getCategorie());
	   
	   produitRepository.save(mat);
	   if(materiel.getDateRetour()!=null) {
		   mat.setDateRetour(materiel.getDateRetour().toString());
		   }
	 
 	   redirAttrs.addFlashAttribute("successUpdate", "modification avec succees");
	    return "redirect:/index"; 
	   
	 }
	 catch(Exception e) {
	   e.printStackTrace();
	 }

	 return "redirect:/index";
	 }


	 //UPDATE ArticleC
	 @RequestMapping("/updateArticleC/{code}")
	 public String getUpdateArticleCForm(Model model,@PathVariable int code){
	 ArticleConsomme a=articleCService.findById(code);
	 model.addAttribute("categories",categorieService.findAllCategories());
	 model.addAttribute("magazins",magazinService.findAllMagazins());
	 model.addAttribute("articleC",a);
	 return "/updateArticleC";
	 }

	 @RequestMapping(value="/updateArticleCIntoDB", method= RequestMethod.POST)
	 public String updateArticleCIntoDB(Model model, @ModelAttribute("articleC") ArticleConsomme articleConsomme ,RedirectAttributes redirAttrs) {
	 try {
	 	List<Categorie> c=categorieService.findAllCategories();
	 	List<Magazin> m=magazinService.findAllMagazins();
	 	ArticleConsomme mat = articleCService.findById(articleConsomme.getCode());
	 mat.setIntitule(articleConsomme.getIntitule());
	 mat.setMatricule(articleConsomme.getMatricule());
	 mat.setMagazin(articleConsomme.getMagazin());
	 mat.setCategorie(articleConsomme.getCategorie());
	 mat.setQte(articleConsomme.getQte());
	 mat.setQteMin(articleConsomme.getQteMin());
	 produitRepository.save(mat);
	
	   redirAttrs.addFlashAttribute("successUpdate", "modification avec succees");
	    return "redirect:/ListArticleC"; 
	 }
	 catch(Exception e) {
	 e.printStackTrace();
	 }

	 return "redirect:/ListArticleC";
	 }

	 
	 
	 @RequestMapping("RetourMateriel")
	 public String DesactMat(Model model) {
	     model.addAttribute("NvModel",new NvModel());
	     model.addAttribute("users",userService.findUsersV2());
	     User user = userService.getLoggedUser();
	     model.addAttribute("materiel",produitRepository.findMaterielPrise());
	     model.addAttribute("user",user);
	     return "/RetourMateriel";
	 }
	 
	 
	 
	 @RequestMapping(value="/RetourMat", method= RequestMethod.POST)
	 public String desactMateriel(Model model,RedirectAttributes redirAttrs, @ModelAttribute("NvModel") NvModel nvModel,@ModelAttribute("user")User user) {
	     try {
	    	 
	    	materielRepository.deactivateTestMateriel(nvModel.getMateriel().getCode());
	 	 	materielRepository.deactivateDateMateriel(nvModel.getMateriel().getCode());
	    	 
	    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	  	    Date date = new Date();
	  	    OperationProduit opProd=new OperationProduit();
	 		opProduitRepository.save(opProd);
	 		Set<OperationProduit> opProds= new HashSet<OperationProduit>();
	        opProds.add(opProd);
	  	    
	  	    
	  	   	Authentication u = SecurityContextHolder.getContext().getAuthentication();
	        String nom = u.getName();
	        System.out.println("$$$ l utilisateur : "+nom);
	 	 	
	 	 	    Operation op2=new Operation();
	 	 	   // op2.setUser(mat.);
	 	 	    op2.setNatureOp("Retour");
	 	 	   
	 	 	    op2.setNomOp("operation num°"+serviceIncrement.increment());
	 	 	    op2.setDateOP(date.toLocaleString());
	 	 	    op2.setNomResp(nom);
	 	 	    op2.setUser(nvModel.getUser());
	 	 	    op2.setOperationProduits(opProds );
	 	 	    
	 	 	    operationService.insertOperation(op2);
	 	 	    
	 	 	     opProd.setOperation(op2);
	 	         opProd.setProduit(nvModel.getMateriel());
	 	         opProd.setTest(0);
	 	         opProd.setTestDesact(1);
	 	         opProd.setDatePrise(date.toLocaleString());
	 	     
	 	 		   opProduitRepository.save(opProd);
	 	 	
	 	 	redirAttrs.addFlashAttribute("succesDesactivate", "Retour materiel avec succes");
	 	     return "redirect:/index";
	 	    
	     }
	     catch(Exception e) {
	         e.printStackTrace();
	     }
	     return "redirect:/index";
	 }

	 
	 
	 
	/* @RequestMapping("/deactivateTestMat")
	 public String deactivateTestMateriel(Model model,RedirectAttributes redirAttrs) {
	 	/*materielRepository.deactivateTestMateriel(code);
	 	materielRepository.deactivateDateMateriel(code);*/
	 	//Materiel mat=materielService.findById(code);
	 	//mat.setDateRetour(null);
	 	/*
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
 	    Date date = new Date();
 	    OperationProduit opProd=new OperationProduit();
		opProduitRepository.save(opProd);
		Set<OperationProduit> opProds= new HashSet<OperationProduit>();
        opProds.add(opProd);
 	    
 	    
 	   Authentication u = SecurityContextHolder.getContext().getAuthentication();
       String nom = u.getName();
       System.out.println("$$$ l utilisateur : "+nom);
	 	
	 	    Operation op2=new Operation();
	 	   // op2.setUser(mat.);
	 	    op2.setNatureOp("insertion");
	 	   
	 	    op2.setNomOp("operation num°"+serviceIncrement.increment());
	 	    op2.setDateOP(date.toLocaleString());
	 	    op2.setNomResp(nom);
	 	    op2.setOperationProduits(opProds );
	 	    
	 	    operationService.insertOperation(op2);
	 	    
	 	     opProd.setOperation(op2);
	         opProd.setProduit(mat);
	         opProd.setTest(0);
	         opProd.setTestDesact(1);
	         opProd.setDatePrise(date.toLocaleString());
	     
	 		   opProduitRepository.save(opProd);
	 	
	 	redirAttrs.addFlashAttribute("succesDesactivate", "Retour materiel avec succes");
	     return "redirect:/";
	 	
	 }
*/
	 
	 @RequestMapping("/DeleteMateriel/{code}")
	 public String deleteMateriel(@PathVariable(name="code") int code,RedirectAttributes redirAttrs){
	 	Materiel mat=materielRepository.getById(code);
	 	System.out.println("********** "+"code materiel que vous avez supprimer : "+code);
	 	materielRepository.delete(mat);
	 	 
		   redirAttrs.addFlashAttribute("successDelete", "suppression avec succees");
		  

	 	return "redirect:/index";
	    } 
	 
	 @RequestMapping("/DeleteArticle/{code}")
	 public String deleteArticle(@PathVariable(name="code") int code,RedirectAttributes redirAttrs){
	 	ArticleConsomme art=articleCRepository.getById(code);
	 	System.out.println("********** "+"code article que vous avez supprimer : "+code);
	 	articleCRepository.delete(art);
	 	
		   redirAttrs.addFlashAttribute("successDelete", "suppression avec succees");
		    

	 	return "redirect:/ListArticleC";
	    } 

}