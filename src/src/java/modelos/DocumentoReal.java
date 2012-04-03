// Generated 20/07/2011 15:25:15 by Hibernate Tools 3.2.0.b9
package modelos;

import java.util.*;

/**
 *
 * Represents a single playable track in the music database.
 *
 * @author Jim Elliott (with help from Hibernate)
 *
 */
public class DocumentoReal implements java.io.Serializable, DocumentoFebInterface {

    private int id;
    private String obaa_entry;
    private Date datetime;
    private Set<Objeto> objetos = new HashSet<Objeto>(0);
    private Repositorio repositorio;
    private RepositorioSubFed repositorioSubFed;
    private boolean deleted;

    public DocumentoReal() {
    }

    public void addTitle(String title) {
        Objeto o = new Objeto();
        o.setAtributo("obaaTitle");
        o.setValor(title);
        o.setDocumento(this);
        objetos.add(o);
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

    public Date getTimestamp() {
        return this.datetime;
    }

    public void setTimestamp(Date date) {
        this.datetime = date;
    }

    public String getObaaEntry() {
        return this.obaa_entry;
    }

    public void setObaaEntry(String entry) {
        this.obaa_entry = entry;
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

    /**
     * @return the excluido
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param excluido the excluido to set
     */
    public void setDeleted(boolean excluido) {
        this.deleted = excluido;
    }

    private List<String> getAttribute(String attribute) {
        ArrayList<String> l = new ArrayList<String>();
        for (Objeto o : getObjetos()) {
            if (o.getAtributo() == attribute) {
                l.add(o.getValor());
            }
        }
        return l;
    }

    public List<String> getTitles() {
        return getAttribute("obaaTitle");
    }

    public List<String> getKeywords() {
        return getAttribute("obaaKeyword");

    }

    public List<String> getDescriptions() {
        return getAttribute("obaaDescription");
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
}
