package it.uniroma3.siw.oscar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.oscar.model.Utente;
import it.uniroma3.siw.oscar.repository.UtenteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

	@Autowired
	protected UtenteRepository utenteRepository;

	@Transactional
	public Utente getUtenteById(Long id) {
		Optional<Utente> risultato = this.utenteRepository.findById(id);
		return risultato.orElse(null);
	}

	@Transactional
	public Utente getUtenteByUsername(String username) {
		Optional<Utente> risultato = this.utenteRepository.findByNome(username);
		return risultato.orElse(null);
	}

	@Transactional
	public Utente saveUtente(Utente utente) {
		return this.utenteRepository.save(utente);
	}

	@Transactional
	public List<Utente> getAllUtenti() {
		List<Utente> risultato = new ArrayList<>();
		Iterable<Utente> iterable = this.utenteRepository.findAll();
		for(Utente utente : iterable)
			risultato.add(utente);
		return risultato;
	}
}
