package it.uniroma3.siw.oscar.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.oscar.model.Film;
import it.uniroma3.siw.oscar.service.FilmService;

@Component
public class FilmValidator implements Validator{
	
	@Autowired
	private FilmService filmService;
	
	private static final Logger logger = LoggerFactory.getLogger(FilmValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Film.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titolo", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "genere", "required");
		
		if(!errors.hasErrors()) {
			logger.debug("I valori inseriti sono validi.");
			if(this.filmService.alreadyExists((Film)target)) {
				logger.debug("Film già presente.");
				errors.reject("duplicato");
			}
		}
		
	}

}
