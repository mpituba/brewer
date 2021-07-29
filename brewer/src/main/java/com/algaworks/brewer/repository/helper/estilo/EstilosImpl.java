package com.algaworks.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;

public class EstilosImpl implements EstilosQueries{
	
	//Injeção do EntityManager
	@PersistenceContext
	private EntityManager manager;
		
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page <Estilo> filtrar(EstiloFilter filtro, Pageable pageable){
		//Criteria do Hibernate
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		
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
		
		adicionarFiltro(filtro, criteria);
		//Ao usar Criteria resolve-se o problema n + 1 das consultas SQL.
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(EstiloFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(EstiloFilter filtro, Criteria criteria) {
		
		//Filtros da página de pesquisa de cerveja
		if (filtro != null) {
							
			//Filtro do nome da página de pesquisa
			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
		}
	}
				
}
