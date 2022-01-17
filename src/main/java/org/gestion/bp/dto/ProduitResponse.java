package org.gestion.bp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.gestion.bp.entities.Produit;
import org.gestion.bp.service.ServiceIncrement;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProduitResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
int idList=0;
private int id;
private int idP;
private int idOp;
private String username;
private String nomOp;
private String nomResp;
private String dateOP;
private String intitule;
private String matricule;
private int qte;
private String dateRetour;
private int test;
private Produit p;
private String natureOp;

public ProduitResponse() {
	// TODO Auto-generated constructor stub
}

//@Autowired
//ServiceIncrement serviceIncrement;
public ProduitResponse( int id, int idP, int idOp, String username, String nomOp, String nomResp,
		String dateOP, String intitule, String matricule, int qte, String dateRetour, int test,Produit p,String natureOp) {
	super();
	idList++;
//	this.idList =serviceIncrement.increment();
	this.id =id;
	this.idP = idP;
	this.idOp = idOp;
	this.username = username;
	this.nomOp = nomOp;
	this.nomResp = nomResp;
	this.dateOP = dateOP;
	this.intitule = intitule;
	this.matricule = matricule;
	this.qte = qte;
	this.dateRetour = dateRetour;
	this.test = test;
	this.p=p;
	this.natureOp=natureOp;
}


public String getNatureOp() {
	return natureOp;
}

public void setNatureOp(String natureOp) {
	this.natureOp = natureOp;
}

public Produit getP() {
	return p;
}

public void setP(Produit p) {
	this.p = p;
}

public int getIdList() {
	return idList;
}

public void setIdList(int idList) {
	this.idList = idList;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getIdP() {
	return idP;
}

public void setIdP(int idP) {
	this.idP = idP;
}

public int getIdOp() {
	return idOp;
}

public void setIdOp(int idOp) {
	this.idOp = idOp;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getNomOp() {
	return nomOp;
}

public void setNomOp(String nomOp) {
	this.nomOp = nomOp;
}

public String getNomResp() {
	return nomResp;
}

public void setNomResp(String nomResp) {
	this.nomResp = nomResp;
}

public String getDateOP() {
	return dateOP;
}

public void setDateOP(String dateOP) {
	this.dateOP = dateOP;
}

public String getIntitule() {
	return intitule;
}

public void setIntitule(String intitule) {
	this.intitule = intitule;
}

public String getMatricule() {
	return matricule;
}

public void setMatricule(String matricule) {
	this.matricule = matricule;
}

public int getQte() {
	return qte;
}

public void setQte(int qte) {
	this.qte = qte;
}

public String getDateRetour() {
	return dateRetour;
}

public void setDateRetour(String dateRetour) {
	this.dateRetour = dateRetour;
}

public int getTest() {
	return test;
}

public void setTest(int test) {
	this.test = test;
}


}
