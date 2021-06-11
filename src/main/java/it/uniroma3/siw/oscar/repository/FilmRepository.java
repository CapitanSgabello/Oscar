package it.uniroma3.siw.oscar.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.model.Film;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {

	public List<Film> findByTitolo(String titolo);

	public List<Film> findByRegista(Artista artista);

	public List<Film> findByGenere(String genere);

	public List<Film> findByTitoloAndDataUscita(String titolo, LocalDate dataUscita);

}
