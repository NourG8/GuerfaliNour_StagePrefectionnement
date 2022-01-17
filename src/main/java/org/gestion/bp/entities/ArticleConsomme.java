package org.gestion.bp.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@DiscriminatorValue("ArticleConsomme")
@Table(name="ArticleConsomme")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@code")
public class ArticleConsomme extends Produit  {

	@NotEmpty
	private int qte;
	@NotEmpty
	private int qteMin;
	
	public ArticleConsomme() {
		// TODO Auto-generated constructor stub
	}

	

	public ArticleConsomme(int code, @Size(min = 3, max = 15) @NotEmpty String intitule, @NotEmpty String matricule,
			int test, String photo, Magazin magazin, Categorie categorie, Set<OperationProduit> operationProduits) {
		super(code, intitule, matricule, test, photo, magazin, categorie, operationProduits);
		// TODO Auto-generated constructor stub
	}

	public ArticleConsomme(@NotEmpty int qte, @NotEmpty int qteMin) {
		super();
		this.qte = qte;
		this.qteMin = qteMin;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public int getQteMin() {
		return qteMin;
	}

	public void setQteMin(int qteMin) {
		this.qteMin = qteMin;
	}
	
}