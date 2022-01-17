package org.gestion.bp.service;

import java.util.Collection;
import java.util.List;

import org.gestion.bp.dao.MaterielRepository;
import org.gestion.bp.dao.ProduitRepository;
import org.gestion.bp.entities.ArticleConsomme;
import org.gestion.bp.entities.Materiel;
import org.gestion.bp.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterielService {
	@Autowired
	MaterielRepository materielRepository;
	
	public Produit UpdateArticleC(Materiel a) {	
		return materielRepository.save(a);
	}
	public void deleteArticleC(Materiel a) {
		materielRepository.delete(a);
	}
	
	  public Materiel findById(int id) {
			return materielRepository.getById(id);
	    }

	public List<Materiel> findAllArticleC() {	
		return materielRepository.findAll();
	}
	int i=0;
	public int increment () {
		return i=i+1;
	}
	
}
	

