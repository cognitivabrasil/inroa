package feb.data.entities;

import ORG.oclc.oai.models.HibernateOaiDocument;
import cognitivabrasil.obaa.LifeCycle.Contribute;
import cognitivabrasil.obaa.OBAA;
import feb.data.interfaces.DocumentoFebInterface;
import feb.data.interfaces.StopWordsDao;
import feb.spring.ApplicationContextProvider;
import feb.util.Operacoes;
import java.util.*;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.util.StopWatch;

/**
 *
 * Representa um documento na Federação;
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Luiz Rossi <lh.rossi@gmail.com>
 *
 */
public class DocumentoReal implements java.io.Serializable,
        DocumentoFebInterface, HibernateOaiDocument {

    static Logger log = Logger.getLogger(DocumentoReal.class);
    private static final long serialVersionUID = 61217365141633065L;
    private int id;
    private String obaaEntry;
    private Date datetime;
    private Set<Objeto> objetos;
    private Repositorio repositorio;
    private RepositorioSubFed repositorioSubFed;
    private Boolean deleted;
    private String obaaXml;
    private OBAA metadata;
    private Set<Autor> autores;
    private Set<Token> tokens;
    private String language;
    List<String> titleTokens;
    List<String> keywordTokens;
    List<String> descriptionTokens;
    private Session session;
    private SessionFactory sessionFactory;
    private static List<String> stopWords;
    private static StopWordsDao stopWordDao;

    public DocumentoReal() {
        obaaEntry = "";
        datetime = new Date(0);
        objetos = new HashSet<Objeto>();
        tokens = new HashSet<Token>();
        deleted = false;
        autores = new HashSet<Autor>();
        language = "pt-BR";
    }

    public void addTitle(String title) {
        Objeto o = new Objeto();
        o.setAtributo("obaaTitle");
        o.setValor(title);
        o.setDocumento(this);
        objetos.add(o);
    }

    public void addAuthor(String author) {
        Autor a = new Autor();
        a.setNome(author);
        a.setDoc(this);
        autores.add(a);
    }

    public void addDescription(String description) {
        Objeto o = new Objeto();
        o.setAtributo("obaaDescription");
        o.setValor(description);
        o.setDocumento(this);
        objetos.add(o);
    }

    public void addLocation(String location) {
        Objeto o = new Objeto();
        o.setAtributo("obaaLocation");
        o.setValor(location);
        o.setDocumento(this);
        objetos.add(o);
    }

    public void addKeyword(String title) {
        Objeto o = new Objeto();
        o.setAtributo("obaaKeyword");
        o.setValor(title);
        o.setDocumento(this);
        objetos.add(o);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Date getTimestamp() {
        return this.datetime;
    }

    public void setTimestamp(Date date) {
        this.datetime = date;
    }

    @Override
    public String getObaaEntry() {
        return this.obaaEntry;
    }

    public void setObaaEntry(String entry) {
        this.obaaEntry = entry;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    /**
     * @return the objeto
     */
    public Set<Objeto> getObjetos() {
        return objetos;
    }

    /**
     * @param objeto the objeto to set
     */
    public void setObjetos(Set<Objeto> objeto) {
        this.objetos = objeto;
    }

    /**
     * @return the repositorio
     */
    public Repositorio getRepositorio() {
        return repositorio;
    }

    /**
     * @param repositorio the repositorio to set
     */
    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the excluido
     */
    @Override
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param excluido the excluido to set
     */
    public void setDeleted(Boolean excluido) {
        this.deleted = excluido;
    }

    public void isDeleted(boolean excluido) {
        this.deleted = excluido;
    }

    private List<String> getAttribute(String attribute) {
        ArrayList<String> l = new ArrayList<String>();
        for (Objeto o : getObjetos()) {
            if (o.getAtributo().equalsIgnoreCase(attribute)) {
                l.add(o.getValor());
            }
        }
        return l;
    }

    @Override
    public List<String> getTitles() {
        return getAttribute("obaaTitle");
    }

    @Override
    public List<String> getKeywords() {
        return getAttribute("obaaKeyword");

    }

    @Override
    public List<String> getDescriptions() {
        return getAttribute("obaaDescription");
    }

    public List<String> getLocation() {
        return getAttribute("obaaLocation");
    }

    public Map<String,Boolean> getLocationHttp() {
        Map<String,Boolean> locationhttp = new HashMap<String,Boolean>();
        for (String loc : getLocation()) {
            locationhttp.put(loc,loc.startsWith("http:"));
        }
        return locationhttp;
    }

    public List<String> getDate() {
        // TODO: fazer esse tratamento no mapeamento. Garantir que a data seja
        // valida.
        ArrayList<String> l = new ArrayList<String>();
        for (Objeto o : getObjetos()) {
            if (o.getAtributo().equalsIgnoreCase("obaaDate")) {
                l.add(o.getValor().replaceAll("[A-Z,a-z,ê,Ê]", " ").replaceAll(" -", ""));
            }
        }
        return l;
    }

    public List<String> getShortDescriptions() {
        ArrayList<String> l = new ArrayList<String>();
        for (Objeto o : getObjetos()) {
            if (o.getAtributo().equalsIgnoreCase("obaaDescription")) {
                if (o.getValor().length() >= 500) {
                    l.add(o.getValor().substring(0, 500) + "(...)");
                } else {
                    l.add(o.getValor());
                }
            }
        }
        return l;
    }

    /**
     * @return the repositorioSubFed
     */
    public RepositorioSubFed getRepositorioSubFed() {
        return repositorioSubFed;
    }

    /**
     * @param repositorioSubFed the repositorioSubFed to set
     */
    public void setRepositorioSubFed(RepositorioSubFed repositorioSubFed) {
        this.repositorioSubFed = repositorioSubFed;
    }

    /**
     * Gets a pretty version of the repository name.
     *
     * If de document is in a repository, return that, if it is in a
     * subFederation, return the name of the Federation and the repository.
     *
     * @return Name of the repository fit to print.
     */
    public String getNomeRep() {
        if (repositorio != null) {
            return repositorio.getName();
        } else if (repositorioSubFed != null) {
            return "Subfedera&ccedil;&atilde;o "
                    + repositorioSubFed.getSubFederacao().getName() + " / "
                    + repositorioSubFed.getName();
        } else {
            log.error("objeto: " + this.getFirstTitle() + "não possui repositório ou federação relacionado!");
            return "";
        }
    }

    /**
     * Gets the OBAA XML directly, consider using getMetadata() instead.
     *
     * @return the obaaXml
     */
    public String getObaaXml() {
        return obaaXml;
    }

    /**
     * Sets the OBAA XML directly, consider using setMetadata instead.
     *
     * @param obaaXml the obaaXml to set
     */
    protected void setObaaXml(String obaaXml) {
        this.obaaXml = obaaXml;
    }

    /**
     * Returns the document metadata.
     *
     * @return the metadata
     * @throws IllegalStateException if there is no XML metadata associated with
     * the document
     */
    public OBAA getMetadata() {
        if (metadata == null) {
            if (getObaaXml() == null) {
                throw new IllegalStateException(
                        "No XML metadata associated with the Object");
            }
            metadata = OBAA.fromString(getObaaXml());
        }
        return metadata;
    }

    /**
     * Sets the metadata of the object, and updates the corresponding XML.
     *
     * @param metadata the metadata to set
     */
    public void setMetadata(OBAA metadata) {
        this.metadata = metadata;
        if (metadata != null) {
            updateIndexes();
            setObaaXml(metadata.toXml());
        } else {
            setObaaXml("");
        }
    }

    /**
     * Updates the indexes (i.e, the Objects related to this Document) from
     * current metadata.
     *
     * This is called inside setMetadata, to keep indexes up-to-date.
     */
    private void updateIndexes() {
        OBAA obaa = this.metadata;

        // Ensure removal of all objects
        objetos.clear();
        autores.clear();
        assert (objetos.isEmpty());

        // Then, add them again
        for (String t : obaa.getGeneral().getTitles()) {
            this.addTitle(t);
        }

        for (String k : obaa.getGeneral().getKeywords()) {
            this.addKeyword(k);
        }

        for (String d : obaa.getGeneral().getDescriptions()) {
            this.addDescription(d);
        }

        if (obaa.getTechnical() != null
                && obaa.getTechnical().getLocation() != null) {
            for (String l : obaa.getTechnical().getLocation()) {
                this.addLocation(l);
            }
        }

        if (obaa.getLifeCycle() != null) {
            for (Contribute c : obaa.getLifeCycle().getContribute()) {
                for (String e : c.getEntities()) {
                    log.debug("e: " + e);
                    this.addAuthor(e.toLowerCase());
                }
            }
        }

    }

    public List<String> getTitlesTokenized() {
        if (titleTokens == null) {
            titleTokens = new ArrayList<String>();
            for (String titulo : getTitles()) {
                titleTokens.addAll(Operacoes.tokeniza(titulo, getStopWords()));
            }

        }
        return titleTokens;

    }

    public List<String> getKeywordsTokenized() {
        if (keywordTokens == null) {
            keywordTokens = new ArrayList<String>();
            for (String keyword : getKeywords()) {
                keywordTokens.addAll(Operacoes.tokeniza(keyword, getStopWords()));
            }

        }
        return keywordTokens;

    }

    public List<String> getDescriptionsTokenized() {
        if (descriptionTokens == null) {
            descriptionTokens = new ArrayList<String>();
            for (String description : getDescriptions()) {
                descriptionTokens.addAll(Operacoes.tokeniza(description,
                        getStopWords()));
            }
        }
        return descriptionTokens;
    }

    /**
     * Generates {@link Token}s from titles, keywords and descriptions and adds
     * them to the object.
     *
     * First, it clears existing tokens.
     */
    public void generateTokens() {
        StopWatch stop = new StopWatch();
        stop.start();
        tokens.clear();
        for (String t : getTitlesTokenized()) {
            tokens.add(new Token(t, this, Token.TITLE));
        }

        for (String t : getKeywordsTokenized()) {
            tokens.add(new Token(t, this, Token.KEYWORD));
        }
        for (String t : getDescriptionsTokenized()) {
            tokens.add(new Token(t, this, Token.DESCRIPTION));
        }
        stop.stop();
        log.debug("Generate tokens levou: " + stop.getTotalTimeSeconds());
    }

    public boolean isIndexEmpty() {
        return (getTitles().isEmpty() && getKeywords().isEmpty() && getDescriptions().isEmpty());
    }

    /*
     * from here on down we implement the methods required by the
     * HibernateOaiDocument interface
     */
    @Override
    public String getXml() {
        return getObaaXml();
    }

    @Override
    public String getOaiIdentifier() {
        return getObaaEntry();
    }

    @Override
    public Collection<String> getSets() {
        Collection<String> c = new HashSet<String>();
        if (getRepositorio() != null) {
            c.add(getRepositorio().getName());
        } else {
            c.add(getRepositorioSubFed().getName());
        }
        return c;
    }

    private List<String> getStopWords() {
        // TODO: this is ugly... see how to do not using getApplicationContext()
        if (stopWords == null) {
            if (stopWordDao == null) {
                ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
                if (ctx == null) {
                    log.fatal("Could not get AppContext bean!");
                    throw new ApplicationContextException(
                            "Could not get AppContext bean!");
                } else {

                    stopWordDao = ctx.getBean(StopWordsDao.class);
                }
            }
            stopWords = stopWordDao.getStopWords(this.language);
        }
        return stopWords;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public String getFirstTitle() {
        if(getTitles().isEmpty()){
            return null; 
        }
        return (getTitles().get(0));
    }

    public Integer getAcessos() {

        return DataAccessUtils.intResult(getSession().createQuery(
                "SELECT COUNT(*) FROM DocumentosVisitas dv, DocumentoReal as d WHERE dv.documento=d.id AND d=:doc").setParameter("doc", this).list());
    }

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

    /**
     * @return the sessionFactory
     */
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
     * Fast method to delete the dependencies of the object.
     *
     * Hibernate will be very slow to do this, bypass it.
     */
    public void deleteDependencies() {
        getSession().createSQLQuery("DELETE FROM objetos WHERE documento = ?").setInteger(0, this.getId()).executeUpdate();
        getSession().createSQLQuery("DELETE FROM autores WHERE documento = ?").setInteger(0, this.getId()).executeUpdate();
        getSession().createSQLQuery("DELETE FROM r1tokens WHERE documento_id = ?").setInteger(0, this.getId()).executeUpdate();

    }
}