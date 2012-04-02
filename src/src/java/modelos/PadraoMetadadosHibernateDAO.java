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
public class PadraoMetadadosHibernateDAO implements PadraoMetadadosDAO {
        @Autowired
    SessionFactory sessionFactory;

    public void delete(PadraoMetadados r) {
        this.sessionFactory.getCurrentSession().delete(r);
    }

    public PadraoMetadados get(int id) {
        return (PadraoMetadados)this.sessionFactory.getCurrentSession().get(PadraoMetadados.class, id);
    }

    public List<PadraoMetadados> getAll() {
        Session s = this.sessionFactory.getCurrentSession();
        return s.createQuery("from PadraoMetadados").list();
    }

    public void save(PadraoMetadados r) {
        HibernateTemplate t = new HibernateTemplate(this.sessionFactory);
        t.saveOrUpdate(r); 
    }

}
