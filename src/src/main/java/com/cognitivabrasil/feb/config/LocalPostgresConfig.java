/*******************************************************************************
 * Copyright (c) 2014 Cognitiva Brasil Tecnologias Educacionais
 * http://www.cognitivabrasil.com.br - contato@cognitivabrasil.com.br
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 * In no event shall the author be liable for any claim or damages.
 *
 * Todos os direitos reservados.
 *******************************************************************************/
package com.cognitivabrasil.feb.config;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.cognitivabrasil.feb.spring.FebConfig;

/**
 * Configuração local para o PostgreSQL, devendo ser usada somente no desenvolvimento.
 * 
 * Nesse módulo são criados os Beans de acesso ao PostgreSQL usando confiruações HardCoded. Serão usados para teste e
 * desenvolvimento local.
 * 
 * @author Paulo Schreiner
 * 
 * @see CloudConfig
 * @see LocalTestConfig
 */
@Configuration
@Profile({ "default", "development" })
public class LocalPostgresConfig {
    @Autowired
    private FebConfig febConfig;

    /**
     * Conecta ao PostgreSQL.
     * 
     * Usa informações hardcoded.
     * 
     * @return conexão ao postgreSQL
     */
    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setUrl("jdbc:postgresql://" + febConfig.getHost() + "/" + febConfig.getDatabase());
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername(febConfig.getUsername());
        dataSource.setPassword(febConfig.getPassword());
        return dataSource;
    }

    /**
     * @return adaptador JPA pra PostgreSQL, necessário para criar um {@link EntityManager}
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setShowSql(false);
        return jpaVendorAdapter;
    }

    /**
     * Cria um EntityManagerFactory que escaneia o pacote com.cognitivabrasil.gofun.entities.
     * 
     * @return Um entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
        lemfb.setDataSource(dataSource());
        lemfb.setJpaVendorAdapter(jpaVendorAdapter());
        lemfb.setPackagesToScan("com.cognitivabrasil.feb.data.entities");
        return lemfb;
    }
}
