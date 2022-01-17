package org.gestion.bp.dao;

import org.gestion.bp.entities.Operation;
import org.gestion.bp.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//@Repository
public interface OperationRepository  extends JpaRepository<Operation, Integer>{
//	@Query(value = "SELECT * FROM Operation a WHERE a.nomOp like:nomOp", nativeQuery = true)
//	Operation getOperation(@Param("nomOp")String nomOp);
////	@Query("SELECT a FROM Operation a WHERE a.nomOp=:nomOp")
	
}
