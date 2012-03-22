/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateTemplate;
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
        HibernateTemplate t = getHibernateTemplate();
        t.setFlushMode(HibernateAccessor.FLUSH_NEVER);
        return t.get(Repositorio.class, id);
    }

    public List<Repositorio> getAll() {
        HibernateTemplate t = getHibernateTemplate();
        t.setFlushMode(HibernateAccessor.FLUSH_NEVER);
        return getHibernateTemplate().find("from Repositorio");
    }

    public void save(Repositorio r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Repositorio get(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO: este metodo é utilizado para testar se já existe um repositorios cadastrado com esse nome
    }

}
