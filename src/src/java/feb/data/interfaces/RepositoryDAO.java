/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.interfaces;

import java.util.List;

import feb.data.entities.Repositorio;

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

    /**
     * Updates the repository with the same id as r2 safely, ignoring null and
     * blank values
     *
     * It does NOT merge the associated Documents.
     *
     * @param r2 A repository that we want to update.
     * @throws IllegalArgumentException when r2 is null
     */
    void updateNotBlank(Repositorio r2);

    /**
     * Gets the repositories that are outdated.
     * @return repositories that are outdated
     */
    public List<Repositorio> getOutDated();
}
