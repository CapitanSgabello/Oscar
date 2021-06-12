package it.uniroma3.siw.oscar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.oscar.controller.validator.FilmValidator;
import it.uniroma3.siw.oscar.service.FilmService;



@Controller
public class FilmController {

	@Autowired
	private FilmService filmService;

	@Autowired
	private FilmValidator filmValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/film", method = RequestMethod.GET)
	public String getFilms(Model model) {
		model.addAttribute("films", this.filmService.tutti());
		return "films.html";
	}
	
	@RequestMapping(value = "/film/{id}", method = RequestMethod.GET)
	public String getFilm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("film", this.filmService.filmPerId(id));
		return "film.html";
	}
}
