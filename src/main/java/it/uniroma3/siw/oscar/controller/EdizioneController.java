package it.uniroma3.siw.oscar.controller;

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

import it.uniroma3.siw.oscar.controller.validator.EdizioneValidator;
import it.uniroma3.siw.oscar.model.Edizione;
import it.uniroma3.siw.oscar.model.Film;
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
	
	@RequestMapping(value="/addEdizione", method = RequestMethod.GET)
	public String addEdizione(Model model) {
		logger.debug("addEdizione");
		model.addAttribute("artisti", this.edizioneService.tuttiArtisti());
		return "presentatoreForm.html";
	}
	
	@RequestMapping(value="/addEdizione/{presentatoreId}", method = RequestMethod.GET)
	public String submitEdizione(@PathVariable("presentatoreId") Long id, Model model) {
		Edizione edizione = new Edizione();
		model.addAttribute("presentatoreId", id);
		model.addAttribute("edizione", edizione);
		return "EdizioneForm.html";
	}
	
	@RequestMapping(value = "/newEdizione/{presentatoreId}", method = RequestMethod.POST)
	public String newEdizione(@PathVariable("presentatoreId") Long id, @ModelAttribute("edizione") Edizione edizione, 
			Model model, BindingResult bindingResult) {
		this.edizioneValidator.validate(edizione, bindingResult);
		if (!bindingResult.hasErrors()) {
			edizione.setPresentatore(this.edizioneService.artistaPerId(id));
			this.edizioneService.save(edizione);
			model.addAttribute("edizioni", this.edizioneService.tutti());
			return "edizioni.html";
		}
		return "edizioneForm.html";
	}

	@RequestMapping(value="/deleteEdizione", method = RequestMethod.GET)
	public String deleteEdizione(Model model) {
		logger.debug("deleteEdizione");
		model.addAttribute("edizioni", this.edizioneService.tutti());
		return "edizioneDelete.html";
	}

	@RequestMapping(value = "/deleteEdizione", method = RequestMethod.POST)
	public String delete(@RequestParam("edizioneId") Long edizioneId, Model model) {
		Edizione edizione = this.edizioneService.edizionePerId(edizioneId);
		this.edizioneService.delete(edizione);
		return "admin/home";
	}


}
