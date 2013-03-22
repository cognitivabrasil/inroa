package feb.data.daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import feb.data.entities.Visita;
import feb.data.interfaces.VisitasDao;
import java.text.SimpleDateFormat;
import java.util.Collections;

/**
 *
 * @author cei
 */
public class VisitasHibernateDao extends AbstractHibernateDAO<Visita> implements VisitasDao {
    
    public VisitasHibernateDao() {
        setClazz(Visita.class);
    }
    
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
    public List<Integer> visitsUpToAMonth(int month, int year, int numMonth) {
        
        List <Integer> output = new ArrayList();
        
        for (int i = 1; i <= numMonth; i++) {            
            
            output.add(visitsInAMonth(month, year));
            
            if (month == 1) {
                month = 12;
                year--;
            } else {
                month--;
            }            
            
        }
        
        Collections.reverse(output);
        
        return output;
    }
    
    @Override
    public List<Integer> visitsToBetweenDates(Date i, Date f){
        Session s = this.sessionFactory.getCurrentSession();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd"); 
        
        String sql = "select qtd_visitas as qtd from"
                + "  (select to_timestamp(universo_ano || '-' || universo_mes || '-01','yyyy-MM-dd') as datee, qtd_visitas from "
                + "  ("
                + "  select universo_ano , universo_mes , coalesce(qtd,null,0) as qtd_visitas from"
                + " ("
                + " select * from generate_series(2010,2030) as universo_ano"
                + " join ("
                + " select * from generate_series(1,12) as universo_mes"
                + " ) tt on universo_ano > universo_mes"
                + " ) universo"
                + " left join "
                + " ("
                + " SELECT extract('month' from horario) as mes, "
                + " extract('year' from horario) as ano , count(*) as qtd"
                + " FROM visitas"
                + " "
                + " GROUP BY mes,ano"
                + " ) tabela_quantidades"
                + " ON universo_ano = ano AND universo_mes = mes"
                + " ORDER BY universo_ano , universo_mes"
                + " ) xx"
                + " )xxx"
                + " WHERE datee BETWEEN '"+formatter.format(i) +"' AND '"+formatter.format(f) +"'";
        
        return (List<Integer>)s.createSQLQuery(sql).list();
    }
    
    @Override
    public List<Integer> visitsInAYear(int year) {
        
        List output = new ArrayList();
        
        for (int i = 1; i <= 12; i++) {
            output.add(visitsInAMonth(i, year));
        }
        
        return output;
    }
    
    @Override
    public Visita get(int id) {
        
        Session s = this.sessionFactory.getCurrentSession();
        Visita v = (Visita) s.createCriteria(Visita.class).
                add(Restrictions.eq("id", id)).uniqueResult();
        
        return v;
    }
}
