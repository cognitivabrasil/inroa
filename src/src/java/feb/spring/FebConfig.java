package feb.spring;

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
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class represents the FEB (database) configuration.
 * 
 * It has accessors for the common database parameters. It should be used as a
 * managed bean, and it has to be configured to receive, by injection, a
 * {@link File} and a {@link Properties}.
 * 
 * It will try to load the properties from the File, but if it doesn't exist, it
 * will use those in the Properties. The password will be saved ENCRYPTED in the
 * file.
 * 
 * To save the file, you have to make sure that the destination folder is
 * writable by the Servlet container.
 * 
 * @author Paulo Schreiner <paulo@cognitivabrasil.com.br>
 * 
 */
public class FebConfig {
	private Logger logger = Logger.getLogger(FebConfig.class);

	private StringEncryptor encryptor;

	private Properties properties;
	private File file;

	private String host;
	private Integer port;
	private transient String password;
	private String username;
	private String database;
	private String filename;

	@Autowired
	public void setEncryptor(StringEncryptor e) {
		encryptor = e;
	}

	protected void save(Writer w) throws IOException {
		Properties n = new Properties();
		n.setProperty("Postgres.host", host);
		n.setProperty("Postgres.port", port.toString());
		n.setProperty("Postgres.username", username);
		n.setProperty("Postgres.database", database);
		n.setProperty("Postgres.password", "ENC(" + encryptor.encrypt(password)
				+ ")");

		n.store(w, null);
	}

	/**
	 * Saves the (possibly modified) configuration file.
	 * 
	 * It will save the file to the same location that it tried to open.
	 * This method should only be called on a Spring Managed bean, NOT
	 * on any other. To update an object you can try something like:
	 * 
	 * {@code febConfig.updateFrom(newConfig);
	 * febConfig.save}
	 * 
	 * where febConfig is the Spring managed one and newCondig is the changed one.
	 * 
	 * NOTE: Actually, you can also call save on non-spring managed beans, but you
	 * have to initialize their lifecycle yourself, namely call {@link setFile()} or 
	 * {@link setFilename()} and {@link setDefaultProperties()} followed by 
	 * {@link postConstruct()}.
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		if (file == null) {
			logger.warn("File is null, trying to open.");
			file = new File(this.filename);
		}
		Writer w = new FileWriter(file);
		save(w);
	}

	/**
	 * @param f
	 *            the file to load and save the configuration
	 */
	public void setFile(File f) {
		file = f;
	}

	/**
	 * @param p
	 *            the default properties
	 */
	public void setDefaultProperties(Properties p) {
		properties = p;
	}

	/**
	 * Tries to populate fields with the File, if not possible, populates with
	 * default properties.
	 * 
	 * Will be run after successful injection.
	 */
	@PostConstruct
	public void postConstruct() {
		Properties p = new EncryptableProperties(encryptor);
		try {
			if (file != null) {
				InputStream s = new FileInputStream(file);
				p.load(s);
				properties = p;
				s.close();
			}
		} catch (IOException e) {
			logger.warn("Config file is missing, using defaults");
		}
		host = properties.getProperty("Postgres.host");
		port = Integer.parseInt(properties.getProperty("Postgres.port"));
		username = properties.getProperty("Postgres.username");
		database = properties.getProperty("Postgres.database");
		password = properties.getProperty("Postgres.password");
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
	 * In order to be able to save the febConf, you have to use the
	 * Spring managed bean, not a newly created one (such as those
	 * returned by spring forms).
	 * @param febConf 
	 */
	public void updateFrom(FebConfig febConf) {
	if(isNotBlank(febConf.getPassword())) {	setPassword(febConf.getPassword()); }
	setDatabase(febConf.getDatabase());
		setUsername(febConf.getUsername());
		setPort(febConf.getPort());
		setHost(febConf.getHost());	
	}
	
    @Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
    }

}
