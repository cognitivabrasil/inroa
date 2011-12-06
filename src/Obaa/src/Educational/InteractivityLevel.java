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
 * The degree of interactivity characterizing this learning object.
 * Interactivity in this context refers to the degree to which the learner can
 * influence the aspect or behavior of the learning object.
 *
 * NOTE 1:--Inherently, this scale is meaningful within the context of a
 * community of practice.
 *
 * NOTE 2:--Learning objects with 5.1:Educational.InteractivityType="active" may
 * have a high interactivity level (e.g., a simulation environment endowed with
 * many controls) or a low interactivity level (e.g., a written set of
 * instructions that solicit an activity). Learning objects with
 * 5.1:Educational.InteractivityType="expositive" may have a low interactivity
 * level (e.g., a piece of linear, narrative text produced with a standard word
 * processor) or a medium to high interactivity level (e.g., a sophisticated
 * hyperdocument, with many internal links and views).
 * 
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
public class InteractivityLevel {

    private String interactivityLevel;

    private enum setOfTerms {verylow, low, medium, high, veryhigh};

    public InteractivityLevel() {
        interactivityLevel = "";
    }

    public InteractivityLevel(String interactivityLevel) {
       setInteractivityLevel(interactivityLevel);
    }

    public void setInteractivityLevel(String interactivityLevel) {
        try {
            setOfTerms.valueOf(interactivityLevel);
            this.interactivityLevel = interactivityLevel;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Structure must be one of: verylow, low, medium, high or veryhigh");

        }
    }

    public String getInteractivityLevel() {
        return interactivityLevel;
    }
}
