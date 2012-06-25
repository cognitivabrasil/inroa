package modelos;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author cei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class StatisticsIT {

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
    }

    @After
    public void tearDown() {
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
}
