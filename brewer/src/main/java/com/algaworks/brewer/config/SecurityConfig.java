package com.algaworks.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * Configuração do Springframework Web Security
 * */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	/*Form de login*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Config da tela de login em memória
		auth.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("CADASTRO_CLIENTE");
	}
	
	//Ignora ou dá acesso a pastas necessárias que ficariam bloqueadas sem isso
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			//Permissão para a pasta css e js e permite a pasta de imagens
			.antMatchers("/layout/**")
			.antMatchers("/images/**");
	}
	
	
	/*Autoriza  e gera form de logout*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login") //Requisita a página de login
				.permitAll()
				.and()
			.csrf().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
