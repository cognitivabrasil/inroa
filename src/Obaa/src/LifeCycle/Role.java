/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LifeCycle;

/**
 *
 * <div class="en">
 *
 * Kind of contribution.
 * NOTE 1:--Minimally, the Author(s) of the learning object should be described.
 * 
 * Value Space:
 * 
 * author, publisher, unknown, initiator, terminator, validator, editor,
 * graphical_designer, technical, implementer, content_provider, 
 * technical_validator, educational_validator, script_writer, 
 * instructional_designer, subject_matter_expert
 * 
 * NOTE 2:--"terminator" is the entity that made the learning object unavailable.
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * 
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class Role {

    private String role;

    private enum setOfTerms {

        author, publisher, unknown, initiator, terminator,
        validator, editor, graphical_designer, technical, implementer,
        content_provider, technical_validator, educational_validator, script_writer,
        instructional_designer, subject_matter_expert
    }

    public Role() {
        role = "";
    }

    public Role(String role) {
        setRole(role);
    }
    
    public void setRole(String role) {
             try {
            setOfTerms.valueOf(role);
            this.role = role;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Structure must be one of: author, publisher, unknown, initiator, terminator,"
                    + "validator, editor, graphical_designer, technical, implementer,"
                    + "content_provider, technical_validator, educational_validator, script_writer,"
                    + "instructional_designer, subject_matter_expert");      
        }
    }
    
    public String getRole() {
        return (this.role);
    }
}
