/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;

/**
 * Data Access Object for FEB Metadata standards
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
public interface UsuarioDAO {
    /**
     * 
     * @return All repositories
     */
    List<Usuario> getAll();
       
    /**
     * Creates or updates the repository.
     * 
     * @param r repository to be created or updated.
     */
    void save(Usuario r);
       
       /**
        * Deletes a repository.
        * 
        * @param r repository to be deleted
        */
       void delete(Usuario r);
       
       /**
        * Gets a specific repository.
        * 
        * @param id id of the repository
        * @return Repository
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
