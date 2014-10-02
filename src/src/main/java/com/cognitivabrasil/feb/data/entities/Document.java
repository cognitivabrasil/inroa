package com.cognitivabrasil.feb.data.entities;

import ORG.oclc.oai.models.OaiDocument;
import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.Technical.Location;

import com.cognitivabrasil.feb.data.interfaces.DocumentoFebInterface;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * Representa um documento na Federação;
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Luiz Rossi <lh.rossi@gmail.com>
 *
 */
@Entity
@Table(name = "documentos")
public class Document implements java.io.Serializable,
        DocumentoFebInterface, OaiDocument {

    private static final Logger log = LoggerFactory.getLogger(Document.class);
    private static final long serialVersionUID = 61217365141633065L;
    private int id;
    private String obaaEntry;
    private DateTime created;
    private Repositorio repositorio;
    private RepositorioSubFed repositorioSubFed;
    private Boolean deleted;
    private String obaaXml;
    private OBAA metadata;
    private Set<Autor> autores;

    public Document() {
        deleted = false;
        autores = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCUMENTOS_SEQ")
    @SequenceGenerator(name="DOCUMENTOS_SEQ", sequenceName="DOCUMENTOS_SEQ")    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Long id) {
        if (id != null) {
            this.id = id.intValue();
        }
    }

    @Transient
    @Override
    public Date getTimestamp() {
        if(this.created==null){
            return null;
        }
        return this.created.toDate();
    }

    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getCreated() {
        return this.created;
    }

    public void setCreated(DateTime date) {
        this.created = date;
    }

    @Override
    @Column(name = "obaa_entry")
    public String getObaaEntry() {
        return this.obaaEntry;
    }

    public void setObaaEntry(String entry) {
        this.obaaEntry = entry;
    }

    @Transient
    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    /**
     * @return the repositorio
     */
    @ManyToOne
    @JoinColumn(name = "id_repositorio")
    public Repositorio getRepositorio() {
        return repositorio;
    }

    /**
     * @param repositorio the repositorio to set
     */
    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
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

    @Override
    @Transient
    public List<String> getTitles() {
        OBAA obaa = getMetadata();
        if (obaa == null || obaa.getGeneral() == null) {
            return null;
        }
        return obaa.getGeneral().getTitles();
    }

    @Override
    @Transient
    public List<String> getKeywords() {
        OBAA obaa = getMetadata();
        if (obaa == null || obaa.getGeneral() == null) {
            return null;
        }
        return obaa.getGeneral().getKeywords();

    }

    @Override
    @Transient
    public List<String> getDescriptions() {
        OBAA obaa = getMetadata();
        if (obaa == null || obaa.getGeneral() == null) {
            return null;
        }
        return obaa.getGeneral().getDescriptions();
    }

    @Transient
    public List<Location> getLocation() {
        OBAA obaa = getMetadata();
        if (obaa == null || obaa.getTechnical() == null) {
            return null;
        }
        return obaa.getTechnical().getLocation();
    }

    @Transient
    public Map<String, Boolean> getLocationHttp() {
        OBAA obaa = getMetadata();
        if (obaa == null || obaa.getTechnical() == null) {
            return null;
        }
        return obaa.getTechnical().getLocationHttp();
    }

    @Transient
    public List<String> getShortDescriptions() {
        List<String> l = new ArrayList<>();
        OBAA obaa = getMetadata();
        if (obaa == null || obaa.getGeneral() == null) {
            return l;
        }
        for (String description : obaa.getGeneral().getDescriptions()) {
            if (description.length() >= 500) {
                l.add(description.substring(0, 500) + "(...)");
            } else {
                l.add(description);
            }
        }
        return l;
    }

    /**
     * @return the repositorioSubFed
     */
    @ManyToOne
    @JoinColumn(name = "id_rep_subfed")
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
    @Transient
    public String getNomeRep() {
        if (repositorio != null) {
            return repositorio.getName();
        } else if (repositorioSubFed != null) {
            return "Subfedera&ccedil;&atilde;o "
                    + repositorioSubFed.getSubFederacao().getName() + " / "
                    + repositorioSubFed.getName();
        } else {
            log.error("O documento: " + this.getId() + "não possui repositório ou federação relacionado!");
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
    @Transient
    public OBAA getMetadata() {
        if (metadata == null) {
            if (getObaaXml() == null || getObaaXml().isEmpty()) {
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
            setObaaXml(metadata.toXml());
        } else {
            setObaaXml("");
        }
    }


    /*
     * from here on down we implement the methods required by the
     * HibernateOaiDocument interface
     */
    @Override
    @Transient
    public String getXml() {
        return getObaaXml();
    }

    @Override
    @Transient
    public String getOaiIdentifier() {
        return getObaaEntry();
    }

    @Override
    @Transient
    public Collection<String> getSets() {
        Collection<String> c = new HashSet<>();
        if (getRepositorio() != null) {
            c.add(getRepositorio().getName());
        } else {
            c.add(getRepositorioSubFed().getName());
        }
        return c;
    }

    @Transient
    public String getFirstTitle() {
        OBAA obaa = getMetadata();
        if (null == obaa || obaa.getGeneral() == null) {
            return null;
        }
        return (obaa.getGeneral().getTitles().get(0));
    }
}
