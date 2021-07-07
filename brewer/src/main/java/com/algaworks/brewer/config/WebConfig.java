package com.algaworks.brewer.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.algaworks.brewer.controller.CervejasController;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * Classe de configuração que ajuda o Spring a encontrar os controllers. 
 * 
 * @author mpituba
 */
@Configuration
@ComponentScan(basePackageClasses = { CervejasController.class })
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}
	
	/**
	 * A TemplateEngine é quem processa os arquivos HTML.
	 * @mpituba
	 */
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		
		//Inserção do Layout Dialect do Thymeleaf
		engine.addDialect(new LayoutDialect());
		
		return engine;
	}
	
	
	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		//Indica que a pasta será procurada em:src/main/resources/templates
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}
	
	//Libera e permite a busca por recursos estáticos
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//Libera a busca por recursos em static
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	


}
