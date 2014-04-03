package feb.services;

import feb.data.daos.AbstractDaoTest;
import feb.data.daos.PalavrasOfensivasHibernateDAO;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.argThat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import feb.data.entities.Search;
import feb.data.interfaces.SearchesDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TagCloudImplIT extends AbstractDaoTest {
    @Autowired
    TagCloudServiceImpl tagService;
    
    @Autowired
    PalavrasOfensivasHibernateDAO instance;

    @Before
    public void before() {
        tagService.setDays(7);

        Calendar cal = Calendar.getInstance();

        List<Search> l = new ArrayList<Search>();
        l.add(new Search("teste", 1));

        cal.add(Calendar.MINUTE, -1);
        l.add(new Search("jorjão", 3));

        cal.add(Calendar.HOUR, -1);
        l.add(new Search("grêmio", 1));

        l.add(new Search("merdinha", 15));

        SearchesDao search = mock(SearchesDao.class);
        when(search.getSearches(argThat(is(3)), argThat(any(Date.class)))).thenReturn(l);
        when(search.getSearches(argThat(is(5)), argThat(any(Date.class)))).thenReturn(l);

        tagService.setSearches(search);
    }

    @Test
    public void getTagCloud() {
        tagService.setMaxSize(3);
        Map<String, Integer> m = tagService.getTagCloud();

        assertThat(m.get("jorjão"), equalTo(3));
        assertThat(m.get("teste"), equalTo(1));

        assertThat(m.size(), equalTo(3));
    }

    @Test
    public void getTotalWeights() {
        tagService.setMaxSize(3);

        assertThat(tagService.getTotalWeight(), closeTo(5, 0.1));
    }
}