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

import OBAA.General.Description;
import OBAA.General.Language;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <div class="en">
 *
 * This category describes the key educational or pedagogic characteristics of
 * this learning object.
 *

 * NOTE:--This is the pedagogical information essential to those involved in
 * achieving a quality learning experience. The audience for this metadata
 * includes teachers, managers, authors, and learners.
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 *</div>
 *
 * <div class="br">
 *
 * Descrição das características educacionais do objeto de aprendizagem.
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class Educational {
private InteractivityType interactiveType;
private ArrayList <LearningResourceType> learningResourceTypes;
private InteractivityLevel interactiveLevel;
private SemanticDensity semanticDensity;
private Collection <IntendedEndUserRole> intendedEndUserRoles;
private Collection <Context> contexts;
private Collection <TypicalAgeRange> tipicalAgeRanges;
private Difficulty difficulty;
private TypicalLearningTime typicalLearningTime;
//Comments on how this learning object is to be used.
private Collection<Description> descriptions;
private Collection<Language> languages;
private LearningContentType learningContentType;
private Interaction interaction;
private Perception perception;




    public Educational() {
        interactiveType = new InteractivityType();
        interactiveLevel = new InteractivityLevel();
        semanticDensity = new SemanticDensity();
        difficulty = new Difficulty();
        typicalLearningTime = new TypicalLearningTime();
        learningContentType = new LearningContentType();
        interaction = new Interaction();
        perception = new Perception();
    }

    public void setContexts(Collection<Context> contexts) {
        this.contexts = contexts;
    }

    public void setDescriptions(Collection<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setIntendedEndUserRoles(Collection<IntendedEndUserRole> intendedEndUserRoles) {
        this.intendedEndUserRoles = intendedEndUserRoles;
    }

    public void setInteractiveLevel(InteractivityLevel interactiveLevel) {
        this.interactiveLevel = interactiveLevel;
    }

    public void setInteractiveType(InteractivityType interactiveType) {
        this.interactiveType = interactiveType;
    }

    public void setLanguages(Collection<Language> languages) {
        this.languages = languages;
    }

    public void setLearningResourceTypes(ArrayList<LearningResourceType> learningResourceTypes) {
        this.learningResourceTypes = learningResourceTypes;
    }

    public void setSemanticDensity(SemanticDensity semanticDensity) {
        this.semanticDensity = semanticDensity;
    }

    public void setTipicalAgeRanges(Collection<TypicalAgeRange> tipicalAgeRanges) {
        this.tipicalAgeRanges = tipicalAgeRanges;
    }

    public void setTypicalLearningTime(TypicalLearningTime typicalLearningTime) {
        this.typicalLearningTime = typicalLearningTime;
    }

    public void setLearningContentType(LearningContentType learningContentType) {
        this.learningContentType = learningContentType;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public void setPerception(Perception perception) {
        this.perception = perception;
    }


    //----------------------------------------------------


    public Collection<Context> getContexts() {
        return contexts;
    }

    public Collection<Description> getDescriptions() {
        return descriptions;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Collection<IntendedEndUserRole> getIntendedEndUserRoles() {
        return intendedEndUserRoles;
    }

    public InteractivityLevel getInteractiveLevel() {
        return interactiveLevel;
    }

    public InteractivityType getInteractiveType() {
        return interactiveType;
    }

    public Collection<Language> getLanguages() {
        return languages;
    }

    public ArrayList<LearningResourceType> getLearningResourceTypes() {
        return learningResourceTypes;
    }

    public SemanticDensity getSemanticDensity() {
        return semanticDensity;
    }

    public Collection<TypicalAgeRange> getTipicalAgeRanges() {
        return tipicalAgeRanges;
    }

    public TypicalLearningTime getTypicalLearningTime() {
        return typicalLearningTime;
    }

    public LearningContentType getLearningContentType() {
        return learningContentType;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public Perception getPerception() {
        return perception;
    }


}
