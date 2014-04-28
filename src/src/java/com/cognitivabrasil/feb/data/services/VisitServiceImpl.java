package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.repositories.VisitRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.cognitivabrasil.feb.data.entities.Visita;
import java.text.SimpleDateFormat;
import java.util.Collections;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cei
 */
@Service
public class VisitServiceImpl implements VisitService {
    @Autowired
    VisitRepository visitRep;
    
    @Override
    public Visita get(int id) {
        return visitRep.findOne(id);
    }
    
    @Override
    public List<Visita> getAll(){
        return visitRep.findAll();
    }
    
    @Override
    public void save(Visita v){
        visitRep.save(v);
    }
    
    @Override
    public int visitsInAMonth(int month, int year) {
        
        LocalDate fromDt = new LocalDate(year, month, 1);
        LocalDate ultilDt = fromDt.minusMonths(1);
        
        return visitRep.countBetweenInclusive(fromDt, ultilDt);
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
        
        return null;//(List<Integer>)s.createSQLQuery(sql).list();
    }
    
    @Override
    public List<Integer> visitsInAYear(int year) {
        
        List output = new ArrayList();
        
        for (int i = 1; i <= 12; i++) {
            output.add(visitsInAMonth(i, year));
        }
        
        return output;
    }
    
}
