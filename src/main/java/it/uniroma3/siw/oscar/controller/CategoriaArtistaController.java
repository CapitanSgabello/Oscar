package it.uniroma3.siw.oscar.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.oscar.controller.validator.CategoriaArtistaValidator;
import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.model.CategoriaArtista;
import it.uniroma3.siw.oscar.model.Credenziali;
import it.uniroma3.siw.oscar.service.CategoriaArtistaService;

@Controller
public class CategoriaArtistaController {
	
	@Autowired
	private CategoriaArtistaService categoriaArtistaService;

	@Autowired
	private CategoriaArtistaValidator categoriaArtistaValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/categoriaArtista/{nome}", method = RequestMethod.GET)
	public String getCategorieArtisti(@PathVariable("nome") String nome, Model model) {
		model.addAttribute("categorieArtisti", this.categoriaArtistaService.cercaPerNome(nome));
		return "categorieArtisti.html";
	}

	@RequestMapping(value = "/categoriaA/{id}", method = RequestMethod.GET)
	public String getCategoriaArtista(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		model.addAttribute("categoriaArtista", this.categoriaArtistaService.categoriaArtistaPerId(id));
		if(request.getUserPrincipal() != null) {
			Credenziali credenziali = categoriaArtistaService.getCredenziali(request.getUserPrincipal().getName());

			if (credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
				model.addAttribute("admin", true);
			}
		}
		return "categoriaArtista.html";
	}
	
	@RequestMapping(value="/addCategoriaArtista", method = RequestMethod.GET)
	public String addCategoriaArtista(Model model) {
		logger.debug("addCategoriaArtista");
		model.addAttribute("categoriaArtista", new CategoriaArtista());
		model.addAttribute("edizioni", this.categoriaArtistaService.tutteLeEdizioni());
		return "CategoriaArtistaForm.html";
	}
	
	@RequestMapping(value = "/categoriaArtista", method = RequestMethod.POST)
	public String newCategoriaArtista(@ModelAttribute("categoriaArtista") CategoriaArtista categoria, 
			Model model, BindingResult bindingResult) {
		this.categoriaArtistaValidator.validate(categoria, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.categoriaArtistaService.save(categoria);
			model.addAttribute("categorieArtisti", this.categoriaArtistaService.tutti());
			return "categorieArtisti.html";
		}
		model.addAttribute("edizioni", this.categoriaArtistaService.tutteLeEdizioni());
		return "CategoriaArtistaForm.html";
	}
	
	@RequestMapping(value="/addVincitoreToCategoriaA/{id}", method = RequestMethod.GET)
	public String addVincitoreToCategoriaA(@PathVariable("id") Long id, Model model) {
		logger.debug("addVincitoreToCategoriaA");
		CategoriaArtista categoria = categoriaArtistaService.categoriaArtistaPerId(id);
		model.addAttribute("categoriaArtista", categoria);
		model.addAttribute("candidati", categoria.getCandidati());
		return "vincitoreArtistaForm.html";
	}
	
	@RequestMapping(value = "/newVincitoreToCategoriaA", method = RequestMethod.POST)
	public String newVincitoreToCategoriaA(@ModelAttribute("categoriaArtista") CategoriaArtista categoria, Model model) {
		this.categoriaArtistaService.save(categoria);
		model.addAttribute("categoriaArtista", categoria);
		model.addAttribute("admin", true);
		return "categoriaArtista.html";
	}
	
	@RequestMapping(value="/addCandidatoToCategoriaA/{id}", method = RequestMethod.GET)
	public String addCandidatoToCategoriaA(@PathVariable("id") Long id, Model model) {
		logger.debug("addCandidatoToCategoriaA");
		model.addAttribute("categoriaArtistaId", id);
		model.addAttribute("artisti", this.categoriaArtistaService.tuttiArtisti());
		return "candidatoArtistaForm.html";
	}
	
	@RequestMapping(value = "/newCandidatoInCategoriaA/{categoriaArtistaId}/{candidatoId}", method = RequestMethod.GET)
	public String newCandidatoInCategoriaA(@PathVariable("categoriaArtistaId") Long categoriaId, @PathVariable("candidatoId") Long candidatoId, Model model) {
		Artista candidato = this.categoriaArtistaService.artistaPerId(candidatoId);
		CategoriaArtista categoria = this.categoriaArtistaService.categoriaArtistaPerId(categoriaId);
		categoria.getCandidati().add(candidato);
		this.categoriaArtistaService.save(categoria);
		model.addAttribute("categoriaArtista", categoria);
		model.addAttribute("admin", true);
		return "categoriaArtista.html";
	}
	
	

}
