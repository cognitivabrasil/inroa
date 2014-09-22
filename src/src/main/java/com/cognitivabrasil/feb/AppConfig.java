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

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.cognitivabrasil.feb.spring.FebConfig;

/**
 * @author Paulo Schreiner
 * 
 *         Configuração (JavaConfig) de aspectos genéricos da aplicação, em especial criação de beans.
 * 
 * @see WebConfig
 */
@Configuration
@ComponentScan(basePackages = { "com.cognitivabrasil.feb.spring", "com.cognitivabrasil.feb.config",
        "com.cognitivabrasil.feb.data", "com.cognitivabrasil.feb.robo", "com.cognitivabrasil.feb.ferramentaBusca" })
@EnableJpaRepositories("com.cognitivabrasil.feb.data.repositories")
public class AppConfig {

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public EnvironmentStringPBEConfig environmentVariablesConfiguration() {
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPasswordEnvName("feb@RNP");
        return config;
    }

    @Bean
    public StringEncryptor jasyptSpringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(environmentVariablesConfiguration());
        return encryptor;
    }

    @Bean(name = "febInf")
    public Properties febInf() {
        Properties p = new Properties();

        // TODO: carregar do arquivo ou usar spring-boot actuator
        return p;
    }

    @Bean(name = "defaultProperties")
    public Properties febConfigDefaultProperties() {
        Properties p = new Properties();
        p.setProperty("Postgres.username", "feb");
        p.setProperty("Postgres.password", "feb@RNP");
        p.setProperty("Postgres.database", "federacao");
        p.setProperty("Postgres.host", "127.0.0.1");
        p.setProperty("Postgres.port", "5432");

        return p;
    }

    @Bean
    public FebConfig febConfig() {
        FebConfig c = new FebConfig();

        c.setDefaultProperties(febConfigDefaultProperties());

        return c;
    }

}
