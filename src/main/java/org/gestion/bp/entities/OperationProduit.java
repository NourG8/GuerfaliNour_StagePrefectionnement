package org.gestion.bp.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@OperationProduit")
public class OperationProduit implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="codeProduit")
	@JsonBackReference
	private Produit produit;
	
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private String datePrise;
	
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private String dateRetour;
	
	@NotEmpty
	private int qte;
	private int test;
	private int testDesact;

	@ManyToOne 
    @JoinColumn(name="idOperation")
	@JsonBackReference
	private Operation operation;
	
	public OperationProduit() {
		// TODO Auto-generated constructor stub
	}


	public OperationProduit(int id, Produit produit, @NotEmpty String datePrise, @NotEmpty String dateRetour,
			@NotEmpty int qte, int test, int testDesact, Operation operation) {
		super();
		this.id = id;
		this.produit = produit;
		this.datePrise = datePrise;
		this.dateRetour = dateRetour;
		this.qte = qte;
		this.test = test;
		this.testDesact = testDesact;
		this.operation = operation;
	}

	


	public int getTestDesact() {
		return testDesact;
	}



	public void setTestDesact(int testDesact) {
		this.testDesact = testDesact;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public String getDatePrise() {
		return datePrise;
	}

	public void setDatePrise(String datePrise) {
		this.datePrise = datePrise;
	}

	public String getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(String dateRetour) {
		this.dateRetour = dateRetour;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public int getTest() {
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
}
