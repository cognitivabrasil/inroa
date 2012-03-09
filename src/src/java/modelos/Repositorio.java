

// Generated 20/07/2011 15:25:15 by Hibernate Tools 3.2.0.b9

package modelos;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
     private String url;
     private String metadataPrefix;
     private Set<Documento> documentos = new HashSet<Documento>(0);
     private Date ultimaAtualizacao;
     private String namespace;
     private int periodicidadeAtualizacao;
     //private Date proximaAtualizacao;
     private String colecoes;

    public Repositorio() {
    }

	
    public int getId() {
        return this.id;
    }
    
    protected void setId(int id) {
        this.id = id;
    }

    //Mapeamento getMapeamento() {
    //    return "";
    //}
    
    List<String> getComunidades() {
        return null;
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

	public String toString() {
		return getNome();
	}
        
        
        public int size() {
            return 0;
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
    public int getPeriodicidadeAtualizacao() {
        return periodicidadeAtualizacao/24;
    }    
    
    public void setPeriodicidadeAtualizacao(int periodicidadeAtualizacao) {
        this.periodicidadeAtualizacao = periodicidadeAtualizacao;
    }

    public Date getProximaAtualizacao() {
        return ultimaAtualizacao /* TODO: + periodicidade */;
    }

    /*public void setProximaAtualizacao(Date proximaAtualizacao) {
        this.proximaAtualizacao = proximaAtualizacao;
    }
    * Não seta, próxima atualização baseada na periodicidade
    */

    public String getColecoes() {
        return colecoes;
    }

    public void setColecoes(String colecoes) {
        this.colecoes = colecoes;
    }

    
    
}


