/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Date;
import java.util.List;

/**
 * This interface contains the basic attributes any document in the FEB 
 * system should have. We should program to this interface while harvesting
 * and while indexing. 
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
public interface DocumentoFebInterface {
    /**
     * 
     * @return List of titles associated to this Document.
     * @throws RuntimeException if the object has a DELETED state
     */
    public List<String> getTitles();
    
    /**
     * 
     * @return List of keywords associated to this Document.
     * @throws RuntimeException if the object has a DELETED state

     */
    public List<String> getKeywords();
    
    /**
     * 
     * @return List of descriptions associated to this Document.
     * @throws RuntimeException if the object has a DELETED state
     */
    public List<String> getDescriptions();
    
    /**
     * 
     * @return Date of the last modification
     */
    public Date getTimestamp();
    
    /**
     * 
     * @return Unique identifier of this object (obaa_entry)
     */
    public String getId();
    
    /**
     * 
     * @return True if this document is deleted, false otherwise.
     */
    public boolean isDeleted();
}
