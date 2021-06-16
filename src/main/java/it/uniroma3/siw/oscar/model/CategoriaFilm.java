package it.uniroma3.siw.oscar.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class CategoriaFilm {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@ManyToOne
	private Edizione edizione;

	@ManyToOne
	private Film vincitore;

	@ManyToMany
	private List<Film> candidati;


	public CategoriaFilm() {
		this.candidati =    new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Edizione getEdizione() {
		return edizione;
	}

	public void setEdizione(Edizione edizione) {
		this.edizione = edizione;
	}

	public Film getVincitore() {
		return vincitore;
	}

	public void setVincitore(Film vincitore) {
		this.vincitore = vincitore;
	}

	public List<Film> getCandidati() {
		return candidati;
	}

	public void setCandidati(List<Film> candidati) {
		this.candidati = candidati;
	}


}
