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
public class PadraoMetadadosHibernateDaoIT extends AbstractDaoTest {
    
    @Autowired
    PadraoMetadadosHibernateDAO instance;
    

    /**
     * Test of delete method, of class RepositoryHibernateDAO.
     */
    @Test
    @Ignore
    public void testDelete() {
       
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGet() {
        int id = 1;
        PadraoMetadados p = instance.get(id);
        
        assertEquals("lom", p.getNome());
        assertEquals("oai_lom", p.getMetadataPrefix());
        assertEquals("lom", p.getNamespace());
    }

    /**
     * Test of getAll method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGetAll() {
        List expResult = null;
        List result = instance.getAll();
        assertEquals(3, result.size());
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
