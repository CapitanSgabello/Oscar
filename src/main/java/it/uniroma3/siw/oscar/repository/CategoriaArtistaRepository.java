package it.uniroma3.siw.oscar.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.model.CategoriaArtista;
import it.uniroma3.siw.oscar.model.Edizione;

@Repository
public interface CategoriaArtistaRepository extends CrudRepository<CategoriaArtista, Long> {

	public List<CategoriaArtista> findByNome(String nome);

	public List<CategoriaArtista> findByVincitore(Artista artista);

	public CategoriaArtista findByNomeAndEdizione(String nome, Edizione edizione);

}
