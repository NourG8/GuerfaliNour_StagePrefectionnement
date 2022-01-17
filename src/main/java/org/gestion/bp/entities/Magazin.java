package org.gestion.bp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="Magazin")
public class Magazin implements Serializable {
	@Id
	@Column(name="nomMagazin",length=15)
	@Size(min=3,max=15)
	@NotEmpty
	private String nomMagazin;

	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy="magazin",cascade = CascadeType.ALL)
	private List<Produit> produits;

	public Magazin() {
		// TODO Auto-generated constructor stub
	}

	public Magazin(@Size(min = 3, max = 15) @NotEmpty String nomMagazin, List<Produit> produits) {
		super();
		this.nomMagazin = nomMagazin;
		this.produits = produits;
	}

	public String getNomMagazin() {
		return nomMagazin;
	}

	public void setNomMagazin(String nomMagazin) {
		this.nomMagazin = nomMagazin;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}
	
}