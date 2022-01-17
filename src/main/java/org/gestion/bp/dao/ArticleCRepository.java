package org.gestion.bp.dao;

import org.gestion.bp.entities.ArticleConsomme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//@Repository
public interface ArticleCRepository extends JpaRepository<ArticleConsomme, Integer> {
	@Transactional 
	@Modifying
	@Query("delete ArticleConsomme u where u.matricule=Null")
	void deleteArt();
	
}
