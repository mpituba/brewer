package com.algaworks.brewer.service.exception;


/**
 * Esta exceção é lançada quando um e-mail já está cadastrado no banco.
 * E é mostrada amigavelmente no frontend. 
 * @author mpituba
 */

public class EmailUsuarioJaCadastradoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public EmailUsuarioJaCadastradoException(String message) {
		super(message);
	}
	
}
