/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import OBAA.OBAA;
import java.util.List;
import metadata.Header;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author paulo
 */
public class DocumentosHibernateDAO implements DocumentosDAO {

    @Autowired
    SessionFactory sessionFactory;
    private Repositorio repository;
    
    public DocumentosHibernateDAO() {
    }
    
    private List<DocumentoReal> getByObaaEntry(String e) {
        return getSession().createQuery(
                "from DocumentoReal as doc where doc.obaaEntry = ?")
                .setString(0, e)
                .list();
               
    }
    
   @Override
    public DocumentoReal get(String e) {
        return (DocumentoReal)getSession().createQuery(
                "from DocumentoReal as doc where doc.obaaEntry = ?")
                .setString(0, e)
                .uniqueResult();
               
    }
   
       public DocumentoReal get(int i) {
        return (DocumentoReal)getSession().get(DocumentoReal.class, i);
               
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
            Transaction t = getSession().beginTransaction();


            getSession().save(doc);
            for (Objeto o : doc.getObjetos()) {
                System.out.println("Saving object");

                getSession().save(o);
            }

            getSession().flush();
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

    /**
     * @return the session
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * @param session the session to set
     */

}
