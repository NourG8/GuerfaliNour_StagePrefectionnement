package org.gestion.bp.dao;

import java.util.Collection;
import java.util.Optional;

import org.gestion.bp.entities.ArticleConsomme;
import org.gestion.bp.entities.Categorie;
import org.gestion.bp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//@Repository
public interface CategorieRepository  extends JpaRepository<Categorie, String>{
//	@Query("SELECT u FROM Categorie u WHERE u.username=:nom")
//	Optional<User> findByUsername(String nom);
	@Query("select o from Categorie  o ")
	public Collection<Categorie> findAllCateg();
}
