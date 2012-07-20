package feb.data.interfaces;

import java.util.List;

import feb.data.entities.Search;

public interface SearchesDao {
	
	public void cleanup();
	
	
	/**
	 * Returns at most {@code limit} distinct searches.
	 * 
	 * A search is considered distinct if the search term is different. Different dates
	 * don't matter.
	 * @param limit
	 * @return
	 */
	public List<Search> getSearches(int limit);
}
