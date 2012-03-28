/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author paulo
 */
public class RepositoryHibernateDAO implements RepositoryDAO {
    @Autowired
    SessionFactory sessionFactory;

    public void delete(Repositorio r) {
        this.sessionFactory.getCurrentSession().delete(r);

    }

    public Repositorio get(int id) {
        return (Repositorio)this.sessionFactory.getCurrentSession().get(Repositorio.class, id);

    }

    public List<Repositorio> getAll() {
         Session s = this.sessionFactory.getCurrentSession();
        return s.createQuery("from Repositorio").list();
    }

    public void save(Repositorio r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Repositorio get(String name) {
        if(name.equalsIgnoreCase("cesta")){
            return new Repositorio();
        }else
            return null;
        //TODO: este metodo é utilizado para testar se já existe um repositorios cadastrado com esse nome. Deve retornar null se nao existir.
    }
}
