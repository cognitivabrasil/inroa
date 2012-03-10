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
public interface PadraoMetadadosDAO {
    /**
     * 
     * @return All repositories
     */
    List<PadraoMetadados> getAll();
       
    /**
     * Creates or updates the repository.
     * 
     * @param r repository to be created or updated.
     */
    void save(PadraoMetadados r);
       
       /**
        * Deletes a repository.
        * 
        * @param r repository to be deleted
        */
       void delete(PadraoMetadados r);
       
       /**
        * Gets a specific repository.
        * 
        * @param id id of the repository
        * @return Repository
        */
       PadraoMetadados get(int id);
}
