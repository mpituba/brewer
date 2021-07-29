package com.algaworks.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.service.event.cerveja.CervejaSalvaEvent;

/**
 * Serviço utilizado para salvar transacionalmente uma cerveja e
 * publicar o evento CervejaSalvaEvent. @author mpituba
 */

@Service
public class CadastroCervejaService {
	
	//Injeção de dependencia do repositório de cervejas
	@Autowired
	private Cervejas cervejas;
	
	//Publicador de evento, que publicará o evento CervejaSalvaEvent
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//Salva a transação da cerveja e publica o evento CervejaSalvaEvent.
	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
	
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}
		
}
