package org.gestion.bp.web;

//import static org.hamcrest.CoreMatchers.instanceOf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.lowagie.text.DocumentException;

import org.apache.commons.io.IOUtils;
import org.gestion.bp.dao.ArticleCRepository;
import org.gestion.bp.dao.MaterielRepository;
import org.gestion.bp.dao.OpProduitRepository;
import org.gestion.bp.dao.OperationRepository;
import org.gestion.bp.dao.ProduitRepository;
import org.gestion.bp.dto.OpProdResponse;
import org.gestion.bp.dto.OperationPDFExporter;
import org.gestion.bp.dto.ProduitResponse;
import org.gestion.bp.entities.ArticleConsomme;
import org.gestion.bp.entities.Categorie;
import org.gestion.bp.entities.Magazin;
import org.gestion.bp.entities.Materiel;
import org.gestion.bp.entities.NvModel;
import org.gestion.bp.entities.Operation;
import org.gestion.bp.entities.OperationProduit;
import org.gestion.bp.entities.Produit;
import org.gestion.bp.entities.Role;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OperationController {
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
	OpProduitRepository opProduitRepository;
	
	@Autowired
	ArticleCService articleCService;
	
	@Autowired
	ServiceIncrement serviceIncrement;
	
	 @Autowired
	    private ServiceOpPDF service;
	 
	 @Autowired
	    private ArticleCRepository articlecRepository;
	 
	 @Value("${dir.images}")
	 private String imageDir;


	 @RequestMapping("/ListOpProd")
	 public String getAllOpProd(Model model) {
		 List<OpProdResponse> op= opProduitRepository.findAllOperationsProd();
		 	int size=op.size();
		 	if(size==0) {
		 		size=0;
		 	}
		 
		 	for(int i=0;i<size;i++) {
		 	  op.get(i).setIdList(i);
		 	  System.out.println("$*$*$*$$*$ : "+op.get(i).getIdList());
		 	}
	 	 model.addAttribute("opProd",op);
	 	 return "/ListOperationsProduits";
	 }
	 
	 
	 
	 @GetMapping("/ListOpProdV2")
		public String findPaginatedOp(@RequestParam(name="page",defaultValue="1") int pageNo,Model model,
               @RequestParam(name="motCle",defaultValue="")String mc) {
		 		int pageSize=5;
				Page<OperationProduit> pageU=opProduitRepository.findAll(PageRequest.of(pageNo-1, pageSize));
				List<OperationProduit> listOuvriers=pageU.getContent();
				
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
				return "/DashBoardAdmin";
	 }
	 

	 @RequestMapping("/ListOpProdMat")
	 public String getAllOpProdMat(Model model) {
	 	 model.addAttribute("opProd",opProduitRepository.findAllOperationsProd());
	 	 return "/listOperationProduitMat";
	 }

	 @RequestMapping("/ListOpProds")
	 public String getOperationsUser(Model model) {
	 	
	 	List<ProduitResponse> op= opProduitRepository.findOperationsUser();
	 	int size=op.size();
	 	if(size==0) {
	 		size=0;
	 	}
	 		/*for(int i=0;i<size;i++) {
 			if(produitService.findAllProduits().get(i).getClass().getSimpleName()=="ArticleConsomme" ) {
	 			op.get(i).setIdList(serviceIncrement.increment());
	 			model.addAttribute("produits",produitService.findAllProduits().get(i));
		}*/	
	 	for(int i=0;i<size;i++) {
	 	  op.get(i).setIdList(i);
	 	  System.out.println("$*$*$*$$*$ : "+op.get(i).getIdList());
	 	}
	 			model.addAttribute("listOperatoionsUser",op);
	 			model.addAttribute("size",size);
	 	        
	 	System.out.println("$*$*$*$$*$ : "+produitService.findAllProduits().get(0).getClass().getName());
	 //	}
	 	 return "/ListOperationsUser";
	 }

	 
	 @RequestMapping("/ListOpProdsMat")
	 public String getOperationsUserMat(Model model) {
	 	
	 	List<ProduitResponse> op= opProduitRepository.findOperationsUser();
	 	int size=op.size();
	 	if(size==0) {
	 		size=0;
	 	}
	 		/*for(int i=0;i<size;i++) {
 			if(produitService.findAllProduits().get(i).getClass().getSimpleName()=="ArticleConsomme" ) {
	 			op.get(i).setIdList(serviceIncrement.increment());
	 			model.addAttribute("produits",produitService.findAllProduits().get(i));
		}*/	
	 	for(int i=0;i<size;i++) {
	 	  op.get(i).setIdList(i);
	 	  System.out.println("$*$*$*$$*$ : "+op.get(i).getIdList());
	 	}
	 			model.addAttribute("listOperatoionsUser",op);
	 			model.addAttribute("size",size);
	 	        
	 	System.out.println("$*$*$*$$*$ : "+produitService.findAllProduits().get(0).getClass().getName());
	 //	}
	 	 return "/ListOpUserMateriel";
	 }
	 
	 
	 @RequestMapping("/DeleteOperationsU/{idList}/{idOp}")
	 public String deleteOperationsUser(@PathVariable(name="idList") int idList,@PathVariable(name="idOp") int idOp	){
	 	List<ProduitResponse> op= opProduitRepository.findOperationsUser();
	 	//System.out.println("********** "+op.get(id).getId());
	 	opProduitRepository.deleteById(op.get(idList).getId());
	 	operationService.deleteOperation(op.get(idList).getIdOp());

	 	return "redirect:/ListOpProds";
	    } 

	 
	 @RequestMapping("/DeleteOperations/{idList}/{idOp}/{id}")
	 public String deleteOperations(@PathVariable(name="idList") int idList,@PathVariable(name="idOp") int idOp,@PathVariable(name="id") int id){
	 	List<ProduitResponse> op= opProduitRepository.findOperationsUser();
	 	System.out.println("********** "+idList);
	 	opProduitRepository.deleteById(op.get(idList).getId());
	 	operationService.deleteOperation(op.get(idList).getIdOp());
	 	if(idList<0) {
	 		idList=0;
	 	}

	 	return "redirect:/ListOpProds";
	    } 
	 
	 
	 @RequestMapping("/DeleteOperationsM/{idList}/{idOp}/{id}")
	 public String deleteOperationsM(@PathVariable(name="idList") int idList,@PathVariable(name="idOp") int idOp,@PathVariable(name="id") int id){
	 	List<ProduitResponse> op= opProduitRepository.findOperationsUser();
	 	System.out.println("********** "+idList);
	 	opProduitRepository.deleteById(op.get(idList).getId());
	 	operationService.deleteOperation(op.get(idList).getIdOp());
	 	if(idList<0) {
	 		idList=0;
	 	}

	 	return "redirect:/ListOpProdsMat";
	    } 
	 

	 @GetMapping("/users/export/pdf")
	 public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
	     response.setContentType("application/pdf");
	     DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	     String currentDateTime = dateFormatter.format(new Date());
	      
	     String headerKey = "Content-Disposition";
	     String headerValue = "attachment; filename=Operations_" + currentDateTime + ".pdf";
	     response.setHeader(headerKey, headerValue);
	      
	     List<ProduitResponse> listop = service.findAllOperationsUser();
	     OperationPDFExporter exporter=new OperationPDFExporter(listop);
	     exporter.export(response);
	      
	     int size=listop.size();
	 	for(int i=0;i<size;i++) {
	     listop.get(i).setTest(1);
	     System.out.println("******"+listop.get(i));
	 	}
    	opProduitRepository.deactivateTestOperationProd();
    	listop.removeAll(listop);
	 }
	 
	 
	 @RequestMapping("/updateOpMateriel/{idOp}/{idOpProd}/{code}")
	 public String deleteOperations(Model model,@PathVariable(name="idOp") int idOp,@PathVariable(name="idOpProd") int id,@PathVariable(name="code") int code	){
	    // model.addAttribute("op",new NvModel());
	     model.addAttribute("Operation",operationRepository.findById(idOp));
	     model.addAttribute("OpProd",opProduitRepository.findById(id));
	     model.addAttribute("produit",materielRepository.findById(code));
	     model.addAttribute("users",userService.findUsersV2());
	     model.addAttribute("produits",produitRepository.findAllMat());
	     
	 System.out.println("opps1 !!!!!! *****"+operationRepository.findById(idOp));
	 System.out.println("opps2 !!!!!! *****"+opProduitRepository.findById(id));
	 System.out.println("opps3 !!!!!! *****"+produitRepository.findById(code));
	 NvModel nvModel=new NvModel();
	 	nvModel.setOperation(operationRepository.findById(idOp).get());
	 	nvModel.setMateriel(materielRepository.findById(code).get());
	 	nvModel.setOperationProduit(opProduitRepository.findById(id).get());

	 model.addAttribute("NvModel",nvModel);

	 System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
	 System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getOperation().getIdOp());
	 System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getOperationProduit().getId());
	 System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getMateriel().getIntitule());

	     return "/updateOpProdMat";
	 }



	 @RequestMapping(value="/updateOpProdMateriel", method= RequestMethod.POST)
	 public String updateop(Model model,RedirectAttributes redirAttrs,HttpServletRequest request,@ModelAttribute("Operation")Operation op,@ModelAttribute("OpProd")OperationProduit opProd,@ModelAttribute("produit")Materiel prod,@ModelAttribute("produits")Produit produits,@ModelAttribute("NvModel") NvModel nvModel,@ModelAttribute("users") User users) {
	 	try {
	 		NvModel nv=new NvModel();
	 		nv.setMateriel(materielRepository.findById(prod.getCode()).get());
	 	
	 		System.out.println("opps1 !!!!!! *****"+operationRepository.findById(op.getIdOp()));
	 		System.out.println("opps2 !!!!!! *****"+opProduitRepository.findById(opProd.getId()));
	 		System.out.println("opps3 !!!!!! *****"+materielRepository.findById(prod.getCode()));
	 		
	 		 Materiel mat1=produitRepository.findMateriel(nvModel.getMateriel().getIntitule());
	 		 Materiel mat2=produitRepository.findMateriel(nv.getMateriel().getIntitule());
	 		 
	 		 //Materiel prod1=produitRepository.findMateriel(nvModel.getProduit().getIntitule());
	 		 

	 		System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
	 		System.out.println("opps4 !!!!!! *****"+nvModel.getMateriel().getCode());
	 		System.out.println("opps5 !!!!!! *****"+nvModel.getMateriel().getIntitule());
	 		System.out.println("opps6 !!!!!! *****"+mat1.getMatricule());
	 		System.out.println("prod !!!!!! *****"+nv.getMateriel().getIntitule());
	 		System.out.println("prod !!!!!! *****"+nv.getMateriel().getMatricule());
	 	  
	 	  materielRepository.updateMateriel(nv.getMateriel().getCode());
	 	  nv.getMateriel().setTest(0);
	 	  nv.getMateriel().setDateRetour(null);
	 	  produitRepository.save(nv.getMateriel());
	 	 
	        op.setNomOp(op.getNomOp());
	        op.setUser(nvModel.getOperation().getUser());
	        operationService.insertOperation(op);
	 	 
	 	opProd.setDateRetour(nvModel.getOperationProduit().getDateRetour());
	 	opProd.setProduit(nvModel.getMateriel());
	 	opProd.setOperation(op);
	 	//opProd.setProduit(nvModel.getMateriel());
	 	
	 	
	 	opProduitRepository.save(opProd);
	 	
	 	Set<OperationProduit> opProds1= new HashSet<OperationProduit>();
	     opProds1.add(opProd);
	     
	     DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	     DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	     Date date = new Date();
	     Date date1 = new Date();
	     LocalDate todaysDate = LocalDate.now();
	     
	    
	   //Materiel mat=produitRepository.findMateriel(nvModel.getMateriel().getIntitule());
	   
	  // System.out.println("$$$$$$$$$$ code:"+mat.getCode());
	   //System.out.println("$$$$$$$$$$ intitule:"+mat.getIntitule());
	   //System.out.println("$$$$$$$$$$ intitule:"+mat.getMatricule());
	    
	     
	     if(nvModel.getOperationProduit().getDateRetour().compareTo(todaysDate.toString() )<0) {
	     	redirAttrs.addFlashAttribute("error", "erreur! vous devez inserer une date superieur a la date d'aujourd hui");
	     	//return "redirect:/insertOperation";
	     }
	     
	   
	     
	     else {
	   mat1.setDateRetour(nvModel.getOperationProduit().getDateRetour());
	   mat1.setTest(1); // 1 cad occupé l materiel hedha 
	   mat1.setOperationProduits(opProds1);
	  // mat1.setCode(mat1.getCode());
	     
	   produitRepository.save(mat1);
	    // produitService.insertProduit(mat1);
	    

	   
	     Authentication u = SecurityContextHolder.getContext().getAuthentication();
	     String nom = u.getName();
	     System.out.println("$$$ l utilisateur : "+nom);
	     
	    // Operation op2=new Operation();
	     op.setUser(nvModel.getOperation().getUser());
	     op.setDateOP(date.toLocaleString());
	     op.setNatureOp("retrait");
	    op.setNomOp(nvModel.getOperation().getNomOp());
	     op.setNomResp(nom);
	     op.setOperationProduits(opProds1);
	     operationService.insertOperation(op);
	     
	     opProd.setOperation(op);
	     opProd.setProduit(mat1);
	     opProd.setTest(0);
	     opProd.setDatePrise(date.toLocaleString());
	     opProd.setDateRetour(nvModel.getOperationProduit().getDateRetour());
	    
	     
	     opProduitRepository.save(opProd);
	     materielRepository.deleteMat();
	    
	     }
	 	}
	 	
	     catch(Exception e) {
	         e.printStackTrace();
	     }
	 	return "redirect:/ListOpProdsMat";
	 }

	 
	 
	 
	 
	 
	 
	 @RequestMapping("/updateOpArticle/{idOp}/{idOpProd}/{code}")
	 public String updateArticle(Model model,@PathVariable(name="idOp") int idOp,@PathVariable(name="idOpProd") int id,@PathVariable(name="code") int code	){
	    // model.addAttribute("op",new NvModel());
	     model.addAttribute("Operation",operationRepository.findById(idOp));
	     model.addAttribute("OpProd",opProduitRepository.findById(id));
	     model.addAttribute("prod",articlecRepository.findById(code));
	     model.addAttribute("users",userService.findUsersV2());
	     model.addAttribute("produits",produitRepository.findAllArt());
	     
	 System.out.println("opps1 !!!!!! *****"+operationRepository.findById(idOp));
	 System.out.println("opps2 !!!!!! *****"+opProduitRepository.findById(id));
	 System.out.println("opps3 !!!!!! *****"+produitRepository.findById(code));
	 NvModel nvModel=new NvModel();
	 	nvModel.setOperation(operationRepository.findById(idOp).get());
	 	nvModel.setArticleConsomme(articlecRepository.findById(code).get());
	 	nvModel.setOperationProduit(opProduitRepository.findById(id).get());
	 	ArticleConsomme a=articlecRepository.findById(code).get();
	 	nvModel.setQte(a.getQte());

	 model.addAttribute("NvModel",nvModel);

	 System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
	 System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getOperation().getIdOp());
	 System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getOperationProduit().getId());
	 System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getArticleConsomme().getIntitule());

	     return "/updateOpProdArt";
	 }



	 @RequestMapping(value="/updateOpArticle", method= RequestMethod.POST)
	 public String updateopArticle(Model model,RedirectAttributes redirAttrs,HttpServletRequest request,@ModelAttribute("Operation")Operation op,@ModelAttribute("OpProd")OperationProduit opProd,@ModelAttribute("prod") ArticleConsomme p ,@ModelAttribute("produits")Produit produits,@ModelAttribute("NvModel") NvModel nvModel,@ModelAttribute("users") User users) {
	 	try {
	 		NvModel nv=new NvModel();
	 		nv.setArticleConsomme(articlecRepository.findById(p.getCode()).get());
	 		
	 	
	 		System.out.println("opps1 !!!!!! *****"+operationRepository.findById(op.getIdOp()));
	 		System.out.println("opps2 !!!!!! *****"+opProduitRepository.findById(opProd.getId()));
	 		System.out.println("opps3 !!!!!! *****"+articlecRepository.findById(p.getCode()));
	 		
	 		 ArticleConsomme mat1=produitRepository.findArticles(nvModel.getArticleConsomme().getIntitule());
	 		 ArticleConsomme mat2=produitRepository.findArticles(nv.getArticleConsomme().getIntitule());
	 		OperationProduit a=opProduitRepository.findById(opProd.getId()).get();
	 			nvModel.setQte(a.getQte());
	 		 

	 		System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
	 		System.out.println("opps4 !!!!!! *****"+nvModel.getArticleConsomme().getCode());
	 		System.out.println("opps5 !!!!!! *****"+nvModel.getArticleConsomme().getIntitule());
	 		
	 		System.out.println("opps4 !!!!!! 11 *****"+nvModel.getOperationProduit().getQte());
	 		System.out.println("opps4 !!!!!! 22 *****"+nvModel.getQte());
	 		
	 		System.out.println("opps6 !!!!!! *****"+mat1.getIntitule());
	 		
	 		System.out.println("prod !!!!!! *****"+nv.getArticleConsomme().getIntitule());
	 		System.out.println("prod !!!!!! *****"+nv.getArticleConsomme().getMatricule());
	 		System.out.println("prod !!!!!! *****"+nv.getArticleConsomme().getQte());
	 	  
	 	 // materielRepository.updateMateriel(nv.getArticleConsomme().getCode());
	 	    nv.getArticleConsomme().setTest(0);
	 		int s=nv.getArticleConsomme().getQte()+nvModel.getQte()-nvModel.getOperationProduit().getQte();
	 		System.out.println("somme qte : "+s);
	 	  nv.getArticleConsomme().setQte(nv.getArticleConsomme().getQte()+nvModel.getQte());
	 	  produitRepository.save(nv.getArticleConsomme());
	 	 
	        op.setNomOp(op.getNomOp());
	        op.setUser(nvModel.getOperation().getUser());
	        operationService.insertOperation(op);
	 	 
	 	opProd.setQte(nvModel.getOperationProduit().getQte());
	 	opProd.setProduit(nvModel.getArticleConsomme());
	 	opProd.setOperation(op);
	 	
	 	
	 	opProduitRepository.save(opProd);
	 	
	 	Set<OperationProduit> opProds1= new HashSet<OperationProduit>();
	     opProds1.add(opProd);
	     
	     DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	     DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	     Date date = new Date();
	     Date date1 = new Date();
	     LocalDate todaysDate = LocalDate.now();
	  
	    
	     int som=mat1.getQte()-nvModel.getOperationProduit().getQte();
	     mat1.setQte(som);
	    // article
	     
	     if(som<0) {
	     	redirAttrs.addFlashAttribute("error", "la quantite dans le stock est moins que la quantite que vous avez introduit !!");
	 		    return "redirect:/ListArticleC";
	     }
	   mat1.setQte(s);
	   mat1.setTest(1); // 1 cad occupé l materiel hedha 
	   mat1.setOperationProduits(opProds1);
	  // mat1.setCode(mat1.getCode());
	     
	   produitRepository.save(mat1);

	   
	     Authentication u = SecurityContextHolder.getContext().getAuthentication();
	     String nom = u.getName();
	     System.out.println("$$$ l utilisateur : "+nom);
	     
	    // Operation op2=new Operation();
	     op.setUser(nvModel.getOperation().getUser());
	     op.setDateOP(date.toLocaleString());
	     op.setNatureOp("retrait");
	     op.setNomOp(nvModel.getOperation().getNomOp());
	     op.setNomResp(nom);
	     op.setOperationProduits(opProds1);
	     operationService.insertOperation(op);
	     
	     opProd.setOperation(op);
	     opProd.setProduit(mat1);
	     opProd.setTest(0);
	     opProd.setDatePrise(date.toLocaleString());
	     opProd.setQte(nvModel.getOperationProduit().getQte());
	    
	     
	     opProduitRepository.save(opProd);
	     articlecRepository.deleteArt();
	    
	     
	 	}
	 	
	     catch(Exception e) {
	         e.printStackTrace();
	     }
		return "redirect:/ListOpProds";
	 }


 
	
	 
}