package org.gestion.bp.dao;

import java.util.List;
import java.util.Optional;

import org.gestion.bp.dto.OrderResponse;
import org.gestion.bp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;	
//@Repository
public interface UserRepository  extends JpaRepository<User, String> {
	@Query("SELECT u FROM User u WHERE u.username=:nom")
	Optional<User> findByUsername(String nom);
//	 on r.roleType=:role
	//@Query("SELECT new org.gestion.bp.dto.OrderResponse(u.username,u.cin,u.prenom,u.age, u.login, u.password,u.photo, r.roleType,r.roleType,r.roleType,r.roleType,r.roleType,r.roleType,r.roleType) FROM User u  JOIN  u.roles r on r.roleType=:role")
	//public List<OrderResponse> findUsers(@Param("role")String role);
	
	@Query("SELECT u FROM User u JOIN  u.roles r on r.roleType=:role")
	public List<User> findUsersV2(@Param("role")String role);
	
}
