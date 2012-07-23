/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import feb.data.entities.Mapeamento;
import feb.data.entities.PadraoMetadados;
import feb.data.entities.SubFederacao;
import feb.data.interfaces.SubFederacaoDAO;

/**
 *
 * @author Marcos
 */

public class SubFederacaoHibernateDAO extends AbstractNamedHibernateDAO<SubFederacao> implements SubFederacaoDAO {

    public SubFederacaoHibernateDAO() {
    	setClazz(SubFederacao.class);
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
