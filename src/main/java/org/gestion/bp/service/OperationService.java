package org.gestion.bp.service;

import java.util.List;

import org.gestion.bp.dao.OperationRepository;
import org.gestion.bp.entities.Categorie;
import org.gestion.bp.entities.Magazin;
import org.gestion.bp.entities.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationService {
	@Autowired
	private OperationRepository operationRepository;
	
	public Operation insertOperation(Operation operation) {
		return operationRepository.save(operation);
	}
	
	public List<Operation> findAllOperations(){
		
		return  operationRepository.findAll();
	}
	
	public void deleteOperation(int id) {	
		operationRepository.deleteById(id);
	}
	
//	public Operation getOperations(String nomOp) {
//		 Operation c=operationRepository.getOperation(nomOp);
//		 return c;
//		}
}
