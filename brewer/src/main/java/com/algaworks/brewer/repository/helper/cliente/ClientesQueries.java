package com.algaworks.brewer.repository.helper.cliente;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.filter.ClienteFilter;

/**
 * Esta Query retorna uma Página com cervejas, e recebe como argumentos os
 * objetos ClienteFilter e o Pageable. @author mpituba
 */

public interface ClientesQueries {

	public Page <Cliente> filtrar(ClienteFilter filtro, Pageable pageable);
	
}
