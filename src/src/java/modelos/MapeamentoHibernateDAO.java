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
public class MapeamentoHibernateDAO extends HibernateDaoSupport implements MapeamentoDAO {

    
    public void delete(Mapeamento m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Mapeamento get(int id) {
        return getHibernateTemplate().get(Mapeamento.class, id);
    }

    public List<Mapeamento> getAll() {
        HibernateTemplate t = getHibernateTemplate();
        t.setFlushMode(HibernateAccessor.FLUSH_NEVER);
        return getHibernateTemplate().find("from Mapeamento");
    }

    public void save(Mapeamento m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
