package com.algaworks.brewer.config;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
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
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.algaworks.brewer.controller.CervejasController;
import com.algaworks.brewer.controller.converter.CidadeConverter;
import com.algaworks.brewer.controller.converter.EstadoConverter;
import com.algaworks.brewer.controller.converter.EstiloConverter;
import com.algaworks.brewer.controller.converter.GrupoConverter;
import com.algaworks.brewer.session.TabelaItensVenda;
import com.algaworks.brewer.thymeleaf.BrewerDialect;
import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;
import com.google.common.cache.CacheBuilder;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * Classe de configura????o que faz o Spring a encontrar os controllers. 
 * ?? uma classe de configura????o que encontra as classes por meio do  componentscan,
 * configurado na classe. Pode-se passar o nome/local da classe como uma string
 * mais a forma @ComponentScan(basePackageClasses = {nomedaclasse.class}) aceita 
 * refatora????o e mudan??a do pacote onde a classe se localiza. @EnableWebMvc habilita o
 * MVC a aplica????o. Esta classe tamb??m permite a configura????o de adaptadores para a apli????o,
 * como o WebMvcConfigurerAdapter.
 * Quando for necess??rio configurar outra tecnologia deve-se alterar os m??todos 
 * abaixo: ViewResolver, TemplateEngine e ITemplateResolver para adequar a tecnologia,
 * no caso de n??o se usar mais o Thymeleaf e se usar por exemplo o jsp em seu lugar.
 * @author mpituba
 */
@Configuration
@ComponentScan(basePackageClasses = { CervejasController.class, TabelaItensVenda.class })
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableCaching
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	
	//Atributo usado pela aplica????o
	private ApplicationContext applicationContext;
	
	/**
	 * M??todo usa do pela aplica????o ao ser inicializada.
	 * @mpituba
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	
	/**
	 * O ViewResolver processa as p??ginas html.
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
	 * A TemplateEngine ?? quem processa os arquivos HTML, depende do TemplateEngine
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
		 * Layout customizado Brewer para simplificar express??es grande ou
		 * mesmo criarmos as nossas e diminuir c??digo no Thymeleaf. A documenta????o
		 * est?? em www.thymeleaf.org/documentation.html  #Extending Thymeleaf.
		 */
		engine.addDialect(new BrewerDialect());
		
		/**
		 * Layout Dialect usado pelo Thymeleaf Extras Data Attribute
		 */
		engine.addDialect(new DataAttributeDialect());
		
		/**Dialeto do Thymeleaf Spring Security Extras*/
		engine.addDialect(new SpringSecurityDialect());
		
		
		return engine;
	}
	
	/**
	 * M??todo que resolve o template, diz aonde est?? o arquivo html da aplica????o.
	 * 
	 * @mpituba
	 */
	private ITemplateResolver templateResolver() {
		//Resolve templates do Spring.
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		//Essencial a configura????o da aplica????o
		resolver.setApplicationContext(applicationContext);
		//Indica que a pasta ser?? procurada em:src/main/resources/templates
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		//Modo do template
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}
	
	//Libera e permite a busca por recursos est??ticos
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//Libera a busca por recursos em static
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	//Registro dos Converters para a p??gina cerveja 
	@Bean
	public FormattingConversionService mvcConversionService () {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new EstiloConverter());
		
		//Conversor para o campo cidade em CadastroCliente
		conversionService.addConverter(new CidadeConverter());
		
		//Conversor para o campo estado
		conversionService.addConverter(new EstadoConverter());
		
		//Conversor do Grupo
		conversionService.addConverter(new GrupoConverter());
		
		//Conversor para o tipo BigDecimal informar o tipo de separa????o em portugu??s no js
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		//Conversor para o tipo Integer informar o tipo de separa????o em portugu??s no js
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		//Conversor de String para Data do LocalDate API Java 8
		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.registerFormatters(conversionService);
		
		return conversionService;
		
	}
	
	//Este Bean fixa o idioma da aplica????o como pt_br em rela????o sinais e pontua????es num??ricas 
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	
	/**
	 * Bean que implementa o cache. Juntamente como o @EnableCaching no WebConfig
	 * e a amnota????o no controlador. @author mpituba
	 */
 	@Bean
 	public CacheManager cacheManager() {
 		
 		//Configura????o das op????es do CacheManager
 		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
 				.maximumSize(3)
 				.expireAfterAccess(20, TimeUnit.SECONDS);
 		
 		//Instancia o CacheManager
 		GuavaCacheManager cacheManager = new GuavaCacheManager();
 		
 		cacheManager.setCacheBuilder(cacheBuilder);
 		
 		return cacheManager;
 	}
 	
 	@Bean
 	public MessageSource messageSource() {
 		
 		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
 		
 		//Refere ao arquivo messages.properties em templates
 		bundle.setBasename("classpath:/messages");
 		
 		bundle.setDefaultEncoding("UTF-8");
 		
 		return bundle;
 	}
 	


}
