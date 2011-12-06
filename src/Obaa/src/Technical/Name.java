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
public class Name {
    
    private String var;
    
    public Name() {
        var = "";
    }

    public Name(String var) {
        this.var = var;
    }
    
    public void setName(String var) {
        this.var = var;
    }
    
    public String getName() {
        return (this.var);
    }
}
