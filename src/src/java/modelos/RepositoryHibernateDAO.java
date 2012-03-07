/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.ArrayList;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author paulo
 */
public class RepositoryHibernateDAO extends HibernateDaoSupport implements RepositoryDAO {

    public void delete(Repositorio r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Repositorio get(int id) {
        return getHibernateTemplate().get(Repositorio.class, id);
    }

    public List<Repositorio> getAll() {
        return getHibernateTemplate().find("from Repositorio");
    }

    public void save(Repositorio r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
