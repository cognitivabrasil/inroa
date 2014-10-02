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

import java.io.File;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.cognitivabrasil.feb.spring.FebConfig;
import com.cognitivabrasil.feb.spring.FebEnvironmentVariables;

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
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public EnvironmentStringPBEConfig environmentVariablesConfiguration() {
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("feb@RNP");
        return config;
    }

    @Bean
    public StringEncryptor jasyptSpringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(environmentVariablesConfiguration());
        encryptor.setPoolSize(5);
        encryptor.initialize();
        return encryptor;
    }
    
    @Bean(name = "febInf")
    public Properties febInf() {
        Properties p = new Properties();

        // TODO: carregar do arquivo ou usar spring-boot actuator
        return p;
    }
    
   
    /**
     * Configura forma de obter variáveis de ambiente para configuração do FEB.
     * 
     * @see FebEnvironmentVariables
     * @see FebConfig
     * 
     * @return bean que configura a fonte de propriedades do Spring
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesLoader() {
        PropertySourcesPlaceholderConfigurer s = new PropertySourcesPlaceholderConfigurer();
        s.setIgnoreUnresolvablePlaceholders(true);
        s.setNullValue("");
        
        Properties p = new Properties();
        p.setProperty("FEB_DATABASE_TYPE", "");
        p.setProperty("FEB_DATABASE_HOST", "");
        p.setProperty("FEB_DATABASE_PORT", "");
        p.setProperty("FEB_DATABASE_DATABASE", "");
        p.setProperty("FEB_DATABASE_USERNAME", "");
        p.setProperty("FEB_DATABASE_PASSWORD", "");

        
        s.setProperties(p);
             
        return s;
    }

    @Bean(name = "defaultProperties")
    public Properties febConfigDefaultProperties() {
        Properties p = new Properties();
        p.setProperty("Database.username", "feb");
        p.setProperty("Database.password", "feb@RNP");
        p.setProperty("Database.database", "federacao");
        p.setProperty("Database.host", "127.0.0.1");
        p.setProperty("Database.port", "5432");

        return p;
    }

    @Bean
    public FebConfig febConfig() {
        FebConfig c = new FebConfig();
        
        File f = new File("/etc/feb/feb.properties");
        
        if(!f.isFile()) {
            log.warn("Arquivo de configurações /etc/feb/feb.properties não existe, vai usar os defaults!");
        }
        else if (!f.canRead()) {
            log.error("Arquivo de configurações /etc/feb/feb.properties existe mas não pode ser lido, vai usar os defaults");
        }
        else if (!f.canWrite()) {
            log.info("Arquivo de configurações /etc/feb/feb.properties não possui permissões de escrita, " + 
                 "não será possivel realizar alterações de configuração pela interface, mas o sistema " +
                    "funcionará corretamente");
        }
        
        if(f.isFile() && f.canRead()) {
            c.setFile(f);
        }

        c.setDefaultProperties(febConfigDefaultProperties());

        return c;
    }

}
