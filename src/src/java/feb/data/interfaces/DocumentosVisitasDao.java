package feb.data.interfaces;

import feb.data.entities.DocumentoReal;
import feb.data.entities.DocumentosVisitas;
import java.util.List;

/**
 *
 * @author cei
 */
public interface DocumentosVisitasDao {

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
