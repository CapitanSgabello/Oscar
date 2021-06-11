package it.uniroma3.siw.oscar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.oscar.model.Edizione;

@Repository
public interface EdizioneRepository extends CrudRepository<Edizione, Long> {
	
	public Edizione findByAnno(Long anno);

}
