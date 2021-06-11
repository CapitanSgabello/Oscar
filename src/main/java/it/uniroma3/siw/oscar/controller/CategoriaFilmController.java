package it.uniroma3.siw.oscar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.oscar.controller.validator.CategoriaFilmValidator;
import it.uniroma3.siw.oscar.controller.validator.EdizioneValidator;
import it.uniroma3.siw.oscar.service.CategoriaFilmService;
import it.uniroma3.siw.oscar.service.EdizioneService;

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
	public String getCategoriaFilm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("categoriaFilm", this.categoriaFilmService.categoriaFilmPerId(id));
		return "categoriaFilm.html";
	}

}
