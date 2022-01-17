package org.gestion.bp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.gestion.bp.dao.CategorieRepository;
import org.gestion.bp.dao.MagazinRepository;
import org.gestion.bp.dao.OpProduitRepository;
import org.gestion.bp.dao.ProduitRepository;
import org.gestion.bp.entities.ArticleConsomme;
import org.gestion.bp.entities.Categorie;
import org.gestion.bp.entities.Magazin;
import org.gestion.bp.entities.Materiel;
import org.gestion.bp.entities.Operation;
import org.gestion.bp.entities.OperationProduit;
import org.gestion.bp.entities.Produit;
import org.gestion.bp.entities.Role;
import org.gestion.bp.entities.User;
import org.gestion.bp.service.OperationService;
import org.gestion.bp.service.ProduitService;
import org.gestion.bp.service.RoleService;
import org.gestion.bp.service.ServiceIncrement;
import org.gestion.bp.service.UserService;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProduitService produitService;
	
	@Autowired
	OpProduitRepository opProduitRepository;
	
	@Autowired
	CategorieRepository categorieRepository;
	
	@Autowired
	OperationService operationService;
	
	@Autowired
	ProduitRepository produitRepository;
	
	@Autowired
	MagazinRepository magazinRepository;
	
	@Autowired
	ServiceIncrement serviceIncrement;
	
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder encoder; 


		encoder = new BCryptPasswordEncoder();
		
		

		Role r1 = roleService.newRole(new Role("GArt",3));
		Role r2 = roleService.newRole(new Role("GMat",4));
		Role r3 = roleService.newRole(new Role("GUser",5));
		Role r4 = roleService.newRole(new Role("OpArt",6));
		Role r5 = roleService.newRole(new Role("OpMat",7));
		Role r6 = roleService.newRole(new Role("USER",2));
		
		
		User u1= new User();
		u1.setLogin("Aya@gmail.com");
		u1.setUsername("Aya");
		u1.setCin(12345678);
		u1.setPrenom("Guerfali");
		u1.setAge(32);
		u1.setCin(11251145);
		encoder = new BCryptPasswordEncoder();
		u1.setPassword(encoder.encode("1234"));
		u1.getRoles().add(r6);
		userService.insertUser(u1);
		
		User u2= new User();
		u2.setLogin("hanin@gmail.com");
		u2.setUsername("Hanin");
		u2.setCin(12321452);
		u2.setPrenom("Ben jemaa");
		u2.setAge(25);
		u2.setCin(11326545);
		encoder = new BCryptPasswordEncoder();
		u2.setPassword(encoder.encode("1234"));
		u2.getRoles().add(r6);
		userService.insertUser(u2);
		
		User u3= new User();
		u3.setLogin("adlen@gmail.com");
		u3.setUsername("Adlen");
		u3.setCin(12345678);
		u3.setPrenom("Srairi");
		u3.setAge(24);
		u3.setCin(11326545);
		encoder = new BCryptPasswordEncoder();
		u3.setPassword(encoder.encode("1234"));
		u3.getRoles().add(r6);
		userService.insertUser(u3);
		
		User u4= new User();
		u4.setLogin("lina@gmail.com");
		u4.setUsername("Lina");
		u4.setCin(12345678);
		u4.setPrenom("Toumi");
		u4.setAge(36);
		u4.setCin(11547820);
		encoder = new BCryptPasswordEncoder();
		u4.setPassword(encoder.encode("1234"));
		u4.getRoles().add(r6);
		userService.insertUser(u4);
		
		User u5= new User();
		u5.setLogin("Maryem@gmail.com");
		u5.setUsername("Maryem");
		u5.setCin(12345678);
		u5.setPrenom("Mokded");
		u5.setAge(36);
		u5.setCin(11050402);
		encoder = new BCryptPasswordEncoder();
		u5.setPassword(encoder.encode("1234"));
		u5.getRoles().add(r6);
		userService.insertUser(u5);
		
		
		User u6= new User();
		u6.setLogin("Dorra@gmail.com");
		u6.setUsername("Dorra");
		u6.setCin(12345678);
		u6.setPrenom("Ayari");
		u6.setAge(36);
		u6.setCin(01251456);
		encoder = new BCryptPasswordEncoder();
		u6.setPassword(encoder.encode("1234"));
		u6.getRoles().add(r6);
		userService.insertUser(u6);
		
		User u7= new User();
		u7.setLogin("Karim@gmail.com");
		u7.setUsername("Karim");
		u7.setCin(12345678);
		u7.setPrenom("Joudi");
		u7.setAge(36);
		u7.setCin(01251420);
		encoder = new BCryptPasswordEncoder();
		u7.setPassword(encoder.encode("1234"));
		u7.getRoles().add(r6);
		userService.insertUser(u7);
		
		User u13= new User();
		u13.setLogin("Yassmine@gmail.com");
		u13.setUsername("Yassmine");
		u13.setCin(12345678);
		u13.setPrenom("khaled");
		u13.setAge(36);
		u13.setCin(01211520);
		encoder = new BCryptPasswordEncoder();
		u13.setPassword(encoder.encode("1234"));
		u13.getRoles().add(r6);
		userService.insertUser(u13);
		
		
		User u8= new User();
		u8.setLogin("Hend@gmail.com");
		u8.setUsername("Hend");
		u8.setCin(12345678);
		u8.setPrenom("Amri");
		u8.setAge(36);
		u8.setCin(11344478);
		encoder = new BCryptPasswordEncoder();
		u8.setPassword(encoder.encode("1234"));
		u8.getRoles().add(r6);
		userService.insertUser(u8);
		
		
		User u9= new User();
		u9.setLogin("MaryemNajjar@gmail.com");
		u9.setUsername("MaryemNj");
		u9.setCin(12345678);
		u9.setPrenom("Najjar");
		u9.setAge(36);
		u9.setCin(01251420);
		encoder = new BCryptPasswordEncoder();
		u9.setPassword(encoder.encode("1234"));
		u9.getRoles().add(r6);
		userService.insertUser(u9);
		
		
		User u10= new User();
		u10.setLogin("Wafik@gmail.com");
		u10.setUsername("Wadik");
		u10.setCin(12345678);
		u10.setPrenom("Lahbib");
		u10.setAge(36);
		u10.setCin(01251420);
		encoder = new BCryptPasswordEncoder();
		u10.setPassword(encoder.encode("1234"));
		u10.getRoles().add(r6);
		userService.insertUser(u10);
		
		User u11= new User();
		u11.setLogin("Rahma@gmail.com");
		u11.setUsername("Rahma");
		u11.setCin(12345678);
		u11.setPrenom("Ben othmane");
		u11.setAge(36);
		u11.setCin(01251420);
		encoder = new BCryptPasswordEncoder();
		u11.setPassword(encoder.encode("1234"));
		u11.getRoles().add(r6);
		userService.insertUser(u11);
		
		
		User u12= new User();
		u12.setLogin("Nour@gmail.com");
		u12.setUsername("NourMb");
		u12.setCin(12345678);
		u12.setPrenom("Meddeb");
		u12.setAge(36);
		u12.setCin(01251420);
		encoder = new BCryptPasswordEncoder();
		u12.setPassword(encoder.encode("1234"));
		u12.getRoles().add(r6);
		userService.insertUser(u12);
		
		User AdminUser = new User();
		AdminUser.setLogin("Ahmed@gmail.com");
		AdminUser.setUsername("Ahmed");
		AdminUser.setCin(12345678);
		AdminUser.setPrenom("Ben saber");
		AdminUser.setAge(36);
		AdminUser.setCin(11326545);
		encoder = new BCryptPasswordEncoder();
		AdminUser.setPassword(encoder.encode("123456789"));
		Role adminRole = roleService.newRole(new Role("ADMIN", 1));
		AdminUser.getRoles().add(adminRole);
		AdminUser.getRoles().add(r1);
		AdminUser.getRoles().add(r2);
		AdminUser.getRoles().add(r3);
		AdminUser.getRoles().add(r4);
		AdminUser.getRoles().add(r5);
		
		userService.insertUser(AdminUser);
		
		System.out.println("password encodé : "+AdminUser.getPassword());
		System.out.println("decoder le password : "+encoder.encode(AdminUser.getPassword()));
		User RespArticle = new User();
		RespArticle.setLogin("nour@gmail.com");
		RespArticle.setUsername("Nour");
		RespArticle.setPrenom("Guerfali");
		RespArticle.setAge(28);
		RespArticle.setCin(11125480);
		encoder = new BCryptPasswordEncoder();
		RespArticle.setPassword(encoder.encode("123456789"));
		Role userRole = roleService.newRole(new Role("USER",2));
		RespArticle.getRoles().add(userRole);
		RespArticle.getRoles().add(r1);
		RespArticle.getRoles().add(r3);
		RespArticle.getRoles().add(r4);
		userService.insertUser(RespArticle);	
		
		
		
		
		User RespMateriel = new User();
		RespMateriel.setLogin("ilhem@gmail.com");
		RespMateriel.setUsername("Ilhem");
		RespMateriel.setPrenom("Ben salha");
		RespMateriel.setAge(32);
		RespMateriel.setCin(11322335);
		encoder = new BCryptPasswordEncoder();
		RespMateriel.setPassword(encoder.encode("123456789"));
		Role userRole1 = roleService.newRole(new Role("USER",2));
		RespMateriel.getRoles().add(userRole1);
		RespMateriel.getRoles().add(r2);
		RespMateriel.getRoles().add(r3);
		RespMateriel.getRoles().add(r5);
		userService.insertUser(RespMateriel);	
		
		Magazin m=new Magazin();
		m.setNomMagazin("magazin n°1");
		magazinRepository.save(m);
		
		Magazin m2=new Magazin();
		m2.setNomMagazin("magazin n°2");
		magazinRepository.save(m2);
		
		Categorie c=new Categorie();
		c.setDescription("categorie ndes ciments");
		c.setNomCateg("Ciment");
		categorieRepository.save(c);
		
		Categorie c2=new Categorie();
		c2.setDescription("categorie des graviers et sables");
		c2.setNomCateg("gravier et sable");
		categorieRepository.save(c2);
		
		Categorie c3=new Categorie();
		c3.setDescription("categorie des fers");
		c3.setNomCateg("Fer");
		categorieRepository.save(c3);
		
		
		Categorie c4=new Categorie();
		c4.setDescription("categorie des mastiques");
		c4.setNomCateg("Mastique");
		categorieRepository.save(c4);
		
		Categorie c5=new Categorie();
		c5.setDescription("categorie des silicones");
		c5.setNomCateg("Silicone");
		categorieRepository.save(c5);
		
		
		Categorie c6=new Categorie();
		c6.setDescription("categorie des Materiaux du jardin ");
		c6.setNomCateg("Materiel jardin ");
		categorieRepository.save(c6);
		
		
		Categorie c7=new Categorie();
		c7.setDescription("categorie des materiux du chantier");
		c7.setNomCateg("Materiel chantier");
		categorieRepository.save(c7);
		
		
		
		
		ArticleConsomme p1=new ArticleConsomme();
		p1.setCategorie(c);
		p1.setQte(60);
		p1.setIntitule("Ciment blanc ");
		p1.setMagazin(m);
		p1.setMatricule("15487987");
		
		ArticleConsomme p2=new ArticleConsomme();
		p2.setCategorie(c);
		p2.setQte(60);
		p2.setIntitule("Ciment noir");
		p2.setMagazin(m);
		p2.setMatricule("159995554");
		
		ArticleConsomme p3=new ArticleConsomme();
		p3.setCategorie(c2);
		p3.setQte(60);
		p3.setIntitule("Sable");
		p3.setMagazin(m);
		p3.setMatricule("199854487");
		
		ArticleConsomme p4=new ArticleConsomme();
		p4.setCategorie(c2);
		p4.setQte(60);
		p4.setIntitule("Gravier Blanc");
		p4.setMagazin(m);
		p4.setMatricule("002145987");
		
		ArticleConsomme p5=new ArticleConsomme();
		p5.setCategorie(c2);
		p5.setQte(60);
		p5.setIntitule("Gravier Noir");
		p5.setMagazin(m);
		p5.setMatricule("002145987");
		
		ArticleConsomme p6=new ArticleConsomme();
		p6.setCategorie(c3);
		p6.setQte(60);
		p6.setIntitule("Barre d'acier");
		p6.setMagazin(m);
		p6.setMatricule("002145987");
		
		ArticleConsomme p7=new ArticleConsomme();
		p7.setCategorie(c3);
		p7.setQte(60);
		p7.setIntitule("Fer abeton diametre 12");
		p7.setMagazin(m);
		p7.setMatricule("055542987");
		
		ArticleConsomme p8=new ArticleConsomme();
		p8.setCategorie(c3);
		p8.setQte(60);
		p8.setIntitule("Fer abeton diametre 20");
		p8.setMagazin(m);
		p8.setMatricule("0122546987");
		
		ArticleConsomme p9=new ArticleConsomme();
		p9.setCategorie(c3);
		p9.setQte(60);
		p9.setIntitule("Fil de fer");
		p9.setMagazin(m);
		p9.setMatricule("5403265087");
		
		ArticleConsomme p10=new ArticleConsomme();
		p10.setCategorie(c4);
		p10.setQte(60);
		p10.setIntitule("Mastique silicone");
		p10.setMagazin(m);
		p10.setMatricule("002145987");
		   
		
		ArticleConsomme p11=new ArticleConsomme();
		p11.setCategorie(c4);
		p11.setQte(60);
		p11.setIntitule("Mastique acrylique");
		p11.setMagazin(m);
		p11.setMatricule("00Ak55858120");
		   
		

		ArticleConsomme p12=new ArticleConsomme();
		p12.setCategorie(c4);
		p12.setQte(60);
		p12.setIntitule("Colle mastic");
		p12.setMagazin(m);
		p12.setMatricule("00Ak578auy45");
		
		ArticleConsomme p13=new ArticleConsomme();
		p12.setCategorie(c5);
		p12.setQte(60);
		p12.setIntitule("Silicone");
		p12.setMagazin(m);
		p12.setMatricule("12ar788d2q4");
		
		
		Materiel prod1=new Materiel();
		prod1.setCategorie(c6);
        prod1.setIntitule("Debroussailleuse");
        prod1.setMagazin(m2);
        prod1.setTest(0);
        prod1.setMatricule("123j456b78");
        
        Materiel prod2=new Materiel();
		prod2.setCategorie(c6);
        prod2.setIntitule("Tondeuse a gazon");
        prod2.setMagazin(m2);
        prod2.setTest(0);
        prod2.setMatricule("54y6897n12");
        
        Materiel prod3=new Materiel();
		prod3.setCategorie(c6);
        prod3.setIntitule("Taille haies");
        prod3.setMagazin(m2);
        prod3.setTest(0);
        prod3.setMatricule("998599aa44");
        
        
        Materiel prod4=new Materiel();
		prod4.setCategorie(c6);
        prod4.setIntitule("Rateau");
        prod4.setMagazin(m2);
        prod4.setTest(0);
        prod4.setMatricule("225645558");
        
        Materiel prod5=new Materiel();
		prod5.setCategorie(c7);
        prod5.setIntitule("Pelle");
        prod5.setMagazin(m2);
        prod5.setTest(0);
        prod5.setMatricule("666689712");
        
        Materiel prod6=new Materiel();
		prod6.setCategorie(c7);
        prod6.setIntitule("Palette");
        prod6.setMagazin(m2);
        prod6.setTest(0);
        prod6.setMatricule("666689712");
        
        Materiel prod7=new Materiel();
		prod7.setCategorie(c7);
        prod7.setIntitule("Rouleau");
        prod7.setMagazin(m2);
        prod7.setTest(0);
        prod7.setMatricule("666689712");
        
        Materiel prod8=new Materiel();
		prod8.setCategorie(c7);
        prod8.setIntitule("Pioche");
        prod8.setMagazin(m2);
        prod8.setTest(0);
        prod8.setMatricule("996c6887a12");
        
        Materiel prod9=new Materiel();
		prod9.setCategorie(c7);
        prod9.setIntitule("Brouette");
        prod9.setMagazin(m2);
        prod9.setTest(0);
        prod9.setMatricule("126689712");
        
        Materiel prod10=new Materiel();
		prod10.setCategorie(c7);
        prod10.setIntitule("Coffre a outils");
        prod10.setMagazin(m2);
        prod10.setTest(0);
        prod10.setMatricule("66ad71ds52");
        
        
        Materiel prod11=new Materiel();
		prod11.setCategorie(c7);
        prod11.setIntitule("Brouette");
        prod11.setMagazin(m2);
        prod11.setTest(0);
        prod11.setMatricule("8266ay89a712");
        
        
        Materiel prod12=new Materiel();
        prod12.setCategorie(c7);
        prod12.setIntitule("Perceuse");
        prod12.setMagazin(m2);
        prod12.setTest(0);
        prod12.setMatricule("106z68ez982");
        
        Materiel prod13=new Materiel();
        prod13.setCategorie(c7);
        prod13.setIntitule("Outil de mesure");
        prod13.setMagazin(m2);
        prod13.setTest(0);
        prod13.setMatricule("4645a52er72");
        
        	Materiel prod14=new Materiel();
        	prod14.setCategorie(c7);
        	prod14.setIntitule("Outil de rampe");
        	prod14.setMagazin(m2);
        	prod14.setTest(0);
        	prod14.setMatricule("016z689a712");
             
             Materiel prod15=new Materiel();
             prod15.setCategorie(c7);
             prod15.setIntitule("Meuleuse");
             prod15.setMagazin(m2);
             prod15.setTest(0);
             prod15.setMatricule("54a000p7d5d");
             
             Materiel prod16=new Materiel();
             prod16.setCategorie(c7);
             prod16.setIntitule("Malaxeuse");
             prod16.setMagazin(m2);
             prod16.setTest(0);
             prod16.setMatricule("55hs7t20650");
             
             
             Materiel prod17=new Materiel();
             prod17.setCategorie(c7);
             prod17.setIntitule("Marteau piqueur");
             prod17.setMagazin(m2);
             prod17.setTest(0);
             prod17.setMatricule("j50afo95l20");
        
        produitRepository.save(prod1);
        produitRepository.save(prod2);
        produitRepository.save(prod3);
        produitRepository.save(prod4);
        produitRepository.save(prod5);
        produitRepository.save(prod6);
        produitRepository.save(prod7);
        produitRepository.save(prod8);
        produitRepository.save(prod9);
        produitRepository.save(prod10);
        produitRepository.save(prod11);
        produitRepository.save(prod12);
        produitRepository.save(prod13);
        produitRepository.save(prod14);
        produitRepository.save(prod15);
        produitRepository.save(prod16);
        produitRepository.save(prod17);
        produitRepository.save(prod9);
        produitRepository.save(prod9);
        produitRepository.save(prod9);
       
        
        produitRepository.save(p1);
        produitRepository.save(p2);
        produitRepository.save(p3);
        produitRepository.save(p4);
        produitRepository.save(p5);
        produitRepository.save(p6);
        produitRepository.save(p7);
        produitRepository.save(p8);
        produitRepository.save(p9);
        produitRepository.save(p10);
        produitRepository.save(p11);
        produitRepository.save(p12);
        produitRepository.save(p13);
 
        
        OperationProduit opProd=new OperationProduit();
        Set<OperationProduit> opProds= new HashSet<OperationProduit>();
        opProds.add(opProd);
        p1.setOperationProduits(opProds);
        produitService.insertProduit(p1);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	    Date date = new Date();
   	
	    Operation op2=new Operation();
	    op2.setNatureOp("insertion");
	   
	    op2.setNomOp("operation num°"+serviceIncrement.increment());
	    op2.setDateOP(date.toLocaleString());
	    op2.setOperationProduits(opProds);
	    operationService.insertOperation(op2);
	    
	   opProd.setOperation(op2);
       opProd.setProduit(p1);
       opProd.setQte(p1.getQte());
       opProd.setTest(1);
       opProd.setDatePrise(date.toLocaleString());
   
		   opProduitRepository.save(opProd);

		   
		   
	        OperationProduit opProd1=new OperationProduit();
	        Set<OperationProduit> opProds1= new HashSet<OperationProduit>();
	        opProds1.add(opProd1);
	        p2.setOperationProduits(opProds1);
	        produitService.insertProduit(p2);
	   	
		    Operation op3=new Operation();
		    op3.setNatureOp("insertion");
		   
		    op3.setNomOp("operation num°"+serviceIncrement.increment());
		    op3.setDateOP(date.toLocaleString());
		    op3.setOperationProduits(opProds1);
		    operationService.insertOperation(op3);
		    
		   opProd1.setOperation(op3);
	       opProd1.setProduit(p2);
	       opProd1.setQte(p2.getQte());
	       opProd1.setTest(1);
	       opProd1.setDatePrise(date.toLocaleString());
	   
			   opProduitRepository.save(opProd1);
			   
			   
		        OperationProduit opProd2=new OperationProduit();
		        Set<OperationProduit> opProds2= new HashSet<OperationProduit>();
		        opProds2.add(opProd2);
		        p3.setOperationProduits(opProds2);
		        produitService.insertProduit(p3);

			    Operation op4=new Operation();
			    op4.setNatureOp("insertion");
			    op4.setNomResp("ilhem");
			   
			    op4.setNomOp("operation num°"+serviceIncrement.increment());
			    op4.setDateOP(date.toLocaleString());
			    op4.setOperationProduits(opProds2);
			    operationService.insertOperation(op4);
			    
			   opProd2.setOperation(op4);
		       opProd2.setProduit(p3);
		       opProd2.setQte(p3.getQte());
		       opProd2.setTest(1);
		       opProd2.setDatePrise(date.toLocaleString());
		   
				   opProduitRepository.save(opProd2);
		   
				   
				   
				   
				   
			        OperationProduit opProd5=new OperationProduit();
			        Set<OperationProduit> opProds5= new HashSet<OperationProduit>();
			        opProds5.add(opProd5);
			        p4.setOperationProduits(opProds5);
			        produitService.insertProduit(p4);

				    Operation op5=new Operation();
				    op5.setNatureOp("insertion");
				    op5.setNomResp("ilhem");
				   
				    op5.setNomOp("operation num°"+serviceIncrement.increment());
				    op5.setDateOP(date.toLocaleString());
				    op5.setOperationProduits(opProds5);
				    operationService.insertOperation(op5);
				    
				   opProd5.setOperation(op5);
			       opProd5.setProduit(p4);
			       opProd5.setQte(p4.getQte());
			       opProd5.setTest(1);
			       opProd5.setDatePrise(date.toLocaleString());
			   
					   opProduitRepository.save(opProd5);
					   
					   
					   
					   
				        OperationProduit opProd6=new OperationProduit();
				        Set<OperationProduit> opProds6= new HashSet<OperationProduit>();
				        opProds6.add(opProd6);
				        prod1.setOperationProduits(opProds6);
				        produitService.insertProduit(prod1);

					    Operation op6=new Operation();
					    op6.setNatureOp("insertion");
					   
					    op6.setNomOp("operation num°"+serviceIncrement.increment());
					    op6.setDateOP(date.toLocaleString());
					    op6.setOperationProduits(opProds6);
					    operationService.insertOperation(op6);
					    
					   opProd6.setOperation(op6);
				       opProd6.setProduit(prod1);
				       opProd6.setDateRetour(prod1.getDateRetour());
				       opProd6.setTest(1);
				       opProd6.setDatePrise(date.toLocaleString());
				   
						   opProduitRepository.save(opProd6);
				   
				   
						   
						   
					        OperationProduit opProd7=new OperationProduit();
					        Set<OperationProduit> opProds7= new HashSet<OperationProduit>();
					        opProds7.add(opProd7);
					        prod2.setOperationProduits(opProds7);
					        produitService.insertProduit(prod2);

						    Operation op7=new Operation();
						    op7.setNatureOp("insertion");
						   
						    op7.setNomOp("operation num°"+serviceIncrement.increment());
						    op7.setDateOP(date.toLocaleString());
						    op7.setOperationProduits(opProds7);
						    operationService.insertOperation(op7);
						    
						   opProd7.setOperation(op7);
					       opProd7.setProduit(prod2);
					       opProd7.setDateRetour(prod2.getDateRetour());
					       opProd7.setTest(1);
					       opProd7.setDatePrise(date.toLocaleString());
					   
							   opProduitRepository.save(opProd7);
						   
							   
							   
							   
						        OperationProduit opProd8=new OperationProduit();
						        Set<OperationProduit> opProds8= new HashSet<OperationProduit>();
						        opProds8.add(opProd8);
						        prod3.setOperationProduits(opProds8);
						        produitService.insertProduit(prod3);

							    Operation op8=new Operation();
							    op8.setNatureOp("insertion");
							   
							    op8.setNomOp("operation num°"+serviceIncrement.increment());
							    op8.setDateOP(date.toLocaleString());
							    op8.setOperationProduits(opProds8);
							    operationService.insertOperation(op8);
							    
							   opProd8.setOperation(op8);
						       opProd8.setProduit(prod3);
						       opProd8.setDateRetour(prod3.getDateRetour());
						       opProd8.setTest(1);
						       opProd8.setDatePrise(date.toLocaleString());
						   
								   opProduitRepository.save(opProd8);
							   
								   
								   

							        OperationProduit opProd9=new OperationProduit();
							        Set<OperationProduit> opProds9= new HashSet<OperationProduit>();
							        opProds9.add(opProd9);
							        prod4.setOperationProduits(opProds8);
							        produitService.insertProduit(prod4);

								    Operation op9=new Operation();
								    op9.setNatureOp("insertion");
								   
								    op9.setNomOp("operation num°"+serviceIncrement.increment());
								    op9.setDateOP(date.toLocaleString());
								    op9.setOperationProduits(opProds9);
								    operationService.insertOperation(op9);
								    
								   opProd9.setOperation(op9);
							       opProd9.setProduit(prod4);
							       opProd9.setDateRetour(prod4.getDateRetour());
							       opProd9.setTest(1);
							       opProd9.setDatePrise(date.toLocaleString());
							   
									   opProduitRepository.save(opProd9);
									   
									   
									   
									   
								        OperationProduit opProd10=new OperationProduit();
								        Set<OperationProduit> opProds10= new HashSet<OperationProduit>();
								        opProds10.add(opProd10);
								        prod5.setOperationProduits(opProds10);
								        produitService.insertProduit(prod5);

									    Operation op10=new Operation();
									    op10.setNatureOp("insertion");
									   
									    op10.setNomOp("operation num°"+serviceIncrement.increment());
									    op10.setDateOP(date.toLocaleString());
									    op10.setOperationProduits(opProds10);
									    operationService.insertOperation(op10);
									    
									   opProd10.setOperation(op10);
								       opProd10.setProduit(prod5);
								       opProd10.setDateRetour(prod5.getDateRetour());
								       opProd10.setTest(1);
								       opProd10.setDatePrise(date.toLocaleString());
								   
										   opProduitRepository.save(opProd10);
								   
							   
										   
										   
										   OperationProduit opProd11=new OperationProduit();
									        Set<OperationProduit> opProds11= new HashSet<OperationProduit>();
									        opProds11.add(opProd11);
									        prod6.setOperationProduits(opProds11);
									        produitService.insertProduit(prod6);

										    Operation op11=new Operation();
										    op11.setNatureOp("insertion");
										   
										    op11.setNomOp("operation num°"+serviceIncrement.increment());
										    op11.setDateOP(date.toLocaleString());
										    op11.setOperationProduits(opProds11);
										    operationService.insertOperation(op11);
										    
										   opProd11.setOperation(op11);
									       opProd11.setProduit(prod6);
									       opProd11.setDateRetour(prod6.getDateRetour());
									       opProd11.setTest(1);
									       opProd11.setDatePrise(date.toLocaleString());
									   
											   opProduitRepository.save(opProd11);
						   
						   
											   
											   OperationProduit opProd12=new OperationProduit();
										        Set<OperationProduit> opProds12= new HashSet<OperationProduit>();
										        opProds12.add(opProd12);
										        prod7.setOperationProduits(opProds12);
										        produitService.insertProduit(prod7);

											    Operation op12=new Operation();
											    op12.setNatureOp("insertion");
											   
											    op12.setNomOp("operation num°"+serviceIncrement.increment());
											    op12.setDateOP(date.toLocaleString());
											    op12.setOperationProduits(opProds12);
											    operationService.insertOperation(op12);
											    
											   opProd12.setOperation(op12);
										       opProd12.setProduit(prod7);
										       opProd12.setDateRetour(prod7.getDateRetour());
										       opProd12.setTest(1);
										       opProd12.setDatePrise(date.toLocaleString());
										   
												   opProduitRepository.save(opProd12);
												   
												   
												   
												   OperationProduit opProd13=new OperationProduit();
											        Set<OperationProduit> opProds13= new HashSet<OperationProduit>();
											        opProds13.add(opProd13);
											        prod8.setOperationProduits(opProds13);
											        produitService.insertProduit(prod8);

												    Operation op13=new Operation();
												    op13.setNatureOp("insertion");
												   
												    op13.setNomOp("operation num°"+serviceIncrement.increment());
												    op13.setDateOP(date.toLocaleString());
												    op13.setOperationProduits(opProds13);
												    operationService.insertOperation(op13);
												    
												   opProd13.setOperation(op13);
											       opProd13.setProduit(prod8);
											       opProd13.setDateRetour(prod8.getDateRetour());
											       opProd13.setTest(1);
											       opProd13.setDatePrise(date.toLocaleString());
											   
													   opProduitRepository.save(opProd13);
													   
													   
													   
													   
													   OperationProduit opProd14=new OperationProduit();
												        Set<OperationProduit> opProds14= new HashSet<OperationProduit>();
												        opProds14.add(opProd14);
												        prod9.setOperationProduits(opProds14);
												        produitService.insertProduit(prod9);

													    Operation op14=new Operation();
													    op14.setNatureOp("insertion");
													   
													    op14.setNomOp("operation num°"+serviceIncrement.increment());
													    op14.setDateOP(date.toLocaleString());
													    op14.setOperationProduits(opProds14);
													    operationService.insertOperation(op14);
													    
													   opProd14.setOperation(op14);
												       opProd14.setProduit(prod9);
												       opProd14.setDateRetour(prod9.getDateRetour());
												       opProd14.setTest(1);
												       opProd14.setDatePrise(date.toLocaleString());
												   
														   opProduitRepository.save(opProd14);
														   
														   
														   
														   OperationProduit opProd15=new OperationProduit();
													        Set<OperationProduit> opProds15= new HashSet<OperationProduit>();
													        opProds15.add(opProd15);
													        p5.setOperationProduits(opProds15);
													        produitService.insertProduit(p5);

														    Operation op15=new Operation();
														    op15.setNatureOp("insertion");
														   
														    op15.setNomOp("operation num°"+serviceIncrement.increment());
														    op15.setDateOP(date.toLocaleString());
														    op15.setOperationProduits(opProds15);
														    operationService.insertOperation(op15);
														    
														   opProd15.setOperation(op15);
													       opProd15.setProduit(p5);
													       opProd15.setTest(1);
													       opProd15.setQte(p5.getQte());													       opProd15.setTest(1);
													       opProd15.setDatePrise(date.toLocaleString());
													   
															   opProduitRepository.save(opProd15);
											   
											   
															   
															   
															   OperationProduit opProd16=new OperationProduit();
														        Set<OperationProduit> opProds16= new HashSet<OperationProduit>();
														        opProds16.add(opProd16);
														        p6.setOperationProduits(opProds16);
														        produitService.insertProduit(p6);

															    Operation op16=new Operation();
															    op16.setNatureOp("insertion");
															   
															    op16.setNomOp("operation num°"+serviceIncrement.increment());
															    op16.setDateOP(date.toLocaleString());
															    op16.setOperationProduits(opProds16);
															    operationService.insertOperation(op16);
															    
															   opProd16.setOperation(op16);
														       opProd16.setProduit(p6);
														       opProd16.setTest(1);
														       opProd16.setQte(p6.getQte());													       opProd15.setTest(1);
														       opProd16.setDatePrise(date.toLocaleString());
														   
																   opProduitRepository.save(opProd16);
											   
																   
																   
																   OperationProduit opProd17=new OperationProduit();
															        Set<OperationProduit> opProds17= new HashSet<OperationProduit>();
															        opProds17.add(opProd17);
															        p7.setOperationProduits(opProds17);
															        produitService.insertProduit(p7);

																    Operation op17=new Operation();
																    op17.setNatureOp("insertion");
																   
																    op17.setNomOp("operation num°"+serviceIncrement.increment());
																    op17.setDateOP(date.toLocaleString());
																    op17.setOperationProduits(opProds17);
																    operationService.insertOperation(op17);
																    
																   opProd17.setOperation(op17);
															       opProd17.setProduit(p7);
															       opProd17.setQte(p7.getQte());
															       opProd17.setTest(1);
															       opProd17.setDatePrise(date.toLocaleString());
															   
																	   opProduitRepository.save(opProd17);
																   
																	   
																	   
																	   
																	   
																	   OperationProduit opProd18=new OperationProduit();
																        Set<OperationProduit> opProds18= new HashSet<OperationProduit>();
																        opProds18.add(opProd18);
																        p8.setOperationProduits(opProds18);
																        produitService.insertProduit(p8);

																	    Operation op18=new Operation();
																	    op18.setNatureOp("insertion");
																	   
																	    op18.setNomOp("operation num°"+serviceIncrement.increment());
																	    op18.setDateOP(date.toLocaleString());
																	    op18.setOperationProduits(opProds18);
																	    operationService.insertOperation(op18);
																	    
																	   opProd18.setOperation(op18);
																       opProd18.setProduit(p8);
																       opProd18.setTest(1);
																       opProd18.setQte(p8.getQte());													       opProd15.setTest(1);
																       opProd18.setDatePrise(date.toLocaleString());
																   
																		   opProduitRepository.save(opProd18);
																		   
																		   
																		   OperationProduit opProd19=new OperationProduit();
																	        Set<OperationProduit> opProds19= new HashSet<OperationProduit>();
																	        opProds19.add(opProd19);
																	        p9.setOperationProduits(opProds19);
																	        produitService.insertProduit(p9);

																		    Operation op19=new Operation();
																		    op19.setNatureOp("insertion");
																		   
																		    op19.setNomOp("operation num°"+serviceIncrement.increment());
																		    op19.setDateOP(date.toLocaleString());
																		    op19.setOperationProduits(opProds19);
																		    operationService.insertOperation(op19);
																		    
																		   opProd19.setOperation(op19);
																	       opProd19.setProduit(p9);
																	       opProd19.setQte(p9.getQte());	
																	       opProd19.setTest(1);
																	       opProd19.setDatePrise(date.toLocaleString());
																	   
																			   opProduitRepository.save(opProd19);
																			   
																			   
																			   
																			   
																			   OperationProduit opProd20=new OperationProduit();
																		        Set<OperationProduit> opProds20= new HashSet<OperationProduit>();
																		        opProds20.add(opProd20);
																		        p10.setOperationProduits(opProds20);
																		        produitService.insertProduit(p10);

																			    Operation op20=new Operation();
																			    op20.setNatureOp("insertion");
																			   
																			    op20.setNomOp("operation num°"+serviceIncrement.increment());
																			    op20.setDateOP(date.toLocaleString());
																			    op20.setOperationProduits(opProds20);
																			    operationService.insertOperation(op20);
																			    
																			   opProd20.setOperation(op20);
																		       opProd20.setProduit(p10);
																		       opProd20.setTest(1);
																		       opProd20.setQte(p10.getQte());													       opProd15.setTest(1);
																		       opProd20.setDatePrise(date.toLocaleString());
																		   
																				   opProduitRepository.save(opProd20);
																   
																   
																   
		}
	
	

}

