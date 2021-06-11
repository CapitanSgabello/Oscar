package it.uniroma3.siw.oscar.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.oscar.model.Utente;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Long> {

	Optional<Utente> findByNome(String nome);

}