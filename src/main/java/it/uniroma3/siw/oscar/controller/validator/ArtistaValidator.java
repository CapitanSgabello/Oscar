package it.uniroma3.siw.oscar.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.oscar.model.Artista;
import it.uniroma3.siw.oscar.service.ArtistaService;

@Component
public class ArtistaValidator implements Validator{

	@Autowired
	private ArtistaService artistaService;

	private static final Logger logger = LoggerFactory.getLogger(ArtistaValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Artista.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");

		if(!errors.hasErrors()) {
			logger.debug("I valori inseriti sono validi.");
			if(this.artistaService.alreadyExists((Artista)target)) {
				logger.debug("Artista già presente.");
				errors.reject("duplicato");
			}
		}

	}

}
