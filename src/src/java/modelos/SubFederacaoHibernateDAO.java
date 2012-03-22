/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author Marcos
 */
public class SubFederacaoHibernateDAO extends HibernateDaoSupport implements SubFederacaoDAO {
    
    public void delete(SubFederacao s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public SubFederacao get(int id) {
        return getHibernateTemplate().get(SubFederacao.class, id);
    }
    
    public List<SubFederacao> getAll() {
        HibernateTemplate t = getHibernateTemplate();
        t.setFlushMode(HibernateAccessor.FLUSH_NEVER);
        return getHibernateTemplate().find("from SubFederacao");
    }
    
    public SubFederacao get(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO: isso é usado na verdade apenas pra testar se já existe uma federacao com esse nome
    }

    public void save(SubFederacao s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
