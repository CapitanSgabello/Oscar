package it.uniroma3.siw.oscar.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.model.CategoriaArtista;
import it.uniroma3.siw.oscar.model.Credenziali;
import it.uniroma3.siw.oscar.model.Edizione;
import it.uniroma3.siw.oscar.repository.CategoriaArtistaRepository;

@Service
public class CategoriaArtistaService {

	@Autowired
	private CategoriaArtistaRepository categoriaArtistaRepo;

	@Autowired
	private EdizioneService edizioneService;

	@Autowired
	private CredenzialiService credenzialiService;

	@Autowired
	private ArtistaService artistaService;

	@Transactional
	public CategoriaArtista save(CategoriaArtista categoriaArtista) {
		return categoriaArtistaRepo.save(categoriaArtista);
	}

	@Transactional
	public void delete(CategoriaArtista categoriaArtista) {
		categoriaArtistaRepo.delete(categoriaArtista);
	}

	@Transactional
	public List<CategoriaArtista> cercaPerNome(String nome) {
		return categoriaArtistaRepo.findByNome(nome);	
	}

	@Transactional
	public List<CategoriaArtista> cercaPerVincitore(Artista artista){
		return categoriaArtistaRepo.findByVincitore(artista);		
	}

	@Transactional
	public List<CategoriaArtista> tutti() {
		return (List<CategoriaArtista>) categoriaArtistaRepo.findAll();
	}

	@Transactional
	public CategoriaArtista categoriaArtistaPerId(Long id) {
		Optional<CategoriaArtista> optional = categoriaArtistaRepo.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(CategoriaArtista categoriaArtista) {

		if (this.categoriaArtistaRepo.findByNomeAndEdizione(categoriaArtista.getNome(), categoriaArtista.getEdizione()) != null)
			return true;
		else 
			return false;
	}

	@Transactional
	public Set<String> tuttiNomi() {
		List<CategoriaArtista> categorie = (List<CategoriaArtista>) categoriaArtistaRepo.findAll();
		Set<String> nomi = new HashSet<>();
		for(CategoriaArtista categoria : categorie) {
			nomi.add(categoria.getNome());
		}
		return nomi;
	}

	@Transactional
	public List<Edizione> tutteLeEdizioni(){
		return this.edizioneService.tutti();
	}

	@Transactional
	public Credenziali getCredenziali(String username) {
		return this.credenzialiService.getCredenziali(username);
	}

	@Transactional
	public Artista artistaPerId(Long id) {
		return this.artistaService.artistaPerId(id);
	}

	@Transactional
	public List<Artista> tuttiArtisti(){
		return this.artistaService.tutti();
	}



}
