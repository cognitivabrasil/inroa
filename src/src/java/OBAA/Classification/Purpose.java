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

import org.simpleframework.xml.Attribute;

/**
 * <div class="en">
 *
 * The purpose of classifying this learning object.
 * 
 * Value Space: discipline, idea, prerequisite, educational objective,
 * accessibility, restrictions, educational level, skill level, security level
 * or competency.
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
public class Purpose {
    
    @Attribute
    private String value;
    private enum setOfTerms {discipline,idea,prerequisite,educational_objective,
    accessibility,restrictions,educational_level,skill_level, security_level,
    competency};

    public Purpose() {
        value = "";
    }

    public void setPurpose(String purpose) throws IllegalArgumentException{
        try {
            setOfTerms.valueOf(purpose);
            this.value = purpose;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Purpose must be one of: fatual, conceitual, procedimental, atitudinal");
        }
    }

    public String getPurpose() {
        return this.value;
    }
}
