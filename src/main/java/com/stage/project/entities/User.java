package com.stage.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users", schema = "public")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne()
    @JoinColumn(name="prof_id")
    private Prof prof;

	@OneToOne
	@JoinColumn(name = "etudiant_id")
	private Etudiant etudiant;

	@Column(name = "first_name", length = 75)
	private String firstName;

	@Column(name = "last_name", length = 80)
	private String lastName;

	@Column(name = "username", length = 65)
	private String username;

	@Column(name = "password", length = 64)
	private String password;

	@Column(name = "passString", length = 64)
	private String passString;

	@Column(name = "email", length = 115)
	private String email;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;


	public User() {

	}

	public User(String firstName, String lastName, String username, String password, String passString, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.passString = passString;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Prof getProf() {
		return prof;
	}

	public void setProf(Prof prof) {
		this.prof = prof;
	}

	@JsonIgnore
	public String getPassString() {
		return passString;
	}

	public void setPassString(String passString) {
		this.passString = passString;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", prof=" + prof +
				", etudiant=" + etudiant +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", passString='" + passString + '\'' +
				", email='" + email + '\'' +
				", role=" + role +
				'}';
	}
}