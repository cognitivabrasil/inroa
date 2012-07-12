/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;


import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import feb.data.entities.Repositorio;
import feb.data.interfaces.RepositoryDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class RepositoryHibernateDAO.
 *
 * @author paulo
 */
public class RepositoryHibernateDAO extends AbstractHibernateDAO<Repositorio> implements RepositoryDAO {
 
	@Override
    public Repositorio get(String name) {
        Session s = this.sessionFactory.getCurrentSession();
        return (Repositorio)s.createCriteria(Repositorio.class).
        		add(Restrictions.eq("nome", name).ignoreCase()).uniqueResult();
    }
    

    @Override
    public void updateNotBlank(Repositorio r2) {
        if(r2.getId() == null) {
            throw new IllegalArgumentException("Cant update a new repository, save it instead");
        }
        Repositorio r = get(r2.getId());
        r.merge(r2);
        save(r);
    }
    
}
