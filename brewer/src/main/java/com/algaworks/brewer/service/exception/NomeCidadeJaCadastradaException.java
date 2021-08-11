package com.algaworks.brewer.service.exception;

/** 
 * Exceção lançada na tela de salvamento do cadastro de cidade para quando uma
 * cidade já existe em um Estado, lançando a exceção. Lógica no cadastroCidadeService 
 * @author mpituba
 */
public class NomeCidadeJaCadastradaException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public NomeCidadeJaCadastradaException(String message) {
		super(message);
	}
}
