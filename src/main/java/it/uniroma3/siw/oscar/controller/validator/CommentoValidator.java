package it.uniroma3.siw.oscar.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.oscar.model.Commento;
import it.uniroma3.siw.oscar.service.CommentoService;



@Component
public class CommentoValidator implements Validator{

	@Autowired
	private CommentoService commentoService;

	private static final Logger logger = LoggerFactory.getLogger(CommentoValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Commento.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testo", "required");

		if(!errors.hasErrors()) {
			logger.debug("I valori inseriti sono validi.");
		}

	}

}
