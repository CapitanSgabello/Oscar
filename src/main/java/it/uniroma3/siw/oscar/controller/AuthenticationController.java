package it.uniroma3.siw.oscar.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.oscar.controller.validator.CredenzialiValidator;
import it.uniroma3.siw.oscar.controller.validator.UtenteValidator;
import it.uniroma3.siw.oscar.model.Credenziali;
import it.uniroma3.siw.oscar.model.Utente;
import it.uniroma3.siw.oscar.service.CredenzialiService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredenzialiService credenzialiService;

	@Autowired
	private UtenteValidator utenteValidator;

	@Autowired
	private CredenzialiValidator credenzialiValidator;

	@RequestMapping(value = "/registrazione", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credenziali", new Credenziali());
		return "registraUtente";
	}

	@RequestMapping(value = { "/registrazione" }, method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("utente") Utente utente,
			BindingResult userBindingResult,
			@ModelAttribute("credenziali") Credenziali credenziali,
			BindingResult credentialsBindingResult,
			Model model) {

		this.utenteValidator.validate(utente, userBindingResult);
		this.credenzialiValidator.validate(credenziali, credentialsBindingResult);

		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			credenziali.setUtente(utente);
			credenzialiService.saveCredenziali(credenziali);
			return "registrazioneAvvenuta"; 
		}
		return "registraUtente";
	}

	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String defaultAfterLogin(Model model, HttpServletRequest request) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());

		String username = request.getUserPrincipal().getName();
		model.addAttribute("utente", this.credenzialiService.getCredenziali(username).getUtente());

		if (credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
			return "admin/home";
		}
		return "areaPersonale.html";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
		return "loginForm";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index";
	}

	public Boolean isUtenteLoggato() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null || AnonymousAuthenticationToken.class.isAssignableFrom(getClass())) {
			return false;
		}
		return auth.isAuthenticated();
	}
}
