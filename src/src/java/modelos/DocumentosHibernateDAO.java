/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import OBAA.LifeCycle.Contribute;
import OBAA.LifeCycle.Entity;
import OBAA.OBAA;
import java.util.List;
import metadata.Header;
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
    
    public DocumentoReal get(String e) {
        return (DocumentoReal)getSession().createQuery(
                "from DocumentoReal as doc where doc.obaaEntry = ?")
                .setString(0, e)
                .uniqueResult();
               
    }
   
       public DocumentoReal get(int i) {
        return (DocumentoReal)getSession().get(DocumentoReal.class, i);
               
    }
       
       public List<DocumentoReal> getAll() {
           return getSession().createQuery("from DocumentoReal").list();
       }

    public void deleteByObaaEntry(String e) {
        for(DocumentoReal d : getByObaaEntry(e)) {
                    System.out.println("DeleteByObaaEntry: " + e);
                    delete(d);
        }
    }
    
    public void delete(DocumentoReal d) {
        getSession().delete(d);
    }

    public void save(OBAA obaa, Header h) {
        DocumentoReal doc = new DocumentoReal();
        doc.setDeleted(false);
        //System.out.println("Going to create documento "+h.getIdentifier());

        Repositorio r = getRepository();
        doc.setRepositorio(r);
        doc.setObaaEntry(h.getIdentifier());
        doc.setTimestamp(h.getTimestamp());

        if (h.isDeleted()) {
            doc.setDeleted(true);
            deleteByObaaEntry(doc.getObaaEntry());
        } else {
            doc.setDeleted(false);
            for (String t : obaa.getTitles()) {
                doc.addTitle(t);
            }

            for (String k : obaa.getKeywords()) {
                doc.addKeyword(k);
            }

            for (String d : obaa.getGeneral().getDescriptions()) {
                doc.addDescription(d);
            }
            
            for (Contribute c: obaa.getLifeCycle().getContribute()){
                for (String e: c.getEntities()){    
//                    if (e.equals("Semeler, Alexandre"))
//                            System.out.println("STOP!");
                    doc.addAuthor(e);
                }
            }
        }

        try {
            //System.out.println("Trying to save");
            //Transaction t = getSession().beginTransaction();


            getSession().save(doc);
            for (Objeto o : doc.getObjetos()) {
                //System.out.println("Saving object");

                getSession().save(o);
            }

            //getSession().flush();
            //t.commit();


            //session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);

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
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override 
    public void flush() {
    	getSession().flush();
    }

}
