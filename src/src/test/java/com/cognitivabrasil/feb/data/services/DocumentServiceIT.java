package com.cognitivabrasil.feb.data.services;

import cognitivabrasil.obaa.General.General;
import cognitivabrasil.obaa.OBAA;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import metadata.Header;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.RepositorioSubFed;
import com.cognitivabrasil.feb.data.entities.SubFederacao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Integration tests of the DocumentosHibernateDao
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext
public class DocumentServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    DocumentService docService;

    @Autowired
    RepositoryService repDao;

    @Autowired
    FederationService fedService;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testGet() {
        Document d = docService.get(1);
        assertEquals("oai:cesta2.cinted.ufrgs.br:123456789/57", d.getObaaEntry());
        assertEquals("Cesta", d.getRepositorio().getName());
    }

    @Test
    public void testGetByObaaEntry() {
        Document d = docService.get("dois");
        assertThat(d, notNullValue());
        assertEquals(2, d.getId());

    }

    @Test
    public void testDelete() {
        Document d = docService.get(1);
        assertThat(d, notNullValue());
        assertEquals(6, docService.getAll().size());

        docService.delete(d);

        assertEquals(5, docService.getAll().size());

    }

    @Test
    public void testSave() {
        OBAA obaa = new OBAA();
        obaa.setGeneral(new General());

        obaa.getGeneral().addTitle("teste1");
        obaa.getGeneral().addTitle("teste2");

        obaa.getGeneral().addKeyword("key1");
        obaa.getGeneral().addKeyword("key2");
        obaa.getGeneral().addKeyword("key3");

        obaa.getGeneral().addDescription("Bla bla");

        Header h = mock(Header.class);

        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("obaa:identifier");

        Repositorio r = repDao.get(1);
        assertThat(r, notNullValue());

        docService.save(obaa, h, r);

        Document d = docService.get("obaa:identifier");
        assertThat(d, notNullValue());
        assertThat(d.getKeywords(), hasItem("key1"));
        assertThat(d.getTitles(), hasItem("teste2"));
        assertThat(d.getDescriptions(), hasItem("Bla bla"));
        assertThat(d.getShortDescriptions(), hasItem("Bla bla"));
    }

    @Test
    public void testSaveSerializationDeserialization() {
        OBAA obaa = new OBAA();
        obaa.setGeneral(new General());

        obaa.getGeneral().addTitle("teste1");
        obaa.getGeneral().addTitle("teste2");

        obaa.getGeneral().addKeyword("key1");
        obaa.getGeneral().addKeyword("key2");
        obaa.getGeneral().addKeyword("key3");

        obaa.getGeneral().addDescription("Bla bla");

        Header h = mock(Header.class);

        Repositorio r = repDao.get(1);

        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("obaa:identifier");

        docService.save(obaa, h, r);

        Document d = docService.get("obaa:identifier");
        assertThat(d, notNullValue());

        assertThat("Should return metadata", d.getMetadata(), notNullValue());
        assertThat(d.getMetadata().getTitles(), hasItem("teste1"));

    }

    /**
     * an existing document, when saved, should replace the old one.
     */
    @Test
    public void saveExisting() {
        OBAA obaa = new OBAA();
        obaa.setGeneral(new General());

        obaa.getGeneral().addTitle("teste1");
        obaa.getGeneral().addTitle("teste2");

        obaa.getGeneral().addKeyword("key1");
        obaa.getGeneral().addKeyword("key2");
        obaa.getGeneral().addKeyword("key3");

        obaa.getGeneral().addDescription("Bla bla");

        Header h = mock(Header.class);

        Repositorio r = repDao.get(1);

        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("dois"); // existing obaa entry

        int oldSize = docService.getAll().size();
        docService.save(obaa, h, r);

        Document d = docService.get("obaa:identifier");

        assertThat(docService.getAll().size(), equalTo(oldSize));

    }

    @Test
    public void myTest() {
        long initNumberDocs = docService.getSizeWithDeleted();
        OBAA obaa = new OBAA();
        obaa.setGeneral(new General());

        obaa.getGeneral().addTitle("teste1");
        obaa.getGeneral().addDescription("Bla bla");

        Header h = mock(Header.class);
        String[] s = {"RepUfrgs1"};
        List<String> s2 = Arrays.asList(s);

        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("obaa:identifier");
        when(h.getSetSpec()).thenReturn(s2);

        SubFederacao r = fedService.get(1);

        em.flush();

        assertThat(r, notNullValue());

        docService.save(obaa, h, r);
        
        em.flush();
        em.clear();

        long finalNumberDocs = docService.getSizeWithDeleted();
        Document d = docService.get("obaa:identifier");
        assertThat(d, notNullValue());
        assertThat(d.getTitles(), hasItem("teste1"));
        assertThat(d.getRepositorioSubFed(), notNullValue());

        assertThat(initNumberDocs, not(equalTo(finalNumberDocs)));

    }

    @Test
    public void testSaveDeleted() {
        OBAA obaa = new OBAA();
        obaa.setGeneral(new General());

        Header h = mock(Header.class);

        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("dois");
        when(h.isDeleted()).thenReturn(true);

        Repositorio r = repDao.get(1);

        docService.save(obaa, h, r);

        Document d = docService.get("dois");
        assertThat(d, notNullValue());
        assertEquals(d.isDeleted(), true);

    }

    @Test
    public void testGetSizeWhitDeleted() {
        long total = docService.getSizeWithDeleted();
        assertThat(total, equalTo(7L));
    }

    @Test
    public void testGetSize() {
        long total = docService.getSize();
        assertThat(total, equalTo(6L));
    }

    @Test
    public void testGetAllPaginated() {
        List<Document> docs = docService.getlAll(new PageRequest(0, 2)).getContent();
        assertThat(docs.size(), equalTo(2));
        assertThat(docs.get(0).getId(), equalTo(1));
        assertThat(docs.get(1).getId(), equalTo(2));
    }

    @Test
    public void testGetAllPaginated2() {
        List<Document> modelDoc = docService.getAll();
        List<Integer> ids = new ArrayList<>();

        Pageable limit = new PageRequest(0, 2);
        Page<Document> docs = docService.getlAll(limit);
        for (Document doc : docs.getContent()) {
            ids.add(doc.getId());
        }

        while (docs.hasNext()) {
            docs = docService.getlAll(docs.nextPageable());
            for (Document doc : docs.getContent()) {
                ids.add(doc.getId());
            }
            em.clear();
        }

        assertThat(modelDoc.size(), equalTo(ids.size()));

        for (Document doc : modelDoc) {
            assertThat(ids.contains(doc.getId()), equalTo(true));
        }
    }

    @Test
    public void testDeleteDocsFromRep() {
        int sizeAllBefore = docService.getAll().size();

        Repositorio cesta = repDao.get(1);
        int numDocsRep = cesta.getDocumentos().size();
        int sizeCestaWithoutDeleted = 4;

        int affected = docService.deleteAllFromRep(cesta);

        assertThat(affected, equalTo(numDocsRep));

        assertThat("Size of Cesta after deletion", docService.getAll().size(), equalTo(sizeAllBefore - sizeCestaWithoutDeleted));
    }

    @Test
    public void testSaveCreated() {
        Date created = new Date(461905200000L);
        Repositorio r = repDao.get(1);
        Header h = mock(Header.class);
        when(h.getTimestamp()).thenReturn(created);
        when(h.getIdentifier()).thenReturn("marcos");

        docService.save(new OBAA(), h, r);

        Document doc = docService.get("marcos");
        assertThat(doc.getCreated(), equalTo(new DateTime(1984, 8, 21, 0, 0)));

    }
    
    @Test
    public void testCountDocSubFed(){
        RepositorioSubFed r = new RepositorioSubFed();
        r.setId(1);
        r.setName("RepUfrgs1");
        int docsSubRep = docService.countFromSubRep(r);
        assertThat(docsSubRep, equalTo(2));
    }
}
