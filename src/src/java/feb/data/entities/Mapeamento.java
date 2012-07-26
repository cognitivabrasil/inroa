/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import feb.data.interfaces.FebDomainObject;
import metadata.MetadataConversorInterface;

/**
 *
 * @author paulo
 * 
 */
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
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
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

    public PadraoMetadados getPadraoMetadados() {
        return padraoMetadados;
    }

    public void setPadraoMetadados(PadraoMetadados padraoMetadados) {
        this.padraoMetadados = padraoMetadados;
    }   
    

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

    public String getXmlSample() {
        return xmlSample;
    }

    public void setXmlSample(String xmlSample) {
        this.xmlSample = xmlSample;
    }

}
