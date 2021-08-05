package com.algaworks.brewer.model;



import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

//@Entity
//@Table(name = "cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message="O nome deve ser preenchido!")
	private String nome;
	
	@NotBlank(message="O CPF/CNPJ deve ser preenchido!")
	private String cpfCnpj;
	
	@NotBlank(message="O telefone deve ser preenchido!")
	private String telefone;
	
	@NotBlank(message="O e-mail deve ser preenchido!")
	private String email;
	
	@NotBlank(message="O logradouro deve ser preenchido!")
	private String logradouro;
	
	@NotBlank(message="O número deve ser preenchido!")
	private String numero;
	
	@NotBlank(message="O complemento deve ser preenchido!")
	private String complemento;
	
	@NotBlank(message="O CEP deve ser preenchido!")
	private String cep;
	
	@NotNull(message = "O estado é obrigatório!")
	//@ManyToOne
	//@JoinColumn(name= "codigo_estado")
	private Estado estado;

	
	
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

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	
	
	
	
}
