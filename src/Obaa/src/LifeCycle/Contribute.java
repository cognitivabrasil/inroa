package LifeCycle;

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
    
    private Entity entity;
    private Role role;
    private Date date;
    
    public Contribute() {
        entity = new Entity();
        role = new Role();
        date = new Date(); 
        
    }

    public Contribute(String entity, String role, String date) {
      
    }

    public void setDate(String date) {
        this.date.setDate(date);
    }

    public void setEntity(String entity) {
        this.entity.setEntity(entity);
    }

    public void setRole(String role) {
        this.role.setRole(role);
    }

    public String getDate() {
        return date.getDateUnvalidate();
    }

    public String getEntity() {
        return entity.getEntity();
    }

    public String getRole() {
        return role.getRole();
    }
}
