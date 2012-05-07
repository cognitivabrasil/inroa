/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.dbunit.Assertion;
import org.dbunit.dataset.SortedTable;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
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
public class RepositoryHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    RepositoryHibernateDAO instance;
    
    @Autowired
    DocumentosHibernateDAO docDao;
    
    @Autowired
    MapeamentoDAO mapDao;

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
        Repositorio cesta = instance.get(id);

        assertEquals("Cesta", cesta.getNome());
    }

    @Test
    public void testSize() {
        int id = 1;
        Repositorio cesta = instance.get(id);

        assertEquals("Cesta", cesta.getNome());
        assertEquals(4, (int) cesta.getSize());
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
    public void testDeleteRemovesDocumentos() {
        Repositorio cesta = instance.get(1);
        
        int sizeCesta = cesta.getDocumentos().size();
        int sizeAllBefore = docDao.getAll().size();
        int sizeAfter = sizeAllBefore - sizeCesta;

        instance.delete(cesta);        

        assertEquals("Size of Cesta before", 5, sizeCesta);
        assertEquals("Size of Cesta after deletion", sizeAfter, docDao.getAll().size());
    }
   

    @Test
    public void testGetMetadataRecord() {
        int id = 1;
        Repositorio cesta = instance.get(id);

        PadraoMetadados p = cesta.getPadraoMetadados();
        assertEquals("lom", p.getNome());
    }

    @Test
    public void getByNameExists() {
        Repositorio cesta = instance.get("Cesta");

        assertThat(cesta, is(notNullValue()));
        assertEquals(1, (int) cesta.getId());
    }

    @Test
    public void getByNameCaseInsensitive() {
        Repositorio cesta = instance.get("cEsTa");

        assertThat(cesta, is(notNullValue()));
        assertEquals(1, (int) cesta.getId());
    }

    @Test
    public void getByNameNoSuchRep() {
        Repositorio cesta = instance.get("Cesdsfdsffsdta");

        assertThat(cesta, is(nullValue()));
    }

    /**
     * Test of save method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testSave() {
        Repositorio r = new Repositorio();

        r.setNome("Novo");
        r.setNamespace("obaa");
        r.setUrl("http://url");
        r.setMapeamento(mapDao.get(1));

        instance.save(r);

        Repositorio r2 = instance.get(1);

        r2.setNome("Jorjao");

        instance.save(r2);

        updated = true;
    }
    @AfterTransaction
    public void testSaveAndUpdate2() throws Exception {
        if (updated) {
            updated = false;
            String[] ignore = {"id", "metadata_prefix", "periodicidade_horas", "padrao_metadados", "mapeamento_id", "descricao", "data_ultima_atualizacao",
                "set", "tipo_mapeamento_id", "tipo_sincronizacao", "data_xml"};
            String[] sort = {"nome"};
            Assertion.assertEqualsIgnoreCols(new SortedTable(getAfterDataSet().getTable("repositorios"), sort), new SortedTable(getConnection().createDataSet().getTable("repositorios"), sort), ignore);

        }
    }
}
