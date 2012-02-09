package OBAA.General;

import metadata.TextElement;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


/**
 *
 * <div class="en">
 * A textual description of the content of this learning object.
 *
 * NOTE:--This description need not be in language
 * and terms appropriate for the users of the learning
 * object being described. The description should be
 * in language and terms appropriate for those that
 * decide whether or not the learning object being
 * described is appropriate and relevant for the users.
 *
 * There is no language treatment implemented in this metadata.
 *
 * </div>
 *
 * <div class="br">
 *
 * </div>
 * @author LuizRossi
 */
@Root
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Description extends TextElement {
	Description() {}
	Description(String t) {super(t);}
	
}


