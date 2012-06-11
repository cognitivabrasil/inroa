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

package OBAA.Classification;

import org.simpleframework.xml.Element;

/**
 * <div class="en">
 *
 * A particular term within a taxonomy. A taxon is a node that has a defined 
 * label or term. A taxon may also have an alphanumeric designation or 
 * identifier for standardized reference. Either or both the label and the entry 
 * may be used to designate a particular taxon. 
 * An ordered list of taxons creates a taxonomic path, i.e., 
 * "taxonomic stairway": this is a path from a more general to more specific 
 * entry in a classification.
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 *</div>
 *
 * <div class="br">
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class Taxon {
    @Element
    private String id;
    @Element
    private String entry;

    public Taxon() {
        id = "";
        entry = "";        
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntry() {
        return entry;
    }

    public String getId() {
        return id;
    }
}
