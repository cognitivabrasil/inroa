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
    public List<VisitasDocumentos> getAll();

    /**
     * Register a visit on the database
     */
    public void save(VisitasDocumentos v);
    
    /**
     * Get the Visita of an id.
     * @param id
     * @return
     */
    public VisitasDocumentos get(int id);
}
