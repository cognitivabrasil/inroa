package feb.data.daos;

import cognitivabrasil.obaa.OBAA;
import java.util.List;
import metadata.Header;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import feb.data.entities.DocumentoReal;
import feb.data.entities.Objeto;
import feb.data.entities.Repositorio;
import feb.data.entities.RepositorioSubFed;
import feb.data.entities.SubFederacao;
import feb.data.entities.SubNodo;
import feb.data.interfaces.DocumentosDAO;
import feb.data.interfaces.TokensDao;
import java.lang.String;
import org.hibernate.Criteria;
import org.springframework.dao.support.DataAccessUtils;

import com.springsource.insight.annotation.InsightEndPoint;
import com.springsource.insight.annotation.InsightOperation;

/**
 * The Class DocumentosHibernateDAO.
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class DocumentosHibernateDAO implements DocumentosDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TokensDao tokenDao;
	private static Logger log = Logger.getLogger(DocumentosHibernateDAO.class
			.getName());

	/*
	 * repository where new documents are going to be saved
	 */
	private SubNodo repository;
	private SubFederacao federation;

	/**
	 * Gets a List of Documents by obaa entry.
	 * 
	 * @param e
	 *            the ObaaEntry
	 * @return List of documentos with this obaaEntry
	 */
	@SuppressWarnings("unchecked")
	private DocumentoReal getByObaaEntry(String e) {
		return (DocumentoReal) getSession().createCriteria(DocumentoReal.class)
				.add(Restrictions.eq("obaaEntry", e)).uniqueResult();

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
		return getSession().createCriteria(DocumentoReal.class).list();
	}

	@Override
	@InsightOperation(label = "deleteByObaaEntry() in DocumentosHibernateDao")
	@InsightEndPoint(label = "deleteByObaaEntry() in DocumentosHibernateDao")
	public void deleteByObaaEntry(String e) {
		DocumentoReal d = getByObaaEntry(e);
		if (d != null) {
			delete(d);
		}
	}

	@Override
	@InsightOperation(label = "delete() in DocumentosHibernateDao")
	@InsightEndPoint(label = "delete() in DocumentosHibernateDao")
	public void delete(DocumentoReal d) {
		getSession().delete(d);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelos.DocumentosDAO#save(OBAA.OBAA, metadata.Header)
	 */
	@Override
	@InsightOperation(label = "save() in DocumentosHibernateDao")
	@InsightEndPoint(label = "save() in DocumentosHibernateDao")
	public void save(OBAA obaa, Header h) throws IllegalStateException {

		DocumentoReal doc = new DocumentoReal();
		doc.setDeleted(false);
		log.trace("Going to create documento " + h.getIdentifier());

		if ((getRepository() == null) && (this.federation == null)) {
			throw new IllegalStateException(
					"Have to set repository or federation before calling save.");
		} else if (getRepository() == null) {
			log.debug("Armazenando objeto do tipo RepositorioSubFed");
			RepositorioSubFed repSubFed = this.federation.getRepositoryByName(h
					.getSetSpec().get(0));
			if (repSubFed == null) {
				throw new IllegalStateException("The repository '"
						+ h.getSetSpec().get(0)
						+ "' doesn't exists in the federation '"
						+ this.federation.getName() + "'");
			} else {
				doc.setRepositorioSubFed(repSubFed); // pega o nome do
														// repositorio do
														// cabeçalho e busca o
														// objeto pelo nome
														// inserindo no doc.
			}
		} else if (getRepository().getClass().equals(Repositorio.class)) {
			log.debug("Armazenando objeto do tipo Repositorio");
			Repositorio r = (Repositorio) getRepository();
			doc.setRepositorio(r);
		}

		doc.setObaaEntry(h.getIdentifier());
		
		DocumentoReal real = getWithoutId(doc);

		real.setTimestamp(h.getTimestamp());

		if (h.isDeleted()) {
			real.setDeleted(true);
			real.setMetadata(null);
			
			try {
				getSession().save(real);
				real.deleteDependencies(); // deletes Objetos, Autores, Tokens
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);

			}
		} else {
			real.setMetadata(obaa);
			log.debug("Tokenizando o documento");
			real.generateTokens();
			
			try {
				getSession().save(real);
//				for (Objeto o : doc.getObjetos()) {
//					getSession().save(o);
//				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);

			}
		}





	}

	/**
	 * Deletes a DocumentReal by obaaEntry and (sub)rep id.
	 * 
	 * This used because of the very unfortunate fact that obaaEntries may not
	 * be unique.
	 * 
	 * @param doc
	 */
	private void deleteWithoutId(DocumentoReal doc) {
		DocumentoReal d;
		if (doc.getRepositorio() != null) {
			d = (DocumentoReal) getSession()
					.createCriteria(DocumentoReal.class)
					.add(Restrictions.eq("repositorio", doc.getRepositorio()))
					.add(Restrictions.eq("obaaEntry", doc.getObaaEntry()))
					.uniqueResult();
		} else if (doc.getRepositorioSubFed() != null) {
			d = (DocumentoReal) getSession()
					.createCriteria(DocumentoReal.class)
					.add(Restrictions.eq("repositorioSubFed",
							doc.getRepositorioSubFed()))
					.add(Restrictions.eq("obaaEntry", doc.getObaaEntry()))
					.uniqueResult();
		} else {
			return;
		}
		if (d != null) {
			delete(d);
		}
	}
	

	private DocumentoReal getWithoutId(DocumentoReal doc) {
		DocumentoReal d;
		if (doc.getRepositorio() != null) {
			d = (DocumentoReal) getSession()
					.createCriteria(DocumentoReal.class)
					.add(Restrictions.eq("repositorio", doc.getRepositorio()))
					.add(Restrictions.eq("obaaEntry", doc.getObaaEntry()))
					.uniqueResult();
		} else if (doc.getRepositorioSubFed() != null) {
			d = (DocumentoReal) getSession()
					.createCriteria(DocumentoReal.class)
					.add(Restrictions.eq("repositorioSubFed",
							doc.getRepositorioSubFed()))
					.add(Restrictions.eq("obaaEntry", doc.getObaaEntry()))
					.uniqueResult();
		} else {
			return doc;
		}
		if (d != null) {
			return d;
		}
		else {
			return doc;
		}
	}

	@Override
	public SubNodo getRepository() {
		return repository;
	}

	@Override
	public void setRepository(SubNodo repository) {
		// if (repository == null) {
		// throw new
		// IllegalArgumentException("called setRepository() with a null argument.");
		// }
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
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void flush() {
		getSession().flush();
	}

	@Override
	public List<DocumentoReal> getwithoutToken() {
		// retorna todos documentos que nao possuem r1tokens preenchido
		String sql = "SELECT d.* FROM documentos d LEFT JOIN r1tokens r1t ON r1t.documento_id = d.id WHERE r1t.documento_id IS NULL AND d.deleted = FALSE";

		return getSession().createSQLQuery(sql).addEntity(DocumentoReal.class)
				.list();
	}
}