package com.cognitivabrasil.feb.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuração do Spring Security.
 * 
 * @author Paulo Schreiner
 */
@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private PersonDetailsService userDetailsService;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    // TODO: colocar permissões corretas
	    http.authorizeRequests().anyRequest().permitAll();
//		http.authorizeRequests().antMatchers("/resources/**", "/login*").permitAll()
//			.and()
//		.authorizeRequests()
//			.anyRequest().authenticated()
//			.and()
//		.formLogin()
//			.loginPage("/login")
//			.permitAll()
//			.and()
//	    .logout()
//	    	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth)
//			throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(
//				passwordEncoder);
//	}
}