package OBAA.LifeCycle;

import java.util.ArrayList;
import java.util.List;
import metadata.TextElement;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 *
 * <div class="en">
 *
 * Those entities (i.e., people, organizations)that have contributed to the 
 * state of this learning object during its life cycle (e.g.,creation, edits, 
 * publication).
 * 
 * NOTE 1:--This data element is different from Meta-Metadata.Contribute.

 * NOTE 2:--Contributions should be considered in a very broad sense here, as 
 * all actions that affect the state of the learning object.
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */

@Root(strict=false)
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Contribute {
    
    @ElementList(inline=true)
    private List<Entity> entity;

    @Element
    private Role role;

    @Element(required=false)
    private String date;
    
    public Contribute() {
       	super(); 
    }

    private List<String> toStringList(List<? extends TextElement> elements) {
		List<String> s = new ArrayList<String>();
		for(TextElement e : elements) {
			s.add(e.getText());
		}
		return s;
	}

    public String getEntity() {
	    return entity.get(0).toString();
    }	   

    public List<String> getEntities() {
	    return toStringList(entity);
	    
    }

    public String getDate() {
	    return date;

    }
    
    public String getRole() {
	    return role.getRole();

    }
    
    public void setRole(String r) {
	    role.setRole(r);

    }

    //TODO:
}
