package it.uniroma3.siw.oscar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.oscar.controller.validator.CategoriaArtistaValidator;
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
	public String getCategoriaArtista(@PathVariable("id") Long id, Model model) {
		model.addAttribute("categoriaArtista", this.categoriaArtistaService.categoriaArtistaPerId(id));
		return "categoriaArtista.html";
	}

}
