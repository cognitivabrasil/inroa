/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Metametadata;

import General.Identifier;
import General.Language;
import LifeCycle.Contribute;
import java.util.ArrayList;
import java.util.Collection;
     
/**
 *
 * <div class="en">
 * This category describes this metadata record itself (rather than the learning 
 * object that this record describes). 
 * 
 * This category describes how the metadata instance can be identified, 
 * who created this metadata instance, how, when, and with what references.
 * 
 * NOTE:--This is not the information that describes the learning object itself.
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class Metametadata {
    
    private Collection <Identifier> identifier;
    private ArrayList <Contribute> contribute; // contribute must be ordered
    private Collection <MetadataSchema> schema;
    private Language language;
    
    public Metametadata() {
        
        this.identifier.clear();
        this.contribute.clear();
        this.schema.clear();
        this.language = new Language();

    }

    public Metametadata(Identifier identifier, Contribute contribute,MetadataSchema schema ,Language language) {
        
        this.identifier.clear();
        this.contribute.clear();
        this.schema.clear();
        this.identifier.add(identifier);
        this.contribute.add(contribute);
        this.schema.add(schema);
        this.language = language;
    }
    
    public void addIdentifier (Identifier identifier) {
        this.identifier.add(identifier);
    }

    public void addContribute(Contribute contribute) {
        this.contribute.add(contribute);
    }

    public void addSchema(MetadataSchema schema) {
        this.schema.add(schema);
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Collection<Contribute> getContribute() {
        return contribute;
    }

    public Collection<Identifier> getIdentifier() {
        return identifier;
    }

    public Collection<MetadataSchema> getSchema() {
        return schema;
    }
        
    public Language getLanguage() {
        return language;
    }

    
}
