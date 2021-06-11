package it.uniroma3.siw.oscar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.oscar.model.Utente;
import it.uniroma3.siw.oscar.repository.UtenteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The UserService handles logic for Users.
 */
@Service
public class UtenteService {

    @Autowired
    protected UtenteRepository utenteRepository;

    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
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

    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */
    @Transactional
    public Utente saveUtente(Utente utente) {
        return this.utenteRepository.save(utente);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    @Transactional
    public List<Utente> getAllUtenti() {
        List<Utente> risultato = new ArrayList<>();
        Iterable<Utente> iterable = this.utenteRepository.findAll();
        for(Utente utente : iterable)
            risultato.add(utente);
        return risultato;
    }
}
