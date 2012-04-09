/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import org.hibernate.Session;


/**
 *
 * @author paulo
 */
public class PadraoMetadadosHibernateDAO extends AbstractHibernateDAO<PadraoMetadados> implements PadraoMetadadosDAO {

    public PadraoMetadados get(String name){        
        Session s = this.sessionFactory.getCurrentSession();
        return (PadraoMetadados) s.createQuery("from PadraoMetadados WHERE nome = :nome").setString("nome", name).uniqueResult();
    }
}
