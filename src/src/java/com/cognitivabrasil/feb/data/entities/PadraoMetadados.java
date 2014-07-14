/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.cognitivabrasil.feb.data.interfaces.FebDomainObject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Entity
@Table(name = "padraometadados")
public class PadraoMetadados implements java.io.Serializable, FebDomainObject {

    private static final long serialVersionUID = -8862349037606469252L;
    private Integer id;
    private String name;
    private String metadataPrefix;
    private String namespace;
    private transient Set<Mapeamento> mapeamentos;
    private transient String atributos;

    public PadraoMetadados() {
        mapeamentos = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    @Column(name = "nome")
    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    /**
     * @return the metadataPrefix
     */
    @Column(name = "metadata_prefix")
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
    @Column(name = "name_space")
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @OneToMany(mappedBy = "padraoMetadados", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("name ASC")
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
    @Transient
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
