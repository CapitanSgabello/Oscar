package it.uniroma3.siw.oscar.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.oscar.model.Artista;

@Repository
public interface ArtistaRepository extends CrudRepository<Artista, Long> {

	public List<Artista> findByCognome(String cognome);

	public List<Artista> findByNomeAndCognome(String nome, String cognome);

}
