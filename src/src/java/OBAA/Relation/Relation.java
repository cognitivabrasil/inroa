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

package OBAA.Relation;

import OBAA.General.Identifier;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <div class="en">
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
@Root
public class Relation {
    @Element (name = "Kind")
    private Kind kind;
    @Element (name = "Resource")
    private Resource resource;


    public Relation() {
        kind = new Kind();
        resource = new Resource();       
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }
     public void setKind(String kind) throws IllegalArgumentException {        
        this.kind.setKind(kind);
     }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    public Kind getKind() {
        return kind;
    }

    public Resource getResource() {
        return resource;
    }
 
    
}