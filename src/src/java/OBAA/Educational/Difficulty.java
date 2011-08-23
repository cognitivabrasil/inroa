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

/**
 * <div class="en">
 *
 * How hard it is to work with or through this learning object for the typical 
 * intended target audience.
 * 
 * NOTE:--The " typical target audience" can be characterized by data elements
 * 5.6:Educational.Context and
 * 5.7:Educational.TypicalAgeRange.
 * 
 * Value Space: very easy, easy, medium, difficult, very difficult
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
public class Difficulty {
    private String difficulty;
        private enum setOfTerms {veryeasy, easy, medium, difficult, verydifficult};

    public Difficulty() {
        difficulty = "";
    }

    public Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setDifficulty(String difficulty) {
        try {
            setOfTerms.valueOf(difficulty);
            this.difficulty = difficulty;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Structure must be one of: veryeasy, easy, medium, difficult, verydifficult");

        }
    }

    public String getDifficulty() {
        return difficulty;
    }
}
