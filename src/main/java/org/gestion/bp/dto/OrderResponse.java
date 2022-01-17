package org.gestion.bp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

import org.gestion.bp.entities.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OrderResponse {

	
	private String username;
	private int cin ;
	private String prenom;
	private int age;
	private String login;
	private String password;
    private String photo;
    private Role roleType;


    


	public OrderResponse(String username, int cin, String prenom, int age, String login, String password, String photo,
			Role roleType) {
		super();
		this.username = username;
		this.cin = cin;
		this.prenom = prenom;
		this.age = age;
		this.login = login;
		this.password = password;
		this.photo = photo;
		this.roleType = roleType;
	}


	public OrderResponse() {
		// TODO Auto-generated constructor stub
	}
	

	public Role getRoleType() {
		return roleType;
	}


	public void setRoleType(Role roleType) {
		this.roleType = roleType;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getCin() {
		return cin;
	}
	public void setCin(int cin) {
		this.cin = cin;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}





	
}

