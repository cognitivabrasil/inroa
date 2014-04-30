package com.cognitivabrasil.feb.data.repositories;

import com.cognitivabrasil.feb.data.entities.Search;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author Paulo Schreiner <paulo@cognitivabrasil.com.br>
 *
 */
public class SearchesJdbcDao extends JdbcDaoSupport {

    private final Logger log = Logger.getLogger(SearchesJdbcDao.class);

    public void cleanup() {
        String sql = "DELETE FROM searches WHERE time < ?";

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Date d = c.getTime();

        try {
            getJdbcTemplate().update(sql,
                    new Object[]{d},
                    new int[]{Types.TIMESTAMP});
        } catch (DataAccessException e) {
            log.error("Error while trying to cleanup", e);
        }
    }

    /**
     * Returns at most {@code limit} distinct searches.
     *
     * A search is considered distinct if the search term is different.
     * Different dates don't matter.
     *
     * @param limit
     * @return
     */
    public List<Search> getSearches(int limit) {
        String sql = "SELECT text, COUNT(*) as count from searches GROUP BY text HAVING COUNT(*) > 1 ORDER BY count DESC,text  LIMIT ?";
        List<Search> customers = getJdbcTemplate().query(sql,
                new Object[]{limit},
                new int[]{Types.BIGINT},
                new BeanPropertyRowMapper<>(Search.class));

        return customers;
    }

    public List<Search> getSearches(Integer i, Date a) {
        String sql = "SELECT text, COUNT(*) as count from searches WHERE time > ? GROUP BY text HAVING COUNT(*) > 1 ORDER BY count DESC,text LIMIT ?";
        List<Search> customers;
        customers = getJdbcTemplate().query(sql,
                new Object[]{a, i},
                new int[]{Types.TIMESTAMP, Types.BIGINT},
                new BeanPropertyRowMapper<>(Search.class));

        return customers;
    }

    public List<Search> getSearches(Date a) {
        String sql = "SELECT text, COUNT(*) as count from searches WHERE time > ? GROUP BY text ORDER BY count DESC,text";
        List<Search> customers = getJdbcTemplate().query(sql,
                new Object[]{a},
                new int[]{Types.TIMESTAMP},
                new BeanPropertyRowMapper<>(Search.class));

        return customers;
    }

    /**
     * Saves a search into the database.
     *
     * The string will be normalized (to lowercase, no leading or trailing
     * spaces, and exactly one space between words) and then save to the
     * database.
     *
     * @param string
     * @param date
     */
    public void save(String string, Date date) {

        String sql = "INSERT INTO searches (text, time) VALUES (?,?)";

        try {
            getJdbcTemplate().update(sql,
                    new Object[]{string, date},
                    new int[]{Types.VARCHAR, Types.TIMESTAMP});
        } catch (DataAccessException e) {
            log.error("Error while trying to save Search", e);
        }
    }

    public void deleteAll() {
        getJdbcTemplate().update("DELETE from searches");
    }

    public void delete(String tag) {
        String sql = "DELETE FROM searches WHERE text = ?";

        try {
            getJdbcTemplate().update(sql,
                    new Object[]{tag},
                    new int[]{Types.VARCHAR});
        } catch (DataAccessException e) {
            log.error("Error while trying to delete tag: " + tag, e);
            throw e;
        }
    }
}
