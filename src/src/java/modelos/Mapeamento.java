/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import metadata.MetadataConversorInterface;

/**
 *
 * @author paulo
 */
public class Mapeamento implements MetadataConversorInterface {
    private Integer id;
    private String description;
    private String xslt;

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

    public String toObaa(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
