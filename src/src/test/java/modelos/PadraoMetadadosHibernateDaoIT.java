/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.dbunit.Assertion;
import org.dbunit.dataset.SortedTable;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;




/**
 *
 * @author paulo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class PadraoMetadadosHibernateDaoIT extends AbstractDaoTest {
    
    @Autowired
    PadraoMetadadosHibernateDAO instance;
    

    /**
     * Test of delete method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testDelete() {
       PadraoMetadados p = instance.get(2);
       instance.delete(p);
       
       assertEquals(2, instance.getAll().size());
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
    public void testSaveAndUpdate() throws Exception {
        PadraoMetadados n = new PadraoMetadados();
        n.setNome("Novo");
        n.setNamespace("obaa");
        n.setMetadataPrefix("obaa");
        
        instance.save(n);
        
        PadraoMetadados u = instance.get(1);
        u.setNome("Updated");
        
        instance.save(u);

        updated = true;

    }

    /* This is needed to get over AbstractTransactionalJUnit4SpringContextTests limitations
     * TODO: find a more elegant and generic solution to integrate spring and DbUnit, maybe with annotations?
     */
    @AfterTransaction
    public void testSaveAndUpdate2() throws Exception {
        if (updated) {
            updated = false;
            String[] ignore = {"id"};
            String[] sort = {"nome"};
            Assertion.assertEqualsIgnoreCols(new SortedTable(getAfterDataSet().getTable("padraometadados"), sort), new SortedTable(getConnection().createDataSet().getTable("padraometadados"), sort), ignore);

        }
    }
}
