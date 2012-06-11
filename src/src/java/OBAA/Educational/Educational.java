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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import metadata.TextElement;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

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
@Root(strict=false)
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Educational {


/**
 * <div class="en">
 *
 * Predominant mode of learning supported by this learning object.
 *
 * "Active" learning (e.g., learning by doing) is supported by content that
 * directly induces productive action by the learner. An active learning object
 * prompts the learner for semantically meaningful input or for some other kind
 * of productive action or decision, not necessarily performed within the
 * learning object's framework. Active documents include simulations,
 * questionnaires, and exercises.
 *
 * "Expositive" learning (e.g., passive learning) occurs when the learner's job
 * mainly consists of absorbing the content exposed to him (generally through
 * text, images or sound). An expositive learning object displays information
 * but does not prompt the learner for any semantically meaningful input.
 * Expositive documents include essays, video clips, all kinds of graphical
 * material, and hypertext documents. When a learning object blends the active
 * and expositive interactivity types, then its interactivity type is "mixed".
 *
 * NOTE:--Activating links to navigate in hypertext documents is not considered
 * to be a productive action.
 *
 * Value Space: active, expositive or mixed
 *
 * Example:
 * active documents (with learner's action):
 * - simulation (manipulates, controls or enters data or parameters);
 * - questionnaire (chooses or writes answers);
 * - exercise (finds solution);
 * -  problem statement (writes solution).
 *
 * expositive documents (with learner's action):
 * - hypertext document (reads, navigates);
 * - video (views, rewinds, starts, stops);
 * - graphical material (views);
 * - audio material (listens, rewinds, starts, stops).
 *
 * mixed document:
 * - hypermedia document with embedded simulation applet.
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 *
 *</div>
 *
 * <div class="br">
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 *
 * @author LuizRossi
 */
	@Element(required=false)
	private String interactivityType;

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
	
	@Element(required=false)
	private String interactivityLevel;

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
	@Element(required=false)
	private String semanticDensity;

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
	@Element(required=false)
	private String difficulty;

/**
 * <div class="en">
 *
 * Approximate or typical time it takes to work with or through this learning
 * object for the typical intended target audience.
 *
 * NOTE:--The " typical target audience" can be characterized by data elements
 * 5.6:Educational.Context and
 * 5.7:Educational.TypicalAgeRange.
 *
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
 *
 * não foi feita a verificação de consistência
 * was not made any consistence verification
 */
	@Element(required=false)
	private String typicalLearningTime;

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

	@ElementList(inline=true,required=false)
	List<IntendedEndUserRole> intendedEndUserRole;
	
	@ElementList(inline=true,required=false)
	private List<LearningResourceType> learningResourceType;
        
        @ElementList(inline=true,required=false)
	private List<TypicalAgeRange> typicalAgeRange;

	@ElementList(inline=true,required=false)
	private List<Description> description;
	
	@ElementList(inline=true,required=false)
	private List<Language> language;
	
	@ElementList(inline=true,required=false)
	private List<Context> context;

        @Element (name = "LearningContentType")
        private LearningContentType learningContentType;
        
        @Element (name = "Interaction")
        private Interaction interaction;
        
        @Element (name = "DidaticStrategy")
        private DidaticStrategy didaticStrategy;

    public Educational() {
    }
    	private List<String> toStringList(List<? extends TextElement> elements) {
		List<String> s = new ArrayList<String>();
		for(TextElement e : elements) {
			s.add(e.getText());
		}
		return s;
	}
    	
	public List<String> getIntendedEndUserRoles() {
	    return toStringList(intendedEndUserRole);
    	}
	
	public List<String> getDescriptions() {
	    return toStringList(description);
    	}
	
	public List<String> getLanguages() {
	    return toStringList(language);
    	}
	
	public List<String> getContexts() {
	    return toStringList(context);
    	}

    public void setContext(List<Context> context) {
        this.context = context;
    }

    public void setDescription(List<Description> description) {
        this.description = description;
    }

    public void setDidaticStrategy(DidaticStrategy didaticStrategy) {
        this.didaticStrategy = didaticStrategy;
    }

    public void setIntendedEndUserRole(List<IntendedEndUserRole> intendedEndUserRole) {
        this.intendedEndUserRole = intendedEndUserRole;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public void setLanguage(List<Language> language) {
        this.language = language;
    }

    public void setLearningContentType(LearningContentType learningContentType) {
        this.learningContentType = learningContentType;
    }

    public void setLearningResourceType(List<LearningResourceType> learningResourceType) {
        this.learningResourceType = learningResourceType;
    }

    public List<Context> getContext() {
        return context;
    }

    public List<Description> getDescription() {
        return description;
    }

    public DidaticStrategy getDidaticStrategy() {
        return didaticStrategy;
    }

    public List<IntendedEndUserRole> getIntendedEndUserRole() {
        return intendedEndUserRole;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public List<Language> getLanguage() {
        return language;
    }

    public LearningContentType getLearningContentType() {
        return learningContentType;
    }

    public List<LearningResourceType> getLearningResourceType() {
        return learningResourceType;
    }

    public List<TypicalAgeRange> getTypicalAgeRange() {
        return typicalAgeRange;
    }

	/**
	 * @return the interactivityType
	 */
	public String getInteractivityType() {
		return interactivityType;
	}

	/**
	 * @param interactivityType the interactivityType to set
	 */
	public void setInteractivityType(String interactivityType) {
		this.interactivityType = interactivityType;
	}

	/**
	 * @return the interactivityLevel
	 */
	public String getInteractivityLevel() {
		return interactivityLevel;
	}

	/**
	 * @param interactivityLevel the interactivityLevel to set
	 */
	public void setInteractivityLevel(String interactivityLevel) {
		this.interactivityLevel = interactivityLevel;
	}

	/**
	 * @return the semanticDensity
	 */
	public String getSemanticDensity() {
		return semanticDensity;
	}

	/**
	 * @param semanticDensity the semanticDensity to set
	 */
	public void setSemanticDensity(String semanticDensity) {
		this.semanticDensity = semanticDensity;
	}

	/**
	 * @return the difficulty
	 */
	public String getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the typicalLearningTime
	 */
	public String getTypicalLearningTime() {
		return typicalLearningTime;
	}

	/**
	 * @param typicalLearningTime the typicalLearningTime to set
	 */
	public void setTypicalLearningTime(String typicalLearningTime) {
		this.typicalLearningTime = typicalLearningTime;
	}


	/**
	 * @param typicalAgeRange the typicalAgeRange to set
	 */
	public void setTypicalAgeRange(List<TypicalAgeRange> typicalAgeRange) {
		this.typicalAgeRange = typicalAgeRange;
	}

	/**
	 * @return the learningResourceType
	 */
	public List<String> getLearningResourceTypes() {
		return toStringList(learningResourceType);
	}

	/**
	 * @param learningResourceType the learningResourceType to set
	 */
	public void setLearningResourceTypes(List<LearningResourceType> learningResourceType) {
		this.learningResourceType = learningResourceType;
	}



}
