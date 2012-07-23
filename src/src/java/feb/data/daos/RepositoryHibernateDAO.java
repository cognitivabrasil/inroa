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
import feb.data.entities.Repositorio;
import feb.data.interfaces.RepositoryDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class RepositoryHibernateDAO.
 *
 * @author paulo
 */
public class RepositoryHibernateDAO extends AbstractNamedHibernateDAO<Repositorio> implements RepositoryDAO {
 
    public RepositoryHibernateDAO() {
    	setClazz(Repositorio.class);
    }
    

    @Override
    public void updateNotBlank(Repositorio r2) {
        if(r2.getId() == null) {
            throw new IllegalArgumentException("Cant update a new repository, save it instead");
        }
        Repositorio r = get(r2.getId());
        r.merge(r2);
        save(r);
    }
    
}
