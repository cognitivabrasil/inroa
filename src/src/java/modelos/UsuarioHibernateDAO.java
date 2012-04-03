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
public class UsuarioHibernateDAO implements UsuarioDAO {

    @Autowired
    SessionFactory sessionFactory;

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

    public void delete(Usuario r) {
        this.sessionFactory.getCurrentSession().delete(r);
    }

    public Usuario get(int id) {
        return (Usuario) this.sessionFactory.getCurrentSession().get(Usuario.class, id);
    }

    public Usuario get(String login) {
        return (Usuario) this.sessionFactory.getCurrentSession().createQuery("from Usuario where login = :login").setString("login", login).uniqueResult();
    }

    public List<Usuario> getAll() {
        return (List<Usuario>) this.sessionFactory.getCurrentSession().createQuery("from Usuario").list();
    }

    // @Transactional
    public void save(Usuario r) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(r);
    }
}
