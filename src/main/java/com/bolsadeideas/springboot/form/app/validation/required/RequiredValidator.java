package com.bolsadeideas.springboot.form.app.validation.required;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class RequiredValidator implements ConstraintValidator<Required,String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null || !StringUtils.hasText(value) || StringUtils.containsWhitespace(value)) {
			return false;
		}
		return true;
	}

}
