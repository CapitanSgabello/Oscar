package it.uniroma3.siw.oscar.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Edizione implements Comparable<Edizione>{
	
	public static final String LUOGO = "Dolby Theatre, Los Angeles";

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String luogo;
	
	@Column(nullable = false, unique=true)
	private Long anno;
	
	@OneToMany(mappedBy="edizione", cascade = {CascadeType.REMOVE})
	private List<CategoriaFilm> categorieFilm;
	
	@OneToMany(mappedBy="edizione", cascade = {CascadeType.REMOVE})
	private List<CategoriaArtista> categorieArtisti;
	
	@ManyToOne
	private Artista presentatore;
	
	public Edizione() {
		this.luogo = LUOGO;
		this.categorieArtisti = new ArrayList<>();
		this.categorieFilm = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public Long getAnno() {
		return anno;
	}

	public void setAnno(Long anno) {
		this.anno = anno;
	}

	public List<CategoriaFilm> getCategorieFilm() {
		return categorieFilm;
	}

	public void setCategorieFilm(List<CategoriaFilm> categorieFilm) {
		this.categorieFilm = categorieFilm;
	}

	public List<CategoriaArtista> getCategorieArtisti() {
		return categorieArtisti;
	}

	public void setCategorieArtisti(List<CategoriaArtista> categorieArtisti) {
		this.categorieArtisti = categorieArtisti;
	}

	public Artista getPresentatore() {
		return presentatore;
	}

	public void setPresentatore(Artista presentatore) {
		this.presentatore = presentatore;
	}

	@Override
	public int compareTo(Edizione o) {
		return this.anno.compareTo(o.anno);
	}
	
	
	
	
	
}
