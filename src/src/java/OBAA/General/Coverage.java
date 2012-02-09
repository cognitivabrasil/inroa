package OBAA.General;

import metadata.TextElement;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
/**
 * <div class="en">
 * The time, culture, geography or region to which this learning object applies.
 * The extent or scope of the content of the learning object. Coverage will typically
 * include spatial location (a place name or geographic coordinates), temporal period (a
 * period label, date, or date range) or jurisdiction (such as a named administrative
 * entity). Recommended best practice is to select a value from a controlled vocabulary
 * (for example, the Thesaurus of Geographic Names [TGN]) and that, where appropriate,
 * named places or time periods be used in preference to numeric identifiers such as sets
 * of coordinates or date ranges.
 * NOTE 1:--This is the definition from the Dublin Core Metadata Element Set, version 1.1
 *
 * Example:
 * ("16th century France")
 * NOTE 2:--A learning object could be about farming
 * in 16th century France: in that case, its subject can be described with
 * 1.5:General.Keyword=("farming") and its
 * 1.6:General.Coverage can be ("16th century France").
 *
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 *
 * <div class="br">
 *
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 * @author LuizRossi
 */

@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Coverage extends TextElement {
	Coverage() {}
	Coverage(String t) {super(t);}
	
}
