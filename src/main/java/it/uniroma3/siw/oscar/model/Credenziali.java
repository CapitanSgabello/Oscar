package it.uniroma3.siw.oscar.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Credenziali {

	public static String DEFAULT_ROLE = "DEFAULT";
	public static String ADMIN_ROLE = "ADMIN";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String ruolo;

	@OneToOne(cascade = CascadeType.ALL)
	private Utente utente;

	public Credenziali() {}

	public static String getDefaultRole() {
		return DEFAULT_ROLE;
	}

	public static void setDefaultRole(String defaultRole) {
		DEFAULT_ROLE = defaultRole;
	}

	public static String getAdminRole() {
		return ADMIN_ROLE;
	}

	public static void setAdminRole(String adminRole) {
		ADMIN_ROLE = adminRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}


}
