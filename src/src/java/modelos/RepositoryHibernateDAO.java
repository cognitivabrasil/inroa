/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author paulo
 */
public class RepositoryHibernateDAO extends AbstractHibernateDAO<Repositorio> implements RepositoryDAO {
 
    public Repositorio get(String name) {
        Session s = this.sessionFactory.getCurrentSession();
        return (Repositorio)s.createCriteria(Repositorio.class).add(Restrictions.eq("nome", name).ignoreCase()).uniqueResult();
    }
    

    public void updateNotBlank(Repositorio r2) {
        if(r2.getId() == null) {
            throw new IllegalArgumentException("Cant update a new repository, save it instead");
        }
        Repositorio r = get(r2.getId());
        r.merge(r2);
        save(r);
    }
    
}
