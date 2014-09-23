package com.cognitivabrasil.feb.data.entities;

import com.cognitivabrasil.feb.data.interfaces.FebDomainObject;
import com.cognitivabrasil.feb.util.Operacoes;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Paulo
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 *
 */
@Transactional
@Entity
@Table(name = "repositorios")
public class Repositorio implements java.io.Serializable, FebDomainObject {

    private static final long serialVersionUID = 1011292251690153763L;
    private Integer id;
    private String name;
    private String descricao;
    private String url;
    private String metadataPrefix;
    private transient Set<Document> documentos;
    @DateTimeFormat(style = "M-")
    private DateTime ultimaAtualizacao;
    private String namespace;
    private String colecoesInternal;
    private PadraoMetadados padraoMetadados;
    private Mapeamento mapeamento;
    private String dataOrigem;
    private String dataOrigemTemp;

    public Repositorio() {
        name = "";
        descricao = "";
        url = "";
        metadataPrefix = "";
        documentos = new HashSet<>(0);
        namespace = "";
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
    @OneToMany(mappedBy = "repositorio", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Document> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(Set<Document> documentos) {
        this.documentos = documentos;
    }

    @Override
    public String toString() {
        return getName();
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

    /**
     * Define a data que foi realizada a última atualização. Neste método é copiada a data de {@link #dataOrigemTemp}
     * para {@link #dataOrigem}.
     *
     * @param ultimaAtualizacao
     */
    public void setUltimaAtualizacao(DateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
        if (this.dataOrigemTemp != null) {
            setDataOrigem(getDataOrigemTemp());
        }
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "data_ultima_atualizacao")
    public DateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    /**
     * Retorna a data da &uacute;ltima atualizaç&atilde;o formatada. Se o reposit&oacute;rio n&atilde;o tiver uma url
     * associada ele informa que n&atilde;o foi informado um endere&ccedil;o para sincroniza&ccedil;&atilde;o.
     *
     * @return String contendo a data neste formato: Dia "x" &agrave;s "y"
     */
    @Transient
    public String getUltimaAtualizacaoFormatada() {
        return Operacoes.ultimaAtualizacaoFrase(getUltimaAtualizacao(),
                getUrl());
    }

    @Column(name = "data_xml")
    public String getDataOrigem() {
        return dataOrigem;
    }

    public void setDataOrigem(String dataOrigem) {
        this.dataOrigem = dataOrigem;
        this.dataOrigemTemp = null;
    }

    @Transient
    public String getDataOrigemTemp() {
        return dataOrigemTemp;
    }

    public void setDataOrigemTemp(String dataOrigemTemp) {
        this.dataOrigemTemp = dataOrigemTemp;
    }

    @Column(name = "name_space")
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Transient
    public Date getProximaAtualizacao() {
        if (ultimaAtualizacao == null) {
            return null;
        } else {
            // soma a periodicidade
            return ultimaAtualizacao.plusDays(1).toDate();
        }
    }

    @Transient
    public String getProximaAtualizacaoFormatada() {
        return Operacoes.ultimaAtualizacaoFrase(getProximaAtualizacao(), getUrl());
    }

    /**
     * Test if repository is outdated.
     *
     * @return true if the repository is outdated or false if the repository is updated.
     */
    @Transient
    public boolean getIsOutdated() {
        //TODO: adicionar algumas horas no new Date para garantir que será atualizado mesmo que tenha demorado na última vez.s
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
     * Updates the repository with the same with the data in r2 safely, ignoring null and blank values
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

    @Override
    public String toStringDetailed() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
    }
}
