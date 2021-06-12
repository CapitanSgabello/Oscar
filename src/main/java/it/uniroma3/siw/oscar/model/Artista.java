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
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
public class Artista {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cognome;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascita;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(nullable = true)
	private LocalDate dataMorte;

	private String nazionalita;
	
	private String imageUrl;
	
	@OneToMany(mappedBy="vincitore")
	private List<CategoriaArtista> categorieVinte;
	
	@OneToMany(mappedBy="candidati")
	private List<CategoriaArtista> categorieCandidate;
	
	@OneToMany(mappedBy="presentatore")
	private List<Edizione> edizioniPresentate;
	
	@ManyToMany(mappedBy="attori")
	private List<Film> filmRecitati;
	
	@OneToMany
	private List<Film> filmDiretti;

	@ManyToMany(mappedBy="operatoriTecnici")
	private List<Film> filmRealizzati;
	
	public Artista() {
	  this.categorieCandidate=new ArrayList<>();
	  this.categorieVinte=new ArrayList<>();
	  this.edizioniPresentate=new ArrayList<>();
	  this.filmDiretti=new ArrayList<>();
	  this.filmRealizzati=new ArrayList<>();
	  this.filmRealizzati=new ArrayList<>();
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public LocalDate getDataMorte() {
		return dataMorte;
	}

	public void setDataMorte(LocalDate dataMorte) {
		this.dataMorte = dataMorte;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<CategoriaArtista> getCategorieVinte() {
		return categorieVinte;
	}

	public void setCategorieVinte(List<CategoriaArtista> categorieVinte) {
		this.categorieVinte = categorieVinte;
	}

	public List<CategoriaArtista> getCategorieCandidate() {
		return categorieCandidate;
	}

	public void setCategorieCandidate(List<CategoriaArtista> categorieCandidate) {
		this.categorieCandidate = categorieCandidate;
	}

	public List<Edizione> getEdizioniPresentate() {
		return edizioniPresentate;
	}

	public void setEdizioniPresentate(List<Edizione> edizioniPresentate) {
		this.edizioniPresentate = edizioniPresentate;
	}

	public List<Film> getFilmRecitati() {
		return filmRecitati;
	}

	public void setFilmRecitati(List<Film> filmRecitati) {
		this.filmRecitati = filmRecitati;
	}

	public List<Film> getFilmDiretti() {
		return filmDiretti;
	}

	public void setFilmDiretti(List<Film> filmDiretti) {
		this.filmDiretti = filmDiretti;
	}

	public List<Film> getFilmRealizzati() {
		return filmRealizzati;
	}

	public void setFilmRealizzati(List<Film> filmRealizzati) {
		this.filmRealizzati = filmRealizzati;
	}
	
}
