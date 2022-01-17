package org.gestion.bp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type_Produit")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@code")
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "type_Produit")
		  @JsonSubTypes({ 
		  @Type(value = Materiel.class, name = "Materiel"), 
		  @Type(value = ArticleConsomme.class, name = "ArticleC") 
		})
public class Produit implements Serializable{
	@Id
	@GeneratedValue
	private int code;
	@Column(name="intitule",length=35)
	@Size(min=3,max=15)
	@NotEmpty
	private String intitule;
	@NotEmpty
	private String matricule;
//	@NotEmpty
//	private int qte;
//	@NotEmpty
//	private int qteMin;
	private int test;
	private String photo;
	
//	@NotEmpty
//	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//	private String dateRetour;
	
	
	@ManyToOne
	@JoinColumn(name="nomMagazin")
	//@JsonIgnoreProperties("nomMagazin")
	@JsonBackReference
	private Magazin magazin;
	
	
	@ManyToOne
    @JoinColumn(name="nomCateg")
	//@JsonIgnoreProperties("nomCateg")
	@JsonBackReference
	private Categorie categorie;

	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy="produit",cascade = CascadeType.REMOVE)
    private Set<OperationProduit> operationProduits;
	
	public Produit() {
		// TODO Auto-generated constructor stub
	}

	
	public Produit(int code, @Size(min = 3, max = 15) @NotEmpty String intitule, @NotEmpty String matricule, int test,
			String photo, Magazin magazin, Categorie categorie, Set<OperationProduit> operationProduits) {
		super();
		this.code = code;
		this.intitule = intitule;
		this.matricule = matricule;
		this.test = test;
		this.photo = photo;
		this.magazin = magazin;
		this.categorie = categorie;
		this.operationProduits = operationProduits;
	}

	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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

	public int getTest() {
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	public Magazin getMagazin() {
		return magazin;
	}

	public void setMagazin(Magazin magazin) {
		this.magazin = magazin;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Set<OperationProduit> getOperationProduits() {
		return operationProduits;
	}

	public void setOperationProduits(Set<OperationProduit> operationProduits) {
		this.operationProduits = operationProduits;
	}


	/*
	 * @Override public String toString() { return "Produit [code=" + code +
	 * ", intitule=" + intitule + ", matricule=" + matricule + ", test=" + test +
	 * ", photo=" + photo + ", magazin=" + magazin + ", categorie=" + categorie +
	 * ", operationProduits=" + operationProduits + "]"; }
	 */

	

}
