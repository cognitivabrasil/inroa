/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.SortedTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;

import feb.data.entities.Mapeamento;
import feb.data.entities.PadraoMetadados;

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
    public void exists() {
    	assertThat(instance.exists("Padrao2"), is(true));
    	assertThat(instance.exists("lom8"), is(false));
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
