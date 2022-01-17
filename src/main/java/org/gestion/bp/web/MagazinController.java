//package org.gestion.bp.web;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.validation.Valid;
//
//import org.gestion.bp.dao.CategorieRepository;
//import org.gestion.bp.dao.MagazinRepository;
//import org.gestion.bp.entities.Categorie;
//import org.gestion.bp.entities.Magazin;
//import org.gestion.bp.exception.ResourceNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@CrossOrigin(origins = "http://localhost:4200")
//@RestController
//@RequestMapping("/api/v1")
//public class MagazinController {
//	 @Autowired
//	    MagazinRepository magazinRepository;
//	    
//	    
//	    @GetMapping("/magazins")
//	    public List<Magazin> getAllMagazins() {
//	        return magazinRepository.findAll();
//	    }
//
//	    @GetMapping("/magazin/{nomMagazin}")
//	    public ResponseEntity<Magazin> getmagazinById(@PathVariable(value = "nomMagazin")String nomMagazin)
//	        throws ResourceNotFoundException {
//	       Magazin magazin= magazinRepository.findById(nomMagazin)
//	          .orElseThrow(() -> new ResourceNotFoundException("Magazin not found for this id :: " + nomMagazin));
//	        return ResponseEntity.ok().body(magazin);
//	    }
//	    
//	    //Register
//	    @PostMapping("/createMagazin")
//	    public Magazin createMagazin(@Valid @RequestBody Magazin magazin) {
//	    	Magazin m=new Magazin();
//	    	m.setNomMagazin(magazin.getNomMagazin());
//	        return magazinRepository.save(m);
//	    }
//
//	    @PutMapping("/updateMagazin/{nomMagazin}")
//	    public ResponseEntity<Magazin> updateMagazin(@PathVariable(value = "nomMagazin") String nomMagazin,
//	         @Valid @RequestBody Magazin magazin) throws ResourceNotFoundException {
//	    	Magazin m= magazinRepository.findById(nomMagazin)
//	        .orElseThrow(() -> new ResourceNotFoundException("Magazin not found for this id :: " + nomMagazin));
//
//	    	m.setNomMagazin(magazin.getNomMagazin());
//	    	final Magazin updatedMagazin= magazinRepository.save(m);
//	        return ResponseEntity.ok(updatedMagazin);
//	    }
//
//	    @DeleteMapping("/deleteMagazin/{nomMagazin}")
//	    public Map<String, Boolean> deleteMagazin(@PathVariable(value = "nomMagazin") String nomMagazin)
//	         throws ResourceNotFoundException {
//	    	Magazin magazin= magazinRepository.findById(nomMagazin)
//	       .orElseThrow(() -> new ResourceNotFoundException("Magazin not found for this id :: " + nomMagazin));
//
//	       magazinRepository.delete(magazin);
//	        Map<String, Boolean> response = new HashMap<>();
//	        response.put("deleted", Boolean.TRUE);
//	        return response;
//	    }
//	}
