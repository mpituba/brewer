package com.algaworks.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class EstilosImpl implements EstilosQueries{
	
	//Injeção do EntityManager
	@PersistenceContext
	private EntityManager manager;
	
	//Bean da paginação, criado para evitar duplicação de código
	@Autowired 
	private PaginacaoUtil paginacaoUtil;
		
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page <Estilo> filtrar(EstiloFilter filtro, Pageable pageable){
		//Criteria do Hibernate
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		
		//Chamada ao Bean da Paginação
		paginacaoUtil.preparar(criteria, pageable);
		
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
