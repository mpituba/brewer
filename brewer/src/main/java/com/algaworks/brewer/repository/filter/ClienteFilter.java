package com.algaworks.brewer.repository.filter;


import com.algaworks.brewer.model.TipoPessoa;

/**
 * Objeto Filtro passado para fazer a pesquisa de cerveja, São os valores
 * dos fitros da página de busca. @author mpituba
 */
public class ClienteFilter {

	private String nome;
	private String cpfOuCnpj;
	
	
	//Getters and Setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}
	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
	
	public Object getCpfOuCpnjSemFormatacao() {
		return TipoPessoa.removerFormatacao(this.cpfOuCnpj);
	}
	
}
