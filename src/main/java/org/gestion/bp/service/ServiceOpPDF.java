package org.gestion.bp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.gestion.bp.dao.OpProduitRepository;
import org.gestion.bp.dto.ProduitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ServiceOpPDF {
	@Autowired
	private OpProduitRepository opProduitRepository;
	
	public List<ProduitResponse>findAllOperationsUser(){
		return opProduitRepository.findOperationsUser();
	}
	
	
}
