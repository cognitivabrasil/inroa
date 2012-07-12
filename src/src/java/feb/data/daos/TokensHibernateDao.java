/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;


import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import feb.data.interfaces.TokensDao;

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
