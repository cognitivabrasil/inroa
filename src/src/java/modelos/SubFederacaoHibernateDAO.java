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
 * @author Marcos
 */
//@Transactional
public class SubFederacaoHibernateDAO implements SubFederacaoDAO {
    
    @Autowired
    SessionFactory sessionFactory;

    public void delete(SubFederacao s) {
        this.sessionFactory.getCurrentSession().delete(s);
    }

    public SubFederacao get(int id) {
        return (SubFederacao)this.sessionFactory.getCurrentSession().get(SubFederacao.class, id);
    }

    public List<SubFederacao> getAll() {
        Session s = this.sessionFactory.getCurrentSession();
        return s.createQuery("from SubFederacao").list();
    }

    public SubFederacao get(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO: isso é usado na verdade apenas pra testar se já existe uma federacao com esse nome
    }

    public void save(SubFederacao s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
