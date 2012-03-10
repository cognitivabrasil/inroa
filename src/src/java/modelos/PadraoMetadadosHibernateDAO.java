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
 * @author paulo
 */
public class PadraoMetadadosHibernateDAO extends HibernateDaoSupport implements PadraoMetadadosDAO {

    public void delete(PadraoMetadados r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PadraoMetadados get(int id) {
        return getHibernateTemplate().get(PadraoMetadados.class, id);
    }

    public List<PadraoMetadados> getAll() {
        HibernateTemplate t = getHibernateTemplate();
        t.setFlushMode(HibernateAccessor.FLUSH_NEVER);
        return getHibernateTemplate().find("from PadraoMetadados");
    }

    public void save(PadraoMetadados r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
