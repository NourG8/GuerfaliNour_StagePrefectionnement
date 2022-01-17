package org.gestion.bp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.lowagie.text.DocumentException;

import org.apache.commons.io.IOUtils;
import org.gestion.bp.dao.CategorieRepository;
import org.gestion.bp.dao.MaterielRepository;
import org.gestion.bp.dao.OpProduitRepository;
import org.gestion.bp.dao.OperationRepository;
import org.gestion.bp.dao.ProduitRepository;
import org.gestion.bp.entities.Categorie;
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
public class CategorieController {

    @Autowired
    CategorieService categorieService;
    @Autowired
   CategorieRepository categorieRepository;
    
    
	 @GetMapping("/ListCategorie")
	 public String getAllCategories(Model model) {
	 	 model.addAttribute("categories",categorieService.findAllCategories());
	 	 return "/ListCategories";
	 }

	
	 //UPDATE USER
	 @RequestMapping("/updateCategorie/{nomCateg}")
	 public String getUpdateCatgorie(Model model,@PathVariable String nomCateg){
	 Categorie cat=categorieService.getCategorie(nomCateg);
	 model.addAttribute("categorie",cat);
	 return "/updateCategorie";
	 }

	 @RequestMapping(value="/updateCategIntoDB", method= RequestMethod.POST)
	 public String updateCategIntoDB(Model model, @ModelAttribute("categorie") Categorie cat){
	 try {
	 	Categorie c = categorieService.getCategorie(cat.getNomCateg());
	     c.setNomCateg(cat.getNomCateg());
	     c.setDescription(cat.getDescription());
	    
	  categorieRepository.save(c);
	 }
	 catch(Exception e) {
	     e.printStackTrace();
	 }

	 return "redirect:/ListCategorie";
	 }
	 
	 
	 @RequestMapping("/AjouterCategorie")
	 public String AjouterCategorie(Model model) {
	     model.addAttribute("categorie",new Categorie());
	     return "/insertCategorie";
	 }

	 @RequestMapping(value="/InsertCategorie", method= RequestMethod.POST)
	 public String insertProdArticleC(Model model, @ModelAttribute("categorie") Categorie categorie) {
	     try {
	    	 Categorie cat=new Categorie();
	 		
	     	cat.setNomCateg(categorie.getNomCateg());
	     	cat.setDescription(categorie.getDescription());
	     	categorieRepository.save(cat);
	      
	     }
	     catch(Exception e) {
	         e.printStackTrace();
	     }
	     return "redirect:/ListCategorie";
	 }
	 
	 
	 
	 @RequestMapping("/DeleteCategorie/{nomCateg}")
	 public String deleteCategorie(@PathVariable(name="nomCateg") String nomCateg){
		 Categorie c = categorieService.getCategorie(nomCateg);
		 categorieService.deleteCategory(c);
	 	return "redirect:/ListCategorie";
	    } 
	 
}