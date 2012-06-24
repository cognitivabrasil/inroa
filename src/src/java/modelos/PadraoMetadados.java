/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.*;

/**
 *
 * @author Marcos Nunes
 */
public class PadraoMetadados implements java.io.Serializable {

	private static final long serialVersionUID = -8862349037606469252L;
	private int id;
    private String nome;
    private String metadataPrefix;
    private String namespace;
    private Set<Mapeamento> mapeamentos;
    private String atributos;

    public PadraoMetadados() {
        id = 0;
        nome = "";
        metadataPrefix = "";
        namespace = "";
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

    public String getAtributos() {
        return atributos;
    }

    public void setAtributos(String atributos) {
        this.atributos = atributos;
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
    
    /**
     *  Separa os atributos da string e coloca em um Set usando o delimitador ";"
     * @return Set<String> contendo todos os atributos.
     */
    public Set<String> getAtributosList() {
        if (atributos.isEmpty()) {
            return new HashSet<String>();
        } else {
            return new HashSet<String>(Arrays.asList(atributos.split(";")));
        }
    }
    
    public void setAtributosList(Collection<String> c) {
        if (c.isEmpty()) {
            this.atributos = "";
        } else {
            this.atributos = join(c, ";");
        }
    }
    
    @Override
    public String toString(){
        return getNome();
    }


}
