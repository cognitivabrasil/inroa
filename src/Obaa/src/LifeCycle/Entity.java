/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LifeCycle;

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
 *
 * @author LuizRossiNote
 */
public class Entity {
    
    private String entity;
    
    public Entity() {
        entity = "";
    }

    public Entity(String entity) {
        this.entity = entity;
    }
    
    public void setEntity(String entity) {
        this.entity = entity;
    }
    
    public String getEntity() {
        return (this.entity);
    }
}
