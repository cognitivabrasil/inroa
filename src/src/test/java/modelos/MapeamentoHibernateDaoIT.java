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
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.test.context.transaction.AfterTransaction;

/**
 *
 * @author paulo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class MapeamentoHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    MapeamentoHibernateDAO instance;
    
    @Test
    public void testGet() {
        Mapeamento m = instance.get(1);
        
        assertEquals("Padrao", m.getName());
        assertEquals("xslt", m.getXslt());
    }
    
    @Test
    public void testGetAll() {
        assertEquals(2, instance.getAll().size());
    }
    
    @Test
    public void testDelete() {
        Mapeamento m = instance.get(2);
        
        instance.delete(m);
        
        assertEquals(1, instance.getAll().size());
    }

    @Test
    public void testSave() {
        Mapeamento m = new Mapeamento();


        m.setName("Jorjao");
        m.setDescription("Blabla");
        m.setXslt("xslt");
        
        PadraoMetadados p = new PadraoMetadados();
        p.setId(1);
        
        m.setPadraoMetadados(p);
       instance.save(m);

        Mapeamento m2 = instance.get(1);

        m2.setName("Updated");
        m2.setXslt("blabla");

        instance.save(m2);
        
        List<Mapeamento> m3 = instance.getAll();
        assertEquals(3, m3.size());
        
        

        updated = true;
    }

    @AfterTransaction
    public void testSaveAndUpdate2() throws Exception {
        if (updated) {
            updated = false;
            String[] ignore = {"id", "padrao_id"};
            String[] sort = {"nome"};
            Assertion.assertEqualsIgnoreCols(new SortedTable(getAfterDataSet().getTable("mapeamentos"), sort), new SortedTable(getConnection().createDataSet().getTable("mapeamentos"), sort), ignore);

        }
    }
}
