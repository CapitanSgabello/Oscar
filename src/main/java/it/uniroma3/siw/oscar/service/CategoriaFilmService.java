package it.uniroma3.siw.oscar.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.model.CategoriaArtista;
import it.uniroma3.siw.oscar.model.CategoriaFilm;
import it.uniroma3.siw.oscar.model.Film;
import it.uniroma3.siw.oscar.repository.ArtistaRepository;
import it.uniroma3.siw.oscar.repository.CategoriaFilmRepository;

@Service
public class CategoriaFilmService {

	@Autowired
	private CategoriaFilmRepository categoriaFilmRepo;
	
	@Autowired
	private CategoriaArtistaService categoriaArtistaService;
	
	@Transactional
	public CategoriaFilm save(CategoriaFilm categoriaFilm) {
		return categoriaFilmRepo.save(categoriaFilm);
	}
	
	@Transactional
	public void delete(CategoriaFilm categoriaFilm) {
		categoriaFilmRepo.delete(categoriaFilm);
	}
	
	@Transactional
	public List<CategoriaFilm> cercaPerNome(String nome) {
		return categoriaFilmRepo.findByNome(nome);	
	}
	
	@Transactional
	public List<CategoriaFilm> cercaPerVincitore(Film film){
		return categoriaFilmRepo.findByVincitore(film);		
	}

	@Transactional
	public List<CategoriaFilm> tutti() {
		return (List<CategoriaFilm>) categoriaFilmRepo.findAll();
	}

	@Transactional
	public Set<String> tuttiNomi() {
		List<CategoriaFilm> categorie = (List<CategoriaFilm>) categoriaFilmRepo.findAll();
		Set<String> nomi = new HashSet<>();
		for(CategoriaFilm categoria : categorie) {
			nomi.add(categoria.getNome());
		}
		return nomi;
	}

	@Transactional
	public CategoriaFilm categoriaFilmPerId(Long id) {
		Optional<CategoriaFilm> optional = categoriaFilmRepo.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public boolean alreadyExists(CategoriaFilm categoriaFilm) {
		
		if (this.categoriaFilmRepo.findByNomeAndEdizione(categoriaFilm.getNome(), categoriaFilm.getEdizione()) != null)
			return true;
		else 
			return false;
	}

	@Transactional
	public Set<String> findTuttiNomiCategoriaArtisti() {
		
		return this.categoriaArtistaService.tuttiNomi();
	}

	

}
