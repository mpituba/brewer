package com.algaworks.brewer.config.init;

import java.util.EnumSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/*Inicializador do Spring Security*/
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer{
	
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		
		/*Filtro que habilita a finalização da sessão num dado tempo em segundos
		servletContext.getSessionCookieConfig().setMaxAge(1800);*/
		
		/*Evita o JssessionId na URL, faz a verificação através de cookie*/
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		
		/*Este filtro mantém a acentuação após a implantação da segurança do Spring*/
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter",
				new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, false,  "/*");
	}
	
	
}
