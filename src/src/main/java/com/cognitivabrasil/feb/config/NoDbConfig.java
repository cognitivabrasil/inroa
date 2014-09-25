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

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Configuração do JPA para testes que usam o Spring mas não utilizam a base de dados.
 * 
 * Testes de controllers ou outros beans que utilizam a infraestrutura do Spring mas não
 * utilizam a base de dados (por exemplo, mockando o Service ou Repository) falham em 
 * máquinas que não tem o Postgres configurado.
 * 
 * Essa classe pode ser usada nesses testes para criar um banco de dados em memória "dummy", que vai
 * deixar o Spring feliz mas não faz nada propriamente dito.
 * 
 * @author Paulo Schreiner
 * 
 * @see LocalTestConfig
 */
@Configuration
@Profile("nodb")
public class NoDbConfig {

    /**
     * @return Fonte de dados embutida H2
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    /**
     * @return adaptador JPA para base H2
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.H2);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    /**
     * @return veja {@link LocalPostgresConfig#entityManagerFactory()} 
     * @see LocalPostgresConfig#entityManagerFactory()
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
