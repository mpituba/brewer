package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cidade")
public class Cidade implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message="A cidade deve ser preenchida!")
	private String nome;
	
	/**
	 * Este atributo precisa do FetchType para que o atributo não seja inicializado
	 * quando uma cidade for selecionada. E o @JsonIgnore do Jackson não envia a 
	 * propriedade para o frontend na busca. @mpituba
	 */
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "codigo_estado")
	@JsonIgnore 
	private Estado estado;

	
	//Getters and Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
	
}
