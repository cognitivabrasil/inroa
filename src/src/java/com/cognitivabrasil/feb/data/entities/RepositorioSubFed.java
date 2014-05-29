package com.cognitivabrasil.feb.data.entities;

import com.cognitivabrasil.feb.spring.ApplicationContextProvider;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.style.ToStringCreator;
import org.springframework.dao.support.DataAccessUtils;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "repositorios_subfed")
public class RepositorioSubFed implements SubNodo{

    private int id;
    private String name;
    private SubFederacao subFederacao;
    private Set<Document> documentos;
    private transient SessionFactory sessionFactory;
    private transient Session session;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @Column(name = "nome")
    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    @ManyToOne
    @JoinColumn(name = "id_subfed")
    public SubFederacao getSubFederacao() {
        return subFederacao;
    }

    public void setSubFederacao(SubFederacao subFederacao) {
        this.subFederacao = subFederacao;
    }

    @Transient
    @Override
    public Integer getSize() {
        return DataAccessUtils.intResult(getSession().createQuery("select count(*) from Document doc WHERE doc.repositorioSubFed = :rep AND doc.deleted = :deleted").
                setParameter("rep", this).setParameter("deleted", false).list());
    }

    
    /**
     * @return the session
     */
    @Transient
    private Session getSession() {
        if (session == null) {
            try {
                session = getSessionFactory().getCurrentSession();
            } catch (HibernateException e) {
                session = getSessionFactory().openSession();
            }
        }
        return session;
    }
    
    @Transient
    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
            if (ctx != null) {
                // TODO:
                sessionFactory = ctx.getBean(SessionFactory.class);
            } else {
                throw new IllegalStateException(
                        "FEB ERRO: Could not get Application context");
            }
        }
        return sessionFactory;
    }

    /**
     * @return the documentos
     */
    
    @OneToMany(mappedBy = "repositorioSubFed", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Document> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(Set<Document> documentos) {
        this.documentos = documentos;
    }
    
        
    /**
     * Delete all Document from this Repositorio. 
     * 
     * This is mainly used to reset a repository, e.g., when the user manually
     * chooses to do so in the interface.
     *
     * @return number os rows affected
     * 
     */
    public int dellAllDocs() {
        //TODO: nao teria só que trocar o deleted pra true? Pq ta deletando tudo da base.
        String hql = "delete from Document as d WHERE d.repositorioSubFed = :rep";
        Query query = getSession().createQuery(hql);
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