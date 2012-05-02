/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import OBAA.OBAA;
import java.util.List;
import metadata.Header;

// TODO: Auto-generated Javadoc
/**
 * DAO interface for FEB documents.
 * 
 * This will be injected by Spring, and can be
 * used to do operations on FEB documents.
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
public interface DocumentosDAO {
    
    /**
     * Gets the document with the specified OBAAEntry.
     *
     * @param obaaEntry the obaa entry
     * @return the document with the corresponding obaaEntry
     */
    DocumentoFebInterface get(String obaaEntry);

    /**
     * Need to call flush on session after saving many documents.
     *
     * @param obaa the OBAA metadata
     * @param h the OAI PMH header
     */
    void save(OBAA obaa, Header h);
    


    
	/**
	 * Convenience method, will call flush on the current session.
	 */
	void flush();

	/**
	 * Gets all the documents present in the System.
	 * Use with extreme care, as it might return to many results.
	 *
	 * @return All the documents
	 */
	List<DocumentoReal> getAll();

	/**
	 * Gets the document by ID.
	 *
	 * @param i the Id
	 * @return the Document with the corresponding ID.
	 */
	DocumentoReal get(int i);

	/**
	 * Delete by obaa entry.
	 *
	 * @param e The obaaEntry
	 */
	void deleteByObaaEntry(String e);

	/**
	 * Delete a document.
	 *
	 * @param d the document to be deleted.
	 */
	void delete(DocumentoReal d);

	/**
	 * Gets the repository where saved documents will be inserted.
	 *
	 * @return the repository
	 */
	Repositorio getRepository();
	
    /**
     * Sets the repository where saved documents will be inserted.
     *
     * @param r the new repository
     */
    void setRepository(Repositorio r);
    
}
