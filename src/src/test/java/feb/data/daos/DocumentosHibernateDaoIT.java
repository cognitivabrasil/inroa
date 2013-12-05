package feb.data.daos;

import cognitivabrasil.obaa.General.General;
import cognitivabrasil.obaa.OBAA;
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

import feb.data.entities.DocumentoReal;
import feb.data.entities.Objeto;
import feb.data.entities.Repositorio;
import feb.data.entities.SubFederacao;

/**
 * Integration tests of the DocumentosHibernateDao
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
    
    @Autowired
    SubFederacaoHibernateDAO subDao;
    
    @Autowired SessionFactory sessionFactory;

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

        try {
        	instance.save(obaa, h, r);	
        	fail("Should throw IllegalStateException!");
        }
        	catch(IllegalStateException e) {

        }

    }
    
    @Test
    public void testGet() {
        DocumentoReal d = instance.get(1);

        assertEquals("oai:cesta2.cinted.ufrgs.br:123456789/57", d.getObaaEntry());
        assertEquals("Cesta", d.getRepositorio().getName());
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
        assertEquals(5, instance.getAll().size());


        instance.delete(d);

        assertEquals(4, instance.getAll().size());

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

        instance.save(obaa, h, r);

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

        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("obaa:identifier");

        instance.save(obaa, h, r);

        DocumentoReal d = instance.get("obaa:identifier");
        assertThat(d, notNullValue());
        
        assertThat("Should return metadata", d.getMetadata(), notNullValue());
        assertThat(d.getMetadata().getTitles(), hasItem("teste1"));

    }
    
    /** an existing document, when saved, should replace the old one. */
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

        int oldSize = instance.getAll().size();
        instance.save(obaa, h, r);

        DocumentoReal d = instance.get("obaa:identifier");
       
        assertThat(instance.getAll().size(), equalTo(oldSize));

    }

    @Test
    public void cascadesObjects() {
        Session s = sessionFactory.getCurrentSession();

        assertThat(s.createCriteria(Objeto.class).list().size(), equalTo(6));

        instance.delete(instance.get(1));
        
        s.flush();
        
        assertThat(s.createCriteria(Objeto.class).list().size(), equalTo(0));

    }
    
    @Test
    public void removesObjects() {
        Session s = sessionFactory.getCurrentSession();

        assertThat(s.createCriteria(Objeto.class).list().size(), equalTo(6));

        DocumentoReal doc = instance.get(1);
        doc.getObjetos().clear();
        s.save(doc);
                
        s.flush();
        
        assertThat(s.createCriteria(Objeto.class).list().size(), equalTo(0));

    }
    
    @Test
    public void addsObjects() {
        Session s = sessionFactory.getCurrentSession();

        assertThat(s.createCriteria(Objeto.class).list().size(), equalTo(6));

        DocumentoReal doc = instance.get(1);
        doc.getObjetos().clear();
        
        doc.addTitle("Teste");
        doc.addTitle("Teste2");
        doc.addKeyword("Teste2");


        s.save(doc);
        
        s.flush();
        
        assertThat(s.createCriteria(Objeto.class).list().size(), equalTo(3));

    }
    
    @Test
    public void myTest() {
        Integer initNumberDocs = instance.getSizeWithDeleted();
        OBAA obaa = new OBAA();
        obaa.setGeneral(new General());

        obaa.getGeneral().addTitle("teste1");
        obaa.getGeneral().addDescription("Bla bla");


        Header h = mock(Header.class);
        String[] s = { "RepUfrgs1" };
        List<String> s2 = Arrays.asList(s);

        when(h.getTimestamp()).thenReturn(new Date());
        when(h.getIdentifier()).thenReturn("obaa:identifier");
        when(h.getSetSpec()).thenReturn(s2);

    
        SubFederacao r = subDao.get(1);
        
        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
        
        assertThat(r, notNullValue());

        instance.save(obaa, h, r);

        Integer finalNumberDocs = instance.getSizeWithDeleted();
        DocumentoReal d = instance.get("obaa:identifier");
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

        instance.save(obaa, h, r);

        DocumentoReal d = instance.get("dois");
        assertThat(d, notNullValue());
        assertEquals(d.isDeleted(), true);

    }

    @Test
    public void testGetSizeWhitDeleted(){
        Integer total = instance.getSizeWithDeleted();
        assertThat(total, equalTo(6));
    }

    @Test
    public void testGetSize(){
        Integer total = instance.getSize();
        assertThat(total, equalTo(5));
    }
}