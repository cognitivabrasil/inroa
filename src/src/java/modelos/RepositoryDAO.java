/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;

/**
 * Data Access Object for FEB repositories
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
public interface RepositoryDAO {
    /**
     * 
     * @return All repositories
     */
    List<Repositorio> getAll();
       
    /**
     * Creates or updates the repository.
     * 
     * @param r repository to be created or updated.
     */
    void save(Repositorio r);
       
       /**
        * Deletes a repository.
        * 
        * @param r repository to be deleted
        */
       void delete(Repositorio r);
       
       /**
        * Gets a specific repository.
        * 
        * @param id id of the repository
        * @return Repository
        */
       Repositorio get(int id);
       
       /**
        * Gets a specific repository.
        * 
        * @param name name of the repository
        * @return Repository
        */
       Repositorio get(String name);
}
