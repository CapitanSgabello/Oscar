package it.uniroma3.siw.oscar.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.oscar.model.CategoriaArtista;
import it.uniroma3.siw.oscar.service.CategoriaArtistaService;

@Component
public class CategoriaArtistaValidator implements Validator{

	@Autowired
	private CategoriaArtistaService categoriaArtistaService;

	private static final Logger logger = LoggerFactory.getLogger(CategoriaArtistaValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return CategoriaArtista.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "edizione", "required");

		if(!errors.hasErrors()) {
			logger.debug("I valori inseriti sono validi.");
			if(this.categoriaArtistaService.alreadyExists((CategoriaArtista)target)) {
				logger.debug("CategoriaArtista già presente.");
				errors.reject("duplicato");
			}
		}

	}

}
