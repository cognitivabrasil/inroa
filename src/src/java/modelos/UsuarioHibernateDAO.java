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
public class UsuarioHibernateDAO extends HibernateDaoSupport implements UsuarioDAO {

    public Usuario authenticate(String login, String password) {
      HibernateTemplate t = getHibernateTemplate();
        t.setFlushMode(HibernateAccessor.FLUSH_NEVER);
        
        Usuario u = get(login);
        if(u != null && u.authenticate(password)) {
            return u;
        }
        return null;
    }

    public void delete(Usuario r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Usuario get(int id) {
        HibernateTemplate t = getHibernateTemplate();
        t.setFlushMode(HibernateAccessor.FLUSH_NEVER);
        return t.get(Usuario.class, id);
    }
    
    public Usuario get(String login) {
        HibernateTemplate t = getHibernateTemplate();
        t.setFlushMode(HibernateAccessor.FLUSH_NEVER);
        return (Usuario)t.find("from Usuario where login = ?", login).get(0);
    }

    public List<Usuario> getAll() {
        HibernateTemplate t = getHibernateTemplate();
        t.setFlushMode(HibernateAccessor.FLUSH_NEVER);
        return getHibernateTemplate().find("from Usuario");
    }

    public void save(Usuario r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
