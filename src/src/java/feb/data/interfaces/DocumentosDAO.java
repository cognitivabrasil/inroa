package feb.data.interfaces;

import cognitivabrasil.obaa.OBAA;
import java.util.List;

import feb.data.entities.DocumentoReal;
import feb.data.entities.SubFederacao;
import feb.data.entities.SubNodo;
import metadata.Header;

// TODO: Auto-generated Javadoc
/**
 * DAO interface for FEB documents.
 *
 * This will be injected by Spring, and can be used to do operations on FEB
 * documents.
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
     * Need to set one repository or one federation before.
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
     * Gets all the documents present in the System. Use with extreme care, as
     * it might return to many results.
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
    SubNodo getRepository();

    /**
     * Sets the repository where saved documents will be inserted.
     *
     * @param r the new repository
     */
    void setRepository(SubNodo r);

    /**
     * Sets the federation where saved documents will be inserted.
     *
     * @param s the new federation
     */
    void setFederation(SubFederacao s);

    /**
     * Get document without tokens.
     * Documents that have not been tokenized.
     * @return List of documents without tokens.
     */
    List<DocumentoReal> getwithoutToken();  
}