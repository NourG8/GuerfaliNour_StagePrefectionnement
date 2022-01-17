package org.gestion.bp.web;

//import static org.hamcrest.CoreMatchers.instanceOf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.lowagie.text.DocumentException;

import org.apache.commons.io.IOUtils;
import org.gestion.bp.dao.MaterielRepository;
import org.gestion.bp.dao.OpProduitRepository;
import org.gestion.bp.dao.OperationRepository;
import org.gestion.bp.dao.ProduitRepository;
import org.gestion.bp.dao.RoleRepository;
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
public class UserController {
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
	RoleRepository roleRepository;
	
	 @Autowired
	    private ServiceOpPDF service;
	 
	 @Value("${dir.images}")
	 private String imageDir;

	 
//Profil	 
	 
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
	 
	 

@RequestMapping("/DashboardAdmin")
public String getdashboardAdmin(Model model) {
	 User user = userService.getLoggedUser();
	    model.addAttribute("categories",categorieService.findAllCategories());
	    model.addAttribute("user",user);
	    model.addAttribute("produits",produitService.findAllProduits());
    return "/DashboardAdmin";
}




//Liste USER

@GetMapping("/ListUsers")
public String getAllUsers(Model model) {
////	return userService.findAllUsers();
	 model.addAttribute("userN",userService.findUsersV2());
	 model.addAttribute("roles",roleRepository.findRoles());
	 System.out.println(userService.findUsersV2().get(0).getUsername());
	 System.out.println(userService.findUsersV2().get(0).getRoles());
	 System.out.println(userService.findUsersV2().get(0).getRoles());
	 if(userService.findUsersV2().get(0).getRoles().equals("USER"))
		System.out.println("true");
	 else if(userService.findUsersV2().get(0).getRoles().equals("ADMIN"))
		 System.out.println("false");
	 return "/ListUsers";
}



@GetMapping("/ListAdmin")
public String getAllA(Model model) {
////	return userService.findAllUsers();
	 model.addAttribute("Admin",userService.findAdminV2());
	 System.out.println(userService.findUsersV2().get(0).getRoles().getClass());
	 
	if(userService.findUsersV2().get(1).getRoles().contains("ADMIN"))
	System.out.println("admin");
	else {
	System.out.println("false");}
		
	 
	 
	 return "/ListAdmin";
}


@RequestMapping("/AjouterOuvrier")
public String AjouterMaterial(Model model) {
    model.addAttribute("User",new User());
    model.addAttribute("roles",roleRepository.findRoles());
    return "/insertOuvrier";
}


@RequestMapping(value="/InsertOuvrier", method= RequestMethod.POST)
public String insertProdMateriel(Model model,RedirectAttributes redirAttrs, @ModelAttribute("User") User user, @ModelAttribute("roles") Role role) {
	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    try {
	    	
	    	User us = userService.getLoggedUser();
	    	System.out.println(us.getAge()+" "+us.getCin()+" "+us.getUsername());
	    	 model.addAttribute("us",us);
	    	
	        String encodedPassword = encoder.encode(user.getPassword());
	        user.setPassword(encodedPassword);
	        Role userRole= roleService.findByRoleType("USER");
	        System.out.println("////////////"+userRole);
//	        user.setRoles(null);
	        Set<Role> roles= new HashSet<Role>();
	        roles.add(userRole);

	        user.setRoles(roles);
	        userService.insertUser(user);
	        //model.addAttribute("user",user);
	        redirAttrs.addFlashAttribute("succes", "ouvrier ajouté avec succees");
	    }
	    catch(Exception e) {
	        e.printStackTrace();
	    }

	    
	    return "redirect:/ListUsers";
	}
	 



@RequestMapping("/AjouterAdmin")
public String AjouterAdmin(Model model) {
    model.addAttribute("Admin",new User());
    
  
    return "/insertAdmin";
}


@RequestMapping(value="/InsertAdmin", method= RequestMethod.POST)
public String insertadmin(Model model,RedirectAttributes redirAttrs, @ModelAttribute("Admin") User user) {
	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    try {
	    	
	    	User us = userService.getLoggedUser();
	    	System.out.println(us.getAge()+" "+us.getCin()+" "+us.getUsername());
	    	 model.addAttribute("us",us);
	    	
	        String encodedPassword = encoder.encode(user.getPassword());
	        user.setPassword(encodedPassword);
	        Role userRole= roleService.findByRoleType("ADMIN");
	        Role userRole1= roleService.findByRoleType("OpMat");
	        Role userRole2= roleService.findByRoleType("OpArt");
	        Role userRole3= roleService.findByRoleType("GArt");
	        Role userRole4= roleService.findByRoleType("GMat");
	        Role userRole5= roleService.findByRoleType("GUser");
	        System.out.println("////////////"+userRole);
//	        user.setRoles(null);
	        Set<Role> roles= new HashSet<Role>();
	        roles.add(userRole);
	        roles.add(userRole1);
	        roles.add(userRole2);
	        roles.add(userRole3);
	        roles.add(userRole4);
	        roles.add(userRole5);

	        user.setRoles(roles);
	        userService.insertUser(user);
	        model.addAttribute("admin",user);
	        redirAttrs.addFlashAttribute("succesA", "Ajout admins avec succes!");
	    }
	    catch(Exception e) {
	        e.printStackTrace();
	    }
	    return "redirect:/ListAdmin";
	}





@RequestMapping("/RoleUser/{username}")
public String AffRoleUserForm(Model model,@PathVariable String username){
User user = userService.findById(username);
model.addAttribute("USER",user);
model.addAttribute("roles",roleRepository.findRoles());
return "/RoleUser";
}

@RequestMapping(value="/RoleUserIntoDB", method= RequestMethod.POST)
public String AffRoleUserIntoDB(Model model, @ModelAttribute("USER") User user, @ModelAttribute("roles") Role role) {
try {
	User u = userService.findById(user.getUsername());
	System.out.println("$$$****----- : "+u.getUsername());
	System.out.println("$$$****----- : "+user.getUsername());
    u.setRoles(user.getRoles());
    userService.insertUser(u);
}
catch(Exception e) {
    e.printStackTrace();
}

return "redirect:/ListUsers";
}


	 
//UPDATE USER
@RequestMapping("/updateUser/{username}")
public String getUpdateUserForm(Model model,@PathVariable String username){
User user = userService.findById(username);
model.addAttribute("user",user);
model.addAttribute("roles",roleRepository.findRoles());
return "/updateUser";
}

@RequestMapping(value="/updateUserIntoDB", method= RequestMethod.POST)
public String updateUserIntoDB(Model model,RedirectAttributes redirAttrs, @ModelAttribute("user") User user, @ModelAttribute("roles") Role role) {
try {
	User u = userService.findById(user.getUsername());
    u.setAge(user.getAge());
    u.setCin(user.getCin());
    u.setLogin(user.getLogin());
    u.setPrenom(user.getPrenom());
    //u.setRoles(user.getRoles());
   
  /*if(!file.isEmpty()) {
    	u.setPhoto(file.getOriginalFilename());
       
    }*/
    userService.insertUser(u);
    redirAttrs.addFlashAttribute("succesM", "Modification avec succees");
   /* if(!(file.isEmpty())) {
		 u.setPhoto(file.getOriginalFilename());
	    	file.transferTo(new File(imageDir+u.getUsername()));
	    	System.out.println("username "+u.getUsername());
	    	System.out.println("photo user "+u.getPhoto());
	    	System.out.println("Photo "+file);
	    }*/
   /*userService.insertUser(u);*/
  
}
catch(Exception e) {
    e.printStackTrace();
}

return "redirect:/ListUsers";
}




//UPDATE USER
@RequestMapping("/updateAdmin/{username}")
public String getUpdateAdminForm(Model model,@PathVariable String username){
User user = userService.findById(username);
model.addAttribute("admin",user);
model.addAttribute("roles",roleRepository.findRoles());
return "/updateAdmin";
}

@RequestMapping(value="/updateAdminIntoDB", method= RequestMethod.POST)
public String updateAdminIntoDB(Model model, RedirectAttributes redirAttrs,@ModelAttribute("admin") User user, @ModelAttribute("roles") Role role) {
try {
	User u = userService.findById(user.getUsername());
  u.setAge(user.getAge());
  u.setCin(user.getCin());
  u.setLogin(user.getLogin());
  u.setPrenom(user.getPrenom());
  u.setRoles(user.getRoles());
 System.out.println("***********"+u.getRoles());

  userService.insertUser(u);

  redirAttrs.addFlashAttribute("succesM", "Modification avec succees");
}
catch(Exception e) {
  e.printStackTrace();
}

return "redirect:/ListAdmin";
}





//UPDATE USER
@RequestMapping("/updateUserAuth/{username}")
public String getUpdateUserFormAuth(Model model,@PathVariable String username){
User user = userService.findById(username);
model.addAttribute("User",user);
return "/updateUserAuth";
}


@RequestMapping(value="/updateUserIntoDBAuth", method= RequestMethod.POST)
public String updateUserIntoDBAuth(Model model, @ModelAttribute("User") User user) {
try {
	User u = userService.findById(user.getUsername());
    u.setAge(user.getAge());
    u.setCin(user.getCin());
    u.setLogin(user.getLogin());
    u.setPrenom(user.getPrenom());
   
  /*if(!file.isEmpty()) {
    	u.setPhoto(file.getOriginalFilename());
       
    }*/
    userService.insertUser(u);
   /* if(!(file.isEmpty())) {
		 u.setPhoto(file.getOriginalFilename());
	    	file.transferTo(new File(imageDir+u.getUsername()));
	    	System.out.println("username "+u.getUsername());
	    	System.out.println("photo user "+u.getPhoto());
	    	System.out.println("Photo "+file);
	    }*/
   /*userService.insertUser(u);*/
  
}
catch(Exception e) {
    e.printStackTrace();
}

return "redirect:/profilAuth";
}



//Photo user
@RequestMapping(value="/getPhotoUser",produces=MediaType.IMAGE_JPEG_VALUE)
@ResponseBody
public byte[] getPhotoUser(String username) throws Exception{
	File f1=new File(imageDir+username);
	return IOUtils.toByteArray(new FileInputStream(f1));
}



//Delete user
@RequestMapping("/DeleteUser/{username}")
public String deleteUser(@PathVariable(name="username") String username){
	User user= userService.findById(username);
	user.setRoles(null);
	System.out.println("********** "+"username de l'utilisateur que vous avez supprimer : "+username );
	userService.deleteUser(user);

	return "redirect:/ListUsers";
   } 


}