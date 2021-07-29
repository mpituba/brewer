package com.algaworks.brewer.repository.helper.cerveja;



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

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;


public class CervejasImpl implements CervejasQueries{
	
	//Injeção do EntityManager
	@PersistenceContext
	private EntityManager manager;
		
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page <Cerveja> filtrar(CervejaFilter filtro, Pageable pageable){
		//Criteria do Hibernate
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		
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

	private Long total(CervejaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(CervejaFilter filtro, Criteria criteria) {
		
		//Filtros da página de pesquisa de cerveja
		if (filtro != null) {
			//fitro do SKU da página de pesquisa.
			if (!StringUtils.isEmpty(filtro.getSku())) {
				criteria.add(Restrictions.eq("sku", filtro.getSku()));
			}
			
			//Filtro do nome da página de pesquisa
			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if (isEstiloPresente(filtro)) {
				criteria.add(Restrictions.eqOrIsNull("estilo", filtro.getEstilo()));
			}
			
			if (filtro.getSabor() != null) {
				criteria.add(Restrictions.eqOrIsNull("sabor", filtro.getSabor()));
			}
			
			if (filtro.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filtro.getOrigem()));
			}
			
			if (filtro.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", filtro.getValorDe()));
			}
			
			if (filtro.getValorAte() !=null) {
				criteria.add(Restrictions.le("valor", filtro.getValorAte()));
			}
		}
	}
	

	private boolean isEstiloPresente(CervejaFilter filtro) {
		return filtro.getEstilo() != null && filtro.getEstilo().getCodigo() != null;
	}



}





