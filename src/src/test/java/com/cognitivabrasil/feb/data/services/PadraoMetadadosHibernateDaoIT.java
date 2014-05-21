/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.PadraoMetadados;
import java.util.List;
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
@ContextConfiguration(locations="classpath:applicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager",  defaultRollback = true)
public class PadraoMetadadosHibernateDaoIT extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Autowired
    MetadataRecordService instance;
    

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
        
        assertEquals("lom", p.getName());
        assertEquals("oai_lom", p.getMetadataPrefix());
        assertEquals("lom", p.getNamespace());
    }

    /**
     * Test of getAll method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGetAll() {
        List<PadraoMetadados> result = instance.getAll();
        assertEquals(3, result.size());
    }
    

    /**
     * Test of save method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testSaveAndUpdate() throws Exception {
        PadraoMetadados n = new PadraoMetadados();
        n.setName("Novo");
        n.setNamespace("obaa");
        n.setMetadataPrefix("obaa");
        
        instance.save(n);
        
        PadraoMetadados u = instance.get(1);
        u.setName("Updated");
        
        instance.save(u);
    }

}
