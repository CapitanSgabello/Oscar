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
public class CategoriaArtista {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@ManyToOne
	private Edizione edizione;
	
	@ManyToOne
	private Artista vincitore;
	
	@ManyToMany
	private List<Artista> candidati;
	
	public CategoriaArtista() {
		this.candidati = new ArrayList<>();
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

	public Artista getVincitore() {
		return vincitore;
	}

	public void setVincitore(Artista vincitore) {
		this.vincitore = vincitore;
	}

	public List<Artista> getCandidati() {
		return candidati;
	}

	public void setCandidati(List<Artista> candidati) {
		this.candidati = candidati;
	}
	
	
}
