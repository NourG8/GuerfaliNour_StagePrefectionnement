package org.gestion.bp.dao;

import java.util.Collection;

import org.gestion.bp.entities.ArticleConsomme;
import org.gestion.bp.entities.Materiel;
import org.gestion.bp.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//@Repository
public interface MaterielRepository extends JpaRepository<Materiel, Integer> {
	@Transactional  
	@Modifying
	@Query("update Materiel u set u.test =0 where u.code=:code")
	void deactivateTestMateriel(@Param("code") int code);
	
	@Transactional  
	@Modifying
	@Query("update Materiel u set u.dateRetour=Null where u.code=:code")
	void deactivateDateMateriel(@Param("code") int code);
	
	@Transactional 
	@Modifying
	@Query("delete Materiel u where u.code=:code")
	void deleteMateriel(@Param("code") int code);
	
	
	@Transactional 
	@Modifying
	@Query("delete Materiel u where u.matricule=Null")
	void deleteMat();
	
	@Transactional  
	@Modifying
	@Query("update Materiel u set u.dateRetour=Null where u.dateRetour!=Null and u.code=:code")
	void updateMateriel(@Param("code") int code);

}
