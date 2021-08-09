package com.algaworks.brewer.model;

import com.algaworks.brewer.model.validation.group.CnpjGroup;
import com.algaworks.brewer.model.validation.group.CpfGroup;

public enum TipoPessoa {

	FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class) {
		
		//Método que formata CPF
		@Override
		public String formatar(String cpfOuCnpj) {
			//Regex que formata o CPF na tela  
			return cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
		}
	},
	JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class) {
		
		//Método que formata o CNPJ
		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
		}
	};
	
	
	private String descricao;
	private String documento;
	private String mascara;
	private Class<?> grupo;
		
	
	//Construtor
	TipoPessoa(String descricao, String documento, String mascara, Class<?> grupo) {
		this.descricao = descricao;
		this.documento = documento;
		this.mascara = mascara;
		this.grupo = grupo;
	}
	
	
	//Método usado para formatar o CPF/CNPJ
	public abstract String formatar(String cpfOuCnpj);

	
	//Método remove Formatação do CPF/CNPJ antes de salvar no banco
	public static String removerFormatacao(String cpfOuCnpj) {
		return cpfOuCnpj.replaceAll("\\.|-|/", "");
	}
	
	
	//Getters do Enum
	public String getDescricao() {
		return descricao;
	}

	public String getDocumento() {
		return documento;
	}

	public String getMascara() {
		return mascara;
	}

	public Class<?> getGrupo() {
		return grupo;
	}
	
	
	
}
