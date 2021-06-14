package it.uniroma3.siw.oscar.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.oscar.model.Commento;
import it.uniroma3.siw.oscar.model.Utente;

@Repository
public interface CommentoRepository extends CrudRepository<Commento, Long>{
	
	public List<Commento> findByUtente(Utente user);

}
