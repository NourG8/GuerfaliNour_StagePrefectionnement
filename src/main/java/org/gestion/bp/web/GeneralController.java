package org.gestion.bp.web;

//import static org.hamcrest.CoreMatchers.instanceOf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
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
import org.gestion.bp.dao.CategorieRepository;
import org.gestion.bp.dao.MaterielRepository;
import org.gestion.bp.dao.OpProduitRepository;
import org.gestion.bp.dao.OperationRepository;
import org.gestion.bp.dao.ProduitRepository;
import org.gestion.bp.dao.RoleRepository;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
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
public class GeneralController {
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
	    private RoleRepository roleRepository;
	 
	 @Autowired
	    private CategorieRepository categorieRepository;
	 
	 
	 @Autowired
	    private OpProduitRepository opProdTRepository;
	 
	 @Autowired
	    private ArticleCRepository articlecRepository;
	 
	 
	 
	 @Value("${dir.images}")
	 private String imageDir;
	 
// kenet register fi 3oudh login ena badaltha l test bark !!
@RequestMapping("/login")
public String getRegisterForm(Model model) {
    model.addAttribute("User",new User());
    User user = userService.getLoggedUser();
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    model.addAttribute("user",user);
    model.addAttribute("Username",user.getUsername());
    return "/login";
}


@RequestMapping(value = "/login", method = RequestMethod.POST, params = "login")
public String login(@RequestParam(value = "username") String username, 
        @RequestParam(value = "password") String password) {

    //do authentication
	return "redirect:/gestionMateriel";
}





@GetMapping("/")
	public String findd(){
	User us = userService.getLoggedUser();
	System.out.println(us.getUsername());
	System.out.println(us.getRoles());
	
	if(us.getRoles().equals("GMat"))
	 return "/gestionMateriel";
	else if(us.getRoles().equals("GArt"))
		return "/gestionArticle";
		else
			return "/dashboardAdmin";
	
}




@GetMapping("/dashboardAdmin")
public String dash(@RequestParam(name="page",defaultValue="1") int pageNo,@RequestParam(name="Page",defaultValue="1") int PageNo,Model model,
        @RequestParam(name="motCle",defaultValue="")String mc) {
	model.addAttribute("sizeM",produitRepository.findAllMateriel().size());
	model.addAttribute("sizeA",produitRepository.findAllArticle().size());
	model.addAttribute("sizeOpM",opProduitRepository.findAllOperationsProdMat().size());
	int size= opProduitRepository.findAllOperationsProdArt().size();
	model.addAttribute("sizeOpA",size);
	model.addAttribute("sizeC",categorieRepository.findAllCateg().size());
	model.addAttribute("sizeAdmin",userService.findAdminV2().size());
	model.addAttribute("sizeUser",userService.findUsersV2().size());
	
	 model.addAttribute("userN",userService.findUsersV2());
	 model.addAttribute("roles",roleRepository.findRoles());
	 model.addAttribute("Admin",userService.findAdminV2());
	 List<OpProdResponse> op= opProduitRepository.findAllOperationsProduits();
 	 model.addAttribute("opProd",op);
 	 
	
	int PageSize=5;
	Page<OperationProduit> PageU=opProdTRepository.RechercheOpProdMat(PageRequest.of(PageNo-1, PageSize));
	List<OperationProduit> ListOuvriers=PageU.getContent();
	
	int PagesCount=PageU.getTotalPages(); 
	int [] Pages=new int [PagesCount];
	for(int i=0;i<PagesCount;i++) Pages[i]=i;
	model.addAttribute("CurrentPage",PageNo);
	model.addAttribute("Pages",Pages);
	model.addAttribute("PageUtilisateurs",PageU);
	model.addAttribute("MotCle",mc);
	model.addAttribute("TotalPages",PageU.getTotalPages());
	model.addAttribute("TotalItems",PageU.getTotalElements());
	model.addAttribute("Produits",produitService.findAllProduits());
	//model.addAttribute("listAdmins",listUtilisateur);
	model.addAttribute("ListOuvriers",ListOuvriers);
	
	
	
	Page<OperationProduit> pageU=opProdTRepository.RechercheOpProdArt(PageRequest.of(pageNo-1, PageSize));
	List<OperationProduit> listOuvriers=PageU.getContent();
	
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


@RequestMapping(value="/insertUserIntoDB", method= RequestMethod.POST)
public String insertUserIntoDB(Model model, @ModelAttribute("User") User user,@ModelAttribute("user") User u) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    try {
    	System.out.println("***************"+u.getUsername());
    	User us = userService.getLoggedUser();
    	System.out.println(us.getAge()+" "+us.getCin()+" "+us.getUsername());
    	 model.addAttribute("us",us);
    	 model.addAttribute("USER",u);
    	
    	
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role userRole= roleService.findByRoleType("USER");
        System.out.println("////////////"+userRole);
//        user.setRoles(null);
        Set<Role> roles= new HashSet<Role>();
        roles.add(userRole);

        user.setRoles(roles);
        userService.insertUser(user);
        model.addAttribute("user",user);
    }
    catch(Exception e) {
        e.printStackTrace();
    }
    return "redirect:/login";
    
}






@GetMapping("/dashboardAd")
public String dashBO(@RequestParam(name="page",defaultValue="1") int pageNo,Model model,
        @RequestParam(name="motCle",defaultValue="")String mc) {
	
	//liste des operation pour les Materiaux
	
		int PageSize=5;
	Page<OperationProduit> PageU=opProdTRepository.findAll(PageRequest.of(pageNo-1, PageSize));
	List<OperationProduit> ListOuvriers=PageU.getContent();
	
	int PagesCount=PageU.getTotalPages(); 
	int [] Pages=new int [PagesCount];
	for(int i=0;i<PagesCount;i++) Pages[i]=i;
	model.addAttribute("CurrentPage",pageNo);
	model.addAttribute("Pages",Pages);
	model.addAttribute("PageUtilisateurs",PageU);
	model.addAttribute("MotCle",mc);
	model.addAttribute("TotalPages",PageU.getTotalPages());
	model.addAttribute("TotalItems",PageU.getTotalElements());
	model.addAttribute("Produits",produitService.findAllProduits());
	//model.addAttribute("listAdmins",listUtilisateur);
	model.addAttribute("ListOuvriers",ListOuvriers);



return "/DashboardAdmin";
	

}





@RequestMapping(value="/redirect", method= RequestMethod.GET)
public void voidtestLogin(HttpServletResponse resp,HttpServletRequest request) throws IOException {
    Authentication auth=SecurityContextHolder.getContext().getAuthentication();
   if(request.isUserInRole("Role_ADMIN"))
  	  resp.sendRedirect("/dashboardAdmin");
    if(request.isUserInRole("ADMIN"))
  	  resp.sendRedirect("/dashboardAdmin");
  
     if(request.isUserInRole("Role_GArt"))
    	  resp.sendRedirect("/gestionArticle");
       if(request.isUserInRole("GArt"))
    	  resp.sendRedirect("/gestionArticle");
       if(request.isUserInRole("Role_GMat"))
    	  resp.sendRedirect("/gestionMateriel");
    
       if(request.isUserInRole("GMat"))
    	  resp.sendRedirect("/gestionMateriel");
    
      
    
    
}




@RequestMapping("/createOpMateriel")
public String createMaterial(Model model,RedirectAttributes redirAttrs,HttpServletRequest request) {
    model.addAttribute("NvModel",new NvModel());
   // Produit produit=materielService.findById(code);
   // if(produit.getTest()==1) {
		  // redirAttrs.addFlashAttribute("error", "cet article n'est pas disponible pour le moment !");
		 //  return "redirect:/"; 
	 //  }
    User user = userService.getLoggedUser();
// System.out.println("$$$$$$$$$$$$$$$$$$$$====> "+produit.toString());
    model.addAttribute("users",userService.findUsersV2());
    model.addAttribute("materiel",produitRepository.findAllMateriel());
    model.addAttribute("user",user);
    System.out.println("$$$materiel : "+produitRepository.findAllMateriel().size());
    System.out.println("$$$ l utilisateur : "+user.getUsername());
    return "/insertOperation";
}



@RequestMapping(value="/InsertMaterial", method= RequestMethod.POST)
public String insertMateriel(Model model,RedirectAttributes redirAttrs,HttpServletRequest request, @ModelAttribute("NvModel") NvModel nvModel ,@ModelAttribute("materiel") Materiel materiel,@ModelAttribute("user")User user,@ModelAttribute("users")User users) {
    try {
    	Collection<Materiel> m=produitRepository.findAllMateriel();
 System.out.println("$$$$$$ le materiel : "+m.size());
    	OperationProduit opProd=new OperationProduit();
		opProduitRepository.save(opProd);
		
		Set<OperationProduit> opProds1= new HashSet<OperationProduit>();
        opProds1.add(opProd);
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    Date date1 = new Date();
	    LocalDate todaysDate = LocalDate.now();
//	     System.out.println(format.format(date));
	
	    System.out.println("******* :"+materiel);
	    System.out.println("AAAAAAAAAA"+nvModel.getMateriel().getCode());
	    System.out.println("$$$$$date 1 :"+nvModel.getMateriel().getDateRetour()+"$$$$$date local :"+ todaysDate.toString()  );
	    
	    
	   Materiel mat=produitRepository.findMateriel(nvModel.getMateriel().getIntitule());
	   System.out.println("$$$$$$$$$$ intitule:"+mat.getIntitule()+"  "+mat.getCode());
	   
	    
	   
	    if(nvModel.getMateriel().getDateRetour().compareTo(todaysDate.toString() )<0) {
	    	redirAttrs.addFlashAttribute("error", "erreur! vous devez inserer une date superieur a la date d'aujourd hui");
	    	//return "redirect:/insertOperation";
	    }
	    
	  
	    
	    else {
        mat.setDateRetour(nvModel.getMateriel().getDateRetour().toString());
        mat.setTest(1); // 1 cad occupé l materiel hedha 
        mat.setOperationProduits(opProds1);
        
        produitService.insertProduit(mat);
	   

        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        String nom = u.getName();
        System.out.println("$$$ l utilisateur : "+nom);
        
	    Operation op2=new Operation();
	    op2.setUser(nvModel.getUser());
	    op2.setDateOP(date.toLocaleString());
	    op2.setNatureOp("retrait");
	    op2.setNomOp("operation num°"+serviceIncrement.increment());
	    op2.setNomResp(nom);
	    op2.setOperationProduits(opProds1);
	    operationService.insertOperation(op2);
	    
	    opProd.setOperation(op2);
        opProd.setProduit(nvModel.getMateriel());
        opProd.setTest(0);
        opProd.setDatePrise(date.toLocaleString());
        opProd.setDateRetour(nvModel.getMateriel().getDateRetour());
        
	    opProduitRepository.save(opProd);   
	    redirAttrs.addFlashAttribute("succes", "prise materiel avec succes!");
	    return "redirect:/index";
	    
	    }
   // }
    }
    catch(Exception e) {
        e.printStackTrace();
    }
    return "redirect:/index";
}



@RequestMapping("/updateOpMat/{idOp}/{idOpProd}/{code}")
public String deleteOperations(Model model,@PathVariable(name="idOp") int idOp,@PathVariable(name="idOpProd") int id,@PathVariable(name="code") int code	){
   // model.addAttribute("op",new NvModel());
    model.addAttribute("Operation",operationRepository.findById(idOp));
    model.addAttribute("OpProd",opProdTRepository.findById(id));
    model.addAttribute("produit",materielRepository.findById(code));
    model.addAttribute("users",userService.findUsersV2());
    model.addAttribute("produits",produitRepository.findAllMat());
    
System.out.println("opps1 !!!!!! *****"+operationRepository.findById(idOp));
System.out.println("opps2 !!!!!! *****"+opProdTRepository.findById(id));
System.out.println("opps3 !!!!!! *****"+produitRepository.findById(code));
NvModel nvModel=new NvModel();
	nvModel.setOperation(operationRepository.findById(idOp).get());
	nvModel.setMateriel(materielRepository.findById(code).get());
	nvModel.setOperationProduit(opProdTRepository.findById(id).get());

model.addAttribute("NvModel",nvModel);

System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getOperation().getIdOp());
System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getOperationProduit().getId());
System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getMateriel().getIntitule());

    return "/updateOpProd";
}



@RequestMapping(value="/updateOpProdMat", method= RequestMethod.POST)
public String updateop(Model model,RedirectAttributes redirAttrs,HttpServletRequest request,@ModelAttribute("Operation")Operation op,@ModelAttribute("OpProd")OperationProduit opProd,@ModelAttribute("produit")Materiel prod,@ModelAttribute("produits")Produit produits,@ModelAttribute("NvModel") NvModel nvModel,@ModelAttribute("users") User users) {
	try {
		NvModel nv=new NvModel();
		nv.setMateriel(materielRepository.findById(prod.getCode()).get());
	
		System.out.println("opps1 !!!!!! *****"+operationRepository.findById(op.getIdOp()));
		System.out.println("opps2 !!!!!! *****"+opProdTRepository.findById(opProd.getId()));
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
	return "redirect:/ListOpProdMat";
}




@RequestMapping("/updateOpArt/{idOp}/{idOpProd}/{code}")
public String updateArticle(Model model,@PathVariable(name="idOp") int idOp,@PathVariable(name="idOpProd") int id,@PathVariable(name="code") int code	){
   // model.addAttribute("op",new NvModel());
    model.addAttribute("Operation",operationRepository.findById(idOp));
    model.addAttribute("OpProd",opProdTRepository.findById(id));
    model.addAttribute("prod",articlecRepository.findById(code));
    model.addAttribute("users",userService.findUsersV2());
    model.addAttribute("produits",produitRepository.findAllArt());
    
System.out.println("opps1 !!!!!! *****"+operationRepository.findById(idOp));
System.out.println("opps2 !!!!!! *****"+opProdTRepository.findById(id));
System.out.println("opps3 !!!!!! *****"+produitRepository.findById(code));
NvModel nvModel=new NvModel();
	nvModel.setOperation(operationRepository.findById(idOp).get());
	nvModel.setArticleConsomme(articlecRepository.findById(code).get());
	nvModel.setOperationProduit(opProdTRepository.findById(id).get());
	ArticleConsomme a=articlecRepository.findById(code).get();
	nvModel.setQte(a.getQte());

model.addAttribute("NvModel",nvModel);

System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getOperation().getIdOp());
System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getOperationProduit().getId());
System.out.println("OPSSSSSS !!!!!! *****"+nvModel.getArticleConsomme().getIntitule());

    return "/updateOpArt";
}



@RequestMapping(value="/updateOpArt", method= RequestMethod.POST)
public String updateopArt(Model model,RedirectAttributes redirAttrs,HttpServletRequest request,@ModelAttribute("Operation")Operation op,@ModelAttribute("OpProd")OperationProduit opProd,@ModelAttribute("prod") ArticleConsomme p ,@ModelAttribute("produits")Produit produits,@ModelAttribute("NvModel") NvModel nvModel,@ModelAttribute("users") User users) {
	try {
		NvModel nv=new NvModel();
		nv.setArticleConsomme(articlecRepository.findById(p.getCode()).get());
		
	
		System.out.println("opps1 !!!!!! *****"+operationRepository.findById(op.getIdOp()));
		System.out.println("opps2 !!!!!! *****"+opProdTRepository.findById(opProd.getId()));
		System.out.println("opps3 !!!!!! *****"+articlecRepository.findById(p.getCode()));
		
		 ArticleConsomme mat1=produitRepository.findArticles(nvModel.getArticleConsomme().getIntitule());
		 ArticleConsomme mat2=produitRepository.findArticles(nv.getArticleConsomme().getIntitule());
		OperationProduit a=opProdTRepository.findById(opProd.getId()).get();
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
	return "redirect:/ListOpProd";
}




//@RequestMapping(value="/InsertMaterial", method= RequestMethod.POST)
//public String insertMateriel(Model model,RedirectAttributes redirAttrs,HttpServletRequest request, @ModelAttribute("NvModel") NvModel nvModel ,@ModelAttribute("materiel") Materiel materiel,@ModelAttribute("user")User user,@ModelAttribute("users")User users) {
//    try {
//    	Materiel p=materielService.findById(materiel.getCode());
//    	System.out.println("$$$$$$$$$$$$$$$$$$$$ le code est : ====> "+p.getCode());
//    	 if(materiel.getTest()==1) {
//			   redirAttrs.addFlashAttribute("error", "cet article n'est pas disponible pour le moment !");
//			   return "redirect:/"; 
//		   }
//    	 else {
//    	OperationProduit opProd=new OperationProduit();
//		opProduitRepository.save(opProd);
//		
//		Set<OperationProduit> opProds1= new HashSet<OperationProduit>();
//        opProds1.add(opProd);
//        
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
//        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//	    Date date = new Date();
//	    Date date1 = new Date();
//	    LocalDate todaysDate = LocalDate.now();
////	     System.out.println(format.format(date));
//	
//	    System.out.println("comparer les 2 dates :"+nvModel.getMateriel().getDateRetour().compareTo(todaysDate.toString()));
//	   
//	    System.out.println("$$$$$date 1 :"+nvModel.getMateriel().getDateRetour()+"$$$$$date local :"+ todaysDate.toString()  );
//	   
//	    if(nvModel.getMateriel().getDateRetour().compareTo(todaysDate.toString() )<0) {
//	    	redirAttrs.addFlashAttribute("error", "erreur! vous devez inserer une date superieur a la date d'aujourd hui");
//	    	//return "redirect:/insertOperation";
//	    }
//	    
//	    else {
//        materiel.setDateRetour(nvModel.getMateriel().getDateRetour().toString());
//        materiel.setTest(1); // 1 cad occupé l materiel hedha 
//        materiel.setOperationProduits(opProds1);
//        produitService.insertProduit(materiel);
//	   
//
//        Authentication u = SecurityContextHolder.getContext().getAuthentication();
//        String nom = u.getName();
//        System.out.println("$$$ l utilisateur : "+nom);
//        
//	    Operation op2=new Operation();
//	    op2.setUser(nvModel.getUser());
//	    op2.setDateOP(date.toLocaleString());
//	    op2.setNatureOp("retrait");
//	    op2.setNomOp("operation num°"+serviceIncrement.increment());
//	    op2.setNomResp(nom);
//	    op2.setOperationProduits(opProds1);
//	    operationService.insertOperation(op2);
//	    
//	    opProd.setOperation(op2);
//        opProd.setProduit(nvModel.getMateriel());
//        opProd.setTest(0);
//        opProd.setDatePrise(date.toLocaleString());
//        opProd.setDateRetour(nvModel.getMateriel().getDateRetour());
//        
//	    opProduitRepository.save(opProd);   
//	    redirAttrs.addFlashAttribute("succes", "prise materiel avec succes!");
//	    return "redirect:/";
//	    
//	    }
//    }
//    }
//    catch(Exception e) {
//        e.printStackTrace();
//    }
//    return "redirect:/";
//}


/*  affecter un article a un ouvrier   */
@RequestMapping("/createOpArticleC")
public String createOpArticleC(Model model ){
    model.addAttribute("NvModel",new NvModel());
    //Produit produit=articleCService.findById(code);
   // System.out.println("$$$$$$$$$$$$$$$$$$$$====> "+produit.getCode());
    model.addAttribute("users",userService.findUsersV2());
    model.addAttribute("produit",produitRepository.findAllArticle());
//    model.addAttribute("produit",produit);
    User user = userService.getLoggedUser();
    model.addAttribute("user",user);
    System.out.println("$$$ l utilisateur : "+user.getUsername());
    return "/insertOpArticleC";
}


@RequestMapping(value="/InsertOpArticleC", method= RequestMethod.POST)
public String insertOpArticleC(Model model,RedirectAttributes redirAttrs,HttpServletRequest request, @ModelAttribute("produit")ArticleConsomme article, @ModelAttribute("NvModel") NvModel nvModel ,@ModelAttribute("user")User user) {
    try {
    	
    	Collection<ArticleConsomme> m=produitRepository.findAllArticle();
    	System.out.println("$$$$$$ l article : "+m.size());
    	
    	 Authentication u = SecurityContextHolder.getContext().getAuthentication();
         String nom = u.getName();
         System.out.println("$$$ l utilisateur : "+nom);
    	 
        //ArticleConsomme p=articleCService.findById(article.getCode());
        //System.out.println("$$$$$$$$$$$$$$$$$$$$ la nouvelle qte : ====> "+(article.getQte()));
        ArticleConsomme a=articleCService.findById(nvModel.getArticleConsomme().getCode());
        
         OperationProduit opProd=new OperationProduit();
 		opProduitRepository.save(opProd);
 		
 		Set<OperationProduit> opProds1= new HashSet<OperationProduit>();
         opProds1.add(opProd);
     	
        int s=article.getQte()-nvModel.getArticleConsomme().getQte();
        int som=a.getQte()-nvModel.getQte();
        a.setQte(som);
       // article
        
        if(som<0) {
        	redirAttrs.addFlashAttribute("error", "la quantite dans le stock est moins que la quantite que vous avez introduit !!");
 		    return "redirect:/ListArticleC";
        }
        
        
        a.setQte(som);
        a.setQteMin(0);
        a.setTest(1);
        a.setOperationProduits(opProds1);
        produitService.insertProduit(a);
        
        System.out.println("$$$$$$$$$$$$$$$$$$$$ la nouvelle qte : ====> "+(a.getQte()));

	    Operation op2=new Operation();
	    op2.setUser(nvModel.getUser());
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	    Date date = new Date();
	    op2.setDateOP(date.toLocaleString());
	    op2.setNatureOp("retrait");
	    
	    op2.setNomOp("operation num°"+serviceIncrement.increment());
	    op2.setNomResp(nom);
	    op2.setOperationProduits(opProds1);
	    operationService.insertOperation(op2);
	    
	    opProd.setOperation(op2);
        opProd.setProduit(a);
        //test eli fel opProd bech na3ref biha imprimeha lwar9a ou nn => 0 ya3ni me imprimehech
        opProd.setTest(0);
        opProd.setDatePrise(date.toLocaleString());
        opProd.setQte(nvModel.getQte());
        
	    opProduitRepository.save(opProd);
	    
//	    String nomOuvrier=request.getParameter("username");
//	    int qte=request.getParameter("qte");
	    
	    
//     HttpSession session=request.getSession();
//     session.setAttribute("username", nvModel.getUser().getUsername());
//     session.setAttribute("qte", nvModel.getArticleConsomme().getQte());
     
     
    }
    catch(Exception e) {
        e.printStackTrace();
    }
    redirAttrs.addFlashAttribute("success", "prise article avec succees");
    return "redirect:/ListArticleC";
//    return "redirect:/createOpArticleC/"+article.getCode();
}



@RequestMapping(value = "/403", method = RequestMethod.GET)
public String accessDenied(Model model, Principal principal) {
    return "403";
}

@RequestMapping(value = "/gestionMateriel", method = RequestMethod.GET)
public String gestionMat(Model model) {
	
	model.addAttribute("size",produitRepository.findAllMateriel().size());
	System.out.println("size $$$$$$$ : "+produitRepository.findAllMateriel().size());
	return "gestionMateriel";
}

@RequestMapping(value = "/gestionArticle", method = RequestMethod.GET)
public String gestionArt(Model model) {
	
	model.addAttribute("size",produitRepository.findAllArticle().size());
	 model.addAttribute("sizeA",produitRepository.findAllArt().size());
	System.out.println("size $$$$$$$ : "+produitRepository.findAllArticle().size());
	return "gestionArticle";
}


//mochkla lena zid fct fel repository te7seb les operations materiels et article kol wehed wahdou  
@RequestMapping(value = "/gestionOpMateriel", method = RequestMethod.GET)
public String gestionOpMat(Model model) {
	 model.addAttribute("size",opProduitRepository.findAllOperationsProdMat().size());
	 System.out.println("******size: "+opProduitRepository.findAllOperationsProdMat().size());
	 model.addAttribute("sizeM",produitRepository.findAllMateriel().size());
	 model.addAttribute("sizeMat",produitRepository.findAllMat().size());
	 model.addAttribute("sizePrise",produitRepository.findAllMat().size()-produitRepository.findAllMateriel().size());
	 
	return "gestionOpMateriel";
}

@RequestMapping(value = "/gestionOpArticle", method = RequestMethod.GET)
public String gestionOpArt(Model model) {
	 int size= opProduitRepository.findAllOperationsProdArt().size();
	 model.addAttribute("sizeA",produitRepository.findAllArt().size());
	 model.addAttribute("sizeD",produitRepository.findArtDispo().size());
	 System.out.println("******size: "+size);
	 
	 model.addAttribute("size",size);
	
	return "gestionOpArticle";
}


@RequestMapping(value = "/gestionUtilisateur", method = RequestMethod.GET)
public String gestionUser(Model model) {
	 model.addAttribute("sizeAdmin",userService.findAdminV2().size());
	 model.addAttribute("size",userService.findUsersV2().size());
	 System.out.println("$$$$$$$$$$$ *********** size : "+userService.findUsersV2().size());
	 System.out.println("$$$$$$$$$$$ *********** sizeAdmin : "+userService.findAdminV2().size());
	 
	return "gestionUser";
}


@RequestMapping(value = "/gestionOpUserMateriel", method = RequestMethod.GET)
public String gestionUserMat(Model model) {
	return "gestioOpUserMat";
}


@RequestMapping(value = "/gestionOpUserArticle", method = RequestMethod.GET)
public String gestionUserArt(Model model) {
	return "gestioOpUserArt";
}

/* hedhy c bon */
/*
@RequestMapping("/AjouterProdMateriel")
public String AjouterMaterial(Model model) {
    model.addAttribute("NvModel",new NvModel());
    model.addAttribute("users",userService.findUsers());
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
		    redirAttrs.addFlashAttribute("successInsertion", "insertion avec succee");
		    return "redirect:/";  
	    
    }
    catch(Exception e) {
        e.printStackTrace();
    }
    return "redirect:/";
}



/* hedhy c bon */
/*
@RequestMapping("/AjouterProdArticleC")
public String AjouterArticleC(Model model) {
    model.addAttribute("NvModel",new NvModel());
    model.addAttribute("users",userService.findUsers());
    model.addAttribute("categories",categorieService.findAllCategories());
    model.addAttribute("magazins",magazinService.findAllMagazins());
    User user = userService.getLoggedUser();
    model.addAttribute("user",user);
    System.out.println("$$$ l utilisateur : "+user.getUsername());
    return "/insertArticleC";
}

@RequestMapping(value="/InsertProdArticleC", method= RequestMethod.POST)
public String insertProdArticleC(Model model, @ModelAttribute("NvModel") NvModel nvModel,@ModelAttribute("user")User user,@RequestParam(name="picture")MultipartFile photo) {
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
         
         
         if(!photo.isEmpty()) {
			 article.setPhoto(photo.getOriginalFilename());
		    	photo.transferTo(new File(imageDir+article.getCode()));
		    }
    	
         
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
        opProd.setTest(0);
        opProd.setDatePrise(date.toLocaleString());
        opProd.setQte(nvModel.getArticleConsomme().getQte());
        
	    opProduitRepository.save(opProd);
	    
	   
     
    }
    catch(Exception e) {
        e.printStackTrace();
    }
    return "redirect:/ListArticleC";
}
*/

/*
@RequestMapping(value="/getPhoto",produces=MediaType.IMAGE_JPEG_VALUE)
@ResponseBody
public byte[] getPhoto(int code) throws Exception{
	File f=new File(imageDir+code);
	return IOUtils.toByteArray(new FileInputStream(f));
}

*/

/*
@RequestMapping(value="/getPhotoUser",produces=MediaType.IMAGE_JPEG_VALUE)
@ResponseBody
public byte[] getPhotoUser(String username) throws Exception{
	File f1=new File(imageDir+username);
	return IOUtils.toByteArray(new FileInputStream(f1));
}
*/

/*
@RequestMapping("/")
public String getAllProduits(Model model) {
    User user = userService.getLoggedUser();
    model.addAttribute("categories",categorieService.findAllCategories());
    model.addAttribute("user",user);
    model.addAttribute("produits",produitService.findAllProduits());
    return "/index";
}
*/

/*
@RequestMapping("/profilAuth")
public String getProfil(Model model) {
    User user = userService.getLoggedUser();
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    String nom = loggedInUser.getName();
    model.addAttribute("username",nom);
    model.addAttribute("user",user);
    model.addAttribute("role",user.getRoles());
    System.out.println(user.getUsername());
    return "/profilAuth";
}
*/

/*
@RequestMapping("/DashboardAdmin")
public String getdashboardAdmin(Model model) {
	 User user = userService.getLoggedUser();
	    model.addAttribute("categories",categorieService.findAllCategories());
	    model.addAttribute("user",user);
	    model.addAttribute("produits",produitService.findAllProduits());
    return "/DashboardAdmin";
}
*/

/*
@RequestMapping("/ListArticleC")
public String getAllArticleC(Model model) {
    User user = userService.getLoggedUser();
    model.addAttribute("categories",categorieService.findAllCategories());
    model.addAttribute("user",user);
    model.addAttribute("produits",produitService.findAllProduits());
//    model.addAttribute("search",new Search());
    return "/ListArticleC";
}
*/


/*
@GetMapping("/ListUsers")
public String getAllUsers(Model model) {
////	return userService.findAllUsers();
	 model.addAttribute("userN",userService.findUsers());
	 return "/ListUsers";
}
*/

/*
@RequestMapping("/ListOpProd")
public String getAllOpProd(Model model) {
	 model.addAttribute("opProd",opProduitRepository.findAllOperationsProd());
	 return "/ListOperationsProduits";
}


@RequestMapping("/ListOpProdMateriel")
public String getOperationsUser(Model model) {
	
	List<ProduitResponse> op= opProduitRepository.findOperationsUser();
	int size=op.size();
	if(size==0) {
		size=0;
	}
		for(int i=0;i<size;i++) {
//			if(produitService.findAllProduits().get(i).getClass().getSimpleName()=="ArticleConsomme" ) {
			op.get(i).setIdList(i);
			model.addAttribute("produits",produitService.findAllProduits().get(i));
//		}	
			model.addAttribute("listOperatoionsUser",op);
			model.addAttribute("size",size);
	        
	System.out.println("$*$*$*$$*$ : "+produitService.findAllProduits().get(0).getClass().getName());
	}
	 return "/ListOperationsUser";
}

@RequestMapping("/DeleteOperations/{idList}/{idOp}")
public String deleteOperations(@PathVariable(name="idList") int idList,@PathVariable(name="idOp") int idOp){
	List<ProduitResponse> op= opProduitRepository.findOperationsUser();
	System.out.println("********** "+op.get(idList).getId());
	opProduitRepository.deleteById(op.get(idList).getId());
	operationService.deleteOperation(op.get(idList).getIdOp());

	return "redirect:/ListOpProdMateriel";
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
//	opProduitRepository.deactivateTestOperationq();
//	listop.removeAll(listop);
}
*/


//UPDATE USER
/*
@RequestMapping("/updateUser/{username}")
public String getUpdateUserForm(Model model,@PathVariable String username){
User user = userService.findById(username);
model.addAttribute("user",user);
return "/updateUser";
}

@RequestMapping(value="/updateUserIntoDB", method= RequestMethod.POST)
public String updateUserIntoDB(Model model, @ModelAttribute("user") User user,@RequestParam(name="picture")MultipartFile file)throws Exception {
try {
	User u = userService.findById(user.getUsername());
    u.setAge(user.getAge());
    u.setCin(user.getCin());
    u.setLogin(user.getLogin());
    u.setPrenom(user.getPrenom());
   
  if(!file.isEmpty()) {
    	u.setPhoto(file.getOriginalFilename());
       
    }
   
    userService.insertUser(u);
    if(!(file.isEmpty())) {
		 u.setPhoto(file.getOriginalFilename());
	    	file.transferTo(new File(imageDir+u.getUsername()));
	    	System.out.println("username "+u.getUsername());
	    	System.out.println("photo user "+u.getPhoto());
	    	System.out.println("Photo "+file);
	    }
   //userService.insertUser(u);
  
}
catch(Exception e) {
    e.printStackTrace();
}

return "redirect:/ListUsers";
}
*/

//UPDATE Materiel
/*
@RequestMapping("/updateMateriel/{code}")
public String getUpdateMaterielForm(Model model,@PathVariable int code){
Materiel m=materielService.findById(code);
model.addAttribute("categories",categorieService.findAllCategories());
model.addAttribute("magazins",magazinService.findAllMagazins());
model.addAttribute("materiel",m);
return "/updateMateriel";
}

@RequestMapping(value="/updateMaterielIntoDB", method= RequestMethod.POST)
public String updateMaterielIntoDB(Model model, @ModelAttribute("materiel") Materiel materiel) {
try {
	List<Categorie> c=categorieService.findAllCategories();
	List<Magazin> m=magazinService.findAllMagazins();
	Materiel mat = materielService.findById(materiel.getCode());
  mat.setIntitule(materiel.getIntitule());
  mat.setMatricule(materiel.getMatricule());
  mat.setMagazin(materiel.getMagazin());
  mat.setCategorie(materiel.getCategorie());
  mat.setDateRetour(materiel.getDateRetour().toString());
  produitRepository.save(mat);
}
catch(Exception e) {
  e.printStackTrace();
}

return "redirect:/";
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
public String updateArticleCIntoDB(Model model, @ModelAttribute("articleC") ArticleConsomme articleConsomme) {
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
}
catch(Exception e) {
e.printStackTrace();
}

return "redirect:/ListArticleC";
}

@RequestMapping("/deactivateTestMateriel/{code}")
public String deactivateTestMateriel(Model model,RedirectAttributes redirAttrs,@PathVariable int code) {
	materielRepository.deactivateTestMateriel(code);
	materielRepository.deactivateDateMateriel(code);
	Materiel mat=materielService.findById(code);
	mat.setDateRetour(null);
	redirAttrs.addFlashAttribute("succesDesactivate", "Retour materiel avec succes");
    return "redirect:/";
	
}
*/

}