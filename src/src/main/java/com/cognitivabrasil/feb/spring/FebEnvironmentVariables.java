package com.cognitivabrasil.feb.spring;

import java.beans.Transient;
import java.util.Properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Bean responsável por colher informações de configuração a partir de variáveis de ambiente.
 * 
 * @author Paulo Schreiner
 *
 */
@Component
public class FebEnvironmentVariables {
    private static final Logger log = LoggerFactory.getLogger(FebEnvironmentVariables.class);
    
    @Value("${FEB_DATABASE_TYPE}")
    private String databaseType;

    @Value("${FEB_DATABASE_HOST}")
    private String databaseHost;

    @Value("${FEB_DATABASE_PORT}")
    private String databasePort;

    @Value("${FEB_DATABASE_DATABASE}")
    private String databaseDatabase;

    @Value("${FEB_DATABASE_USERNAME}")
    private String databaseUsername;

    @Value("${FEB_DATABASE_PASSWORD}")
    private String databasePassword;

    public String getDatabaseHost() {
        return databaseHost;
    }

    public void setDatabaseHost(String databaseHost) {
        this.databaseHost = databaseHost;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseDatabase() {
        return databaseDatabase;
    }

    public void setDatabaseDatabase(String databaseDatabase) {
        this.databaseDatabase = databaseDatabase;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
    }

    /**
     * @return properties com informações da configuração de acesso à base
     * @throws RuntimeException quando o usuário especificou apenas algumas configurações, não todas.
     */
    @Transient
    public Properties getProperties() {
        Properties p = new Properties();
        
        if(getDatabaseType() == null
                || getDatabaseHost() == null
                || getDatabasePort() == null
                || getDatabaseUsername() == null
                || getDatabaseDatabase() == null
                || getDatabasePassword() == null) {
            log.error("Se for especificar alguma informação via variáveis de ambiente deve especificar todas");
            
            throw new RuntimeException("Se for especificar alguma informação via variáveis de ambiente deve especificar todas");
        }
        
        p.setProperty("Database.type", getDatabaseType());
        p.setProperty("Database.host", getDatabaseHost());
        p.setProperty("Database.port", getDatabasePort());
        p.setProperty("Database.username", getDatabaseUsername());
        p.setProperty("Database.database", getDatabaseDatabase());
        p.setProperty("Database.password", getDatabasePassword());

        return p;
    }

}
