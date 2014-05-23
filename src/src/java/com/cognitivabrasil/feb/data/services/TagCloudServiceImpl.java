package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.Search;
import com.cognitivabrasil.feb.data.services.SearchService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tag cloud implementation with a fixed number of words.
 *
 * @author paulo
 *
 */
public class TagCloudServiceImpl implements TagCloudService {

    private int size;
    SearchService searches;
    private int days;
    private final Logger log = Logger.getLogger(TagCloudServiceImpl.class);

    @Autowired
    public void setSearches(SearchService s) {
        searches = s;
    }

    @Override
    public Map<String, Integer> getTagCloud() {
        Map<String, Integer> m = new TreeMap<String, Integer>();


        List<Search> l = searches.getSearches(getMaxSize(), getDate());
        log.trace("Tag cloud. Número de resultados: "+l.size()+" Número máximo permitido: "+getMaxSize());
        for (Search s : l) {
            m.put(s.getText(), s.getCount());

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
     * E.g, if
     *
     * @{code days} is 7, will generate a tag cloud with searches done in the
     * last 7 days.
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
    
    @Override
    public void delete(String tag){
        searches.delete(tag);
    }
}
