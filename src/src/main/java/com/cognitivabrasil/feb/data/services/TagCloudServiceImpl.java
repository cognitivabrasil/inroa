package com.cognitivabrasil.feb.data.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.cognitivabrasil.feb.data.entities.Search;

import org.springframework.beans.factory.annotation.Value;

/**
 * Tag cloud implementation with a fixed number of words.
 *
 * @author paulo
 *
 */
//TODO: get days and Max Size from configuration
@Service
public class TagCloudServiceImpl implements TagCloudService {

    private int size;
    SearchService searches;
    private int days;
    private static final Logger log = LoggerFactory.getLogger(TagCloudServiceImpl.class);

    @Autowired
    PalavrasOfensivasHibernateDAO badWords;
    private Map<String, Integer> tagCloudCache;
    private DateTime lastUpdateDate;

    @Autowired
    public void setSearches(SearchService s) {
        searches = s;
    }

    @Override
    public Map<String, Integer> getTagCloud() {
        if(tagCloudCache != null && lessThanFiveMinutesAgo()) {
            return tagCloudCache;
        }
        
        Map<String, Integer> m = new TreeMap<>();
        
        Date d = getDate();

        List<Search> l = searches.getSearches(getMaxSize(), d);
        log.trace("Tag cloud. Número de resultados: " + l.size() + " Número máximo permitido: " + getMaxSize());
        for (Search s : l) {

            if (!badWords.contains(s.getText())) {

                m.put(s.getText(), s.getCount());

                if (m.size() >= getMaxSize()) {
                    break;
                }
            }
        }

        lastUpdateDate = new DateTime();
        tagCloudCache = m;
        return m;
    }

    private boolean lessThanFiveMinutesAgo() {
        DateTime now = new DateTime();
        
        return lastUpdateDate.isAfter(now.minusMinutes(5));     
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

    @Override
    public int getMaxSize() {
        return size;
    }

    /**
     * Sets the size (number of words) of the tag cloud.
     *
     * @param size
     */
    @Value("30")
    @Override
    public void setMaxSize(int size) {
        this.size = size;
    }

    /**
     * Set delta of days for the tag cloud. E.g, if @{code days} is 7, will generate a tag cloud with searches done in
     * the last 7 days.
     *
     * @param days
     */
    @Value("15")
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
    public void delete(String tag) {
        searches.delete(tag);
    }
}
