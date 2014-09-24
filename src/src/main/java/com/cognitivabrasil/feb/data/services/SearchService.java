package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.Search;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;

public interface SearchService {

    /**
     * Cleans up the Searches table.
     *
     * Implementation dependent, will be called regularly and may do any housekeeping tasks that are deemed necessary.
     */
    public void cleanup();

    /**
     * Returns at most @{code limit} distinct searches that occurred after @{code date}.
     *
     * @param limit maximum number of searches
     * @param date Searches from this date.
     * @return List of searches that meet the criteria.
     */
    public List<Search> getSearches(Integer limit, Date date);

    /**
     * Saves a search into the database.
     *
     * The string will be normalized (to lowercase, no leading or trailing spaces, and exactly one space between words)
     * and then save to the database.
     *
     * @param string search string the user entered
     * @param date date of the search
     */
    void save(String string, Date date);

    public void save(String string, DateTime date);

    /**
     * Deletes all entries.
     */
    public void deleteAll();

    /**
     * Deletes a specific tag
     */
    public void delete(String tag);

}
