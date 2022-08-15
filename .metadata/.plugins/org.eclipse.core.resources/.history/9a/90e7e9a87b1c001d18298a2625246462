package com.shopme.common.entity;

import javax.persistence.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@Column(length = 128, nullable = false)
	private String email;
	
	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(name = "first_name", length = 64, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 64, nullable = false)
	private String lastName;
	
	@Column(length = 128, unique = true)
	private String photos;
	
	private Boolean enabled;
	
	@ManyToMany
	@JoinTable(name = "users_roles", 
			   joinColumns = @JoinColumn(name = "user_id"),
			   inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	

	public User() {
	}
	

	public User(String email, String password, String firstName, String lastName) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}


	@Override
	public String toString() {
		return "User [Id=" + Id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", roles=" + roles + "]";
	}
	

}
