package it.uniroma3.siw.oscar.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.oscar.model.Commento;
import it.uniroma3.siw.oscar.model.Credenziali;
import it.uniroma3.siw.oscar.model.Film;
import it.uniroma3.siw.oscar.model.Utente;
import it.uniroma3.siw.oscar.repository.CommentoRepository;

@Service
public class CommentoService {

		
		@Autowired
		private CommentoRepository commentoRepo;
		
		@Autowired
		private CredenzialiService credenzialiService;
		
		@Autowired
		private FilmService filmService;
		
		@Transactional
		public Commento save(Commento commento) {
			return commentoRepo.save(commento);
		}
		@Transactional
		public void delete(Commento commento) {
			commentoRepo.delete(commento);
		}
		
		@Transactional
		public List<Commento> cercaPerUtente(Utente utente) {
			return commentoRepo.findByUtente(utente);	
		}
		
		@Transactional
		public Film cercaFilmPerId(Long id) {
			return  filmService.filmPerId(id);	
		}
		
		@Transactional
		public List<Commento> tutti() {
			return (List<Commento>)commentoRepo.findAll();
		}

		@Transactional
		public Commento commentoPerId(Long id) {
			Optional<Commento> recensione = commentoRepo.findById(id);
			if (recensione.isPresent())
				return recensione.get();
			else 
				return null;
		}

		public Credenziali cercaCredenzialiPerUsername(String username) {
			return credenzialiService.getCredenziali(username);
		}

	}


