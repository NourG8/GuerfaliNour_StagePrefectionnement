package org.gestion.bp.service;

import java.util.List;

import org.gestion.bp.dao.ArticleCRepository;
import org.gestion.bp.dao.MaterielRepository;
import org.gestion.bp.entities.ArticleConsomme;
import org.gestion.bp.entities.Materiel;
import org.gestion.bp.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleCService {
	@Autowired
	ArticleCRepository articleCRepository;
	
	public ArticleConsomme UpdateArticleC(ArticleConsomme a) {	
		return articleCRepository.save(a);
	}
	public void deleteArticleC(ArticleConsomme a) {
		articleCRepository.delete(a);
	}
	
	  public ArticleConsomme findById(int id) {
			return articleCRepository.getById(id);
	    }

	public List<ArticleConsomme> findAllArticleC() {	
		return articleCRepository.findAll();
	}
	
	int i =0;
	public int increment (int i) {
		return i=i+1;
	}
	
}

