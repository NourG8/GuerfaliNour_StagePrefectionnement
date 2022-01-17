package org.gestion.bp.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Role {
	@Id
	private String roleType;
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	 
	
	
	@ManyToMany(cascade = CascadeType.ALL , fetch =FetchType.LAZY , mappedBy = "roles" )
	@JsonBackReference
    private Set<User> users = new HashSet<>();
	
	public Role() {
		super();
	}

	public Role(String roleType, int id, Set<User> users) {
		super();
		this.roleType = roleType;
		this.id = id;
		this.users = users;
	}

	public Role(String roleType, int id) {
		super();
		this.roleType = roleType;
		this.id = id;
	}
	
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return  roleType ;
	}
	
	
}