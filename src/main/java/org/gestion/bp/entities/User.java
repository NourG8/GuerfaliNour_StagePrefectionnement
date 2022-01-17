package org.gestion.bp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
public class User implements Serializable {

	@Id
	@Column(name="username",length=15)
	@Size(min=3,max=15)
	private String username;
	
	@Column(name="cin",length=8)
	@NotEmpty
	@Size(min=8,max=8)
	private int cin ;
	
	@Column(name="Prenom",length=15)
	@NotEmpty
	@Size(min=3,max=15)
	private String prenom;
	
	@Column(name="Age",length=2)
	@NotEmpty
	@Size(min=1,max=2)
	private int age;
	
	@Column(name="Login",length=40)
	@NotEmpty
	@Size(min=15,max=40)
	private String login;
	
	@Column(name="Password",length=1200)
	@NotEmpty
	@Size(min=8,max=30)
	private String password;
	
	private String photo;
	
	@NotNull
	private boolean enabled=true;
	
//	@JsonBackReference
	@ManyToMany(cascade = CascadeType.ALL , fetch =FetchType.EAGER)
    @JoinTable(name="users_roles" , joinColumns = @JoinColumn(name="username") , inverseJoinColumns=@JoinColumn(name="role"))
//	@JsonIgnoreProperties("role")
	@JsonBackReference
	private Set<Role> roles = new HashSet<>();
	
//	@JsonIgnoreProperties
	@JsonManagedReference
	@JsonIgnore
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL , fetch =FetchType.EAGER)
	private List<Operation> operations;

	
	public User() {}


	public User(@Size(min = 3, max = 15) String username, @NotEmpty @Size(min = 8, max = 8) int cin,
			@NotEmpty @Size(min = 3, max = 15) String prenom, @NotEmpty @Size(min = 1, max = 2) int age,
			@NotEmpty @Size(min = 15, max = 40) String login, @NotEmpty @Size(min = 8, max = 30) String password,
			String photo, @NotNull boolean enabled, Set<Role> roles, List<Operation> operations) {
		super();
		this.username = username;
		this.cin = cin;
		this.prenom = prenom;
		this.age = age;
		this.login = login;
		this.password = password;
		this.photo = photo;
		this.enabled = enabled;
		this.roles = roles;
		this.operations = operations;
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


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public List<Operation> getOperations() {
		return operations;
	}


	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	
	
}