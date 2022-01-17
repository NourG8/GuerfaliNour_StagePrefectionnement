package org.gestion.bp.dao;

import org.gestion.bp.entities.Magazin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//@Repository
public interface MagazinRepository  extends JpaRepository<Magazin, String> {

}
