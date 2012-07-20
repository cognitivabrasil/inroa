package feb.services;

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
	
	@Autowired
	public void setSearches(SearchesDao s) {
		searches = s;
	}
	
	@Override
	public Map<String, Double> getTagCloud() {
		Map<String,Double> m = new HashMap<String,Double>();
		
		List<Search> l = searches.getSearches(getMaxSize());
		for(Search s : l) {
			if(m.get(s.getText()) != null) {
				m.put(s.getText(), m.get(s.getText())+1);
			}
			else {
				m.put(s.getText(), new Double(1));
				if(m.size() >= getMaxSize()) {
					break;
				}
			}
		}
		
		return m;
	}

	@Override
	public Float getTotalWeight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTagCloudAsJs() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxSize() {
		return size;
	}

	/**
	 * Sets the size (number of words) of the tag cloud.
	 * @param size
	 */
	public void setMaxSize(int size) {
		this.size = size;
	}

}
