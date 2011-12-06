/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Metametadata;

/**
 *
 * <div class="en">
 * The name and version of the authoritative specification used to create this 
 * metadata instance.
 * 
 * NOTE:--This data element may be user selectable or system generated.
 * If multiple values are provided, then the metadata instance shall conform to 
 * multiple metadata schemas.
 * 
 * 
 * Example "OBAAv1.0"
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class MetadataSchema {
    
    private String schema;
    
    public MetadataSchema() {
        schema = "";
    }

    public MetadataSchema(String schema) {
        this.schema = schema;
    }
    
    public void setMetadataSchema(String schema) {
        this.schema = schema;
    }
    
    public String getMetadataSchema() {
        return (this.schema);
    }
}
