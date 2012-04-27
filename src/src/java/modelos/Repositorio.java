// Generated 20/07/2011 15:25:15 by Hibernate Tools 3.2.0.b9
package modelos;

import java.util.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.support.DataAccessUtils;
import spring.ApplicationContextProvider;

/**
 *
 * @author Marcos, Paulo
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
    private Date dataOrigem;

    public Repositorio() {
//        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
//        if(ctx != null) {
//            //TODO:
//            sessionFactory = ctx.getBean(SessionFactory.class);
//        }
        id = null;
        nome = "";
        descricao = "";
        url = "";
        metadataPrefix = "";
        documentos = new HashSet<DocumentoReal>(0);
        ultimaAtualizacao = new Date(0);
        dataOrigem = new Date(0);
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
     * Delete all DocumentoReal from this Repositorio
     *
     * @return Rows affected
     * 
     */
    public int dellAllDocs() {
        String hql = "delete from DocumentoReal as d WHERE d.repositorio = :rep";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter("rep", this);
        return query.executeUpdate();
    }

    public List<Repositorio> getOutDatedDocs() {
        //TODO: Jorjão ve se tu consegue fazer isso com linguagem do hibernate. Nao tem que ficar em RepositoryHibernateDAO?
        //o incremento de 4 horas é para descontar da periodicidade da ultima atualizacao. Pq se o robo rodou ontem as 02h e atualizou o repositorio as 02:10h hoje quando rodar novamente nao vai atualiza pq nao faz 24h ainda.
        return getSessionFactory().getCurrentSession().createSQLQuery("SELECT * FROM repositorios r WHERE r.data_ultima_atualizacao <= ((now() - r.periodicidade_horas * INTERVAL '1 DAY') + INTERVAL '4 HOUR')").addEntity(Repositorio.class).list();
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

    public Date getDataOrigem() {
        return dataOrigem;
    }

    public void setDataOrigem(Date dataOrigem) {
        this.dataOrigem = dataOrigem;
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
        if (ultimaAtualizacao == null) { //TODO: Jorjao porque ta dando null se no construtor ta inicializando?
            return new Date(0);
        }
        return new Date(ultimaAtualizacao.getTime() + periodicidadeAtualizacao * 24 * 60 * 60 * 1000); // soma a periodicidade
    }

    /**
     * Test if repository is outdated.
     * @return true if the repository is outdated or false if the repository is updated.
     */
    public boolean isOutdated() {
        if (getProximaAtualizacao().before(new Date())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return The collections associated with this repository
     */
    public Set<String> getColecoes() {
        if (colecoesInternal.isEmpty()) {
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
        if (c.isEmpty()) {
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
            throw new IllegalArgumentException("Merge must not be used on repositories with different Ids");
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
        if (notBlank(r2.getNome())) {
            setNome(r2.getNome());
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
    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
            if (ctx != null) {
                //TODO:
                sessionFactory = ctx.getBean(SessionFactory.class);
            } else {
                throw new IllegalStateException("FEB ERRO: Could not get Application context");
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
