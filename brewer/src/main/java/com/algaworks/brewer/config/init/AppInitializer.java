package com.algaworks.brewer.config.init;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.algaworks.brewer.config.WebConfig;

/**
 * Classe utilizada como Dispatcher Controller utiliza a importação acima, bem como
 * a api servlet 3.0.1. Usada inicialmente como uma configuração do Spring por meio
 * de programação.
 * @author mpituba
 */

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return new Class<?>[] { JPAConfig.class };
	}

	//Classe que ensina o Spring a achar os controllers.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class  };
	}

	//Esta configuração indica a url ou caminho que será entregue ao DispacherServlet	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	
	//Filtro para forçar o encoding de caracteres para utf-8.
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		
		return new Filter[] {characterEncodingFilter};
	}
	

}
