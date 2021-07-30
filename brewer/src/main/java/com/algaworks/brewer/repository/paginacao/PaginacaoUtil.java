package com.algaworks.brewer.repository.paginacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil {
	
	public void preparar(Criteria criteria, Pageable pageable) {
		
		//Variáveis com valores de referência para a paginação
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		//Referência para o primeiro registro da página gerada
		criteria.setFirstResult(primeiroRegistro);
		//Quantidade de registros da página gerada
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		//Objeto que recebe a ordenação vinda da página quando é solicitada no GET
		Sort sort = pageable.getSort();
		//System.out.println(">>>>> Sort: " + sort);
		
		if (sort != null) {
			//Itera todos os parâmetros de ordenação, aqui utiliza um parâmetro
			Sort.Order order = sort.iterator().next();
			String propertie = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(propertie) : Order.desc(propertie));
		}
		
	}
}
