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

package OBAA.Accessibility;

/**
 * <div class="en">
 *
 * Indicates that the described resource is or contains one or more of the 
 * learnerScaffold tools.
 * 
 * Value Space: dictionary, calculator, noteTaking, peerInteraction, abacus
 * thesaurus, spellChecker, homophoneChecker, mindMappingSoftware or outlineTool
 * 
 * according to IMS GLOBAL v1.0 http://www.imsglobal.org/
 *</div>
 *
 * <div class="br">
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class LearnerScaffold {

    private String learnerScaffold;
    private enum setOfTerms {
        dictionary, calculator, noteTaking, peerInteraction, abacus, thesaurus, 
        spellChecker, homophoneChecker, mindMappingSoftware, outlineTool
    };

    public LearnerScaffold() {
        learnerScaffold = "";
    }

    public void setLearnerScaffold(String learnerScaffold) throws IllegalArgumentException{
        
        try {
            setOfTerms.valueOf(learnerScaffold);
            this.learnerScaffold = learnerScaffold;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("LearnerScaffold must be one of: dictionary, calculator, noteTaking, peerInteraction, abacus, thesaurus, spellChecker, homophoneChecker, mindMappingSoftware or outlineTool");
        }
    }

    public String getLearnerScaffold() {
        return this.learnerScaffold;
    }
}
