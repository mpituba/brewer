package com.algaworks.brewer.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.algaworks.brewer.config.JPAConfig;
import com.algaworks.brewer.config.SecurityConfig;
import com.algaworks.brewer.config.ServiceConfig;
import com.algaworks.brewer.config.WebConfig;

/**
 * Classe utilizada como Dispatcher Servlet ou Front Controller do Spring utiliza a
 *  importação acima, bem como a api servlet 3.0.1. Usada inicialmente como uma configuração 
 *  do Spring por meio de programação.
 * @author mpituba
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return new Class<?>[] { JPAConfig.class, ServiceConfig.class, SecurityConfig.class };
	}

	/**
	 * Classe do Spring MVC que indica os controllers. A classe configurada aqui no caso
	 * o WebConfig.class ensina o Spring a achar os controllers. @author mpituba
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class  };
	}
	
	/**
	 * É o equivalente ao UrlMappings no web.xml.
	 * Esta configuração indica a url ou caminho que será entregue ao DispacherServlet.
	 * O "/" indica que da aplicação para frente entregará ao Dispatcher Servlet.	
	 * @mpituba
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	
	//Filtro para forçar o encoding de caracteres para utf-8.
	@Override
	protected Filter[] getServletFilters() {
		
		
		return new Filter[] { };
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
}
