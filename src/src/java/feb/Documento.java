

// Generated 20/07/2011 15:25:15 by Hibernate Tools 3.2.0.b9

package feb;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *  
 *       Represents a single playable track in the music database.
 *       @author Jim Elliott (with help from Hibernate)
 *     
 */
public class Documento  implements java.io.Serializable {


     private int id;
     private String obaa_entry;
     private Date datetime;
     private Set<Objeto> objetos = new HashSet<Objeto>(0);
     private Repositorio repositorio;

    public Documento() {
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




}


