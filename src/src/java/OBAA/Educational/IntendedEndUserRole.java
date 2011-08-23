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
 * Principal user(s) for which this learning object was designed, most dominant
 * first.
 *
 * NOTE 1:--A learner works with a learning object in order to learn something.
 * An author creates or publishes a learning object. A manager manages the
 * delivery of this learning object, e.g., a university or college. The document
 * for a manager is typically a curriculum.
 *
 * NOTE 2:--In order to describe the intended end user role through the skills
 * the user is intended to master, or the tasks he or she is intended to be
 * able to accomplish, the category 9:Classification can be used.
 *
 * 
 * Value Space: teacher, author, learner or manager
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
public class IntendedEndUserRole {

    private String intendedEndUserRole;
    private enum setOfTerms {teacher, author, learner, manager};

    public IntendedEndUserRole() {
        intendedEndUserRole = "";
    }

    public IntendedEndUserRole(String intendedEndUserRole) {
        this.intendedEndUserRole = intendedEndUserRole;
    }

    public void setIntendedEndUserRole(String intendedEndUserRole) {
        try {
            setOfTerms.valueOf(intendedEndUserRole);
            this.intendedEndUserRole = intendedEndUserRole;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Structure must be one of: teacher, author, learner or manager");

        }
    }

    public String getIntendedEndUserRole() {
        return intendedEndUserRole;
    }
}
