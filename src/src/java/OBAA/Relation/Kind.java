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

import org.simpleframework.xml.Attribute;

/**
 * <div class="en">
 * 
 * Nature of the relationship between this learning object and the target 
 * learning object, identified by 7.2:Relation.Resource.
 * 
 * Based on Dublin Core: 
 * ispartof: is part of
 * haspart: has part
 * isversionof: is version of
 * hasversion: has version
 * isformatof: is format of
 * hasformat: has format
 * references: references
 * isreferencedby: is referenced by
 * isbasedon: is based on
 * isbasisfor: is basis for
 * requires: requires
 * isrequiredby: is required by
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
public class Kind {
    @Attribute
    private String kind;
        private enum setOfTerms {
        isPartOf, hasPart, isVersionOf, hasVersion, isFormatOf, hasFormat, 
        references, isReferencedBy, isBasedOn, isBasisFor, requires, isRequiredBy
    };

    public Kind() {
        kind = "";
    }

   public void setKind(String kind) throws IllegalArgumentException{
        
        try {
            setOfTerms.valueOf(kind);
            this.kind = kind;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Kind must be one of:  isPartOf, hasPart, isVersionOf, hasVersion, isFormatOf, hasFormat, references, isReferencedBy, isBasedOn, isBasisFor, requires or isRequiredBy");
        }
    }

    public String getKind() {
        return kind;
    }
}