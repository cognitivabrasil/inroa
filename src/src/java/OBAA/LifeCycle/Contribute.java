package OBAA.LifeCycle;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

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
public class Contribute {
    
    @ElementList(inline=true)
    private List<Entity> entity;

    @Element
    private Role role;

    @Element(required=false)
    private String date;
    
    public Contribute() {
        entity = new ArrayList<Entity>();
        role = new Role();
        date = ""; 
        
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
 *
 * <div class="en">
 * The identification of and information about entities (i.e., people, organizations)
 * contributing to this learning object. The entities shall be ordered as most relevant first
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 */
    public void addEntity(Entity entity) {
        this.entity.add(entity);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role.setRole(role);
    }

    public String getDate() {
        return date;

    }

    public String getRole() {
        return role.getRole();
    }
    
    public String getFirstEntity() {
        return entity.get(0).toString();
    }
    
    public List<Entity> getEntity() {
        return entity;
    }
}