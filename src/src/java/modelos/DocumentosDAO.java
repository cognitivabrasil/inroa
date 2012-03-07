/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import OBAA.OBAA;
import java.util.List;
import metadata.Header;

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
     * 
     * @param obaaEntry 
     * @return the document with the corresponding obaaEntry
     */
    DocumentoFebInterface get(String obaaEntry);

    /**
     * 
     * @param obaa
     * @param h
     */
    void save(OBAA obaa, Header h);
    
    void setRepository(Repositorio r);
    
}
