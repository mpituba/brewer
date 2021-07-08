package com.algaworks.brewer.model;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

public class Cerveja {
	
	@NotEmpty(message = "SKU é obrigatório!")
	private String sku;
	
	@NotEmpty(message = "Nome é obrigatório!")
	private String nome;
	
	@NotEmpty(message = "A descrição é obrigatória!")
	@Size(max = 50, message = "A descrição tem mais de 50 caracteres")
	private String descricao;
	
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public String toString() {
		return "Cerveja [sku=" + sku + ", nome=" + nome + ", descricao=" + descricao + "]";
	}
	
	//ToString
	
	
}
