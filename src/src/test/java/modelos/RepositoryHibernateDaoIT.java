/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
import org.springframework.test.context.transaction.TransactionConfiguration;




/**
 *
 * @author paulo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
public class RepositoryHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    RepositoryHibernateDAO instance;
 
    /**
     * Test of delete method, of class RepositoryHibernateDAO.

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
    
    @Test
    public void testSize() {
        int id = 1;
        Repositorio cesta = instance.get(id);
        
        assertEquals("Cesta", cesta.getNome());
        assertEquals(4, (int)cesta.getSize());
    }

    /**
     * Test of getAll method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        List expResult = null;
        List result = instance.getAll();
        assertEquals(3, result.size());
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
        assertEquals("lom", cesta.getNamespace());
    }
    
    @Test
    public void testDelete() {
        Repositorio cesta = instance.get(1);
        
        instance.delete(cesta);
        
        List<Repositorio> l = instance.getAll();
        assertEquals(2, l.size());
        
    }
    
    @Test
    public void testGetMetadataRecord() {
        int id = 1;
        Repositorio cesta = instance.get(id);
        
        PadraoMetadados p = cesta.getPadraoMetadados();
        assertEquals("lom", p.getNome());
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
