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
     * Gets the.
     *
     * @param obaaEntry the obaa entry
     * @return the document with the corresponding obaaEntry
     */
    DocumentoFebInterface get(String obaaEntry);

    /**
     * Need to call flush on session after saving many documents.
     *
     * @param obaa the obaa
     * @param h the h
     */
    void save(OBAA obaa, Header h);
    
    /**
     * Sets the repository.
     *
     * @param r the new repository
     */
    void setRepository(Repositorio r);

    
	/**
	 * Convenience method, will call flush on the current session.
	 */
	void flush();
    
}
