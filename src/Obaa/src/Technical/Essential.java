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
public class Essential {
    
    private String var;
    
    public Essential() {
        var = "";
    }

    public Essential(String var) {
        this.var = var;
    }
    
    public void setEssential(String var) {
        this.var = var;
    }
    
    public String getEssential() {
        return (this.var);
    }
}
