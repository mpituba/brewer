package com.algaworks.brewer.model;

public enum Sabor {

	ADOCICADA("Adocicada"),
	AMARGA("Amarga"),
	FORTE("Forte"),
	FRUTADA("Frutada"),
	SUAVE("Suave");
	
	//Atributo descricao do Enum
	private String descricao;
	
	//Contrutor da descricao do enum
	Sabor(String descricao){
		this.descricao = descricao;
	}
	
	//Getter do enum
	public String getDescricao() {
		return descricao;
		
	}
	
}
