/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author paulo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class SubfederacaoHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    SubFederacaoHibernateDAO instance;

    public SubfederacaoHibernateDaoIT() {
    }

    /**
     * Test of delete method, of class RepositoryHibernateDAO.
     *
     * /**
     * Test of get method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id = 1;
        SubFederacao ufrgs = instance.get(id);

        assertEquals("UFRGS", ufrgs.getNome());
    }

    /**
     * Test of getAll method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");;
        List result = instance.getAll();
        assertEquals(3, result.size());
    }

    @Test
    @Transactional
    public void testRepository() {
        System.out.println("repository");;

        List<SubFederacao> l = instance.getAll();
        SubFederacao ufrgs = l.get(0);

        List<RepositorioSubFed> r = new ArrayList<RepositorioSubFed>(ufrgs.getRepositorios());

        RepositorioSubFed t = r.get(0);

        assertEquals("RepUfrgs1", t.getNome());
        assertEquals("Get from getDocumentos", 2, t.getDocumentos().size());
        assertEquals("Get from size()", 2, (int) t.getSize());


    }

    @Test
    public void testDelete() {
        SubFederacao cesta = instance.get(1);

        instance.delete(cesta);

        List<SubFederacao> l = instance.getAll();
        assertEquals(2, l.size());


    }

    @Test
    public void testGetByName() {
        SubFederacao ufrgs = instance.get("UFRGS");


        assertThat(ufrgs, is(notNullValue()));
        assertEquals("UFRGS", ufrgs.getNome());
        assertEquals(1, (int) ufrgs.getId());
    }

    @Test
    public void testGetByNameCaseInsensitive() {
        SubFederacao ufrgs = instance.get("ufRgs");


        assertThat(ufrgs, is(notNullValue()));
        assertEquals("UFRGS", ufrgs.getNome());
        assertEquals(1, (int) ufrgs.getId());
    }

    @Test
    public void testGetByNameNonExisting() {
        SubFederacao fake = instance.get("rgterter");

        assertThat(fake, is(nullValue()));

    }

    @Test
    public void testSaveAndUpdate() throws Exception {
        System.out.println("save");

        SubFederacao f = new SubFederacao();
        f.setNome("Nova");
        f.setUrl("http://nova");
        f.setDataXML(new Date());

        instance.save(f);

        SubFederacao fTeste = instance.get("Nova");
        assertThat(fTeste, is(notNullValue()));
        assertEquals("Nova", fTeste.getNome());

        assertEquals("Nr correto de Subfederacoes apos adicao", 4, instance.getAll().size());

        SubFederacao f2 = instance.get(3);
        f2.setNome("Jorjao");
        f2.setUrl("http://jorjao");

        instance.save(f2);

        updated = true;

    }

    /*
     * This is needed to get over AbstractTransactionalJUnit4SpringContextTests
     * limitations TODO: find a more elegant and generic solution to integrate
     * spring and DbUnit, maybe with annotations?
     */
    @AfterTransaction
    public void testSaveAndUpdate2() throws Exception {
        if (updated) {
            updated = false;
            String[] ignore = {"id", "data_xml", "data_ultima_atualizacao", "descricao"};
            String[] sort = {"nome"};
            Assertion.assertEqualsIgnoreCols(new SortedTable(getAfterDataSet().getTable("dados_subfederacoes"), sort), new SortedTable(getConnection().createDataSet().getTable("dados_subfederacoes"), sort), ignore);

        }
    }
}
