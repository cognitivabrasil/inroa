/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import feb.data.interfaces.FebDomainObject;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class PadraoMetadados implements java.io.Serializable, FebDomainObject {

    private static final long serialVersionUID = -8862349037606469252L;
    private Integer id;
    private String name;
    private String metadataPrefix;
    private String namespace;
    private transient Set<Mapeamento> mapeamentos;
    private transient String atributos;

    public PadraoMetadados() {
        id = 0;
        name = "";
        metadataPrefix = "";
        namespace = "";
        mapeamentos = new HashSet<Mapeamento>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @deprecated use @{link getName()} instead.
     */
    @Deprecated
    public String getNome() {
        return name;
    }

    /**
     *
     * @deprecated use @{link setName()} instead.
     */
    @Deprecated
    public void setNome(String nome) {
        this.name = nome;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
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

    /**
     * Separa os atributos da string e coloca em um Set usando o delimitador ";"
     *
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
            this.atributos = StringUtils.join(c, ";");
        }
    }

    @Override
    public String toString() {
    	return getName() + "(" + getId() + ")";
    }

	@Override
	public String toStringDetailed() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
	}
}
