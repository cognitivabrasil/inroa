/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Transactional
public class StopWordHibernateDao implements StopWordsDao {

    @Autowired
    private SessionFactory sessionFactory;

    static Logger log = Logger.getLogger(StopWordHibernateDao.class);
    private HashMap<String, List<String>> stopwordsLang;

    public StopWordHibernateDao() {
        stopwordsLang = new HashMap<String, List<String>>();
    }

    @Override
    public List<String> getStopWords(String lang) {
        if(stopwordsLang!=null && stopwordsLang.containsKey(lang)){
            return stopwordsLang.get(lang);
        }else{
            loadStopWords(lang);
            return stopwordsLang.get(lang);
        }
    }

    private void loadStopWords(String lang){
        String sql = "SELECT stopword FROM stopwords;";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ROOT_ENTITY);
        List<String> results = query.list();
        if(results.isEmpty()){
            log.error("No stopword found for language "+lang);
        }
        stopwordsLang.put(lang, results);
    }
}
