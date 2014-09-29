/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.springframework.test.context.transaction.TransactionConfiguration;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.services.PalavrasOfensivasHibernateDAO;

/**
 *
 * @author luiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext
public class PalavrasOfensivasHibernateDaoIT extends AbstractTransactionalJUnit4SpringContextTests /* extends  AbstractDaoTest */ {

    @Autowired
    PalavrasOfensivasHibernateDAO instance;

    @Test
    @Ignore("falhando após o merge, é preciso converter para Spring Data ou JPA")
    public void simpleTest() {
        assertThat(instance.contains("xota"), equalTo(true));
        assertThat(instance.contains("bacteria"), equalTo(false));
        assertThat(instance.contains("a (xota) da vizinha"), equalTo(true));
    }

}
