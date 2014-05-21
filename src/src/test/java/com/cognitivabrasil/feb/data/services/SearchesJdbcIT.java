/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.Search;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Paulo Schreiner <paulo@cognitivabrasil.com.br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@TransactionConfiguration(transactionManager = "transactionManager",  defaultRollback = true)
@Transactional
public class SearchesJdbcIT extends AbstractTransactionalJUnit4SpringContextTests {


	@Autowired SearchService sDao;
	
	@Test
	public void getSearches() throws ParseException {
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date a = dfm.parse("2012-06-26 20:15:00");
		
		List<Search> l = sDao.getSearches(2, a);

		assertThat(l.size(), equalTo(2));
		
		assertThat(l.get(0).getText(), equalTo("jorjao"));
		assertThat(l.get(0).getCount(), equalTo(3));

		assertThat(l.get(1).getText(), equalTo("bla"));
		assertThat(l.get(1).getCount(), equalTo(2));

	}
	
	@Test
	public void getSearches2() throws ParseException {
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date a = dfm.parse("2012-06-26 20:15:00");
		
		List<Search> l = sDao.getSearches(2);
		
		assertThat(l.size(), equalTo(2));
		
		assertThat(l.get(0).getText(), equalTo("jorjao"));
		assertThat(l.get(0).getCount(), equalTo(4));

		assertThat(l.get(1).getText(), equalTo("bla"));
		assertThat(l.get(1).getCount(), equalTo(2));

	}
	
	@Test
	public void getSearches3() throws ParseException {
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date a = dfm.parse("2012-06-26 20:15:00");
		
		List<Search> l = sDao.getSearches(a);
		
		assertThat(l.size(), equalTo(3));
		
		assertThat(l.get(0).getText(), equalTo("jorjao"));
		assertThat(l.get(0).getCount(), equalTo(3));

		assertThat(l.get(1).getText(), equalTo("bla"));
		assertThat(l.get(1).getCount(), equalTo(2));
		
		assertThat(l.get(2).getText(), equalTo("teste"));
		assertThat(l.get(2).getCount(), equalTo(2));

	}
	
	/**
	 * Tests that we can add Searches.
	 * @throws ParseException
	 */
	@Test
	public void getAdd() throws ParseException {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, -1);
		Date before = c.getTime();
		
		sDao.save("pretto", new Date());
		sDao.save(" Marcos   Nunes", new Date());
		sDao.save("pretto", new Date());

		
		List<Search> l = sDao.getSearches(before);
		
		assertThat(l.size(), equalTo(2));
		
		assertThat(l.get(0).getText(), equalTo("pretto"));
		assertThat(l.get(0).getCount(), equalTo(2));

		assertThat(l.get(1).getText(), equalTo("marcos nunes"));
		assertThat(l.get(1).getCount(), equalTo(1));

	}
	
	@Test
	public void clear() {
		sDao.deleteAll();
		
		List<Search> l = sDao.getSearches(10);
		
		assertThat(l.size(), equalTo(0));
	}
        
        @Test
        public void delete(){
            List<Search> l = sDao.getSearches(10);
            int before = l.size();
            //System.out.println(before);
            sDao.delete("teste");
            
            assertThat(sDao.getSearches(10), hasSize(before-1));
        }
	
	
}
