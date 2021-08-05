package com.algaworks.brewer.model;



import java.io.Serializable;

import java.util.Objects;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "estado")
public class Estado implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotEmpty(message = "O Nome deve ser preenchido!")
	@Size(min = 0, max = 50, message = "O nome deve ter até 50 caracteres!")
	@NotNull
	private String nome;
	
	@NotEmpty(message = "A sigla deve ser preenchida!")
	@Size(min = 2, max = 2, message = "A sigla deve ter 2 caracteres!")
	@NotNull
	private String sigla;

	//Getters and Setters 
	
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	
	
	
	//ToString
	@Override
	public String toString() {
		return "Estado [codigo=" + codigo + ", nome=" + nome + ", sigla=" + sigla + "]";
	}

	
	//Hashcode
	@Override
	public int hashCode() {
		return Objects.hash(codigo, nome, sigla);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(nome, other.nome)
				&& Objects.equals(sigla, other.sigla);
	}

	
	
	
	
	
	
	
	
	
}
