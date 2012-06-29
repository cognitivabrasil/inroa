/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Arrays;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TokensHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    TokensHibernateDao tokensDao;
    @Autowired
    DocumentosHibernateDAO docDao;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testSaveTokens() {
        String tokens = "imobil, maquin, impers, repet, hadshak, mens, envi, mediante, comput, bloque, intrus, ocorr, mens, troc, ilustr, animaca, mitnick, ataqu, tcp, seguranc, mitnick, tcp, ataqu, tcp, seguranc, exploraraca";
        String tokensArray[] = tokens.split(", ");        
        List<String> tokensList = Arrays.asList(tokensArray);
        DocumentoReal doc = docDao.get(1);

        tokensDao.saveTokens(doc);

        String sql = "SELECT token FROM r1tokens where documento_id=1";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ROOT_ENTITY);
        List<String> results = query.list();

        assert(results.containsAll(tokensList));
        assert(tokensList.containsAll(results));
    }
}

