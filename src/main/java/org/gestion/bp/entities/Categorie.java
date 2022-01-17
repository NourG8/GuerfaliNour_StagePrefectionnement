package org.gestion.bp.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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
@Table(name="Categorie")
@DiscriminatorValue("categorie")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Categorie implements Serializable {
	@Id
	@Column(name="nomCateg",length=45)
	@Size(min=3,max=15)
	@NotEmpty
	private String nomCateg;

	@NotEmpty
	private String description;
	
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy="categorie",cascade = CascadeType.ALL)
	//@JsonIgnoreProperties("categorie")
	private Collection<Produit> produits;
	
	public Categorie() {
		// TODO Auto-generated constructor stub
	}

	public Categorie(@Size(min = 3, max = 15) @NotEmpty String nomCateg, @NotEmpty String description,
			Collection<Produit> produits) {
		super();
		this.nomCateg = nomCateg;
		this.description = description;
		this.produits = produits;
	}

	public String getNomCateg() {
		return nomCateg;
	}

	public void setNomCateg(String nomCateg) {
		this.nomCateg = nomCateg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<Produit> getProduits() {
		return produits;
	}

	public void setProduits(Collection<Produit> produits) {
		this.produits = produits;
	}
	
}