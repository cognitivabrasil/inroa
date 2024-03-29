/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import java.util.List;

import com.cognitivabrasil.feb.data.entities.PadraoMetadados;

/**
 * Data Access Object for FEB Metadata standards
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
public interface MetadataRecordService {

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

    /**
     * Gets a specific MetadataRecord.
     *
     * @param name name of the MetadataRecord
     * @return PadraoMetadados
     */
    PadraoMetadados get(String name);
}
