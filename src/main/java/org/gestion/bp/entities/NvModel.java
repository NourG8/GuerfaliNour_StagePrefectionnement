package org.gestion.bp.entities;


public class NvModel {
private Operation operation;
private User user;
private Produit produit;
private ArticleConsomme articleConsomme;
private OperationProduit operationProduit;
private Materiel materiel;
private int qte;

public NvModel() {
	// TODO Auto-generated constructor stub
}

public NvModel(Operation operation, User user, Produit produit, ArticleConsomme articleConsomme,Materiel materiel,int qte, OperationProduit operationProduit) {
	super();
	this.operation = operation;
	this.user = user;
	this.produit = produit;
	this.articleConsomme = articleConsomme;
	this.operationProduit=operationProduit;
	this.materiel=materiel;
	this.qte=qte;
}



public OperationProduit getOperationProduit() {
	return operationProduit;
}

public void setOperationProduit(OperationProduit operationProduit) {
	this.operationProduit = operationProduit;
}

public int getQte() {
	return qte;
}

public void setQte(int qte) {
	this.qte = qte;
}

public Materiel getMateriel() {
	return materiel;
}

public void setMateriel(Materiel materiel) {
	this.materiel = materiel;
}

public Operation getOperation() {
	return operation;
}
public void setOperation(Operation operation) {
	this.operation = operation;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public Produit getProduit() {
	return produit;
}
public void setProduit(Produit produit) {
	this.produit = produit;
}
public ArticleConsomme getArticleConsomme() {
	return articleConsomme;
}
public void setArticleConsomme(ArticleConsomme articleConsomme) {
	this.articleConsomme = articleConsomme;
}
}
