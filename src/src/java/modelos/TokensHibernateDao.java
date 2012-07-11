/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class TokensHibernateDao implements TokensDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void delete(DocumentoReal doc) {
        String sql = "DELETE FROM r1tokens where documento_id=" + doc.getId();
        Session s = this.sessionFactory.getCurrentSession();
        s.createSQLQuery(sql).executeUpdate();

    }

    @Override
    public void saveTokens(DocumentoReal doc) {
    	doc.generateTokens();
    }

	public void setSessionFactory(SessionFactory sessionFactory2) {
		sessionFactory = sessionFactory2;		
	}
}
