/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;

import org.hibernate.criterion.Restrictions;

import feb.data.entities.Mapeamento;
import feb.data.interfaces.MapeamentoDAO;


/**
 *
 * @author marcos
 */
public class MapeamentoHibernateDAO extends AbstractHibernateDAO<Mapeamento> implements  MapeamentoDAO {

	@Override
	public boolean exists(String name) {
		return sessionFactory.getCurrentSession().createCriteria(Mapeamento.class).
			add(Restrictions.eq("name", name)).uniqueResult() != null;
	}

}
