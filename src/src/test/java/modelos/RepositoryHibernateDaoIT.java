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
//@ContextConfiguration(locations = {"testApplicationContext.xml"})
public class RepositoryHibernateDaoIT {
    
    @Autowired
    RepositoryHibernateDAO instance;
    
    public RepositoryHibernateDaoIT() {
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
     * Test of delete method, of class RepositoryHibernateDAO.
     */
    @Test
    @Ignore
    public void testDelete() {
        System.out.println("delete");
        Repositorio r = null;
        RepositoryHibernateDAO instance = new RepositoryHibernateDAO();
        instance.delete(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id = 1;
        Repositorio cesta = instance.get(id);
        
        assertEquals("Cesta", cesta.getNome());
    }

    /**
     * Test of getAll method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        List expResult = null;
        List result = instance.getAll();
        assertEquals(1, result.size());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testRepository() {
        List<Repositorio> l = instance.getAll();
        Repositorio cesta = l.get(0);
        
        assertEquals("Cesta", cesta.getNome());
        assertEquals("dfsd", cesta.getDescricao());
        assertEquals("http://cesta2.cinted.ufrgs.br/oai/request", cesta.getUrl());
        //assertEquals("cesta", cesta.getNomeFederacao());
        //assertEquals(24, cesta.getPeriodicidate());
        assertEquals("lom", cesta.getNamespace());
        assertEquals("lom", cesta.getUltimaAtualizacao());
 

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
