package com.algaworks.brewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.algaworks.brewer.security.AppUserDetailsService;

/*
 * Configuração do Springframework Web Security
 * */
@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired 
	private UserDetailsService userDetailsService;
	
	/*Form de login*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
			
	}
	
	//Ignora ou dá acesso a pastas necessárias que ficariam bloqueadas sem isso
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			//Permissão para a pasta css e js e permite a pasta de imagens
			.antMatchers("/layout/**")
			.antMatchers("/javascripts/**")
			.antMatchers("/stylesheets/**")
			//.antMatchers("/fotos/**")
			.antMatchers("/images/**");
	}
	
	
	/*Autoriza  e gera form de logout*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/cidades/nova").hasRole("CADASTRAR_CIDADE")
				.antMatchers("/usuarios/**").hasRole("CADASTRAR_USUARIO")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login") //Requisita a página de login
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")//Chama o controller de acesso negado
				.and()	
			.sessionManagement()
				.invalidSessionUrl("/login");//Leva uma sessão inválida para a página de login
				
				
		/** Máximo de vezes que mesmo usuário fica logado em abas diferentes
						.and()
			.sessionManagement()
				.maximumSessions(1);
		*/	
			
	}			
	
		
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
