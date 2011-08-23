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
 * Specific kind of learning object. The most dominant kind shall be first.
 *
 * NOTE:--The vocabulary terms are defined as in the OED:1989 and as used by
 * educational communities of practice.
 *
 * Value Space: exercise, simulation, questionnaire, diagram, figure, graph,
 * index, slide, table, narrative text, exam, experiment, problem statement
 * self assessment, lecture
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
public class LearningResourceType {
private String learningResourceType;
    private enum setOfTerms {
        exercise, simulation, questionnaire, diagram, figure, graph, index, slide, table, narrativetext, exam, experiment, problemstatement, selfassessment, lecture
    };

    public LearningResourceType() {
        learningResourceType = "";
    }

    public LearningResourceType(String learningResourceType) {
        this.learningResourceType = learningResourceType;
    }

    public void setLearningResourceType(String learningResourceType) {
        try {
            setOfTerms.valueOf(learningResourceType);
            this.learningResourceType = learningResourceType;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Structure must be one of: exercise, simulation, questionnaire, diagram, figure, graph, index, slide, table, narrativetext, exam, experiment, problemstatement, selfassessment or lecture");

        }
    }

    public String getLearningResourceType() {
        return learningResourceType;
    }
}
