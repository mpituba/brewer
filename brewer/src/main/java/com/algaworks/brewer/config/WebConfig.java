package com.algaworks.brewer.config;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.algaworks.brewer.controller.CervejasController;
import com.algaworks.brewer.controller.converter.CidadeConverter;
import com.algaworks.brewer.controller.converter.EstadoConverter;
import com.algaworks.brewer.controller.converter.EstiloConverter;
import com.algaworks.brewer.thymeleaf.BrewerDialect;
import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;
import com.google.common.cache.CacheBuilder;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * Classe de configuração que faz o Spring a encontrar os controllers. 
 * É uma classe de configuração que encontra as classes por meio do  componentscan,
 * configurado na classe. Pode-se passar o nome/local da classe como uma string
 * mais a forma @ComponentScan(basePackageClasses = {nomedaclasse.class}) aceita 
 * refatoração e mudança do pacote onde a classe se localiza. @EnableWebMvc habilita o
 * MVC a aplicação. Esta classe também permite a configuração de adaptadores para a aplição,
 * como o WebMvcConfigurerAdapter.
 * Quando for necessário configurar outra tecnologia deve-se alterar os métodos 
 * abaixo: ViewResolver, TemplateEngine e ITemplateResolver para adequar a tecnologia,
 * no caso de não se usar mais o Thymeleaf por exemplo e o jsp em seu lugar.
 * @author mpituba
 */
@Configuration
@ComponentScan(basePackageClasses = { CervejasController.class })
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableCaching
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	
	//Atributo usado pela aplicação
	private ApplicationContext applicationContext;
	
	/**
	 * Método usa do pela aplicação ao ser inicializada.
	 * @mpituba
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	
	/**
	 * O ViewResolver processa as páginas html.
	 * @mpituba
	 */
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}
	
	/**
	 * A TemplateEngine é quem processa os arquivos HTML, depende do TemplateEngine
	 * que usa o templateRsolver. Necessita ser um Bean
	 * @mpituba
	 */
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		
		//Layout Dialect do Thymeleaf
		engine.addDialect(new LayoutDialect());
		
		/**
		 * Layout customizado Brewer para simplificar expressões grande ou
		 * mesmo criarmos as nossas e diminuir código no Thymeleaf. A documentação
		 * está em www.thymeleaf.org/documentation.html  #Extending Thymeleaf.
		 */
		engine.addDialect(new BrewerDialect());
		
		/**
		 * Layout Dialect usado pelo Thymeleaf Extras Data Attribute
		 */
		engine.addDialect(new DataAttributeDialect());
		
		return engine;
	}
	
	/**
	 * Método que resolve o template, diz aonde está o arquivo html da aplicação.
	 * 
	 * @mpituba
	 */
	private ITemplateResolver templateResolver() {
		//Resolve templates do Spring.
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		//Essencial a configuração da aplicação
		resolver.setApplicationContext(applicationContext);
		//Indica que a pasta será procurada em:src/main/resources/templates
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		//Modo do template
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}
	
	//Libera e permite a busca por recursos estáticos
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//Libera a busca por recursos em static
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	//Registro dos Converters para a página cerveja 
	@Bean
	public FormattingConversionService mvcConversionService () {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new EstiloConverter());
		
		//Conversor para o campo cidade em CadastroCliente
		conversionService.addConverter(new CidadeConverter());
		
		//Conversor para o campo estado
		conversionService.addConverter(new EstadoConverter());
		
		//Conversor para o tipo BigDecimal informar o tipo de separação em português no js
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		//Conversor para o tipo Integer informar o tipo de separação em português no js
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		return conversionService;
		
	}
	
	//Este Bean fixa o idioma da aplicação como pt_br em relação sinais e pontuações numéricas 
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	
	/**
	 * Bean que implementa o cache. Juntamente como o @EnableCaching no WebConfig
	 * e a amnotação no controlador. @author mpituba
	 */
 	@Bean
 	public CacheManager cacheManager() {
 		
 		//Configuração das opções do CacheManager
 		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
 				.maximumSize(3)
 				.expireAfterAccess(20, TimeUnit.SECONDS);
 		
 		//Instancia o CacheManager
 		GuavaCacheManager cacheManager = new GuavaCacheManager();
 		
 		cacheManager.setCacheBuilder(cacheBuilder);
 		
 		return cacheManager;
 	}
 	


}
