package org.gestion.bp.service;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.gestion.bp.dao.ProduitRepository;
import org.gestion.bp.entities.ArticleConsomme;
import org.gestion.bp.entities.Magazin;
import org.gestion.bp.entities.Materiel;
import org.gestion.bp.entities.Produit;
import org.gestion.bp.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProduitService {
	@Autowired
	ProduitRepository produitRepository;
	
	public Produit insertProduit(Produit produit) {	
		return produitRepository.save(produit);
	}
	
	
	public Materiel insertMateriel(Materiel materiel) {	
		return produitRepository.save(materiel);
	}
	
	public ArticleConsomme insertArticleConsomme(ArticleConsomme artC) {	
		return produitRepository.save(artC);
	}
	
	public Produit UpdateProduit(Produit produit) {	
		return produitRepository.save(produit);
	}
	public void deleteProduit(Produit produit) {
		produitRepository.delete(produit);
	}
	
	public List<Produit> findAllProduits() {	
		return produitRepository.findAll();
	}
	
	public Collection<Produit> findbyNameAndDescription(String keyword) throws Exception{
		Collection<Produit> articles= produitRepository.findByNameAndDescription(keyword);
		if (articles==null){
			throw new Exception();
		}
		return articles;
	}

	 public Produit findByProduitType(String test) {
		    return produitRepository.findByProduitType(test);
	    }
	
    public Produit findById(int id) {
		return produitRepository.getById(id);
    }

	public Produit updateArticle(Produit produit) {
		return produitRepository.save(produit);
	}

	public Collection<Produit> findByCategoryAndKeyword(String category, String keyword){
		return produitRepository.findByCategoryAndKeyword(category,keyword);
	}

	public Collection<Produit> findByCategory(String category) {
		return produitRepository.findByCategory(category);
	}
	
	public Collection<ArticleConsomme> findArticleConsomme(String intitule){
		return produitRepository.findArticleConsomme(intitule);
	}
	
	public Materiel findMateriel(String intitule){
		return produitRepository.findMateriel(intitule);
	}
	
	public void deleteProdiut(Produit produit) {	
		produitRepository.delete(produit);
	}
	
	

	
	public Page<Materiel> RechercheProduit(String mc, int pageNo, int pageSize) {
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		return this.produitRepository.RechercheProduit(mc, pageable);
	}
	
	public Page<Produit> findPaginatedO(int pageNo, int pageSize) {
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		return this.produitRepository.findAll(pageable);
	}
     
	
}
	


