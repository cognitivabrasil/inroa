package com.cognitivabrasil.feb.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
//	    http.authorizeRequests().anyRequest().permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/admin/**").authenticated()
			.and()
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/users/passwd").authenticated()
            .and()			
		.authorizeRequests().antMatchers(HttpMethod.POST, "/admin/alterDB").hasRole("CHANGE_DATABASE")
            .and()
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/repositories/*/update*").hasRole("UPDATE")
            .and()
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/federations/*/update*").hasRole("UPDATE")
            .and()
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/efetuaRecalculoIndice").hasRole("UPDATE")
            .and()            
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/repositories/**").hasRole("MANAGE_REP")
            .and()
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/federations/**").hasRole("MANAGE_REP")
            .and()
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/mapeamentos/**").hasRole("MANAGE_MAPPINGS")
            .and()          
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/users/**").hasRole("MANAGE_USERS")
            .and()    
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/metadataStandard/**").hasRole("MANAGE_METADATA")
            .and()    
        .authorizeRequests().antMatchers(HttpMethod.POST, "/admin/statistics/**").hasRole("MANAGE_STATISTICS")
            .and()                
		.authorizeRequests().antMatchers("/admin/**").denyAll() // deny all access not explicitly given
		    .and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
		.csrf().disable() // TODO: retirar isto, ver #FEB-500
	    .logout()
	    	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
	    auth.inMemoryAuthentication().withUser("admin").password("teste").roles(
	            "CHANGE_DATABASE",
	            "UPDATE",
	            "MANAGE_REP",
	            "MANAGE_MAPPINGS",
	            "MANAGE_METADATA",
	            "MANAGE_USERS",
	            "MANAGE_STATISTICS"
	           );
//		auth.userDetailsService(userDetailsService).passwordEncoder(
//				passwordEncoder);
	}
}