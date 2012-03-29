/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Marcos Nunes
 */
public class PadraoMetadados implements java.io.Serializable {
    
    private int id;
    private String nome;
    private String metadataPrefix;
    private String namespace;
    private Set<Mapeamento> mapeamentos;

    public PadraoMetadados() {
        id=0;
        nome="";
        metadataPrefix="";
        namespace="";
        mapeamentos = new HashSet<Mapeamento>();
    }
    
    
 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the metadataPrefix
     */
    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    /**
     * @param metadataPrefix the metadataPrefix to set
     */
    public void setMetadataPrefix(String metadataPrefix) {
        this.metadataPrefix = metadataPrefix;
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Set<Mapeamento> getMapeamentos() {
        return mapeamentos;
    }

    public void setMapeamentos(Set<Mapeamento> mapeamentos) {
        this.mapeamentos = mapeamentos;
    }
   
    
    
    
}
