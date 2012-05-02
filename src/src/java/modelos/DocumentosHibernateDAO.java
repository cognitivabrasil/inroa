/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;

import metadata.Header;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import OBAA.OBAA;
import OBAA.LifeCycle.Contribute;

/**
 * The Class DocumentosHibernateDAO.
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
public class DocumentosHibernateDAO implements DocumentosDAO {

	@Autowired
	SessionFactory sessionFactory;
	Logger log = Logger.getLogger(DocumentosHibernateDAO.class.getName());

	/* repository where new documents are going to be saved */
	private Repositorio repository;

	/**
	 * Gets a List of Documents by obaa entry. Note that in theory it shouldn't
	 * be possible to have 2 objects with the same OBAAEntry but in some bizarre
	 * circumstances it might happen.
	 * 
	 * @param e
	 *            the ObaaEntry
	 * @return List of documentos with this obaaEntry
	 */
	@SuppressWarnings("unchecked")
	private List<DocumentoReal> getByObaaEntry(String e) {
		return getSession().createCriteria(DocumentoReal.class)
				.add(Restrictions.eq("obaaEntry", e)).list();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelos.DocumentosDAO#get(java.lang.String)
	 */
	@Override
	public DocumentoReal get(String e) {
		return (DocumentoReal) getSession()
				.createQuery(
						"from DocumentoReal as doc where doc.obaaEntry = ?")
				.setString(0, e).uniqueResult();

	}

	@Override
	public DocumentoReal get(int i) {
		return (DocumentoReal) getSession().get(DocumentoReal.class, i);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoReal> getAll() {
		return getSession().createQuery("from DocumentoReal").list();
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
	public void save(OBAA obaa, Header h) {
		DocumentoReal doc = new DocumentoReal();
		doc.setDeleted(false);
		log.trace("Going to create documento " + h.getIdentifier());

		Repositorio r = getRepository();
		doc.setRepositorio(r);
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
	public Repositorio getRepository() {
		return repository;
	}

	@Override
	public void setRepository(Repositorio repository) {
		this.repository = repository;
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

}
