

// Generated 20/07/2011 15:25:15 by Hibernate Tools 3.2.0.b9

package feb;

import java.util.HashSet;
import java.util.Set;

/**
 *  
 *       Represents a single playable track in the music database.
 *       @author Jim Elliott (with help from Hibernate)
 *     
 */
public class Repositorio  implements java.io.Serializable {


     private int id;
     private String nome;
     private String descricao;
     private Set<Documento> documentos = new HashSet<Documento>(0);

    public Repositorio() {
    }

	
    public int getId() {
        return this.id;
    }
    
    protected void setId(int id) {
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
	public Set<Documento> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}



}


