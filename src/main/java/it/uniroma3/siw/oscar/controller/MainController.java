package it.uniroma3.siw.oscar.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.oscar.model.Utente;
import it.uniroma3.siw.oscar.service.CredenzialiService;

@Controller
public class MainController {
	
	@Autowired
	private CredenzialiService credenzialiService;
	
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {		
		if(request.getUserPrincipal() != null) {
			model.addAttribute("utente", credenzialiService.getCredenziali(request.getUserPrincipal().getName()).getUtente());
		}
			return "index.html";
	}
}
