package org.gestion.bp.dao;

import java.util.Collection;
import java.util.List;

import org.gestion.bp.dto.ProduitResponse;
import org.gestion.bp.entities.ArticleConsomme;
import org.gestion.bp.entities.Materiel;
import org.gestion.bp.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//@Repository
public interface ProduitRepository  extends JpaRepository<Produit, Integer>{
	@Query(value = "SELECT * FROM Produit a WHERE a.test like :test", nativeQuery = true)
	Produit findByProduitType(String test);
	
	@Query(value = "SELECT * FROM Produit a WHERE a.intitule like %:keyWord%", nativeQuery = true)
	Collection<Produit> findByNameAndDescription(String keyWord);

	@Query(value = "SELECT * FROM Produit a WHERE a.nomCateg=:category and  a.intitule like %:keyWord% ", nativeQuery = true)
	Collection<Produit> findByCategoryAndKeyword(String category, String keyWord);


	@Query(value = "SELECT * FROM  Produit a WHERE a.nomCateg=:category", nativeQuery = true)
	Collection<Produit> findByCategory(String category);
	
	@Query(value = "SELECT * FROM  ArticleConsomme a WHERE a.intitule=:intitule", nativeQuery = true)
	Collection<ArticleConsomme> findArticleConsomme(String intitule);
	
	@Query(value = "SELECT * FROM Produit a WHERE a.intitule=:intitule", nativeQuery = true)
	Materiel findMateriel(@Param("intitule")String intitule);
	
	@Query(value = "SELECT * FROM Produit a WHERE a.intitule=:intitule", nativeQuery = true)
	ArticleConsomme findArticles(@Param("intitule")String intitule);
	
	@Query(value = "SELECT * FROM Materiel a WHERE a.intitule=:intitule", nativeQuery = true)
	Materiel findProduit(@Param("intitule")String intitule);
	
	@Query(value = "SELECT * FROM  ArticleConsomme a WHERE a.intitule=:intitule", nativeQuery = true)
	Collection<Materiel> findArticleC(int intitule);
	
	@Query("select o from Materiel o where o.intitule like :y order by o.code desc")
   public Page<Materiel> RechercheProduit(@Param("y")String mc,  Pageable pageable);
	
	@Query("SELECT new org.gestion.bp.dto.ProduitResponse(op.id,p.code, o.id,o.user.username, o.nomOp, o.nomResp ,o.dateOP, p.intitule ,p.matricule,op.qte,op.dateRetour,op.test,p,o.natureOp) FROM Operation o  JOIN  o.operationProduits op  JOIN op.produit p  where p.test like : y")
	   public Page<ProduitResponse> RechercheProduitMat(@Param("y")String mc,  Pageable pageable);
		
	
	@Query("select o from ArticleConsomme o where o.intitule like :y order by o.code desc")
	  public Page<ArticleConsomme> RechercheArticle(@Param("y")String mc,  Pageable pageable);
	
	@Query("select o from Produit o order by o.code desc ")
	public Page<Produit> chercherUtilisateursO(Pageable pageable);
	
	@Query("select o from Materiel o where o.dateRetour=null")
	public Collection<Materiel> findAllMateriel();
	
	@Query("select o from Materiel o where o.dateRetour!=null")
	public Collection<Materiel> findMaterielPrise();
	
	@Query("select o from ArticleConsomme  o where o.qte>=0")
	public Collection<ArticleConsomme> findAllArticle();
	
	@Query("select o from Materiel o")
	public Collection<Materiel> findAllMat();
	
	
	@Query("select o from ArticleConsomme  o")
	public Collection<ArticleConsomme > findAllArt();
	
	@Query("select o from ArticleConsomme  o where o.qte!=0")
	public Collection<ArticleConsomme > findArtDispo();

	

}
