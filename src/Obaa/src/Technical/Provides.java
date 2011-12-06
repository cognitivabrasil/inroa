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
public class Provides {
    
    private String var;
    
    public Provides() {
        var = "";
    }

    public Provides(String var) {
        this.var = var;
    }
    
    public void setProvides(String var) {
        this.var = var;
    }
    
    public String getProvides() {
        return (this.var);
    }
}
