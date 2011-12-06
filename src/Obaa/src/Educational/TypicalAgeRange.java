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
 * Age of the typical intended user. This data element shall refer to 
 * developmental age, if that would be different from chronological age.
 * 
 * NOTE 1:--The age of the learner is important for finding learning objects, 
 * especially for school age learners and their teachers. When applicable, the 
 * string should be formatted as minimum age-maximum age or minimum age-. 
 * (NOTE:--This is a compromise between adding three component elements (minimum 
 * age, maximum age, and description) and having just a free text field.)
 * 
 * NOTE 2:--Alternative schemes for what this data element tries to cover (such 
 * as various reading age or reading level schemes, IQ's or developmental age 
 * measures) should be represented through the 9:Classification category.
 *
 * Examples: "7-9", "0-5", "15", "18-", ("suitable for children over 7"),
 * ("en","adults only")
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
public class TypicalAgeRange {
private String typicalAgeRange;

    public TypicalAgeRange() {
        typicalAgeRange = "";
    }

    public TypicalAgeRange(String typicalAgeRange) {
        this.typicalAgeRange = typicalAgeRange;
    }

    public void setTypicalAgeRange(String typicalAgeRange) {
        this.typicalAgeRange = typicalAgeRange;
    }

    public String getTypicalAgeRange() {
        return typicalAgeRange;
    }
}
