package modelos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author cei
 */
public class VisitasHibernateDao extends AbstractHibernateDAO<Visita> implements VisitasDao {


     @Override    
    public int visitsInAMonth(int month, int year) {


        month--;
        Calendar from = Calendar.getInstance();
        Calendar until = Calendar.getInstance();

        from.set(Calendar.YEAR, year);
        from.set(Calendar.MONTH, month);
        from.set(Calendar.DAY_OF_MONTH, 1);

        until.set(Calendar.YEAR, year);
        until.set(Calendar.MONTH, ++month);
        until.set(Calendar.DAY_OF_MONTH, 1);

        until.add(Calendar.DAY_OF_MONTH, -1);

        Date fromDate = from.getTime();
        Date untilDate = until.getTime();
        
        Session s = this.sessionFactory.getCurrentSession();
        
        Criteria c = s.createCriteria(Visita.class);
                
        c.add(Restrictions.gt("horario", fromDate));
        c.add(Restrictions.lt("horario", untilDate));
        
        c.setProjection(Projections.rowCount());
        
        List list = c.list();
        return (Integer.parseInt(list.get(0).toString()));    
    }

    @Override
    public List<Integer> visitsInAYear(int year) {
      
        List output = new ArrayList();
        
        for (int i=1; i<=12; i++){
            output.add(visitsInAMonth(i, year));
        }
        
        return output;
    }
}
