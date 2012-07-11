package modelos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import static org.junit.Assert.*;

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
    VisitasDao v;
    
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
//        System.out.println("!!!!!!!!!!!!!!"+sql);
//        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();        
    }

    @After
    public void tearDown() {
//        String sql = "DELETE FROM visitas";
//        Session s = this.sessionFactory.getCurrentSession();
//        s.createSQLQuery(sql).executeUpdate();
    }

    @Test
    public void testfromListToJsList() {
        
        List<SubNodo> repList = new ArrayList<SubNodo>();
        
        Repositorio r = mock(Repositorio.class);

        when(r.getNome()).thenReturn("teste");
        when(r.getSize()).thenReturn(321);        
        
        repList.add(r);

        Repositorio r2 = mock(Repositorio.class);

        when(r2.getNome()).thenReturn("teste2");
        when(r2.getSize()).thenReturn(321);    
        
        repList.add(r2);
        
        Estatistica run = new Estatistica();
        String teste = run.fromListToJsList(repList);
        System.out.println(teste);
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
}
