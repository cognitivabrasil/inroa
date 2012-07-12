package modelos;

import java.util.List;

import feb.data.entities.Visita;

/**
 *
 * @author cei
 */
public interface VisitasDao {

    /**
     *
     * @return All visits' timestamp
     */
    public List<Visita> getAll();

    /**
     * Register a visit on the database
     */
    public void save(Visita v);

    /**
     * Calculate the number of visits in a month
     *
     * @param month number of the month in a year
     * @param year the year in 4 digits in a gregorian calendar
     * @return
     */
    public int visitsInAMonth(int month, int year);

    /**
     * Calculate the number of visits in a year
     *
     * @param year the year in 4 digits in a gregorian calendar
     * @return a list of 12 elements with the visits by month
     */
    public List<Integer> visitsInAYear(int year);

    /**
     * Get the Visita of an id.
     * @param id
     * @return
     */
    public Visita get(int id);
}
