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
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import postgres.Conectar;

/**
 *
 * @author paulo
 */
public class DocumentosHibernateDAO implements DocumentosDAO {

    SessionFactory sessionFactory;
    Session session;
    private Repositorio repository;
    
    public DocumentosHibernateDAO() {
        Conectar c = new Conectar();

            Connection conn = c.conectaBD();
            sessionFactory = new Configuration().configure().setProperty("hibernate.show_sql", "false").buildSessionFactory();
            session = sessionFactory.openSession(conn);
    }
    
    private List<DocumentoReal> getByObaaEntry(String e) {
        return session.createQuery(
                "from DocumentoReal as doc where doc.obaaEntry = ?")
                .setString(0, e)
                .list();
               
    }
    
   @Override
    public DocumentoReal get(String e) {
        return (DocumentoReal)session.createQuery(
                "from DocumentoReal as doc where doc.obaaEntry = ?")
                .setString(0, e)
                .uniqueResult();
               
    }
   
       public DocumentoReal get(int i) {
        return (DocumentoReal)session.get(DocumentoReal.class, i);
               
    }

    private void deleteByObaaEntry(String e) {
        for(DocumentoReal d : getByObaaEntry(e)) {
                    System.out.println("DeleteByObaaEntry: " + e);
                    sessionFactory.openSession().delete(d);
        }
    }

    @Override
    public void save(OBAA obaa, Header h) {
        DocumentoReal doc = new DocumentoReal();
        System.out.println("Going to create documento...");

        doc.setRepositorio(getRepository());
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

    /**
     * @return the repositorio
     */
    public Repositorio getRepository() {
        return repository;
    }

    /**
     * @param repositorio the repositorio to set
     */
    public void setRepository(Repositorio repository) {
        this.repository = repository;
    }
}
