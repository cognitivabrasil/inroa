package com.cognitivabrasil.feb.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Beans que precisam ser criados para o Spring Security.
 * 
 * @author Paulo Schreiner
 */
@Configuration
public class SpringSecurityBeans {
	/**
	 * Cria um codificador (hash) de senha para não armazenarmos senhas na base de dados.
	 * @return codificador de senha.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
