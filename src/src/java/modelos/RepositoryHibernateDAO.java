/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author paulo
 */
public class RepositoryHibernateDAO extends AbstractHibernateDAO<Repositorio> implements RepositoryDAO {
 
    public Repositorio get(String name) {
        Session s = this.sessionFactory.getCurrentSession();
        return (Repositorio) s.createQuery("from Repositorio WHERE nome = :nome").setString("nome", name).uniqueResult();
    }
}
