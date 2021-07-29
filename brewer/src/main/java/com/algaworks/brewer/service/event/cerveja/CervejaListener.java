package com.algaworks.brewer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.brewer.storage.FotoStorage;

/**
 * Listener que escuta quando ocorre o evento CervejaSalvaEvent sob a 
 * condição em @EventListener. @author mpituba
 */

@Component
public class CervejaListener {
	
	
	@Autowired
	private FotoStorage fotoStorage;
	
	/** 
	 * Listener que executará o método CervejaSalva sempre que o evento
	 * CervejaSalvaEvent ocorrer. A assinatura do método indica qual evento 
	 * o chamará. @author mpituba
	 */
	@EventListener(condition = "#evento.temFoto()")
	public void cervejaSalva(CervejaSalvaEvent evento) {
		//Executa o salvamento da foto neste listener.
		fotoStorage.salvar(evento.getCerveja().getFoto());
	}
	
}
