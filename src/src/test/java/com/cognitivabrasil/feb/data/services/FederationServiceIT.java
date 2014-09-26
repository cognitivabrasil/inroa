/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.RepositorioSubFed;
import com.cognitivabrasil.feb.data.entities.SubFederacao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.joda.time.DateTime;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author paulo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class FederationServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    FederationService instance;
    @Autowired
    private DocumentService docService;
    @PersistenceContext
    private EntityManager em;

    public FederationServiceIT() {
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
    public void testDelete() {
        SubFederacao cesta = instance.get(1);

        instance.delete(cesta);


        List<SubFederacao> l = instance.getAll();
        assertEquals(3, l.size());


    }

    @Test
    public void testDeleteRemovesDocuments() {
        SubFederacao cesta = instance.get(1);

        int sizeDocsCesta = 0;
        for (RepositorioSubFed r : cesta.getRepositorios()) {
            sizeDocsCesta += r.getDocumentos().size();
        }
        int sizeAllDocsBefore = docService.getAll().size();
        int sizeAfterShould = sizeAllDocsBefore - sizeDocsCesta;

        instance.delete(cesta);

        assertThat("Size of UFRGS before", sizeDocsCesta, equalTo(2));
        assertThat("Size of UFRGS after deletion", docService.getAll().size(), equalTo(sizeAfterShould));
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
        f.setDataXml("2012-03-19T18:01:54Z");

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

    }

    @Test
    public void testSaveDataXML() {
        String date = "1984-08-21T05:35:00Z";
        SubFederacao fed = new SubFederacao();
        fed.setName("marcosn");
        fed.setDataXmlTemp(date);
        fed.setUltimaAtualizacao(DateTime.now());

        assertThat(fed.getDataXml(), equalTo(date));
        instance.save(fed);
        em.flush();
        em.clear();
        SubFederacao fed2 = instance.get("marcosn");
        assertThat(fed2.getDataXml(), equalTo(date));
    }
    
    @Test
    public void testSetDataXml(){
        SubFederacao f = instance.get(1);
        f.setDataXmlTemp("2014-09-26T14:20:11Z");
        
        f.setUltimaAtualizacao(DateTime.now());
        instance.save(f);
        
        em.flush();
        em.clear();
        f = instance.get(1);
        assertThat(f.getDataXml(), equalTo("2014-09-26T14:20:11Z"));
    }
    
    @Test
    public void testGetDocsRepSubFed(){
        SubFederacao fed = instance.get(1);
        Set<RepositorioSubFed> setRep = fed.getRepositorios();
        assertThat(setRep, hasSize(1));
        RepositorioSubFed rep = setRep.iterator().next();
        assertThat(rep.getDocumentos(), hasSize(2));
    }
     
    @Test
    public void testDeleteAllDocs(){
        long before = docService.getSize();
        
        SubFederacao sf = instance.get(1);
        
        instance.deleteAllDocs(sf);
        
        long after = docService.getSize();
        
        assertThat(after, equalTo(before-2));
    }

}