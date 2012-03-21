/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import javax.sql.DataSource;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Integration tests of the UsuarioHibernateDao 
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class DocumentosHibernateDaoIT {

    @Autowired
    DocumentosHibernateDAO instance;
    @Autowired
    DataSource dataSource;
    IDatabaseConnection connection;

    public DocumentosHibernateDaoIT() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void init() throws Exception {
        System.out.println("Before");
        // Insere os dados no banco de dados
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getBeforeDataSet());
    }

    @After
    public void after() throws Exception {
        System.out.println("After");
        //Limpa a base de dados
        DatabaseOperation.DELETE_ALL.execute(getConnection(), getBeforeDataSet());
    }

    private IDatabaseConnection getConnection() throws Exception {
        System.out.println("Get Connection");
        // Pega a conexão com o banco de dados
        if (connection == null) {
            Connection con = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = con.getMetaData();
            connection = new DatabaseConnection(con);
        }

        return connection;

    }

    private IDataSet getBeforeDataSet() throws Exception {
        System.out.println("get DataSet");
        // Pega o arquivo de para inserir
        File file = new File("src/test/resources/documentosDataBefore.xml");
        return new FlatXmlDataSet(file);
    }

    private IDataSet getAfterDataSet() throws Exception {
        System.out.println("get DataSet");
        // Pega o arquivo de para inserir
        File file = new File("src/test/resources/usuarioDataAfter.xml");
        return new FlatXmlDataSet(file);
    }

    
    @Test
    public void testGet() {
        DocumentoReal d = instance.get(1);
        
        assertEquals("oai:cesta2.cinted.ufrgs.br:123456789/57", d.getObaaEntry());
        assertEquals("Cesta", d.getRepositorio().getNome());
    }
}
