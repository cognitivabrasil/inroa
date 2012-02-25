/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import OBAA.OBAA;
import java.sql.Connection;
import metadata.Header;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import postgres.Conectar;

/**
 *
 * @author paulo
 */
public class DocumentosService {
    Session session;
    Repositorio repositorio;
    
    public DocumentosService(Repositorio r) {
        repositorio = r;
    }
    
    public void save(OBAA obaa, Header h) {
        if(session == null) {
                 Conectar c = new Conectar();
               
               Connection conn = c.conectaBD();
                SessionFactory sessionFactory = new Configuration().configure().setProperty("hibernate.show_sql", "true").buildSessionFactory();
        session = sessionFactory.openSession(conn);
        }
        DocumentoReal doc = new DocumentoReal();
                System.out.println("Going to create documento...");

        doc.setRepositorio(repositorio);
        doc.setObaaEntry(h.getIdentifier());
        doc.setTimestamp(h.getTimestamp());
        
        for(String t : obaa.getTitles()) {
                    doc.addTitle(t);
        }
        
        for(String k : obaa.getKeywords()) {
            doc.addKeyword(k);
        }
        
        for(String d : obaa.getGeneral().getDescriptions()) {
            doc.addDescription(d);
        }
        
        
        try {
        System.out.println("Trying to save");
       Transaction t = session.beginTransaction();
       
   
        session.save(doc);
       for(Objeto o : doc.getObjetos()) {
           System.out.println("Saving object");
           
           session.save(o);
       }
       
        session.flush();
                t.commit();

        
        //session.close();
    }
    catch(Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        
    }
}
}
