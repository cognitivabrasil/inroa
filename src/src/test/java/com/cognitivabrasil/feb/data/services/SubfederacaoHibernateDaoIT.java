/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.RepositorioSubFed;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.dbunit.Assertion;
import org.dbunit.dataset.SortedTable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class SubfederacaoHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    FederationService instance;
    @Autowired
    private DocumentService docDao;
    @PersistenceContext
    private EntityManager em;

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
        //System.out.println("get");
        int id = 1;
        SubFederacao ufrgs = instance.get(id);

        assertEquals("UFRGS", ufrgs.getName());
    }

    /**
     * Test of getAll method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGetAll() {
        //System.out.println("getAll");;
        List result = instance.getAll();
        assertEquals(4, result.size());
    }

    @Test
    @Ignore("Dont know why, it gives an error") // TODO:
    public void testRepository() {
        //System.out.println("repository");;

        List<SubFederacao> l = instance.getAll();
        SubFederacao ufrgs = l.get(0);

        List<RepositorioSubFed> r = new ArrayList<RepositorioSubFed>(ufrgs.getRepositorios());

        RepositorioSubFed t = r.get(0);

        assertEquals("RepUfrgs1", t.getName());
        assertEquals("Get from getDocumentos", 2, t.getDocumentos().size());
        assertEquals("Get from size()", 2, (int) t.getSize());


    }

    @Test
    public void testDelete() {
        SubFederacao cesta = instance.get(1);

        instance.delete(cesta);


        List<SubFederacao> l = instance.getAll();
        assertEquals(3, l.size());


    }

    @Test
    public void testDeleteRemovesDocuments() {
        SubFederacao cesta = instance.get(1);

        int sizeCesta = 0;
        for (RepositorioSubFed r : cesta.getRepositorios()) {
            sizeCesta += r.getDocumentos().size();
        }
        int sizeAllBefore = docDao.getAll().size();
        int sizeAfterShould = sizeAllBefore - sizeCesta;

        instance.delete(cesta);

        assertEquals("Size of UFRGS before", 2, sizeCesta);
        assertEquals("Size of UFRGS after deletion", sizeAfterShould, docDao.getAll().size());
    }

    @Test
    public void testGetByName() {
        SubFederacao ufrgs = instance.get("UFRGS");


        assertThat(ufrgs, is(notNullValue()));
        assertEquals("UFRGS", ufrgs.getName());
        assertEquals(1, (int) ufrgs.getId());
    }

    @Test
    public void testGetByNameCaseInsensitive() {
        SubFederacao ufrgs = instance.get("ufRgs");


        assertThat(ufrgs, notNullValue());
        assertEquals("UFRGS", ufrgs.getName());
        assertEquals(1, (int) ufrgs.getId());
    }

    @Test
    public void testGetByNameNonExisting() {
        SubFederacao fake = instance.get("rgterter");

        assertThat(fake, is(nullValue()));

    }

    @Test
    public void testSaveAndUpdate() throws Exception {
        //System.out.println("save");

        SubFederacao f = new SubFederacao();
        f.setName("Nova");
        f.setUrl("http://nova");
        f.setDataXML("2012-03-19T18:01:54Z");

        Set<String> listaRep = new HashSet<>();
        listaRep.add("marcos");
        listaRep.add("jorge");
        listaRep.add("preto");

        f.atualizaListaSubRepositorios(listaRep);

        assertThat(instance.getAll(), hasSize(4));

        instance.save(f);

        SubFederacao fTeste = instance.get("Nova");
        assertThat(fTeste, is(notNullValue()));
        assertEquals("Nova", fTeste.getName());
        // assertThat(fTeste.getRepositorios(), hasSize(3));

        assertThat(instance.getAll(), hasSize(5));

        //assertEquals("Nr correto de Subfederacoes apos adicao", 5, instance.getAll().size());
        //System.out.println("Repositorios: "+fTeste.getRepositorios());

        SubFederacao f2 = instance.get(3);
        f2.setName("Jorjao");
        f2.setUrl("http://jorjao");

        instance.save(f2);

        updated = true;

    }

    @Test
    public void testSaveDataXML() {
        String date = "1984-08-21T05:35:00Z";
        SubFederacao fed = new SubFederacao();
        fed.setName("marcosn");
        fed.setDataXMLTemp(date);
        fed.setUltimaAtualizacao(new Date());

        assertThat(fed.getDataXML(), equalTo(date));
        instance.save(fed);
        em.flush();
        em.clear();
        SubFederacao fed2 = instance.get("marcosn");
        assertThat(fed2.getDataXML(), equalTo(date));
    }

    /*
     * This is needed to get over AbstractTransactionalJUnit4SpringContextTests
     * limitations TODO: find a more elegant and generic solution to integrate
     * feb.spring and DbUnit, maybe with annotations?
     */
    @AfterTransaction
    public void testSaveAndUpdate2() throws Exception {
        if (updated) {
            updated = false;
            String[] ignore = {"id", "data_xml", "data_ultima_atualizacao", "descricao", "version"};
            String[] sort = {"nome"};
            Assertion.assertEqualsIgnoreCols(new SortedTable(getAfterDataSet().getTable("dados_subfederacoes"), sort), new SortedTable(getConnection().createDataSet().getTable("dados_subfederacoes"), sort), ignore);

        }
    }
}
