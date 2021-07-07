package com.algaworks.brewer.model;

import org.hibernate.validator.constraints.NotBlank;

public class Usuario {
	
	@NotBlank(message="O nome deve ser preenchido!")
	private String nome;
	
	@NotBlank(message="O e-mail deve ser preenchido!")
	private String email;
	
	@NotBlank(message="A data de nascimento deve ser preenchida!")
	private String dataNascimento;
	
	private String senha;
	
	private String confirmacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacao() {
		return confirmacao;
	}

	public void setConfirmacao(String confirmacao) {
		this.confirmacao = confirmacao;
	}
	
	
}
