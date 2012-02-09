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
 * Educational especification about the content of the learning object.
 * 
 * Value Space: Fatual, Conceitual, Procedimental, Atitudinal
 *
 * Adaptapted from http://www.portalobaa.org
 *</div>
 *
 * <div class="br">
 * Especificação educacional do tipo do conteúdo do objeto de aprendizagem.
 *
 * Domínio: Fatual, Conceitual, Procedimental, Atitudinal
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class LearningContentType {

    private String learningContentType;
    private enum setOfTerms {fatual, conceitual, procedimental, atitudinal};

    public LearningContentType() {
        learningContentType = "";
    }

    public LearningContentType(String learningContentType) {
        this.learningContentType = learningContentType;
    }

    public void setLearningContentType(String learningContentType) {
        try {
            setOfTerms.valueOf(learningContentType);
            this.learningContentType = learningContentType;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Structure must be one of: fatual, conceitual, procedimental, atitudinal");
        }
    }

    public String getLearningContentType() {
        return learningContentType;
    }
}
