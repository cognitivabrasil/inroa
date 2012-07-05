package modelos;

import java.util.Date;
import java.util.List;

/**
 *
 * @author cei
 */
public interface VisitasDao {

    /**
     *
     * @return All visits' timestamp
     */
    public List<Visitas> getAll();

    /**
     * Register a visit on the database
     */
    public void register(Date d);
}
