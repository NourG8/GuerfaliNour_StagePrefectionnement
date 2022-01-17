package org.gestion.bp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@DiscriminatorValue("Materiel")
@Table(name="Materiel")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@code")
public class Materiel extends Produit{

	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private String dateRetour;

	public Materiel() {
		// TODO Auto-generated constructor stub
	}
	
	


	public Materiel(int code, @Size(min = 3, max = 15) @NotEmpty String intitule, @NotEmpty String matricule, int test,
			String photo, Magazin magazin, Categorie categorie, Set<OperationProduit> operationProduits) {
		super(code, intitule, matricule, test, photo, magazin, categorie, operationProduits);
		// TODO Auto-generated constructor stub
	}




	public Materiel(@NotEmpty String dateRetour) {
		super();
		this.dateRetour = dateRetour;
	}

	public String getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(String dateRetour) {
		this.dateRetour = dateRetour;
	}




	/*
	 * @Override public String toString() { return "Materiel [dateRetour=" +
	 * dateRetour + ", getDateRetour()=" + getDateRetour() + ", getPhoto()=" +
	 * getPhoto() + ", getCode()=" + getCode() + ", getIntitule()=" + getIntitule()
	 * + ", getMatricule()=" + getMatricule() + ", getTest()=" + getTest() +
	 * ", getMagazin()=" + getMagazin() + ", getCategorie()=" + getCategorie() +
	 * ", getOperationProduits()=" + getOperationProduits() + ", toString()=" +
	 * super.toString() + ", getClass()=" + getClass() + ", hashCode()=" +
	 * hashCode() + "]"; }
	 */



	
	
}
