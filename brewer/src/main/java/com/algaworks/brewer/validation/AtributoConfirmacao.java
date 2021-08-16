package com.algaworks.brewer.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import com.algaworks.brewer.validation.validator.AtributoConfirmacaoValidator;

/**
 * Classe Utilizada para verificar se os atributos de senha e confirmção de 
 * senha são iguais.É passada uma classe em código Java que valida. @author mpituba
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AtributoConfirmacaoValidator.class})
public @interface AtributoConfirmacao {

		//Sobreescrita da mensagem de erro apresentada na tela
		@OverridesAttribute(constraint = Pattern.class, name = "message")
		String message() default "Atributos não conferem";
		
		Class<?>[] groups() default {};
		Class<? extends Payload>[]  payload() default {};	
		
		//Atributos recebidos para a validação
		String atributo();
		String atributoConfirmacao();
}
