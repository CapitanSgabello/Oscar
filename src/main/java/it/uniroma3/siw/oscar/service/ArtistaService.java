package it.uniroma3.siw.oscar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepo;
	
	@Transactional
	public Artista save(Artista artista) {
		return artistaRepo.save(artista);
	}
	
	@Transactional
	public void delete(Artista artista) {
		 artistaRepo.delete(artista);
	}
	
	@Transactional
	public List<Artista> cercaPerCognome(String cognome) {
		return artistaRepo.findByCognome(cognome);	
	}
	
	@Transactional
	public List<Artista> cercaPerNomeECognome(String nome, String cognome){
		return artistaRepo.findByNomeAndCognome(nome, cognome);		
	}
	
	@Transactional
	public List<Artista> cercaPerNome(String nome){
		return artistaRepo.findByNome(nome);
	}

	@Transactional
	public List<Artista> tutti() {
		return (List<Artista>) artistaRepo.findAll();
	}

	@Transactional
	public Artista artistaPerId(Long id) {
		Optional<Artista> optional = artistaRepo.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public boolean alreadyExists(Artista artista) {
		List<Artista> artisti = this.artistaRepo.findByNomeAndCognome(artista.getNome(), artista.getCognome());
		if (artisti.size() > 0)
			return true;
		else 
			return false;
	}

	

}
