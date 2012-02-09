package OBAA.Educational;

import metadata.TextElement;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

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

@Root(strict=false)
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Language extends TextElement {
    public Language() {
    }

}
