package com.cognitivabrasil.feb.data.services;

import cognitivabrasil.obaa.General.General;
import cognitivabrasil.obaa.OBAA;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import metadata.Header;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.cognitivabrasil.feb.data.entities.DocumentoReal;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.PageRequest;

/**
 * Integration tests of the DocumentosHibernateDao
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class DocumentosHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    DocumentService docService;

    @Autowired
    RepositoryService repDao;

    @Autowired
    FederationService fedService;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Ignore
    public void testSaveException() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
        OBAA obaa = new OBAA();
        obaa.setGeneral(new General());

        obaa.getGeneral().addTitle("teste1");
        obaa.getGeneral().addTitle("teste2");

        obaa.getGeneral().addKeyword("key1");
        obaa.getGeneral().addKeyword("key2");
        obaa.getGeneral().addKeyword("key3");

        obaa.getGeneral().addDescription("Bla bla");

        // set repository to null through reflection
        Class aClass = DocumentService.class;
        Field field = aClass.getDeclaredField("repository");
        field.setAccessible(true);
        field.set(docService, null);

        Header h = mock(Header.class);

        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("obaa:identifier");

        Repositorio r = repDao.get(1);

        try {
            docService.save(obaa, h, r);
            fail("Should throw IllegalStateException!");
        } catch (IllegalStateException e) {

        }

    }

    @Test
    public void testGet() {
        DocumentoReal d = docService.get(1);

        assertEquals("oai:cesta2.cinted.ufrgs.br:123456789/57", d.getObaaEntry());
        assertEquals("Cesta", d.getRepositorio().getName());
    }

    @Test
    public void testGetByObaaEntry() {
        DocumentoReal d = docService.get("dois");
        assertThat(d, notNullValue());
        assertEquals(2, d.getId());

    }

    @Test
    public void testDelete() {
        DocumentoReal d = docService.get(1);
        assertThat(d, notNullValue());
        assertEquals(5, docService.getAll().size());

        docService.delete(d);

        assertEquals(4, docService.getAll().size());

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

        DocumentoReal d = docService.get("obaa:identifier");
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

        DocumentoReal d = docService.get("obaa:identifier");
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

        DocumentoReal d = docService.get("obaa:identifier");

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

        long finalNumberDocs = docService.getSizeWithDeleted();
        DocumentoReal d = docService.get("obaa:identifier");
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

        DocumentoReal d = docService.get("dois");
        assertThat(d, notNullValue());
        assertEquals(d.isDeleted(), true);

    }

    @Test
    public void testGetSizeWhitDeleted() {
        long total = docService.getSizeWithDeleted();
        assertThat(total, equalTo(6L));
    }

    @Test
    public void testGetSize() {
        long total = docService.getSize();
        assertThat(total, equalTo(5L));
    }

    @Test
    public void testGetAllPaginated() {
        List<DocumentoReal> docs = docService.getlAll(new PageRequest(0,2)).getContent();
        assertThat(docs.size(), equalTo(2));
        assertThat(docs.get(0).getId(), equalTo(1));
        assertThat(docs.get(1).getId(), equalTo(2));
    }
}
