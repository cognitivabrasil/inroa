// Generated 20/07/2011 15:25:15 by Hibernate Tools 3.2.0.b9
package com.cognitivabrasil.feb.data.entities;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Transactional;

import feb.data.interfaces.FebDomainObject;
import feb.spring.ApplicationContextProvider;
import feb.util.Operacoes;
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
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Paulo
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 *
 */
@Transactional
@Entity
@Table(name = "repositorios")
public class Repositorio implements java.io.Serializable, SubNodo, FebDomainObject {

    private static final Logger log = Logger.getLogger(Repositorio.class);
    private static final long serialVersionUID = 1011292251690153763L;
    private Integer id;
    private String name;
    private String descricao;
    private String url;
    private String metadataPrefix;
    private transient Set<DocumentoReal> documentos;
    @DateTimeFormat(style = "M-")
    private DateTime ultimaAtualizacao;
    private String namespace;
    private Integer periodicidadeAtualizacao;
    private String colecoesInternal;
    private PadraoMetadados padraoMetadados;
    private Mapeamento mapeamento;
    private transient SessionFactory sessionFactory;
    private transient Session session;
    private Date dataOrigem;
    private Date dataOrigemTemp;

    public Repositorio() {
        id = null;
        name = "";
        descricao = "";
        url = "";
        metadataPrefix = "";
        documentos = new HashSet<>(0);
        ultimaAtualizacao = null;
        dataOrigem = null;
        namespace = "";
        periodicidadeAtualizacao = 1;
        colecoesInternal = "";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    @Override
    @Column(name = "nome")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String nome) {
        this.name = nome;
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
    @OneToMany(mappedBy = "repositorio", cascade = CascadeType.ALL, orphanRemoval = true)
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
        return getName();
    }

    /**
     *
     * @return Number of documents present in the repository (non-deleted
     * documents only)
     */
    @Transactional(readOnly = true)
    @Override
    @Transient
    //TODO: mover isso aqui para o Service
    public Integer getSize() {
        return DataAccessUtils.intResult(getSession().createQuery(
                "select count(*) from DocumentoReal doc WHERE doc.repositorio = :rep AND doc.deleted = :deleted").setParameter("rep", this).setParameter("deleted", false).list());
    }

    @Override
    @Transient
    public Integer getVisits() {
        //TODO: mover isso aqui para o Service
        return DataAccessUtils.intResult(getSession().createQuery(
                "SELECT COUNT(*) FROM DocumentosVisitas dv, DocumentoReal d WHERE d.id=dv.documento AND d.repositorio= :rep").setParameter("rep", this).list());
    }

    /**
     * Delete all DocumentoReal from this Repositorio
     *
     * @return Rows affected
     *
     */
    public int dellAllDocs() {
        //TODO: mover isso aqui para o Service
        String hql = "delete from DocumentoReal as d WHERE d.repositorio = :rep";
        Query query = getSession().createQuery(hql);
        query.setParameter("rep", this);
        return query.executeUpdate();
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
    @Column(name = "url_or_ip")
    public String getUrl() {
        return url;
    }

    /**
     * @return the metadataPrefix
     */
    @Column(name = "metadata_prefix")
    public String getMetadataPrefix() {
        return metadataPrefix;
    }
    
    public void setUltimaAtualizacao(DateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
        if (this.dataOrigemTemp != null) {
            this.dataOrigem = this.dataOrigemTemp;
        }
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "data_ultima_atualizacao")
    public DateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }
    
    /**
     * Retorna a data da &uacute;ltima atualizaç&atilde;o formatada. Se o
     * reposit&oacute;rio n&atilde;o tiver uma url associada ele informa que
     * n&atilde;o foi informado um endere&ccedil;o para
     * sincroniza&ccedil;&atilde;o.
     *
     * @return String contendo a data neste formato: Dia "x" &agrave;s "y"
     */
    @Transient
    public String getUltimaAtualizacaoFormatada() {
        return Operacoes.ultimaAtualizacaoFrase(getUltimaAtualizacao(),
                getUrl());
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data_xml")
    public Date getDataOrigem() {
        return dataOrigem;
    }

    public void setDataOrigem(Date dataOrigem) {
        this.dataOrigem = dataOrigem;
        this.dataOrigemTemp = null;
    }

    @Column(name = "name_space")
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    // TODO ver o que precisa para modificar na base para dias e nao horas.
    @Column(name = "periodicidade_horas")
    public Integer getPeriodicidadeAtualizacao() {
        return periodicidadeAtualizacao;
    }

    public void setPeriodicidadeAtualizacao(Integer periodicidadeAtualizacao) {
        this.periodicidadeAtualizacao = periodicidadeAtualizacao;
    }

    @Transient
    public Date getProximaAtualizacao() {
        if (ultimaAtualizacao == null) {
            return null;
        } else {
            return ultimaAtualizacao.plusDays(1).toDate(); // soma a periodicidade
        }
    }

    @Transient
    public String getProximaAtualizacaoFormatada() {
        return Operacoes.ultimaAtualizacaoFrase(getProximaAtualizacao(), getUrl());
    }

    /**
     * Test if repository is outdated.
     *
     * @return true if the repository is outdated or false if the repository is
     * updated.
     */
    @Transient
    public boolean getIsOutdated() {
        return getProximaAtualizacao() == null || getProximaAtualizacao().before(new Date());
    }

    /**
     *
     * @return The collections associated with this repository
     */
    @Transient
    public Set<String> getColecoes() {
        if (colecoesInternal.isEmpty()) {
            return new HashSet<>();
        } else {
            return new HashSet<>(Arrays.asList(colecoesInternal.split(";")));
        }
    }

    /**
     * Sets collections associated with this repository
     *
     * @param c The collections
     */
    public void setColecoes(Collection<String> c) {
        if (c.isEmpty()) {
            this.colecoesInternal = "";
        } else {
            this.colecoesInternal = StringUtils.join(c, ";");
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
    @ManyToOne
    @JoinColumn(name = "padrao_metadados")
    public PadraoMetadados getPadraoMetadados() {
        return padraoMetadados;
    }

    /**
     * @param padraoMetadados the padraoMetadados to set
     */
    public void setPadraoMetadados(PadraoMetadados padraoMetadados) {
        this.padraoMetadados = padraoMetadados;
    }

    @ManyToOne
    @JoinColumn(name = "mapeamento_id")
    public Mapeamento getMapeamento() {
        return mapeamento;
    }

    public void setMapeamento(Mapeamento mapeamento) {
        this.mapeamento = mapeamento;
    }

    /**
     * Just for hibernate
     */
    @Column(name = "internal_set")
    protected String getColecoesInternal() {
        return colecoesInternal;
    }

    private boolean notBlank(String s) {
        return s != null && !(s.equals(""));
    }

    private boolean notBlank(Set s) {
        return s != null && !(s.isEmpty());
    }

    /**
     * Updates the repository with the same with the data in r2 safely, ignoring
     * null and blank values
     *
     * It does NOT merge the Documents.
     *
     * @param r2 A repository that we want to update.
     * @throws IllegalArgumentException If the ids dont match.
     */
    public void merge(Repositorio r2) {

        if (r2.getId() != null && !(r2.getId().equals(getId()))) {
            throw new IllegalArgumentException(
                    "Merge must not be used on repositories with different Ids");
        }

        if (notBlank(r2.getDescricao())) {
            setDescricao(r2.getDescricao());
        }
        if (notBlank(r2.getMetadataPrefix())) {
            setMetadataPrefix(r2.getMetadataPrefix());
        }
        if (notBlank(r2.getNamespace())) {
            setNamespace(r2.getNamespace());
        }
        if (notBlank(r2.getName())) {
            setName(r2.getName());
        }
        if (notBlank(r2.getUrl())) {
            setUrl(r2.getUrl());
        }
        if (notBlank(r2.getColecoes())) {
            setColecoes(r2.getColecoes());
        }
        if (r2.getPadraoMetadados() != null) {
            setPadraoMetadados(r2.getPadraoMetadados());
        }
        if (r2.getMapeamento() != null) {
            setMapeamento(r2.getMapeamento());
        }

    }

    /**
     * Just for Hibernate
     */
    protected void setColecoesInternal(String colecoesInternal) {
        this.colecoesInternal = colecoesInternal;
    }

    @Deprecated
    @Transient
    public String getColecoesString() {
        return getColecoesInternal();
    }

    @Deprecated
    public void setColecoesString(String s) {
        setColecoesInternal(s);
    }

    /**
     * @return the sessionFactory
     */
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
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
                log.warn("Could not getCurrentSession()!", e);
                session = getSessionFactory().openSession();
            }
        }
        return session;
    }

    @Override
    public String toStringDetailed() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
    }
}