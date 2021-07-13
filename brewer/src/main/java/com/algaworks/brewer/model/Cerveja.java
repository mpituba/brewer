package com.algaworks.brewer.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.algaworks.brewer.validation.SKU;

@Entity
@Table(name = "cerveja")
public class Cerveja {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	//@Pattern(regexp = "([a-zA-Z]{2}\\d{4})?", message = "SKU deve seguir o padrão XX9999")
	@SKU
	@NotEmpty(message = "SKU é obrigatório!")
	private String sku;
	
	@NotEmpty(message = "Nome é obrigatório!")
	private String nome;
	
	@NotEmpty(message = "A descrição é obrigatória!")
	@Size(max = 50, message = "A descrição tem mais de 50 caracteres")
	private String descricao;
	
	private BigDecimal valor;
	
	@Column(name = "teor_alcoolico")
	private BigDecimal teorAlcoolico;
	
	private BigDecimal comissao;
	
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	
	@ManyToOne
	@JoinColumn(name= "codigo_estilo")
	private Estilo estilo;
	
	
	
	
	
	//ToString
	
	@Override
	public String toString() {
		return "Cerveja [sku=" + sku + ", nome=" + nome + ", descricao=" + descricao + "]";
	}

	//Getters and Setters
	
	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


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


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}


	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}


	public BigDecimal getComissao() {
		return comissao;
	}


	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}


	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}


	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}


	public Origem getOrigem() {
		return origem;
	}


	public void setOrigem(Origem origem) {
		this.origem = origem;
	}


	public Sabor getSabor() {
		return sabor;
	}


	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}


	public Estilo getEstilo() {
		return estilo;
	}


	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}
	

	
	
}
