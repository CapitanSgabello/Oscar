package it.uniroma3.siw.oscar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Commento {
	
	public Commento() {
		
	}
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	
	@Column(nullable = false, length = 500)
	private String testo;
	
	@ManyToOne
	private Utente utente;
	
	@ManyToOne
	private Film film;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Film getFilm() {
		return film;
	}

	public void setFilm (Film  film ) {
		this.film = film;
	}

}
