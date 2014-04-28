/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import feb.data.interfaces.FebDomainObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import metadata.MetadataConversorInterface;

/**
 *
 * @author paulo
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 *
 */
@Entity
@Table(name = "mapeamentos")
public class Mapeamento implements MetadataConversorInterface, FebDomainObject {

    private Integer id;
    private String description;
    private String name;
    private String xslt;
    private PadraoMetadados padraoMetadados;
    private String xmlSample;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "nome")
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    @Column(name = "descricao")
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the xslt
     */
    public String getXslt() {
        return xslt;
    }

    /**
     * @param xslt the xslt to set
     */
    public void setXslt(String xslt) {
        this.xslt = xslt;
    }
    
    @Column(name = "xml_exemplo")
    public String getXmlSample() {
        return xmlSample;
    }

    public void setXmlSample(String xmlSample) {
        this.xmlSample = xmlSample;
    }

    @ManyToOne
    @JoinColumn(name = "padrao_id")
    public PadraoMetadados getPadraoMetadados() {
        return padraoMetadados;
    }

    public void setPadraoMetadados(PadraoMetadados padraoMetadados) {
        this.padraoMetadados = padraoMetadados;
    }

    @Override
    public String toObaa(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return getName() + "(" + getId() + ")";
    }

    @Override
    public String toStringDetailed() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
    }

    /**
     * Retorna um boolean informando se o mapeamento é interno ou não. Se for um
     * mapeamento interno ele não poderá ser editado pelo usuário.
     *
     * @return true se o mapeamento pode ser editado ou false caso contrário.
     */
    @Transient
    public boolean isEditable() {
        return id == null || id > 99;
    }

}
