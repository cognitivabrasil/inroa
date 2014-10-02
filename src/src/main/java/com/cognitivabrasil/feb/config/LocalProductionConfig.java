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

import java.sql.SQLException;

import javax.naming.ConfigurationException;
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
 * Configuração local da base de dados para a Produção.
 * 
 * Nesse módulo são criados os Beans de acesso a base de dados com base nas configurações
 * extraídas do {@link FebConfig}. A base de dados pode ser Postgres ou Oracle.
 * 
 * @author Paulo Schreiner
 * 
 * @see LocalTestConfig
 */
@Configuration
@Profile({ "default", "oracle", "postgres", "development" })
public class LocalProductionConfig {
    @Autowired
    private FebConfig febConfig;

    /**
     * Conecta à base de dados.
     * 
     * Usa informações do {@link FebConfig}.
     * 
     * @return conexão a base SQL
     * @throws SQLException se ocorrer um erro na conexão à base
     * @throws ConfigurationException se há algum problema com a configuração
     */
    @Bean
    public DataSource dataSource() throws SQLException, ConfigurationException {
        if (febConfig.getDatabaseType() == Database.ORACLE) {
            oracle.jdbc.pool.OracleDataSource dataSource = new oracle.jdbc.pool.OracleDataSource();
            dataSource.setURL("jdbc:oracle:thin:@" + febConfig.getHost() + ":" + 
                    febConfig.getPort() + ":" + febConfig.getDatabase());
            dataSource.setUser(febConfig.getUsername());
            dataSource.setPassword(febConfig.getPassword());
            return dataSource;
        }
        else if (febConfig.getDatabaseType() == Database.POSTGRESQL) {
            org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
            dataSource.setUrl("jdbc:postgresql://" + febConfig.getHost() + "/" + febConfig.getDatabase());
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUsername(febConfig.getUsername());
            dataSource.setPassword(febConfig.getPassword());
            return dataSource;
        }
        else {
            throw new ConfigurationException("Problema na configuração da base de dados");
        }
    }

    /**
     * @return adaptador JPA pra PostgreSQL ou Oracle, necessário para criar um {@link EntityManager}
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(febConfig.getDatabaseType());
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setShowSql(false);
        return jpaVendorAdapter;
    }

    /**
     * Cria um EntityManagerFactory que escaneia o pacote com.cognitivabrasil.gofun.entities.
     * 
     * @return Um entity manager factory
     * @throws SQLException  se ocorrer um erro ao conectar à base
     * @throws ConfigurationException  se houver um erro na configuração
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException, ConfigurationException {
        LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
        lemfb.setDataSource(dataSource());
        lemfb.setJpaVendorAdapter(jpaVendorAdapter());
        lemfb.setPackagesToScan("com.cognitivabrasil.feb.data.entities");
        return lemfb;
    }
    

}
