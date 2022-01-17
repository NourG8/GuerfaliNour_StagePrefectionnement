package org.gestion.bp.service;

import java.util.List;

import org.gestion.bp.dao.CategorieRepository;
import org.gestion.bp.entities.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategorieService {

	@Autowired
	CategorieRepository categoryRepository;
	
	public Categorie insertCategory(Categorie category) {
		return categoryRepository.save(category);
	}
	
	public List<Categorie> findAllCategories(){
		
		return  categoryRepository.findAll();
	}

	public Categorie UpdateCategory(Categorie category) {	
		return categoryRepository.save(category);
	}
	
	public void deleteCategory(Categorie category) {	
		categoryRepository.delete(category);
	}
	
	public Categorie getCategorie(String nomC) {
	 Categorie c=categoryRepository.getOne(nomC);
	 if(c==null) throw new RuntimeException("Compte introuvable !");
	 return c;
	}

	
}
