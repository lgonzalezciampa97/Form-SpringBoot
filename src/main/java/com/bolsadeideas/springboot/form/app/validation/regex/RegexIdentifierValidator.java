package com.bolsadeideas.springboot.form.app.validation.regex;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegexIdentifierValidator implements ConstraintValidator<RegexIdentifier, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value.matches("[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")) {
			return true;
		}
		return false;
	}

}
