/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import OBAA.OBAA;
import java.sql.Connection;
import java.util.List;
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
    
    public List<DocumentoReal> getByObaaEntry(String e) {
        return session.createQuery(
                "from DocumentoReal as doc where doc.obaaEntry = ?")
                .setString(0, e)
                .list();
               
    }

    private void deleteByObaaEntry(String e) {
        for(DocumentoReal d : getByObaaEntry(e)) {
                    System.out.println("DeleteByObaaEntry: " + e);
                    session.delete(d);
        }
    }

    public void save(OBAA obaa, Header h) {
        if (session == null) {
            Conectar c = new Conectar();

            Connection conn = c.conectaBD();
            SessionFactory sessionFactory = new Configuration().configure().setProperty("hibernate.show_sql", "false").buildSessionFactory();
            session = sessionFactory.openSession(conn);
        }
        DocumentoReal doc = new DocumentoReal();
        System.out.println("Going to create documento...");

        doc.setRepositorio(repositorio);
        doc.setObaaEntry(h.getIdentifier());
        doc.setTimestamp(h.getTimestamp());

        if (h.isDeleted()) {
            doc.setDeleted(true);
            deleteByObaaEntry(doc.getObaaEntry());
        } else {
            for (String t : obaa.getTitles()) {
                doc.addTitle(t);
            }

            for (String k : obaa.getKeywords()) {
                doc.addKeyword(k);
            }

            for (String d : obaa.getGeneral().getDescriptions()) {
                doc.addDescription(d);
            }
        }

        try {
            System.out.println("Trying to save");
            Transaction t = session.beginTransaction();


            session.save(doc);
            for (Objeto o : doc.getObjetos()) {
                System.out.println("Saving object");

                session.save(o);
            }

            session.flush();
            t.commit();


            //session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
    }
}
