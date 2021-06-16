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

import it.uniroma3.siw.oscar.controller.validator.CategoriaFilmValidator;
import it.uniroma3.siw.oscar.model.CategoriaArtista;
import it.uniroma3.siw.oscar.model.CategoriaFilm;
import it.uniroma3.siw.oscar.model.Credenziali;
import it.uniroma3.siw.oscar.model.Film;
import it.uniroma3.siw.oscar.service.CategoriaFilmService;

@Controller
public class CategoriaFilmController {
	
	@Autowired
	private CategoriaFilmService categoriaFilmService;

	@Autowired
	private CategoriaFilmValidator categoriaFilmValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/categorie", method = RequestMethod.GET)
	public String getCategorie(Model model) {
		model.addAttribute("categorieFilm", this.categoriaFilmService.tuttiNomi());
		model.addAttribute("categorieArtisti", this.categoriaFilmService.findTuttiNomiCategoriaArtisti());
		return "categorie.html";
	}

	@RequestMapping(value = "/categoriaFilm/{nome}", method = RequestMethod.GET)
	public String getCategorieFilm(@PathVariable("nome") String nome, Model model) {
		model.addAttribute("categorieFilm", this.categoriaFilmService.cercaPerNome(nome));
		return "categorieFilm.html";
	}

	@RequestMapping(value = "/categoriaF/{id}", method = RequestMethod.GET)
	public String getCategoriaFilm(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		model.addAttribute("categoria", this.categoriaFilmService.categoriaFilmPerId(id));
		if(request.getUserPrincipal() != null) {
			Credenziali credenziali = categoriaFilmService.getCredenziali(request.getUserPrincipal().getName());

			if (credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
				model.addAttribute("admin", true);
			}
		}
		return "categoriaFilm.html";
	}
	
	@RequestMapping(value="/addCategoriaFilm", method = RequestMethod.GET)
	public String addCategoriaFilm(Model model) {
		logger.debug("addCategoriaFilm");
		model.addAttribute("categoriaFilm", new CategoriaArtista());
		model.addAttribute("edizioni", this.categoriaFilmService.tutteLeEdizioni());
		return "CategoriaFilmForm.html";
	}
	
	@RequestMapping(value = "/categoriaFilm", method = RequestMethod.POST)
	public String newCategoriaFilm(@ModelAttribute("categoriaFilm") CategoriaFilm categoria, 
			Model model, BindingResult bindingResult) {
		this.categoriaFilmValidator.validate(categoria, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.categoriaFilmService.save(categoria);
			model.addAttribute("categorieFilm", this.categoriaFilmService.tutti());
			return "categorieFilm.html";
		}
		model.addAttribute("edizioni", this.categoriaFilmService.tutteLeEdizioni());
		return "CategoriaFilmForm.html";
	}
	
	@RequestMapping(value="/addVincitoreToCategoriaF/{id}", method = RequestMethod.GET)
	public String addVincitoreToCategoriaF(@PathVariable("id") Long id, Model model) {
		logger.debug("addVincitoreToCategoriaF");
		CategoriaFilm categoria = categoriaFilmService.categoriaFilmPerId(id);
		model.addAttribute("categoria", categoria);
		model.addAttribute("candidati", categoria.getCandidati());
		return "vincitoreFilmForm.html";
	}
	
	@RequestMapping(value = "/newVincitoreToCategoriaF", method = RequestMethod.POST)
	public String newVincitoreToCategoriaF(@RequestParam("id") Long filmId, @ModelAttribute("categoria") CategoriaFilm categoria, Model model) {
		categoria.setVincitore(this.categoriaFilmService.filmPerId(filmId));
		this.categoriaFilmService.save(categoria);
		model.addAttribute("categoria", categoria);
		model.addAttribute("admin", true);
		return "categoriaFilm.html";
	}
	
	@RequestMapping(value="/addCandidatoToCategoriaF/{id}", method = RequestMethod.GET)
	public String addCandidatoToCategoriaF(@PathVariable("id") Long id, Model model) {
		logger.debug("addCandidatoToCategoriaF");
		model.addAttribute("categoriaFilmId", id);
		model.addAttribute("films", this.categoriaFilmService.tuttiIFilm());
		return "candidatoFilmForm.html";
	}
	
	@RequestMapping(value = "/newCandidatoInCategoriaF/{categoriaFilmId}/{candidatoId}", method = RequestMethod.GET)
	public String newCandidatoInCategoriaF(@PathVariable("categoriaFilmId") Long categoriaId, @PathVariable("candidatoId") Long candidatoId, Model model) {
		Film candidato = this.categoriaFilmService.filmPerId(candidatoId);
		CategoriaFilm categoria = this.categoriaFilmService.categoriaFilmPerId(categoriaId);
		categoria.getCandidati().add(candidato);
		this.categoriaFilmService.save(categoria);
		model.addAttribute("categoria", categoria);
		model.addAttribute("admin", true);
		return "categoriaFilm.html";
	}

	@RequestMapping(value="/deleteCategoriaFilm", method = RequestMethod.GET)
	public String deleteCategoriaFilm(Model model) {
		logger.debug("deleteCategoriaFilm");
		model.addAttribute("categorieFilm", this.categoriaFilmService.tutti());
		return "categoriaFilmDelete.html";
	}

	@RequestMapping(value = "/deleteCategoriaFilm", method = RequestMethod.POST)
	public String delete(@RequestParam("id") Long categoriaFilmId, Model model) {
		CategoriaFilm categoriaFilm = this.categoriaFilmService.categoriaFilmPerId(categoriaFilmId);
		this.categoriaFilmService.delete(categoriaFilm);
		return "admin/home";
	}


}
