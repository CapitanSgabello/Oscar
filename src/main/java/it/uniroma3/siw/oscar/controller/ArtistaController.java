package it.uniroma3.siw.oscar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.oscar.controller.validator.ArtistaValidator;
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
	
	@RequestMapping(value = "/artista/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artista", this.artistaService.artistaPerId(id));
		return "artista.html";
	}
}
