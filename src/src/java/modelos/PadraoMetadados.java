/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;

/**
 *
 * @author Marcos Nunes
 */
public class PadraoMetadados implements java.io.Serializable {
    
    private int id;
    private String nome;
    private String metadataPrefix;
    private String namespace;
    private List<Mapeamento> mapeamentos;

 
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

    public List<Mapeamento> getMapeamentos() {
        return mapeamentos;
    }

    public void setMapeamentos(List<Mapeamento> mapeamentos) {
        this.mapeamentos = mapeamentos;
    }
    
    
    
    
}
