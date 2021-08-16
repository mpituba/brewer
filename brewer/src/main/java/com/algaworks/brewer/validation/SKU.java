package com.algaworks.brewer.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/**
 * Classe Utilizada para criar o validador @SKU,
 * usado para as regras de validação do campo SKU. @author mpituba
 */

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "([a-zA-Z]{2}\\d{4})?")
public @interface SKU {
	
	//Sobreescrita da mensagem de erro apresentada na tela
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "SKU deve seguir o padrão XX9999";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[]  payload() default {};
	
}
