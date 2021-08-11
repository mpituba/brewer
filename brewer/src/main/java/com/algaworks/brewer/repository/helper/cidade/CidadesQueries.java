package com.algaworks.brewer.repository.helper.cidade;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.CidadeFilter;

/**
 * Esta Query retorna uma Página com cervejas, e recebe como argumentos os
 * objetos CervejaFilter e o Pageable. @author mpituba
 */

public interface CidadesQueries {

	public Page <Cidade> filtrar(CidadeFilter filtro, Pageable pageable);
	
}
