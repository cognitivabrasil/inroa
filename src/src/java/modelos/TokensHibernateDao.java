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
    private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory2) {
		sessionFactory = sessionFactory2;		
	}
}
