package com.algaworks.brewer.repository.filter;


import com.algaworks.brewer.model.Estado;


/**
 * Objeto Filtro passado para fazer a pesquisa de cidade, São os valores
 * dos fitros da página de pequisa de cidade. @author mpituba
 */
public class CidadeFilter {

	private String nome;
	private Estado estado;
	
	
	//Getters and Setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
		
}
