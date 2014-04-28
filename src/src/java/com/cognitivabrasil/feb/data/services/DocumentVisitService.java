package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.DocumentoReal;
import com.cognitivabrasil.feb.data.entities.DocumentosVisitas;
import java.util.List;

/**
 *
 * @author cei
 */
public interface DocumentVisitService {

    /**
     *
     * @return All visits' timestamp
     */
    public List<DocumentosVisitas> getAll();

    /**
     * Register a visit on the database
     */
    public void save(DocumentosVisitas v);

    /**
     * Get the Visita of an id.
     * @param id
     * @return
     */
    public DocumentosVisitas get(int id);
    
    public List<DocumentoReal> getTop(int n);
}
