/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import OBAA.General.General;
import OBAA.OBAA;

import java.lang.reflect.Field;
import java.util.Date;
import metadata.Header;
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
import org.junit.Ignore;
import static org.mockito.Mockito.*;

/**
 * Integration tests of the UsuarioHibernateDao
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class DocumentosHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    DocumentosHibernateDAO instance;
    
    @Autowired
    RepositoryHibernateDAO repDao;

    @Test @Ignore
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
        Class  aClass = DocumentosHibernateDAO.class;
        Field field = aClass.getDeclaredField("repository");
        field.setAccessible(true);        
        field.set(instance, null);

        Header h = mock(Header.class);
        
        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("obaa:identifier");
        
        Repositorio r = repDao.get(1);
        instance.setRepository(r);

        try {
        	instance.save(obaa, h);	
        	fail("Should throw IllegalStateException!");
        }
        	catch(IllegalStateException e) {

        }

    }
    
    @Test
    public void testGet() {
        DocumentoReal d = instance.get(1);

        assertEquals("oai:cesta2.cinted.ufrgs.br:123456789/57", d.getObaaEntry());
        assertEquals("Cesta", d.getRepositorio().getNome());
    }

    @Test
    public void testGetByObaaEntry() {
        DocumentoReal d = instance.get("dois");
        assertThat(d, notNullValue());
        assertEquals(2, d.getId());

    }

    @Test
    public void testDelete() {
        DocumentoReal d = instance.get(1);
        assertThat(d, notNullValue());
        assertEquals(6, instance.getAll().size());


        instance.delete(d);

        assertEquals(5, instance.getAll().size());

    }

    @Test
    public void testDeleteByObaaEntry() {
        instance.deleteByObaaEntry("dois");

        assertEquals(5, instance.getAll().size());
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
        instance.setRepository(r);

        instance.save(obaa, h);

        DocumentoReal d = instance.get("obaa:identifier");
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
        instance.setRepository(r);

        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("obaa:identifier");

        instance.save(obaa, h);

        DocumentoReal d = instance.get("obaa:identifier");
        assertThat(d, notNullValue());
        
        assertThat("Should return metadata", d.getMetadata(), notNullValue());
        assertThat(d.getMetadata().getTitles(), hasItem("teste1"));

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
        instance.setRepository(r);

        instance.save(obaa, h);

        DocumentoReal d = instance.get("dois");
        assertThat(d, notNullValue());
        assertEquals(d.isDeleted(), true);

    }
}
