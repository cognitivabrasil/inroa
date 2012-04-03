// Generated 20/07/2011 15:25:15 by Hibernate Tools 3.2.0.b9
package modelos;

import java.util.*;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.support.DataAccessUtils;
import spring.ApplicationContextProvider;

/**
 *
 * Represents a single playable track in the music database.
 *
 * @author Jim Elliott (with help from Hibernate)
 *
 */
public class Repositorio implements java.io.Serializable {

    private Integer id;
    private String nome;
    private String descricao;
    private String url;
    private String metadataPrefix;
    private Set<DocumentoReal> documentos;
    private Date ultimaAtualizacao;
    private String namespace;
    private Integer periodicidadeAtualizacao;
    private String colecoesInternal;
    private PadraoMetadados padraoMetadados;
    private Mapeamento mapeamento;
    private SessionFactory sessionFactory;

    public Repositorio() {
//        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
//        if(ctx != null) {
//            //TODO:
//            sessionFactory = ctx.getBean(SessionFactory.class);
//        }
        id = 0;
        nome = "";
        descricao = "";
        url = "";
        metadataPrefix = "";
        documentos = new HashSet<DocumentoReal>(0);
        ultimaAtualizacao = null;
        namespace = "";
        periodicidadeAtualizacao = 1;
        colecoesInternal = "";
    }

    static String join(Collection<?> s, String delimiter) {
        StringBuilder builder = new StringBuilder();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next());
            if (!iter.hasNext()) {
                break;
            }
            builder.append(delimiter);
        }
        return builder.toString();
    }

    public Integer getId() {
        return this.id;
    }

    //TODO: porque esse metodo estava protected?
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    @Override
    public String toString() {
        return getNome();
    }

    /**
     *
     * @return Number of documents present in the repository (non-deleted
     * documents only)
     */
    public Integer getSize() {
        return DataAccessUtils.intResult(
                getSessionFactory().getCurrentSession().createQuery("select count(*) from DocumentoReal doc WHERE doc.repositorio = :rep AND doc.deleted = :deleted").
                setParameter("rep", this).setParameter("deleted", false).list());
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param metadataPrefix the metadataPrefix to set
     */
    public void setMetadataPrefix(String metadataPrefix) {
        this.metadataPrefix = metadataPrefix;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the metadataPrefix
     */
    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    //TODO ver o que precisa para modificar na base para dias e nao horas.
    public Integer getPeriodicidadeAtualizacao() {
        return periodicidadeAtualizacao;
    }

    public void setPeriodicidadeAtualizacao(Integer periodicidadeAtualizacao) {
        this.periodicidadeAtualizacao = periodicidadeAtualizacao;
    }

    public Date getProximaAtualizacao() {
        return new Date(ultimaAtualizacao.getTime() + periodicidadeAtualizacao * 60 * 60 * 1000); // soma a periodicidade em horas
    }

    /**
     *
     * @return The collections associated with this repository
     */
    public Set<String> getColecoes() {
        if (colecoesInternal == "") {
            return new HashSet<String>();
        } else {
            return new HashSet<String>(Arrays.asList(colecoesInternal.split(";")));
        }
    }

    /**
     * Sets collections associated with this repository
     *
     * @param c The collections
     */
    public void setColecoes(Collection<String> c) {
        if (c.size() == 0) {
            this.colecoesInternal = "";
        } else {
            this.colecoesInternal = join(c, ";");
        }
    }

    /**
     * Adds a collection to the repositoru
     *
     * @param colecao Collection to add
     */
    public void addColecao(String colecao) {
        Set<String> c = getColecoes();
        c.add(colecao);
        setColecoes(c);
    }

    /**
     * @return the padraoMetadados
     */
    public PadraoMetadados getPadraoMetadados() {
        return padraoMetadados;
    }

    /**
     * @param padraoMetadados the padraoMetadados to set
     */
    public void setPadraoMetadados(PadraoMetadados padraoMetadados) {
        this.padraoMetadados = padraoMetadados;
    }

    public Mapeamento getMapeamento() {
        return mapeamento;
    }

    public void setMapeamento(Mapeamento mapeamento) {
        this.mapeamento = mapeamento;
    }

    /**
     * Just for hibernate
     */
    protected String getColecoesInternal() {
        return colecoesInternal;
    }

    /**
     * Just for Hibernate
     */
    protected void setColecoesInternal(String colecoesInternal) {
        this.colecoesInternal = colecoesInternal;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
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
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
