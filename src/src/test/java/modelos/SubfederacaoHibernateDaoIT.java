/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;




/**
 *
 * @author paulo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class SubfederacaoHibernateDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    SubFederacaoHibernateDAO instance;
    @Autowired
    DataSource dataSource;
    static IDatabaseConnection connection;

    public SubfederacaoHibernateDaoIT() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        connection.close();
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
       // DatabaseOperation.DELETE_ALL.execute(getConnection(), getBeforeDataSet());
    }

    private IDatabaseConnection getConnection() throws Exception {
        System.out.println("Get Connection");
        // Pega a conexão com o banco de dados
        if (connection == null) {
            Connection con = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = con.getMetaData();
            connection = new DatabaseConnection(con);
            System.out.println("New connection");
        }

        return connection;

    }

    private IDataSet getBeforeDataSet() throws Exception {
        System.out.println("get DataSet");
        // Pega o arquivo de para inserir
        File file = new File("src/test/resources/documentosDataBefore.xml");
        return new FlatXmlDataSet(file);
    }
    /**
     * Test of delete method, of class RepositoryHibernateDAO.

    /**
     * Test of get method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id = 1;
        SubFederacao ufrgs = instance.get(id);
        
        assertEquals("UFRGS", ufrgs.getNome());
    }
    
   /**
     * Test of getAll method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");;
        List result = instance.getAll();
        assertEquals(3, result.size());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    @Transactional
    public void testRepository() {
        System.out.println("repository");;
        
        List<SubFederacao> l = instance.getAll();
        SubFederacao ufrgs = l.get(0);
        
        List<RepositorioSubFed> r = new ArrayList<RepositorioSubFed>(ufrgs.getRepositorios());
        
        RepositorioSubFed t = r.get(0);
        
        assertEquals("RepUfrgs1", t.getNome());
 

    }
    
    @Test
    public void testDelete() {       
        SubFederacao cesta = instance.get(1);
      
        instance.delete(cesta);
     
        List<SubFederacao> l = instance.getAll();
        assertEquals(2, l.size());

        
    }

    /**
     * Test of save method, of class RepositoryHibernateDAO.
     */
    @Test
    @Ignore
    public void testSave() {
        System.out.println("save");
        Repositorio r = null;
        RepositoryHibernateDAO instance = new RepositoryHibernateDAO();
        instance.save(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
