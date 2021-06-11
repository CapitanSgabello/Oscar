package it.uniroma3.siw.oscar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.oscar.controller.validator.EdizioneValidator;
import it.uniroma3.siw.oscar.service.EdizioneService;

@Controller
public class EdizioneController {
	
	@Autowired
	private EdizioneService edizioneService;

	@Autowired
	private EdizioneValidator edizioneValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/edizioni", method = RequestMethod.GET)
	public String getEdizioni(Model model) {
		model.addAttribute("edizioni", this.edizioneService.tutti());
		return "edizioni.html";
	}

	@RequestMapping(value = "/edizione/{id}", method = RequestMethod.GET)
	public String getEdizione(@PathVariable("id") Long id, Model model) {
		model.addAttribute("edizione", this.edizioneService.edizionePerId(id));
		return "edizione.html";
	}

}
