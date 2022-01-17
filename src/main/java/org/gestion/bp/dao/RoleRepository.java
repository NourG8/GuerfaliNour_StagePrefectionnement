package org.gestion.bp.dao;

import java.util.List;

import org.gestion.bp.entities.Role;
import org.gestion.bp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//@Repository
public interface RoleRepository  extends JpaRepository<Role, String> {
	Role findByRoleType(String roleType);
	
	@Query("SELECT u FROM Role u")
	public List<Role> findRoles();
}
