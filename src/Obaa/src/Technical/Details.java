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
public class Details {
    
    private String var;
    
    public Details() {
        var = "";
    }

    public Details(String var) {
        this.var = var;
    }
    
    public void setDetails(String var) {
        this.var = var;
    }
    
    public String getDetails() {
        return (this.var);
    }
}
