package com.centraltech.calculator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListNotEmptyValidator.class)
@Documented
public @interface ListNotEmpty {
	
    String message() default "List should not be empty";
    
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};

}
