/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;

import feb.data.entities.DocumentosVisitas;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class DocumentosVisitasHibernateDaoIT extends AbstractDaoTest {

    @Autowired
    DocumentosVisitasHibernateDao docVisDao;
    @Autowired
    VisitasHibernateDao visDao;

    @Test
    public void testGetAll() {
        List<DocumentosVisitas> l = docVisDao.getAll();
        assertThat(l.size(), equalTo(2));
    }

    @Test
    public void testVisitsBetweenDates() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date from = (Date) formatter.parse("01/03/2013");
        Date until = (Date) formatter.parse("31/07/2013");
//        2, 1, 0, 1, 2

        List<Integer> actual = visDao.visitsToBetweenDates(from, until);
        assertThat(actual.size(), equalTo(5));
        assertEquals(actual.get(0), new BigInteger("2"));
        assertEquals(actual.get(1), new BigInteger("1"));
        assertEquals(actual.get(2), new BigInteger("0"));
        assertEquals(actual.get(3), new BigInteger("1"));
        assertEquals(actual.get(4), new BigInteger("2"));
    }
}
