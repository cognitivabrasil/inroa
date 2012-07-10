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
    public List<Visita> getAll();

    /**
     * Register a visit on the database
     */
    public void save(Visita v);
    /**
     * Calculate the number of visits in a month
     * @param month number of the month in a year
     * @return 
     */
    public int visitsInAMonth(int month);
    /**
     * Calculate the number of visits in a year
     * @param year the year in 4 digits in a gregorian calendar
     * @return a list of 12 elements with the visits by month
     */
    public List<Integer> visitsInAYear(int year);
}
