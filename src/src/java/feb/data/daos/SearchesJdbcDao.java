package feb.data.daos;

import feb.data.entities.Search;
import feb.data.interfaces.SearchesDao;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author Paulo Schreiner <paulo@cognitivabrasil.com.br>
 *
 */
public class SearchesJdbcDao extends JdbcDaoSupport implements SearchesDao {

    private Logger logger = Logger.getLogger(SearchesJdbcDao.class);

    @Override
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
            logger.error("Error while trying to cleanup", e);
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
        String sql = "SELECT text, COUNT(*) as count from searches GROUP BY text ORDER BY count DESC,text  LIMIT ?";
        List<Search> customers = getJdbcTemplate().query(sql,
                new Object[]{limit},
                new int[]{Types.BIGINT},
                new BeanPropertyRowMapper<Search>(Search.class));

        return customers;
    }

    @Override
    public List<Search> getSearches(Integer i, Date a) {
        String sql = "SELECT text, COUNT(*) as count from searches WHERE time > ? GROUP BY text  ORDER BY count DESC,text LIMIT ?";
        List<Search> customers = getJdbcTemplate().query(sql,
                new Object[]{a, i},
                new int[]{Types.TIMESTAMP, Types.BIGINT},
                new BeanPropertyRowMapper<Search>(Search.class));

        return customers;
    }

    public List<Search> getSearches(Date a) {
        String sql = "SELECT text, COUNT(*) as count from searches WHERE time > ? GROUP BY text ORDER BY count DESC,text";
        List<Search> customers = getJdbcTemplate().query(sql,
                new Object[]{a},
                new int[]{Types.TIMESTAMP},
                new BeanPropertyRowMapper<Search>(Search.class));

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
    @Override
    public void save(String string, Date date) {
        String sql = "INSERT INTO searches VALUES (?,?)";
        String[] l = string.trim().toLowerCase().split("\\s+");
        List<String> n = new ArrayList<String>();
        for (String s : l) {
            n.add(s);
        }
        String cons = StringUtils.join(n, " ");


        try {
            getJdbcTemplate().update(sql,
                    new Object[]{cons, date},
                    new int[]{Types.VARCHAR, Types.TIMESTAMP});
        } catch (DataAccessException e) {
            logger.error("Error while trying to save Search", e);
        }

    }

    @Override
    public void deleteAll() {
        getJdbcTemplate().update("DELETE from searches");
    }

    @Override
    public void delete(String tag) {
        String sql = "DELETE FROM searches WHERE text = ?";


        try {
            getJdbcTemplate().update(sql,
                    new Object[]{tag},
                    new int[]{Types.VARCHAR});
        } catch (DataAccessException e) {
            logger.error("Error while trying to delete tag: "+tag, e);
            throw e;
        }
    }
}
