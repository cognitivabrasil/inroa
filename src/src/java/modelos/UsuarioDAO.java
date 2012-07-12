package modelos;

import java.util.List;

import feb.data.entities.Usuario;

/**
 * Data Access Object for FEB Metadata standards
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
public interface UsuarioDAO {

    /**
     *
     * @return All users
     */
    List<Usuario> getAll();

    /**
     * Creates or updates the user.
     *
     * @param u user to be created or updated.
     */
    void save(Usuario u);

    /**
     * Deletes a user.
     *
     * @param u user to be deleted
     */
    void delete(Usuario u);

    /**
     * Gets a specific user.
     *
     * @param id id of the user
     * @return User
     */
    Usuario get(int id);

    /**
     * Gets a specific user by login.
     *
     * @param login Login of the user
     * @return Usuario if found, otherwise null
     */
    Usuario get(String login);

    /**
     * Gets a specific user only if the password matches.
     *
     * @param login Username of the user
     * @param password Password supplied
     * @return User object if password matches login, null otherwise
     */
    Usuario authenticate(String login, String password);
}
