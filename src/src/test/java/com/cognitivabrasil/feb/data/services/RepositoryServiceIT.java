/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.PadraoMetadados;
import com.cognitivabrasil.feb.data.entities.Repositorio;

/**
 *
 * @author paulo
 * @author Marcos Nunes <marcos@cognitivabrasil.com.br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext
public class RepositoryServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    RepositoryService repDao;
    @Autowired
    DocumentService docDao;
    @Autowired
    MappingService mapDao;
    @PersistenceContext
    private EntityManager em;

    /**
     * Test of delete method, of class RepositoryHibernateDAO.
     *
     * /**
     * Test of get method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGet() {
        int id = 1;
        Repositorio cesta = repDao.get(id);

        assertEquals("Cesta", cesta.getName());
        assertThat(cesta.getDataXml(), equalTo("1984-08-21T07:35:00Z"));
    }

    @Test
    public void testSize() {
        int id = 1;
        Repositorio cesta = repDao.get(id);

        assertEquals("Cesta", cesta.getName());
        assertEquals(4, (int) repDao.size(cesta));
    }

    /**
     * Test of getAll method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGetAll() {
        List result = repDao.getAll();
        assertEquals(4, result.size());
    }

    @Test
    public void testRepository() {
        List<Repositorio> l = repDao.getAll();
        Repositorio cesta = l.get(0);

        assertEquals("Cesta", cesta.getName());
        assertEquals("dfsd", cesta.getDescricao());
        assertEquals("http://cesta2.cinted.ufrgs.br/oai/request", cesta.getUrl());
        assertEquals("lom", cesta.getNamespace());
    }

    @Test
    public void testDelete() {
        int sizeBefore = repDao.getAll().size();

        Repositorio cesta = repDao.get(1);
        repDao.delete(cesta);

        int sizeAfter = repDao.getAll().size();
        assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    public void testDeleteRepAndDocuments() {
        Repositorio cesta = repDao.get(1);

        int sizeCesta = 4;
        int sizeAllBefore = docDao.getAll().size();
        int sizeAfter = sizeAllBefore - sizeCesta;

        repDao.delete(cesta);

        assertEquals("Size of Cesta after deletion", sizeAfter, docDao.getAll().size());
    }

    @Test
    public void testGetMetadataRecord() {
        int id = 1;
        Repositorio cesta = repDao.get(id);

        PadraoMetadados p = cesta.getPadraoMetadados();
        assertEquals("lom", p.getName());
    }

    @Test
    public void getByNameExists() {
        Repositorio cesta = repDao.get("Cesta");

        assertThat(cesta, is(notNullValue()));
        assertEquals(1, (int) cesta.getId());
    }

    @Test
    public void getByNameCaseInsensitive() {
        Repositorio cesta = repDao.get("cEsTa");

        assertThat(cesta, is(notNullValue()));
        assertEquals(1, (int) cesta.getId());
    }

    @Test
    public void getByNameNoSuchRep() {
        Repositorio cesta = repDao.get("Cesdsfdsffsdta");

        assertThat(cesta, is(nullValue()));
    }

    /**
     * Test of save method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testSave() {
        Repositorio r = new Repositorio();

        r.setName("Novo");
        r.setNamespace("obaa");
        r.setUrl("http://url");
        r.setMapeamento(mapDao.get(1));

        repDao.save(r);

        Repositorio r2 = repDao.get(1);

        r2.setName("Jorjao");

        repDao.save(r2);
    }

    @Test
    public void testUpdateDate() {
        Repositorio rep = new Repositorio();
        String date = "1984-08-21T13:32:03Z";
        rep.setDataXmlTemp(date);
        rep.setUltimaAtualizacao(DateTime.now());
        rep.setName("marcosn");
        rep.setNamespace("obaa");
        rep.setUrl("http://url");
        rep.setMapeamento(mapDao.get(1));

        assertThat(rep.getDataXml(), equalTo(date));
        repDao.save(rep);
        em.flush();
        em.clear();

        Repositorio rep2 = repDao.get("marcosn");
        assertThat(rep2.getDataXml(), equalTo(date));
    }
    
    @Test
    public void testSetDataXml(){
        Repositorio r = repDao.get(2);
        r.setDataXmlTemp("2014-09-26T14:20:11Z");
        assertThat(r.getDataXml(), equalTo(null));
        r.setUltimaAtualizacao(DateTime.now());
        repDao.save(r);
        
        em.flush();
        em.clear();
        r = repDao.get(2);
        assertThat(r.getDataXml(), equalTo("2014-09-26T14:20:11Z"));
    }

}
