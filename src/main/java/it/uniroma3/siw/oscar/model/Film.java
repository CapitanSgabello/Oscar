package it.uniroma3.siw.oscar.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Film {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	private String imageUrl;

	@Column(nullable = false)
	private String titolo;

	@Column(nullable = false)
	private String genere;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataUscita;

	@OneToMany(mappedBy="vincitore")
	private List<CategoriaFilm> categorieVinte;

	@OneToMany(mappedBy="candidati")
	private List<CategoriaFilm> categorieCandidate;

	@ManyToMany
	private List<Artista> attori;

	@ManyToMany
	private List<Artista> operatoriTecnici;

	@ManyToOne
	private Artista regista;

	@OneToMany(mappedBy="film")
	private List<Commento> commenti;

	public Film() {
		this.attori = new ArrayList<>();
		this.categorieCandidate = new ArrayList<>();
		this.categorieVinte = new ArrayList<>();
		this.operatoriTecnici = new ArrayList<>();
		this.commenti = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public LocalDate getDataUscita() {
		return dataUscita;
	}

	public void setDataUscita(LocalDate dataUscita) {
		this.dataUscita = dataUscita;
	}

	public List<CategoriaFilm> getCategorieVinte() {
		return categorieVinte;
	}

	public void setCategorieVinte(List<CategoriaFilm> categorieVinte) {
		this.categorieVinte = categorieVinte;
	}

	public List<CategoriaFilm> getCategorieCandidate() {
		return categorieCandidate;
	}

	public void setCategorieCandidate(List<CategoriaFilm> categorieCandidate) {
		this.categorieCandidate = categorieCandidate;
	}

	public List<Artista> getAttori() {
		return attori;
	}

	public void setAttori(List<Artista> attori) {
		this.attori = attori;
	}

	public List<Artista> getOperatoriTecnici() {
		return operatoriTecnici;
	}

	public void setOperatoriTecnici(List<Artista> operatoriTecnici) {
		this.operatoriTecnici = operatoriTecnici;
	}

	public Artista getRegista() {
		return regista;
	}

	public void setRegista(Artista regista) {
		this.regista = regista;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}



}
