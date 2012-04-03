/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Set;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.support.DataAccessUtils;
import spring.ApplicationContextProvider;

/**
 *
 * @author Marcos
 */
public class RepositorioSubFed {

    private int id;
    private String nome;
    private SubFederacao subFederacao;
    private Set<DocumentoReal> documentos;
    SessionFactory sessionFactory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
     * @param sessionFactory the sessionFactory to set
     */
    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
}
