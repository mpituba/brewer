package com.algaworks.brewer.service.event.cerveja;


import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cerveja;

/**
 * Evento lançado pelo CervejaListener
 * @author mpituba
 */

public class CervejaSalvaEvent {
	
	private Cerveja cerveja;
	
	//Construtor
	public CervejaSalvaEvent(Cerveja cerveja) {
		this.cerveja =cerveja;
	}
	
	//Getter
	public Cerveja getCerveja() {
		return cerveja;
	}
	
	/**
	 * Método usado como condição. Verifica se há uma foto durante salvamento. Usado no 
	 * CervejaListener em: condition = "#evento.temFoto()" @author mpituba
	 */ 
	public boolean temFoto() {
		return !StringUtils.isEmpty(cerveja.getFoto());
	}
	
}
