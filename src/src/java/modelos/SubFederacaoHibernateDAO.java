/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marcos
 */

public class SubFederacaoHibernateDAO extends AbstractHibernateDAO<SubFederacao> implements SubFederacaoDAO {

    public SubFederacao get(String nome) {
        Session s = this.sessionFactory.getCurrentSession();
        return (SubFederacao)s.createCriteria(SubFederacao.class).add(Restrictions.eq("nome", nome).ignoreCase()).uniqueResult();
    }

    public void updateNotBlank(SubFederacao r2) {
        if (r2.getId() == null) {
            throw new IllegalArgumentException("Cant update a new SubFederation, save it instead");
        }
        SubFederacao r = get(r2.getId());
        r.merge(r2);
        save(r);
    }
}
