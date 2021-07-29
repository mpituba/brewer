package com.algaworks.brewer.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;

/**
 * Esta Query retorna uma Página com estilos, e recebe como argumentos os
 * objetos EstiloFilter e o Pageable. @author mpituba
 */
public interface EstilosQueries {

	public Page <Estilo> filtrar(EstiloFilter filtro, Pageable pageable);
		
}
