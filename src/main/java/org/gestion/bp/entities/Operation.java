package org.gestion.bp.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@Operation")
public class Operation implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idOp;
	@Column(name="nomOp",length=20)
	@Size(min=3,max=20)
	@NotEmpty
	private String nomOp;
	
	@Column(name="natureOp",length=15)
	@Size(min=3,max=15)
	@NotEmpty
	private String natureOp;
	
	
	@Column(name="nomResp",length=15)
	@Size(min=3,max=15)
	@NotEmpty
	private String nomResp;
	
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private String dateOP;
	

	@ManyToOne 
	@JoinColumn(name="UserName")
	@JsonBackReference
	private User user;
	
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy="operation",cascade = CascadeType.ALL)
    private Set<OperationProduit> operationProduits;

	public Operation() {
		// TODO Auto-generated constructor stub
	}

	public Operation(int id, @Size(min = 3, max = 20) @NotEmpty String nomOp,
			@Size(min = 3, max = 15) @NotEmpty String natureOp, @Size(min = 3, max = 15) @NotEmpty String nomResp,
			@NotEmpty String dateOP, User user, Set<OperationProduit> operationProduits) {
		super();
		this.idOp = id;
		this.nomOp = nomOp;
		this.natureOp = natureOp;
		this.nomResp = nomResp;
		this.dateOP = dateOP;
		this.user = user;
		this.operationProduits = operationProduits;
	}

	public int getIdOp() {
		return idOp;
	}

	public void setId(int id) {
		this.idOp = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<OperationProduit> getOperationProduits() {
		return operationProduits;
	}

	public void setOperationProduits(Set<OperationProduit> operationProduits) {
		this.operationProduits = operationProduits;
	}

}
