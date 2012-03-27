/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Set;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
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
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        HibernateTemplate template = ctx.getBean(HibernateTemplate.class);

        return DataAccessUtils.intResult(
                template.find("select count(*) from DocumentoReal doc WHERE doc.repositorioSubFed = ? AND doc.deleted = ?", this, false));
  
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
