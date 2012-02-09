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

package OBAA.Educational;

import metadata.TextElement;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * <div class="en">
 * The principal environment within which the learning and use of this learning
 * object is intended to take place.
 * 
 * NOTE:--Suggested good practice is to use one of the values of the value space 
 * and to use an additional instance of this data element for further 
 * refinement, as in ("LOMv1.0","higher education") and 
 * ("http://www.ond.vlaanderen.be/onderwijsinvlaanderen/Default.htm", 
 * "kandidatuursonderwijs")
 *
 * Value Space: school, higher education, training, other
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
@Root(strict=false)
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Context extends TextElement{

    private enum setOfTerms {school, highereducation, training, other};

    public Context() {
    }

    public Context(String context) {
        setContext(context);
    }

    public void setContext(String context) {

        try {
            setOfTerms.valueOf(context);
            setText(context);

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Structure must be one of: school, highereducation, training, other");
        }
    }

    public String getContext() {
        return getText();
    }
}
