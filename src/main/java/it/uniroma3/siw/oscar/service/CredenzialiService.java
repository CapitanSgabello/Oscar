package it.uniroma3.siw.oscar.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.oscar.model.Credenziali;
import it.uniroma3.siw.oscar.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	
	@org.springframework.beans.factory.annotation.Autowired(required=true)
    protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredenzialiRepository credenzialiRepository;
	
	@Transactional
	public Credenziali getCredenziali(Long id) {
		Optional<Credenziali> risultato = this.credenzialiRepository.findById(id);
		return risultato.orElse(null);
	}

	@Transactional
	public Credenziali getCredenziali(String username) {
		Optional<Credenziali> risultati = this.credenzialiRepository.findByUsername(username);
		return risultati.orElse(null);
	}
		
    @Transactional
    public Credenziali saveCredenziali(Credenziali credenziali) {
    	credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
    	credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
        return this.credenzialiRepository.save(credenziali);
    }
}
