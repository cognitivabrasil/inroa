/*******************************************************************************
 * Copyright (c) 2014 Cognitiva Brasil Tecnologias Educacionais
 * http://www.cognitivabrasil.com.br - contato@cognitivabrasil.com.br
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 * In no event shall the author be liable for any claim or damages.
 *
 * Todos os direitos reservados.
 *******************************************************************************/
package com.cognitivabrasil.feb;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;

/**
 * @author Paulo Schreiner
 * 
 *         Configuração (JavaConfig) de aspectos genéricos da aplicação, em
 *         especial criação de beans.
 * 
 * @see WebConfig
 */
@Configuration
@ComponentScan(basePackages = { "com.cognitivabrasil.feb.spring",
		"com.cognitivabrasil.feb.config",
		"com.cognitivabrasil.feb.data.services" })
@EnableJpaRepositories("com.cognitivabrasil.feb.data.repositories")
public class AppConfig {

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
