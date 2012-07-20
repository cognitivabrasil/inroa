package feb.services;

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

public class TagCloudImplTest {
	TagCloudServiceImpl tagService;
	
	@Before
	public void before() {
		tagService = new TagCloudServiceImpl();
		tagService.setDays(7);
		
		Calendar cal = Calendar.getInstance();
		
		List<Search> l = new ArrayList<Search>();
		l.add(new Search("teste", 1));
		
		cal.add(Calendar.MINUTE, -1);
		l.add(new Search("jorjão", 3));
		
		cal.add(Calendar.HOUR, -1);
		l.add(new Search("grêmio", 1));

		
		SearchesDao search = mock(SearchesDao.class);
		when(search.getSearches(argThat(is(3)), argThat(any(Date.class)))).thenReturn(l);
		when(search.getSearches(argThat(is(5)), argThat(any(Date.class)))).thenReturn(l);

		
		tagService.setSearches(search);
	}

	@Test
	public void getTagCloud() {
		tagService.setMaxSize(3);
		Map<String, Double> m = tagService.getTagCloud();
		
		assertThat(m.get("jorjão"), closeTo(3, 0.1));
		assertThat(m.get("teste"), closeTo(1, 0.1));
		
		assertThat(m.size(), equalTo(3));
	}
	
	
	@Test
	public void getTotalWeights() {
		tagService.setMaxSize(3);
		
		assertThat(tagService.getTotalWeight(), closeTo(5, 0.1));
	}
}
