/*
 * OBAA - Agent Based Leanring Objetcs
 *
 * This file is part of Obaa.
 * Obaa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Obaa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Obaa. If not, see <http://www.gnu.org/licenses/>.
 */

package OBAA.Metametadata;

import OBAA.General.Identifier;
import OBAA.LifeCycle.Contribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
     
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
@Root
public class Metametadata {
    @ElementList (name = "Identifiers")
    private Set <Identifier> identifier;
    @ElementList (name = "Contribute")
    private ArrayList <Contribute> contribute; // contribute must be ordered
    @ElementList (name = "Schema")
    private Set <MetadataSchema> schema;
    @Element (name = "Language")
    private String language;
    
    public Metametadata() {
        
        this.identifier = new TreeSet<Identifier>();
        this.contribute = new ArrayList<Contribute>();
        this.schema = new TreeSet<MetadataSchema>();
        this.language = "";
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

    public void setLanguage(String language) {
        this.language = language;
    }

    public Collection<Contribute> getContribute() {
        return contribute;
    }

    public Collection<Identifier> getIdentifier() {
        return identifier;
    }
    public void setMetadataSchema(Set<MetadataSchema> schema) {
        this.schema = schema;
    }
    public Set<MetadataSchema> getSchema() {
        return schema;
    }
    public void addMetadataSchema (MetadataSchema newMetadataSchema){        
        this.schema.add(newMetadataSchema);
    }

          
    public String getLanguage() {
        return language;
    }
}
