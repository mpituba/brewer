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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

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
	
	@NotBlank(message = "A descrição é obrigatória!")
	@Size(max = 50, message = "A descrição tem mais de 50 caracteres")
	private String descricao;
	
	@NotNull(message = "O valor é obrigatório!")
	@DecimalMin(value = "0.01", message = "O valor mínimo do teor alcoólico é 0.01%")
	@DecimalMax(value = "9999999.99", message = "O valor da cerveja deve ser menor que R$ 9.999.999,99")
	private BigDecimal valor;
	
	@NotNull(message = "O teoralcoolico é obrigatório!")
	@DecimalMax(value = "100.0", message = "O valor do teor alcoólico deve ser menor que 100")
	@Column(name = "teor_alcoolico")
	private BigDecimal teorAlcoolico;
	
	@NotNull(message = "A comissão é obrigatória!")
	@DecimalMax(value = "100.0", message = "A comissão deve ser igual ou menor que 100")
	private BigDecimal comissao;
	
	@NotNull(message = "A quntidade em estoque é obrigatória!")
	@Max(value = 9999, message = "A quantidade em estoque deve ser menor que 9.999")
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@NotNull(message = "A origem é obrigatória!")
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@NotNull(message = "O sabor é obrigatório!")
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	
	@NotNull(message = "O estilo é obrigatório!")
	@ManyToOne
	@JoinColumn(name= "codigo_estilo")
	private Estilo estilo;
	
	private String foto;
	
	
	@Column(name = "content_type")
	private String contentType;
	
	
	//Callbacks JPA
	/**
	 * CallBack JPA Methods - Centraliza na classe algumas regras
	 * Antes de persistir e antes de fazer Update executará o método
	 * que mudará o sku para uppercase. Há também o Postload que executa ação 
	 * depois que busca no banco de dados.Prepersist é usado antes de persistir 
	 * e PreUpdate antes de ser atualizado.
	 * @mpituba
	 */
	@PrePersist
	@PreUpdate
	private void prePersistUpdate() {
		sku = sku.toUpperCase();
	}
	
	//ToString
	
	@Override
	public String toString() {
		return "Cerveja [codigo=" + codigo + ", sku=" + sku + ", nome=" + nome + ", descricao=" + descricao + ", valor="
				+ valor + ", teorAlcoolico=" + teorAlcoolico + ", comissao=" + comissao + ", quantidadeEstoque="
				+ quantidadeEstoque + ", origem=" + origem + ", sabor=" + sabor + ", estilo=" + estilo + "]";
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "cerveja-mock.png";		
	} 
	
	
	
	
}
