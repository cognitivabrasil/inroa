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
public class SubFederacaoHibernateDAO extends AbstractHibernateDAO<SubFederacao> implements SubFederacaoDAO {


    public SubFederacao get(String nome) {
        Session s = this.sessionFactory.getCurrentSession();
        return (SubFederacao) s.createQuery("from SubFederacao WHERE nome = :nome").setString("nome", nome).uniqueResult();
    }

}
