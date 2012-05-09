/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import static org.hamcrest.Matchers.*;
import java.io.File;
import java.sql.Connection;
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
import org.dbunit.Assertion;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Integration tests of the UsuarioHibernateDao
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml", "classpath:spring-security.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class UsuarioHibernateDaoIT extends AbstractDaoTest {

	
    @Autowired
    UsuarioDAO instance;


    public UsuarioHibernateDaoIT() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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

    @Test
    public void testAuthenticateNoSuchUser() {
        Usuario u1 = instance.authenticate(null, "random");
        assertThat(u1, is(nullValue()));
        
        u1 = instance.authenticate("nosuchuser", "random");
        assertThat(u1, is(nullValue()));
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
    
    @Test
    public void testGetPermissions() {
        Usuario u = instance.get("admin");
        
        assertThat(u.getPermissions(), hasItems("PERM_MANAGE_USERS", "PERM_MANAGE_MAPPING"));
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
    @Rollback(false)
    public void testSaveAndUpdate() throws Exception {
        System.out.println("save");
        Usuario r = instance.get(1);
        r.setLogin("jorjao");
        instance.save(r);

        Usuario r2 = new Usuario();
        r2.setLogin("paulo");
        r2.setPasswordMd5("bla");
        r2.setDescription("teste");

        updated = true;
        instance.save(r2);



    }

    /* This is needed to get over AbstractTransactionalJUnit4SpringContextTests limitations
     * TODO: find a more elegant and generic solution to integrate spring and DbUnit, maybe with annotations?
     */
    @AfterTransaction
    public void testSaveAndUpdate2() throws Exception {
        if (updated) {
            updated = false;
            String[] ignore = {"id", "permissions", "role"};
            String[] sort = {"login"};
            Assertion.assertEqualsIgnoreCols(new SortedTable(getAfterDataSet().getTable("usuarios"), sort), new SortedTable(getConnection().createDataSet().getTable("usuarios"), sort), ignore);

        }
    }
}
