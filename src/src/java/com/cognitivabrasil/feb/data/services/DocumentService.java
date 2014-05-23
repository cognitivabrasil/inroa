package com.cognitivabrasil.feb.data.services;

import cognitivabrasil.obaa.OBAA;
import com.cognitivabrasil.feb.data.entities.DocumentoReal;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import java.util.List;
import metadata.Header;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for FEB documents.
 *
 * This will be injected by Spring, and can be used to do operations on FEB
 * documents.
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public interface DocumentService {

    /**
     * Gets the document with the specified OBAAEntry.
     *
     * @param obaaEntry the obaa entry
     * @return the document with the corresponding obaaEntry
     */
    DocumentoReal get(String obaaEntry);
    
    /**
     * Gets the document by ID.
     *
     * @param i the Id
     * @return the Document with the corresponding ID.
     */
    DocumentoReal get(int i);

    /**
     * Delete a document.
     *
     * @param d the document to be deleted.
     */
    void delete(DocumentoReal d);
    
    /**
     * Deletes all documents from the repository entered.
     *
     * @param r Repository that will have your documents deleted.
     * @return Rows affected
     */
    int deleteAllFromRep(Repositorio r);
    

    /**
     * Need to call flush on session after saving many documents. 
     * @param obaa the OBAA metadata
     * @param h the OAI PMH header
     * @param r repository to save the document in
     */
    void save(OBAA obaa, Header h, Repositorio r);

    /**
     * Need to call flush on session after saving many documents. 
     * @param obaa the OBAA metadata
     * @param h the OAI PMH header
     * @param s federation to save the document in
     */
    void save(OBAA obaa, Header h, SubFederacao s);

    
    /**
     * Gets all the documents present in the System. Use with extreme care, as
     * it might return to many results.
     *
     * @return All the documents
     */
    List<DocumentoReal> getAll();
    
    /**
     * Returns all documents within the range set in Paggeable. 
     * Use with extreme care, as it might return to many results.
     *
     * @param pageable Object Pageable with information about limit and offset
     * @return All documents within this range
     */
    Page<DocumentoReal> getlAll(Pageable pageable);

    /**
     * Get the number of documents
     * @return the total number of documents
     */
    long getSize();

    /**
     * Gets the number of documents including those deleted.
     * @return number of documents
     */
    long getSizeWithDeleted();
}