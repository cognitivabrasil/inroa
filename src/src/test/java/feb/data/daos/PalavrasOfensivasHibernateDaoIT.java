/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;

import feb.data.entities.PalavrasOfensivas;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author luiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class PalavrasOfensivasHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    PalavrasOfensivasHibernateDAO instance;

    @Test
    public void simpleTest() {
        assertThat(instance.contains("xota"), equalTo(true));
        assertThat(instance.contains("bacteria"), equalTo(false));
        assertThat(instance.contains("a (xota) da vizinha"), equalTo(true));
    }
    
    @Test
    public void getTest(){        
        PalavrasOfensivas theFWord = instance.get(371);        
        assertThat(theFWord.getWord(), equalTo("fuck"));
    }
}
