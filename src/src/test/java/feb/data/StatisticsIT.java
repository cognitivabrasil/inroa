package feb.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

import feb.data.daos.AbstractDaoTest;
import com.cognitivabrasil.feb.data.entities.Estatistica;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.SubNodo;
import com.cognitivabrasil.feb.data.services.VisitService;

/**
 *
 * @author cei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class StatisticsIT extends AbstractDaoTest {

    @Autowired
    VisitService v;
    
    @Autowired
    SessionFactory sessionFactory;
    
    public StatisticsIT() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        
//        Calendar c1 = Calendar.getInstance();        
//        c1.set(2012, 6, 10);
//        
//        Calendar c2 = Calendar.getInstance();        
//        c2.set(2012, 6, 9);
//        
//        Date d1 = c1.getTime();
//        Date d2 = c2.getTime();
//                
//        
//        String sql = "INSERT INTO visitas (horario) VALUES ("+d1+", "+d2+")";
//        
//        //System.out.println("!!!!!!!!!!!!!!"+sql);
//        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();        
    }

    @After
    public void tearDown() {
//        String sql = "DELETE FROM visitas";
//        Session s = this.sessionFactory.getCurrentSession();
//        s.createSQLQuery(sql).executeUpdate();
    }

    @Test
    public void testConvertIntList(){
        List confirmation = new ArrayList<Integer>();
        confirmation.add(2);
        confirmation.add(1);
        confirmation.add(2);
        confirmation.add(1);
        confirmation.add(0);
        confirmation.add(1);
        confirmation.add(2);
        confirmation.add(0);
        confirmation.add(0);
        confirmation.add(1);
        confirmation.add(0);
        confirmation.add(3);
        
        Estatistica e = new Estatistica();
        
        assertEquals("[2, 1, 2, 1, 0, 1, 2, 0, 0, 1, 0, 3]", e.convertIntList(confirmation));
    }
    @Test
    public void testConvertNodoList() {
        
        List<SubNodo> repList = new ArrayList<SubNodo>();
        
        Repositorio r = mock(Repositorio.class);

        when(r.getName()).thenReturn("teste");
        when(r.getSize()).thenReturn(321);        
        
        repList.add(r);

        Repositorio r2 = mock(Repositorio.class);

        when(r2.getName()).thenReturn("teste2");
        when(r2.getSize()).thenReturn(321);    
        
        repList.add(r2);
        
        Estatistica run = new Estatistica();
        String teste = run.convertNodoList(repList, "size");
        ////System.out.println(teste);
        
        assertEquals("[ [ 'teste', 321 ], [ 'teste2', 321 ] ]", teste);
    }
    
    @Test 
    public void testVisitsInAMonth() {
        assertEquals(2, v.visitsInAMonth(7, 2012));
        assertEquals(3, v.visitsInAMonth(12, 2012));
    }
    
    @Test 
    public void testVisitsInAYear() {
        
        List confirmation = new ArrayList<Integer>();
        confirmation.add(2);
        confirmation.add(1);
        confirmation.add(2);
        confirmation.add(1);
        confirmation.add(0);
        confirmation.add(1);
        confirmation.add(2);
        confirmation.add(0);
        confirmation.add(0);
        confirmation.add(1);
        confirmation.add(0);
        confirmation.add(3);
                
        assertEquals(confirmation, v.visitsInAYear(2012));
    }
    
    @Test 
    public void testVisitsUpToAMonth() {
        
        List confirmation = new ArrayList<Integer>();
//        confirmation.add(2); //jan 12
//        confirmation.add(1);
//        confirmation.add(2);
//        confirmation.add(1);
//        confirmation.add(0);
//        confirmation.add(1);
//        confirmation.add(2);
//        confirmation.add(0);
        confirmation.add(0); //set 12
        confirmation.add(1);
        confirmation.add(0);
        confirmation.add(3); //dez 12
        
        confirmation.add(2); //jan 13
        confirmation.add(1);
        confirmation.add(2);
        confirmation.add(1);
        confirmation.add(0);
        confirmation.add(1);
        confirmation.add(2);
        confirmation.add(0); //ago 13
//        confirmation.add(0);
//        confirmation.add(1);
//        confirmation.add(0);
//        confirmation.add(3); //dez 13
                
        assertEquals(confirmation, v.visitsUpToAMonth(8, 2013,12));
    }
}
