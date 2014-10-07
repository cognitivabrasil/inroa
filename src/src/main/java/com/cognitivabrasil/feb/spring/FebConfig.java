package com.cognitivabrasil.feb.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Properties;

import javax.annotation.PostConstruct;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.Database;

import com.cognitivabrasil.feb.AppConfig;

/**
 * This class represents the FEB (database) configuration.
 *
 * It has accessors for the common database parameters. It should be used as a managed bean, and it has to be configured
 * to receive, by injection, a {@link File} and a {@link Properties}.
 *
 * It will try to load the properties the Envirnoment, if not available it will try to load it from the File, 
 * and finally it will use those in the Properties (default) ( {@link AppConfig#febConfigDefaultProperties()} ).
 *
 * To save the file, you have to make sure that the destination folder is writable by the Servlet container.
 * 
 * @see FebEnvironmentVariables
 *
 * @author Paulo Schreiner <paulo@cognitivabrasil.com.br>
 *
 */
//TODO: fazer pegar de configuração
//TODO: fazer ter configuração padrão
public class FebConfig {

    private static final Logger log = LoggerFactory.getLogger(FebConfig.class);

    private StringEncryptor encryptor;

    private Properties properties;
    private File file;

    private String host;
    private Integer port;
    private transient String password;
    private String username;
    private String database;
    private String filename;

    private Database databaseType;

    private FebEnvironmentVariables environmentVariables;

    @Autowired
    public void setEncryptor(StringEncryptor e) {
        encryptor = e;
    }
    
    @Autowired
    public void setFebEnvironmentVariables(FebEnvironmentVariables env) {
        log.debug("Environment variables = " + env);
        environmentVariables = env;
    }

    protected void save(Writer w) throws IOException {
        Properties n = new Properties();
        n.setProperty("Database.type", databaseType == Database.ORACLE ? "Oracle" : "Postgres");
        n.setProperty("Database.host", host);
        n.setProperty("Database.port", port.toString());
        n.setProperty("Database.username", username);
        n.setProperty("Database.database", database);
        n.setProperty("Database.password", "ENC(" + encryptor.encrypt(password)
                + ")");

        n.store(w, null);
    }

    /**
     * Saves the (possibly modified) configuration file.
     *
     * It will save the file to the same location that it tried to open. This method should only be called on a Spring
     * Managed bean, NOT on any other. To update an object you can try something like:
     *
     * {@code febConfig.updateFrom(newConfig);
     * febConfig.save}
     *
     * where febConfig is the Spring managed one and newCondig is the changed one.
     *
     * NOTE: Actually, you can also call save on non-spring managed beans, but you have to initialize their lifecycle
     * yourself, namely call {@link #setFile(java.io.File)} or {@link #setFilename(java.lang.String)} and
     * {@link #setDefaultProperties(java.util.Properties)} followed by {@link #postConstruct()}.
     *
     * @throws IOException
     */
    public void save() throws IOException {
        if (file == null) {
            log.warn("File is null, trying to open.");
            file = new File(this.filename);
        }
        Writer w = new FileWriter(file);
        save(w);
    }

    /**
     * @param f the file to load and save the configuration
     */
    public void setFile(File f) {
        file = f;
    }

    /**
     * @param p the default properties
     */
    public void setDefaultProperties(Properties p) {
        properties = p;
    }

    /**
     * Tries to populate fields with the File, if not possible, populates with default properties.
     *
     * Will be run after successful injection.
     */
    @PostConstruct
    public void postConstruct() {
        Properties p = new EncryptableProperties(encryptor);
        try {
            if(environmentVariables.getDatabaseType() != null) {
                log.info("Utilizando informações de variáveis do ambiente para configuração...");
                
                properties = environmentVariables.getProperties();
            }
            else if (file != null) {
                log.info("Usando informações de arquivo de configuração...");
                
                InputStream s = new FileInputStream(file);
                p.load(s);
                properties = p;
                s.close();
            }
        } catch (IOException e) {
            log.warn("Config file is missing, using defaults.");
        }
        
        if(properties.getProperty("Database.type", "Postgres").equals("Oracle")) {
            databaseType = Database.ORACLE;
        }
        else {
            databaseType = Database.POSTGRESQL;
        }
        
        host = properties.getProperty("Database.host");
        port = Integer.parseInt(properties.getProperty("Database.port"));
        username = properties.getProperty("Database.username");
        database = properties.getProperty("Database.database");
        password = properties.getProperty("Database.password");
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFilename(String string) {
        this.filename = string;
        file = new File(filename);
    }

    /**
     * Updates one febConf object with the values of another.
     *
     * In order to be able to save the febConf, you have to use the Spring managed bean, not a newly created one (such
     * as those returned by spring forms).
     *
     * @param febConf
     */
    public void updateFrom(FebConfig febConf) {
        if (isNotBlank(febConf.getPassword())) {
            setPassword(febConf.getPassword());
        }
        setDatabase(febConf.getDatabase());
        setUsername(febConf.getUsername());
        setPort(febConf.getPort());
        setHost(febConf.getHost());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
    }

    /**
     * @return o tipo da base de dados (Postgresql ou Oracle).
     */
    public Database getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(Database t) {
        databaseType = t;
    }

    // TODO: ler do arquivo/variaveis de ambiente
    public String getSolrUrl() {
        return "http://localhost:8983/solr/";
    }
}