package it.uniroma3.siw.oscar.controller;

import java.util.List;

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

import it.uniroma3.siw.oscar.controller.validator.ArtistaValidator;
import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.service.ArtistaService;

@Controller
public class ArtistaController {

	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private ArtistaValidator artistaValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/artisti", method = RequestMethod.GET)
	public String getArtisti(Model model) {
		model.addAttribute("artisti", this.artistaService.tutti());
		return "artisti.html";
	}

	@RequestMapping(value = "/artisti", method = RequestMethod.POST)
	public String getArtistiByCognome(@RequestParam("cognome") String input, Model model) {
		input= input.toLowerCase();
		input = input.substring(0, 1).toUpperCase() + input.substring(1);
		List<Artista> artisti = this.artistaService.cercaPerCognome(input);
		artisti.addAll(this.artistaService.cercaPerNome(input));
		model.addAttribute("artisti", artisti);
		return "artisti.html";
	}
	
	@RequestMapping(value = "/artista/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artista", this.artistaService.artistaPerId(id));
		return "artista.html";
	}
	
	@RequestMapping(value="/addArtista", method = RequestMethod.GET)
	public String addArtista(Model model) {
		logger.debug("addArtista");
		model.addAttribute("artista", new Artista());
		return "artistaForm.html";
	}
	
	@RequestMapping(value = "/artista", method = RequestMethod.POST)
	public String newArtista(@ModelAttribute("artista") Artista artista, 
			Model model, BindingResult bindingResult) {
		this.artistaValidator.validate(artista, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.artistaService.save(artista);
			model.addAttribute("artisti", this.artistaService.tutti());
			return "artisti.html";
		}
		return "artistaForm.html";
	}

	@RequestMapping(value="/deleteArtista", method = RequestMethod.GET)
	public String deleteArtista(Model model) {
		logger.debug("deleteArtista");
		model.addAttribute("artisti", this.artistaService.tutti());
		return "artistaDelete.html";
	}
	
	@RequestMapping(value = "/deleteArtista", method = RequestMethod.POST)
	public String delete(@RequestParam("id") Long artistaId, 
			Model model) {
		Artista artista = this.artistaService.artistaPerId(artistaId);
		this.artistaService.delete(artista);
		return "admin/home";
	}
}
