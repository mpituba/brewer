package com.algaworks.brewer.repository.filter;

/**
 * Objeto Filtro passado para fazer a pesquisa de estilo, é o valor do fitro
 * da página de busca. @author mpituba
 */

public class EstiloFilter {
	
	private Long codigo;
	
	private String nome;

	
	//Getter and Setter
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
	
}
