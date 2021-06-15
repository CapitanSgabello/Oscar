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

import it.uniroma3.siw.oscar.controller.validator.FilmValidator;
import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.model.Credenziali;
import it.uniroma3.siw.oscar.model.Film;
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
	
	@RequestMapping(value = "/film/genere/{genere}", method = RequestMethod.GET)
	public String getFilmsByGenere(@PathVariable("genere") String genere, Model model) {
		model.addAttribute("films", this.filmService.cercaPerGenere(genere));
		model.addAttribute("genereFilm", genere);
		return "films.html";
	}

	/*
	@RequestMapping(value = "/film", method = RequestMethod.POST)
	public String getFilmsByTitolo(@RequestParam("titolo") String titolo, Model model) {
		titolo = titolo.toLowerCase();
		titolo = titolo.substring(0, 1).toUpperCase() + titolo.substring(1);
		model.addAttribute("films", this.filmService.cercaPerTitolo(titolo));
		return "films.html";
	}
	*/

	@RequestMapping(value = "/film/{id}", method = RequestMethod.GET)
	public String getFilm(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		model.addAttribute("film", this.filmService.filmPerId(id));

		if(request.getUserPrincipal() != null) {
			Credenziali credenziali = filmService.getCredenziali(request.getUserPrincipal().getName());

			if (credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
				model.addAttribute("admin", true);
			}
		}
		return "film.html";
	}

	@RequestMapping(value="/addFilm", method = RequestMethod.GET)
	public String addFilm(Model model) {
		logger.debug("addFilm");
		model.addAttribute("artisti", this.filmService.tuttiArtisti());
		return "registaForm.html";
	}

	@RequestMapping(value="/addFilm/{registaId}", method = RequestMethod.GET)
	public String submitFilm(@PathVariable("registaId") Long id, Model model) {
		Film film = new Film();
		model.addAttribute("registaId", id);
		model.addAttribute("film", film);
		return "filmForm.html";
	}

	@RequestMapping(value = "/newFilm/{registaId}", method = RequestMethod.POST)
	public String newFilm(@PathVariable("registaId") Long id, @ModelAttribute("film") Film film, 
			Model model, BindingResult bindingResult) {
		this.filmValidator.validate(film, bindingResult);
		if (!bindingResult.hasErrors()) {
			film.setRegista(this.filmService.artistaPerId(id));
			this.filmService.save(film);
			model.addAttribute("films", this.filmService.tutti());
			return "films.html";
		}
		return "filmForm.html";
	}

	@RequestMapping(value="/deleteFilm", method = RequestMethod.GET)
	public String deleteFilm(Model model) {
		logger.debug("deleteFilm");
		model.addAttribute("films", this.filmService.tutti());
		return "filmDelete.html";
	}

	@RequestMapping(value = "/deleteFilm", method = RequestMethod.POST)
	public String delete(@RequestParam("id") Long filmId, 
			Model model) {
		Film film = this.filmService.filmPerId(filmId);
		this.filmService.delete(film);
		return "admin/home";
	}

	@RequestMapping(value="/addAttoreToFilm/{id}", method = RequestMethod.GET)
	public String addAttoreToFilm(@PathVariable("id") Long id, Model model) {
		logger.debug("addAttoreToFilm");
		model.addAttribute("filmId", id);
		model.addAttribute("artisti", this.filmService.tuttiArtisti());
		return "attoreForm.html";
	}

	@RequestMapping(value = "/newAttoreInFilm/{filmId}/{attoreId}", method = RequestMethod.GET)
	public String newAttoreInFilm(@PathVariable("filmId") Long filmId, @PathVariable("attoreId") Long attoreId, Model model) {
		Artista attore = this.filmService.artistaPerId(attoreId);
		Film film = this.filmService.filmPerId(filmId);
		film.getAttori().add(attore);
		this.filmService.save(film);
		model.addAttribute("film", film);
		model.addAttribute("admin", true);
		return "film.html";
	}

	@RequestMapping(value="/addOperatoreToFilm/{id}", method = RequestMethod.GET)
	public String addOperatoreToFilm(@PathVariable("id") Long id, Model model) {
		logger.debug("addOperatoreToFilm");
		model.addAttribute("filmId", id);
		model.addAttribute("artisti", this.filmService.tuttiArtisti());
		return "operatoreForm.html";
	}

	@RequestMapping(value = "/newOperatoreInFilm/{filmId}/{operatoreId}", method = RequestMethod.GET)
	public String newOperatoreInFilm(@PathVariable("filmId") Long filmId, @PathVariable("operatoreId") Long operatoreId, Model model) {
		Artista operatore = this.filmService.artistaPerId(operatoreId);
		Film film = this.filmService.filmPerId(filmId);
		film.getOperatoriTecnici().add(operatore);
		this.filmService.save(film);
		model.addAttribute("film", film);
		model.addAttribute("admin", true);
		return "film.html";
	}
}
