package feb.data.interfaces;

import feb.data.entities.Search;
import java.util.Date;
import java.util.List;

public interface SearchesDao {
	
	/**
	 * Cleans up the Searches table.
	 * 
	 * Implementation dependent, will be called regularly and may do any housekeeping tasks
	 * that are deemed necessary.
	 */
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


	/**
	 * Returns at most @{code limit} distinct searches that occurred after @{code date}.
	 * @param limit
	 * @param date
	 * @return
	 */
	public List<Search> getSearches(Integer limit, Date date);


	/**
	 * Saves the search.
	 * 
	 * The implementation may process the string.
	 * 
	 * @param string search string the user entered
	 * @param date date of the search
	 */
	void save(String string, Date date);


	/**
	 * Deletes all entries.
	 */
	public void deleteAll();
        
        /**
         * Deletes a specific tag
         */
        public void delete(String tag);

}
