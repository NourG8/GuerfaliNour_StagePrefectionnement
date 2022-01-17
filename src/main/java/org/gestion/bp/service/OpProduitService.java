package org.gestion.bp.service;

import java.util.List;

import org.gestion.bp.dao.OpProduitRepository;
import org.gestion.bp.dto.ProduitResponse;
import org.gestion.bp.entities.Magazin;
import org.gestion.bp.entities.Operation;
import org.gestion.bp.entities.OperationProduit;
import org.springframework.data.repository.query.Param;

public class OpProduitService {
	private OpProduitRepository opProduitRepository;

	public List<ProduitResponse>findOperationsUser(){
		return opProduitRepository.findOperationsUser();
	}
	
	public void deleteOperationProduit(int id) {	
		opProduitRepository.deleteById(id);
	}
	
	
}
