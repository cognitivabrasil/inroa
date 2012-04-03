/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author paulo
 */
public class UsuarioHibernateDAO extends AbstractHibernateDAO<Usuario> implements UsuarioDAO {

    public Usuario authenticate(String login, String password) {

        if (login == null) {
            return null;
        }

        Usuario u = get(login);
        if (u != null && u.authenticate(password)) {
            return u;
        }
        return null;
    }

    public Usuario get(String login) {
        return (Usuario) this.sessionFactory.getCurrentSession().createQuery("from Usuario where login = :login").setString("login", login).uniqueResult();
    }

   
}
