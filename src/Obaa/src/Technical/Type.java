/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Technical;

/**
 *
 * <div class="en">
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
public class Type {
    
    private String var;
    
    public Type() {
        var = "";
    }

    public Type(String var) {
        this.var = var;
    }
    
    public void setType(String var) {
        this.var = var;
    }
    
    public String getType() {
        return (this.var);
    }
}
