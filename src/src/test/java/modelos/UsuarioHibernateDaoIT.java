/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;




/**
 *
 * @author paulo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:testApplicationContext.xml")
public class UsuarioHibernateDaoIT {
    
    @Autowired
    UsuarioHibernateDAO instance;
    
    public UsuarioHibernateDaoIT() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
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
    @Ignore
    public void testDelete() {
        System.out.println("delete");
        Usuario r = null;
        UsuarioHibernateDAO instance = new UsuarioHibernateDAO();
        instance.delete(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
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
        assertEquals(1, result.size());
    }

    /**
     * Test of save method, of class UsuarioHibernateDAO.
     */
    @Test
    @Ignore
    public void testSave() {
        System.out.println("save");
        Usuario r = null;
        UsuarioHibernateDAO instance = new UsuarioHibernateDAO();
        instance.save(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
