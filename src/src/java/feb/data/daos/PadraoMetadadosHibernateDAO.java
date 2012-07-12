/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;


import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import feb.data.entities.PadraoMetadados;
import feb.data.interfaces.PadraoMetadadosDAO;


/**
 *
 * @author paulo
 */
public class PadraoMetadadosHibernateDAO extends AbstractHibernateDAO<PadraoMetadados> implements PadraoMetadadosDAO {

    public PadraoMetadados get(String name){        
        Session s = this.sessionFactory.getCurrentSession();
        return (PadraoMetadados) s.createCriteria(PadraoMetadados.class).add(Restrictions.eq("nome", name)).uniqueResult();
    }
}
