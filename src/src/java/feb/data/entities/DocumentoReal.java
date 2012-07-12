package feb.data.entities;

import ORG.oclc.oai.models.HibernateOaiDocument;
import cognitivabrasil.obaa.LifeCycle.Contribute;
import cognitivabrasil.obaa.OBAA;
import java.util.*;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;

import feb.data.interfaces.DocumentoFebInterface;
import feb.data.interfaces.StopWordsDao;
import feb.util.Operacoes;
import spring.ApplicationContextProvider;

/**
 *
 * Representa um documento na Federação;
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Luiz Rossi <lh.rossi@gmail.com>
 *
 */
public class DocumentoReal implements java.io.Serializable, DocumentoFebInterface, HibernateOaiDocument {
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

    public void addDescription(String title) {
        Objeto o = new Objeto();
        o.setAtributo("obaaDescription");
        o.setValor(title);
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

    protected void setId(int id) {
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

    public List<String> getDate() {
        //TODO: fazer esse tratamento no mapeamento. Garantir que a data seja valida.
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
            return repositorio.getNome();
        } else {
            return "Subfedera&ccedil;&atilde;o "
                    + repositorioSubFed.getSubFederacao().getNome()
                    + " / " + repositorioSubFed.getNome();
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
                throw new IllegalStateException("No XML metadata associated with the Object");
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
        updateIndexes();
        setObaaXml(metadata.toXml());
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
        objetos.removeAll(objetos);
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

        if (obaa.getLifeCycle() != null) {
            for (Contribute c : obaa.getLifeCycle().getContribute()) {
                for (String e : c.getEntities()) {
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
                descriptionTokens.addAll(Operacoes.tokeniza(description, getStopWords()));
            }
        }
        return descriptionTokens;
    }

    /**
     * Generates {@link Token}s from titles, keywords and descriptions and adds them to the object.
     * 
     * First, it clears existing tokens.
     */
    public void generateTokens() {
    	tokens.clear();
    	for(String t : getTitlesTokenized()) {
    		tokens.add(new Token(t, this, Token.TITLE));
    	}

    	for(String t : getKeywordsTokenized()) {
    		tokens.add(new Token(t, this, Token.KEYWORD));
    	}
    	for(String t : getDescriptionsTokenized()) {
    		tokens.add(new Token(t, this, Token.DESCRIPTION));
    	}
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
            c.add(getRepositorio().getNome());
        } else {
            c.add(getRepositorioSubFed().getNome());
        }
        return c;
    }

    private List<String> getStopWords() {
    	// TODO: this is ugly...
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.fatal("Could not get AppContext bean!");
            throw new ApplicationContextException("Could not get AppContext bean!");
        } else {

            StopWordsDao stopWordDao = ctx.getBean(StopWordsDao.class);
            return stopWordDao.getStopWords(this.language);
        }
    }

	public void setTokens(Set<Token> tokens) {
		this.tokens = tokens;
	}

	public Set<Token> getTokens() {
		return tokens;
	}
}