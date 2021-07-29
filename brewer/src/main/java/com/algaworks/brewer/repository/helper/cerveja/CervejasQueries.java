package com.algaworks.brewer.repository.helper.cerveja;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;

/**
 * Esta Query retorna uma Página com cervejas, e recebe como argumentos os
 * objetos CervejaFilter e o Pageable. @author mpituba
 */

public interface CervejasQueries {

	public Page <Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);
	
}
