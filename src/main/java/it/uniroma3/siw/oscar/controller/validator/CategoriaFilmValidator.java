package it.uniroma3.siw.oscar.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.oscar.model.CategoriaFilm;
import it.uniroma3.siw.oscar.service.CategoriaFilmService;

@Component
public class CategoriaFilmValidator implements Validator{

	@Autowired
	private CategoriaFilmService categoriaFilmService;

	private static final Logger logger = LoggerFactory.getLogger(CategoriaFilmValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return CategoriaFilm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "edizione", "required");

		if(!errors.hasErrors()) {
			logger.debug("I valori inseriti sono validi.");
			if(this.categoriaFilmService.alreadyExists((CategoriaFilm)target)) {
				logger.debug("CategoriaFilm già presente.");
				errors.reject("duplicato");
			}
		}

	}

}
