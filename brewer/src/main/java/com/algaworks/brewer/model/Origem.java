package com.algaworks.brewer.model;

public enum Origem {

	NACIONAL("Nacional"),
	INTERNACIONAL("Internacional");
	
	private String descricao;
	
	//Construtor para funcionar a descricao do enum
	Origem(String descricao){
		this.descricao = descricao;
	}
	
	//Getter da descricao para o enem como é uma constante não precisa de setter
	public String getDescricao() {
		return descricao;
	}

}
