package feb.data.entities;

import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.style.ToStringCreator;
import org.springframework.dao.support.DataAccessUtils;

import feb.spring.ApplicationContextProvider;

/**
 *
 * @author Marcos
 */
public class RepositorioSubFed implements SubNodo{

    private int id;
    private String name;
    private SubFederacao subFederacao;
    private Set<DocumentoReal> documentos;
    SessionFactory sessionFactory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public SubFederacao getSubFederacao() {
        return subFederacao;
    }

    public void setSubFederacao(SubFederacao subFederacao) {
        this.subFederacao = subFederacao;
    }

    public Integer getSize() {

        return DataAccessUtils.intResult(
                getSessionFactory().getCurrentSession().
                createQuery("select count(*) from DocumentoReal doc WHERE doc.repositorioSubFed = :rep AND doc.deleted = :deleted").
                setParameter("rep", this).setParameter("deleted", false).list());
  
    }
    
    @Override
    public Integer getVisits() {
        return DataAccessUtils.intResult(getSessionFactory().getCurrentSession().
                createQuery("SELECT COUNT(*) FROM DocumentosVisitas dv, DocumentoReal d WHERE d.id=dv.documento AND d.repositorioSubFed = :rep;").setParameter("rep", this).list());
    }
    
    private SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
            if (ctx != null) {
                //TODO:
                sessionFactory = ctx.getBean(SessionFactory.class);
            } else {
                throw new IllegalStateException("Could not get Application context");
            }
        }
        return sessionFactory;
    }

    /**
     * @return the documentos
     */
    public Set<DocumentoReal> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(Set<DocumentoReal> documentos) {
        this.documentos = documentos;
    }
    
        
    /**
     * Delete all DocumentoReal from this Repositorio. 
     * 
     * This is mainly used to reset a repository, e.g., when the user manually
     * chooses to do so in the interface.
     *
     * @return number os rows affected
     * 
     */
    public int dellAllDocs() {
        //TODO: nao teria só que trocar o deleted pra true? Pq ta deletando tudo da base.
        String hql = "delete from DocumentoReal as d WHERE d.repositorioSubFed = :rep";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter("rep", this);
        return query.executeUpdate();
    }
    
    @Override
    public boolean equals(Object rsf){
        if(rsf == null || !rsf.getClass().equals(RepositorioSubFed.class))
            return false;
        else {
        	return this.getName().equals( ((RepositorioSubFed)rsf).getName());
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", this.getId()).append("nome", this.getName()).toString();
    }    
}