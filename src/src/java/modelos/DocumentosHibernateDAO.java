package modelos;

import cognitivabrasil.obaa.OBAA;
import java.util.List;
import metadata.Header;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class DocumentosHibernateDAO.
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class DocumentosHibernateDAO implements DocumentosDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private static Logger log = Logger.getLogger(DocumentosHibernateDAO.class.getName());

    /*
     * repository where new documents are going to be saved
     */
    private SubNodo repository;
    private SubFederacao federation;

    /**
     * Gets a List of Documents by obaa entry. Note that in theory it shouldn't
     * be possible to have 2 objects with the same OBAAEntry but in some bizarre
     * circumstances it might happen.
     *
     * @param e the ObaaEntry
     * @return List of documentos with this obaaEntry
     */
    @SuppressWarnings("unchecked")
    private List<DocumentoReal> getByObaaEntry(String e) {
        return getSession().createCriteria(DocumentoReal.class).add(Restrictions.eq("obaaEntry", e)).list();

    }

    /*
     * (non-Javadoc)
     *
     * @see modelos.DocumentosDAO#get(java.lang.String)
     */
    @Override
    public DocumentoReal get(String e) {
        return (DocumentoReal) getSession().createQuery(
                "from DocumentoReal as doc where doc.obaaEntry = ?").setString(0, e).uniqueResult();

    }

    @Override
    public DocumentoReal get(int i) {
        return (DocumentoReal) getSession().get(DocumentoReal.class, i);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentoReal> getAll() {
        return getSession().createCriteria(DocumentoReal.class).list();
    }

    @Override
    public void deleteByObaaEntry(String e) {
        for (DocumentoReal d : getByObaaEntry(e)) {
            log.trace("DeleteByObaaEntry: " + e);
            delete(d);
        }
    }

    @Override
    public void delete(DocumentoReal d) {
        getSession().delete(d);
    }

    /*
     * (non-Javadoc)
     *
     * @see modelos.DocumentosDAO#save(OBAA.OBAA, metadata.Header)
     */
    @Override
    public void save(OBAA obaa, Header h) throws IllegalStateException {
        DocumentoReal doc = new DocumentoReal();
        doc.setDeleted(false);
        log.trace("Going to create documento " + h.getIdentifier());

        if ((getRepository() == null) && (this.federation == null)) {
            throw new IllegalStateException("Have to set repository or federation before calling save.");
        } else if (getRepository() == null) {
            log.debug("Armazenando objeto do tipo RepositorioSubFed");
            RepositorioSubFed repSubFed = this.federation.getRepositoryByName(h.getSetSpec().get(0));
            if (repSubFed == null) {
                throw new IllegalStateException("The repository '" + h.getSetSpec().get(0) + "' doesn't exists in the federation '" + this.federation.getNome() + "'");
            } else {
                doc.setRepositorioSubFed(repSubFed); //pega o nome do repositorio do cabeçalho e busca o objeto pelo nome inserindo no doc.
            }
        } else if (getRepository().getClass().equals(Repositorio.class)) {
            log.debug("Armazenando objeto do tipo Repositorio");
            Repositorio r = (Repositorio) getRepository();
            doc.setRepositorio(r);
        }

        doc.setObaaEntry(h.getIdentifier());
        doc.setTimestamp(h.getTimestamp());

        if (h.isDeleted()) {
            doc.setDeleted(true);
            deleteByObaaEntry(doc.getObaaEntry());
        } else {
            doc.setMetadata(obaa);
        }

        try {
            getSession().save(doc);
            for (Objeto o : doc.getObjetos()) {
                getSession().save(o);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);

        }
    }

    @Override
    public SubNodo getRepository() {
        return repository;
    }

    @Override
    public void setRepository(SubNodo repository) {
        if (repository == null) {
            throw new IllegalArgumentException("called setRepository() with a null argument.");
        }
        this.repository = repository;
    }

    @Override
    public void setFederation(SubFederacao federation) {
        this.federation = federation;
    }

    /**
     * Gets the session.
     *
     * @return the session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public List<DocumentoReal> getwithoutToken(){
        //retorna todos documentos que nao possuem r1tokens preenchido
        String sql = "SELECT d.* FROM documentos d LEFT JOIN r1tokens r1t ON r1t.documento_id = d.id WHERE r1t.documento_id IS NULL AND d.deleted = FALSE";

        return getSession().createSQLQuery(sql).addEntity(DocumentoReal.class).list();
    }
}