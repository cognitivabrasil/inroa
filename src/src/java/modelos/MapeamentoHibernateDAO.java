/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author marcos
 */
public class MapeamentoHibernateDAO implements MapeamentoDAO {
    @Autowired
    SessionFactory sessionFactory;
    
    public void delete(Mapeamento m) {
        this.sessionFactory.getCurrentSession().delete(m);
    }

    public Mapeamento get(int id) {
        return (Mapeamento)this.sessionFactory.getCurrentSession().get(Mapeamento.class, id);
    }

    public List<Mapeamento> getAll() {
        return this.sessionFactory.getCurrentSession().createQuery("from Mapeamento").list();
    }

    public void save(Mapeamento m) {
        HibernateTemplate t = new HibernateTemplate(this.sessionFactory);
        t.saveOrUpdate(m);    
    }
    
}
