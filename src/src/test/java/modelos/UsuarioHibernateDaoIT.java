/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.File;
import java.sql.DatabaseMetaData;
import java.util.List;
import javax.sql.DataSource;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.SortedTable;
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
public class UsuarioHibernateDaoIT {

    @Autowired
    UsuarioHibernateDAO instance;
    @Autowired
    DataSource dataSource;
    static IDatabaseConnection connection;

    public UsuarioHibernateDaoIT() {
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
           C onnection con = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = con.getMetaData();
            connection = new DatabaseConnection(con);
        }

        return connection;

    }

    private IDataSet getBeforeDataSet() throws Exception {
        System.out.println("get DataSet");
        // Pega o arquivo de para inserir
        File file = new File("src/test/resources/usuarioDataBefore.xml");
        return new FlatXmlDataSet(file);
    }

    private IDataSet getAfterDataSet() throws Exception {
        System.out.println("get DataSet");
        // Pega o arquivo de para inserir
        File file = new File("src/test/resources/usuarioDataAfter.xml");
        return new FlatXmlDataSet(file);
    }

    
    /**
     * Test of authenticate method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testAuthenticate() {

        Usuario u1 = instance.authenticate("admin", "teste1");
        assertEquals(null, u1);

        Usuario u2 = instance.authenticate("admin", "teste");
        assertEquals("admin", u2.getLogin());
    }

    /**
     * Test of delete method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Usuario r = instance.get(2);
        instance.delete(r);

        assertEquals(1, instance.getAll().size());
    }

    /**
     * Test of get method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testGet() {
        System.out.println("*****************get");
        int id = 1;
        Usuario u = instance.get(id);
        assertEquals("admin", u.getLogin());
        assertEquals("698dc19d489c4e4db73e28a713eab07b", u.getPasswordMd5());
        assertEquals("Administrador da federação", u.getDescription());
    }

    @Test
    public void testGetByLogin() {
        Usuario u = instance.get("admin");
        assertEquals("admin", u.getLogin());
        assertEquals("698dc19d489c4e4db73e28a713eab07b", u.getPasswordMd5());
        assertEquals("Administrador da federação", u.getDescription());
    }

    /**
     * Test of getAll method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        List result = instance.getAll();
        assertEquals(2, result.size());
    }

    /**
     * Test of save method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testSaveAndUpdate() throws Exception {
        System.out.println("save");
        Usuario r = instance.get(1);
        r.setLogin("jorjao");
        instance.save(r);

        Usuario r2 = new Usuario();
        r2.setLogin("paulo");
        r2.setPasswordMd5("bla");
        r2.setDescription("teste");

        instance.save(r2);

        String[] ignore = {"id"};

        Assertion.assertEqualsIgnoreCols(new SortedTable(getAfterDataSet().getTable("usuarios")), new SortedTable(getConnection().createDataSet().getTable("usuarios")), ignore);

    }
}
