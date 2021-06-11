package it.uniroma3.siw.oscar.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.oscar.model.CategoriaFilm;
import it.uniroma3.siw.oscar.model.Edizione;
import it.uniroma3.siw.oscar.model.Film;

@Repository
public interface CategoriaFilmRepository extends CrudRepository<CategoriaFilm, Long> {

	public List<CategoriaFilm> findByNome(String nome);

	public List<CategoriaFilm> findByVincitore(Film film);
	
	public CategoriaFilm findByNomeAndEdizione(String nome, Edizione edizione);

}
