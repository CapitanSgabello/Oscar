package it.uniroma3.siw.oscar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.model.Film;
import it.uniroma3.siw.oscar.repository.FilmRepository;

@Service
public class FilmService {
	
	@Autowired
	private FilmRepository filmRepo;
	
	@Transactional
	public Film save(Film film) {
		return filmRepo.save(film);
	}
	
	@Transactional
	public void delete(Film film) {
		 filmRepo.delete(film);
	}
	
	@Transactional
	public List<Film> cercaPerTitolo(String titolo) {
		return filmRepo.findByTitolo(titolo);	
	}
	
	@Transactional
	public List<Film> cercaPerRegista(Artista artista) {
		return filmRepo.findByRegista(artista);	
	}
	
	@Transactional
	public List<Film> cercaPerGenere(String genere) {
		return filmRepo.findByGenere(genere);	
	}

	@Transactional
	public List<Film> tutti() {
		return (List<Film>) filmRepo.findAll();
	}

	@Transactional
	public Film filmPerId(Long id) {
		Optional<Film> optional = filmRepo.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public boolean alreadyExists(Film film) {
		List<Film> films = this.filmRepo.findByTitoloAndDataUscita(film.getTitolo(), film.getDataUscita());
		if (films.size() > 0)
			return true;
		else 
			return false;
	}

	

}
