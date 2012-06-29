package modelos;

/**
 * Data Access Object for FEB Tokens
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public interface TokensDao {

    /**
     * Deletes all tokens from a document.
     *
     * @param d document to be deleted all tokens.
     */
    void delete(DocumentoReal d);

    /**
     * Tokenizes a document and saves.
     * @param d
     */
    void saveTokens(DocumentoReal d);

}
