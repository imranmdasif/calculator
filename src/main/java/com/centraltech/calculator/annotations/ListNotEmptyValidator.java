package com.centraltech.calculator.annotations;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ListNotEmptyValidator implements ConstraintValidator<ListNotEmpty, List<?>> {
	
	@Override
	public void initialize(ListNotEmpty constraintAnnotation) {
		// Do nothing
	}

	@Override
	public boolean isValid(List<?> value, ConstraintValidatorContext context) {		
		if(value.isEmpty()) 
			return false;
		return true;
	}



}
