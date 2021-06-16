package it.uniroma3.siw.oscar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.model.Edizione;
import it.uniroma3.siw.oscar.repository.EdizioneRepository;

@Service
public class EdizioneService {

	@Autowired
	private EdizioneRepository edizioneRepo;

	@Autowired
	private ArtistaService artistaService;

	@Transactional
	public Edizione save(Edizione edizione) {
		return edizioneRepo.save(edizione);
	}

	@Transactional
	public void delete(Edizione edizione) {
		edizioneRepo.delete(edizione);
	}

	@Transactional
	public Edizione cercaPerAnno(Long anno) {
		return edizioneRepo.findByAnno(anno);	
	}

	@Transactional
	public List<Edizione> tutti() {
		return (List<Edizione>) edizioneRepo.findAll();
	}

	@Transactional
	public Edizione edizionePerId(Long id) {
		Optional<Edizione> optional = edizioneRepo.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Edizione edizione) {

		if (this.edizioneRepo.findByAnno(edizione.getAnno()) != null)
			return true;
		else 
			return false;
	}

	@Transactional
	public List<Artista> tuttiArtisti(){
		return this.artistaService.tutti();
	}

	@Transactional
	public Artista artistaPerId(Long id) {
		return this.artistaService.artistaPerId(id);
	}



}
