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

package Educational;

/**
 * <div class="en">
 *
 * The degree of conciseness of a learning object. The semantic density of a
 * learning object may be estimated in terms of its size, span, or --in the case
 * of self-timed resources such as audio or video-- duration. The semantic
 * density of a learning object is independent of its difficulty. It is best
 * illustrated with examples of expositive material, although it can be used
 * with active resources as well.
 *
 * NOTE 1:--Inherently, this scale is meaningful within the context of a
 * community of practice.
 *
 * Value Space: very low, low, medium, high, very high
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
public class SemanticDensity {
private String semanticDensity;
private enum setOfTerms {verylow, low, medium, high, veryhigh};

    public SemanticDensity() {
        semanticDensity = "";
    }

    public SemanticDensity(String semanticDensity) {
        setSemanticDensity(semanticDensity);
    }

    public void setSemanticDensity(String semanticDensity){
        try {
            setOfTerms.valueOf(semanticDensity);
            this.semanticDensity = semanticDensity;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Structure must be one of: verylow, low, medium, high or veryhigh");

        }
    }

    public String getSemanticDensity() {
        return semanticDensity;
    }
}
