package com.algaworks.brewer.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Estilo {

	@NotEmpty(message="O nome deve ser prenchido!")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
