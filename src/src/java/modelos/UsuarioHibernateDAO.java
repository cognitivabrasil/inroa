/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import feb.data.entities.Usuario;

/**
 *
 * @author paulo
 */
public class UsuarioHibernateDAO extends AbstractHibernateDAO<Usuario> implements UsuarioDAO, UserDetailsService {

    private static Logger log = Logger.getLogger(UsuarioHibernateDAO.class.getName());

    @Override
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

    @Override
    public Usuario get(String login) {
        return (Usuario) this.sessionFactory.getCurrentSession().createQuery("from Usuario where login = :login").setString("login", login).uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        log.debug("Trying to get user \"" + login + "\"");
        Usuario d = null;
        try {
            d = (Usuario) this.sessionFactory.getCurrentSession().createQuery("from Usuario where login = :login").setString("login", login).uniqueResult();
        } catch (Exception e) {
            log.error("Erro ao carregar usuário", e);
        }
        if (d == null) {
            log.debug("No such user " + login);
            throw new UsernameNotFoundException(
                    "No such user: " + login);
        }
        return d;
    }
}
