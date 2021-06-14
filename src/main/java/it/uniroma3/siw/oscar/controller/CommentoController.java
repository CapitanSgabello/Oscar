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

import it.uniroma3.siw.oscar.controller.validator.CommentoValidator;
import it.uniroma3.siw.oscar.model.Commento;
import it.uniroma3.siw.oscar.model.Credenziali;
import it.uniroma3.siw.oscar.model.Film;
import it.uniroma3.siw.oscar.model.Utente;
import it.uniroma3.siw.oscar.service.CommentoService;
import it.uniroma3.siw.oscar.service.CredenzialiService;


@Controller
public class CommentoController {
	@Autowired
	private CommentoValidator commentoValidator;
	
	@Autowired
	private CommentoService commentoService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/addCommento/{id}", method = RequestMethod.GET)
	public String addCommento(@PathVariable("id") Long filmId, Model model ) {
		logger.debug("addCommento");
		model.addAttribute("commento", new Commento());
		model.addAttribute("film", commentoService.cercaFilmPerId(filmId) );
		
		return "commentoForm.html";
	}
	
	@RequestMapping(value = "/commento/{filmId}", method = RequestMethod.POST)
	public String newCommento(@PathVariable("filmId") Long filmId, @ModelAttribute("commento") Commento commento, 
			Model model, BindingResult bindingResult, HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		Utente user = this.commentoService.cercaCredenzialiPerUsername(username).getUtente();
		commento.setUtente(user);
		this.commentoValidator.validate(commento, bindingResult);
		if (!bindingResult.hasErrors()) {
			Film film = this.commentoService.cercaFilmPerId(filmId);
			commento.setFilm(film);
			this.commentoService.save(commento);
			model.addAttribute("film", film);
			model.addAttribute("utente", user);
			if(this.commentoService.cercaCredenzialiPerUsername(username).getRuolo().equals(Credenziali.ADMIN_ROLE)) {
				model.addAttribute("admin", 1); 
			}
			return "film.html";
		}
		return "commentoForm.html";
	}
	
	
	@RequestMapping(value = "/deleteCommento/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long commentoId, 
			Model model, HttpServletRequest request) {
		Commento commento = this.commentoService.commentoPerId(commentoId);
		String username = request.getUserPrincipal().getName();
		Credenziali credenziali = this.commentoService.cercaCredenzialiPerUsername(username);
		if(credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
			model.addAttribute("film", commento.getFilm());
			model.addAttribute("admin", 1);
			this.commentoService.delete(commento);
			
			return "film.html";
		} else {
			this.commentoService.delete(commento);
			model.addAttribute("utente", credenziali.getUtente());
			
			return "areaPersonale.html";
		}
		
		
		
	}
	
	//eliminazione commento da utente

}
