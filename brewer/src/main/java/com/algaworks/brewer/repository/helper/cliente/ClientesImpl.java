package com.algaworks.brewer.repository.helper.cliente;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;


public class ClientesImpl implements ClientesQueries{
	
	//Injeção do EntityManager
	@PersistenceContext
	private EntityManager manager;
	
	//Bean da paginação, criado para evitar duplicação de código
	@Autowired 
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page <Cliente> filtrar(ClienteFilter filtro, Pageable pageable){
		//Criteria do Hibernate
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		
		//Chamada ao Bean da paginação
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filtro, criteria);
		//Ao usar Criteria resolve-se o problema n + 1 das consultas SQL.
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(ClienteFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ClienteFilter filtro, Criteria criteria) {
		
		//Filtros da página de pesquisa de cerveja
		if (filtro != null) {
						
			//Filtro do nome da página de pesquisa
			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			//fitro do CpfOuCnpj da página de pesquisa.
			if (!StringUtils.isEmpty(filtro.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("cpfOuCnpj", filtro.getCpfOuCnpj()));
			}
			
			
		}
	}
	

}





