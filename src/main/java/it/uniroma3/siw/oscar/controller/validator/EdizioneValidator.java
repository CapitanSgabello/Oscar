package it.uniroma3.siw.oscar.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.oscar.model.Edizione;
import it.uniroma3.siw.oscar.service.EdizioneService;

@Component
public class EdizioneValidator implements Validator{

	@Autowired
	private EdizioneService edizioneService;

	private static final Logger logger = LoggerFactory.getLogger(EdizioneValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Edizione.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "anno", "required");

		if(!errors.hasErrors()) {
			logger.debug("I valori inseriti sono validi.");
			if(this.edizioneService.alreadyExists((Edizione)target)) {
				logger.debug("Edizione già presente.");
				errors.reject("duplicato");
			}
		}

	}

}
