package org.gestion.bp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.gestion.bp.entities.Categorie;
import org.gestion.bp.entities.Magazin;
import org.gestion.bp.entities.Produit;

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
public class OpProdResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    int idList;
	private int id;
	private int code;
	private int idOp;
	private String nomOp;
	private String natureOp;
	private String nomResp;
	private String dateOP;
    private String username;
    private String intitule;
    private String matricule;
    private String magazin;
    private String categorie;
    private int qte;
    private int qteRest;
    private String dateRetour;
    private Produit p;
	/**
	 * 
	 */
	public OpProdResponse() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public OpProdResponse(int id, int code, int idOp, String nomOp, String natureOp, String nomResp, String dateOP,
			String username, String intitule, String matricule, String magazin, String categorie, int qte, 
			String dateRetour,Produit p) {
		super();
		this.id = id;
		this.code = code;
		this.idOp = idOp;
		this.nomOp = nomOp;
		this.natureOp = natureOp;
		this.nomResp = nomResp;
		this.dateOP = dateOP;
		this.username = username;
		this.intitule = intitule;
		this.matricule = matricule;
		this.magazin = magazin;
		this.categorie = categorie;
		this.qte = qte;
		//this.qteRest = qteRest;
		this.dateRetour = dateRetour;
		this.p = p;
		//this.typeP = typeP;
	}



//	public String getTypeP() {
//		return typeP;
//	}
//
//
//	public void setTypeP(String typeP) {
//		this.typeP = typeP;
//	}
//

	public Produit getP() {
		return p;
	}



	public void setP(Produit p) {
		this.p = p;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getIdOp() {
		return idOp;
	}
	public void setIdOp(int idOp) {
		this.idOp = idOp;
	}
	public String getNomOp() {
		return nomOp;
	}
	public void setNomOp(String nomOp) {
		this.nomOp = nomOp;
	}
	public String getNatureOp() {
		return natureOp;
	}
	public void setNatureOp(String natureOp) {
		this.natureOp = natureOp;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getMagazin() {
		return magazin;
	}
	public void setMagazin(String magazin) {
		this.magazin = magazin;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public int getQteRest() {
		return qteRest;
	}
	public void setQteRest(int qteRest) {
		this.qteRest = qteRest;
	}
	public String getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(String dateRetour) {
		this.dateRetour = dateRetour;
	}



	public int getIdList() {
		return idList;
	}



	public void setIdList(int idList) {
		this.idList = idList;
	}
	
	
}
