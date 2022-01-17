package org.gestion.bp.dao;

import java.util.List;

import org.gestion.bp.dto.OpProdResponse;
import org.gestion.bp.dto.OrderResponse;
import org.gestion.bp.dto.ProduitResponse;
import org.gestion.bp.entities.Materiel;
import org.gestion.bp.entities.OperationProduit;
import org.gestion.bp.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface OpProduitRepository extends JpaRepository<OperationProduit, Integer> {
/*  @Query("SELECT new org.gestion.bp.dto.OrderResponse(u.username, u.login, u.password, r.roleType) FROM User u  JOIN  u.roles r on r.roleType=:role")
	public List<OrderResponse> findUsers(@Param("role")String role); */
	
	@Query("SELECT new org.gestion.bp.dto.ProduitResponse(op.id,p.code, o.id,o.user.username, o.nomOp, o.nomResp ,o.dateOP, p.intitule ,p.matricule,op.qte,op.dateRetour,op.test,p,o.natureOp) FROM Operation o  JOIN  o.operationProduits op  JOIN op.produit p  where op.test=0")
	public List<ProduitResponse>findOperationsUser();
	
	@Transactional  
	@Modifying
	@Query("update OperationProduit u set u.test =1 where u.testDesact=0")
	void deactivateTestOperationq();
	
	@Transactional  
	@Modifying
	@Query("update OperationProduit u set u.dateRetour=:dateRetour where u.id=:id")
	void updateOpProd(@Param("dateRetour")String dateRetour,@Param("id")int id);
	
	
	@Query("select o from OperationProduit o where o.qte=0 order by o.id desc")
	   public Page<OperationProduit> RechercheOpProdMat(Pageable pageable);
	
	@Query("select o from OperationProduit o where o.qte!=0 order by o.id desc")
	   public Page<OperationProduit> RechercheOpProdArt(Pageable pageable);
	
	
	@Transactional  
	@Modifying
	@Query("update OperationProduit u set u.test =1 where u.test=0")
	void deactivateTestOperationProd();
	
	@Query("SELECT new org.gestion.bp.dto.OpProdResponse(op.id, p.code, o.id , o.nomOp, o.natureOp ,o.nomResp, o.dateOP, o.user.username, p.intitule ,p.matricule,p.magazin.nomMagazin, p.categorie.nomCateg,op.qte,op.dateRetour,p) FROM Operation o  JOIN  o.operationProduits op  JOIN op.produit p ")
	public List<OpProdResponse>findAllOperationsProd();
	
	@Query("SELECT op FROM OperationProduit op where op.qte!=0")
	public List<OperationProduit>findAllOperationsProdArt();
	
	@Query("SELECT op FROM OperationProduit op where op.qte=0")
	public List<OperationProduit>findAllOperationsProdMat();
	
	
	@Query("SELECT new org.gestion.bp.dto.OpProdResponse(op.id, p.code, o.id , o.nomOp, o.natureOp ,o.nomResp, o.dateOP, o.user.username, p.intitule ,p.matricule,p.magazin.nomMagazin, p.categorie.nomCateg,op.qte,op.dateRetour,p) FROM Operation o  JOIN  o.operationProduits op  JOIN op.produit p order by op.id DESC")
	public List<OpProdResponse>findAllOperationsProduits();
	
	


}

