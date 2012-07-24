package feb.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import feb.data.entities.Search;
import feb.data.interfaces.SearchesDao;

/**
 * Tag cloud implementation with a fixed number of words.
 * 
 * @author paulo
 * 
 */
public class TagCloudServiceImpl implements TagCloudService {

	private int size;
	SearchesDao searches;
	private int days;

	@Autowired
	public void setSearches(SearchesDao s) {
		searches = s;
	}

	@Override
	public Map<String, Double> getTagCloud() {
		Map<String, Double> m = new HashMap<String, Double>();
		

		List<Search> l = searches.getSearches(getMaxSize(), getDate());
		for (Search s : l) {
			m.put(s.getText(), new Double(s.getCount()));

			if (m.size() >= getMaxSize()) {
				break;
			}

		}

		return m;
	}

	@Override
	public Double getTotalWeight() {
		Double t = 0.0;
		List<Search> l = searches.getSearches(getMaxSize(), getDate());
		int i = 0;
		for (Search s : l) {
			i++;
			t += s.getCount();
			if (i >= getMaxSize()) {
				break;
			}
		}
		return t;
	}


	public int getMaxSize() {
		return size;
	}

	/**
	 * Sets the size (number of words) of the tag cloud.
	 * 
	 * @param size
	 */
	public void setMaxSize(int size) {
		this.size = size;
	}
	
	/**
	 * Set delta of days for the tag cloud.
	 * 
	 * E.g, if @{code days} is 7, will generate a tag cloud with searches
	 * done in the last 7 days.
	 * @param days
	 */
	public void setDays(int days) {
		this.days = days;
	}
	
	private Date getDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -days);
		return c.getTime();
	}

	@Override
	public void clear() {
		searches.deleteAll();
	}

}
