package feb.data.interfaces;

import feb.data.entities.VisitasDocumentos;
import java.util.List;

/**
 *
 * @author Luiz H. L. Rossi <lh.rossi@cognitivabrasil.com>
 */
public interface VisitasDocumentosDao {
    
    /**
     *
     * @return All visits' timestamp
     */
    List<VisitasDocumentos> getAll();

    /**
     * Register a visit on the database
     */
    void save(VisitasDocumentos v);
    
    /**
     * Get the Visita of an id.
     * @param id
     * @return
     */
    VisitasDocumentos get(int id);
}
