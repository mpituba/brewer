package com.algaworks.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Esta classe ajuda a montar as URL's com os elementos de filtro que deverão 
 * aparecer num GET. E também ajuda com funcionalidades do Page. @author mpituba
 */

public class PageWrapper<T> {
	
	//Página genérica a ser envelopada
	private Page<T> page;
	
	//Builder - Classe usada para construir a URL com filtro 
	private UriComponentsBuilder uriBuilder;
	
	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest){
		//Recebe a página genérica
		this.page = page;
		//Inicialização do UriBuilder, é feita por meio da requisição httpServlet
		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
	}
	
	/**
	 * Métodos do objeto Page e métodos Java são disponibilizados na página 
	 * html/Thymeleaf via Wrapper, assim temos métodos/código Java em substituição
	 * a um código Thymeleaf mais verboso e nomenclatura amigável. @author mpituba
	 */
	public List<T> getConteudo(){
		return page.getContent();
	}
	
	public boolean isVazia() {
		return page.getContent().isEmpty();
	}
	
	public int getAtual() {
		return page.getNumber();
	}
	
	public boolean isPrimeira() {
		return page.isFirst();
	}
	
	public boolean isUltima() {
		return page.isLast();
	}
	
	public int getTotal() {
		return page.getTotalPages();
	}
	
	/**
	 * Cria uma uri do link de paginação específico desta página de pesquisa cerveja
	 * Em replaceQueryParam, se houver um parâmetro page, este será substituido pelo
	 * parâmetro pagina. Constroi e codifica a uri. @author mpituba
	 */
	public String urlParaPagina(int pagina) {
		return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}
	
	//Outro uribuilder usado na ordenação da página pesquisa cerveja 
	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder
				.fromUriString(uriBuilder.build(true).encode().toUriString());
		
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));
		
		return uriBuilderOrder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
	}
	
	//Método inverte a direção da ordenação asc/desc
	public String inverterDirecao(String propriedade) {
		String direcao = "asc";
		
		//Retorna a ordenação da página
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade): null;
		
		//Inverte se não for nula
		if (order != null) {
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}
		
		return direcao;
	}
	
	//Inverte a ordenação de ascendente para descendente
	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");		
	}
	
	//Verifica se uma página é ordenada
	public boolean ordenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		
		if (order == null) {
			return false;
		}
		
		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}
}
