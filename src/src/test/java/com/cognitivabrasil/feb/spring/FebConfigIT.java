package com.cognitivabrasil.feb.spring;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.cognitivabrasil.feb.AppConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class FebConfigIT extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private StringEncryptor stringEncryptor;

    private FebConfig c;
    private Properties defaultProperties;

    private FebConfig initFebConfig() {
        FebConfig conf = new FebConfig();
        conf.setEncryptor(stringEncryptor);
        return conf;
    }

    @Before
    public void before() {
        c = initFebConfig();

        defaultProperties = new Properties();
        defaultProperties.setProperty("Postgres.port", "3333");
        defaultProperties.setProperty("Postgres.password", "pwd");
        defaultProperties.setProperty("Postgres.database", "db");
        defaultProperties.setProperty("Postgres.username", "user");
        defaultProperties.setProperty("Postgres.host", "127.0.0.1");

    }

    @Test
    public void simpleLoad() {
        assertThat(stringEncryptor.decrypt("FgXMlUcSSyN4Q7RhUP5xQw=="), equalTo("feb@RNP"));

    }

    @Test
    public void loadFromFile() {
        c.setFile(new File("src/main/resources/feb.properties"));
        c.setDefaultProperties(new Properties());
        c.postConstruct();

        assertThat(c.getPort(), equalTo(5432));
        assertThat(c.getPassword(), equalTo("feb@RNP"));
        assertThat(c.getDatabase(), equalTo("federacao"));

    }

    /**
     * Tests that we load the default in case the file is not found
     */
    @Test
    public void loadWithoutFile() {
        c.setFile(new File("estearquivonaoexiste"));

        c.setDefaultProperties(defaultProperties);
        c.postConstruct();

        assertThat(c.getPort(), equalTo(3333));
        assertThat(c.getPassword(), equalTo("pwd"));
    }

    /**
     * Tests if saves the password in an encrypted form.
     *
     * @throws IOException
     */
    @Test
    public void saveEncrypted() throws IOException {
        StringWriter w = new StringWriter();

        c.setFile(new File("src/main/resources/feb.properties"));
        c.setDefaultProperties(defaultProperties);
        c.postConstruct();

        c.save(w);

        Properties p = new Properties();
        p.load(new StringReader(w.toString()));

        assertThat((String) p.getProperty("Postgres.port"), equalTo("5432"));
        assertThat((String) p.getProperty("Postgres.password"), startsWith("ENC("));
    }

    /**
     * test that we can actually save to a file.
     *
     * @throws IOException
     */
    @Test
    public void saveFile() throws IOException {

        // first, save a file
        File f = new File("/tmp/feb.properties");
        if (f.exists()) {
            f.delete();
        }

        assertFalse("Arquivo /tmp/feb.properties não deveria existir mas existe", f.exists());

        c.setFile(f);
        c.setDefaultProperties(defaultProperties);
        c.postConstruct();

        c.save();
        // now, open it and check

        c = initFebConfig();
        c.setFile(new File("/tmp/feb.properties"));
        c.setDefaultProperties(new Properties());
        c.postConstruct();

        assertThat(c.getPort(), equalTo(3333));
        assertThat(c.getDatabase(), equalTo("db"));
        assertThat(c.getPassword(), equalTo("pwd"));
        assertThat(c.getHost(), equalTo("127.0.0.1"));

        f.delete();
    }

    @Test
    public void filenameNonExisting() throws IOException {
        // first, save a file
        File f = new File("/tmp/feb.properties");
        if (f.exists()) {
            f.delete();
        }

        assertFalse(f.exists());

        c.setFilename("/tmp/feb.properties");
        c.setDefaultProperties(defaultProperties);
        c.postConstruct();

        c.save();
    }

    @Test
    public void update() {
        c.setFile(new File("src/main/resources/feb.properties"));
        c.setDefaultProperties(new Properties());
        c.postConstruct();

        FebConfig other = new FebConfig();
        other.setFilename("/tmp/bbbbfeb.properties");
        other.setPort(234);

        c.updateFrom(other);
        assertThat(c.getPort(), equalTo(234));
        assertThat(c.getPassword(), equalTo("feb@RNP"));
    }

}
