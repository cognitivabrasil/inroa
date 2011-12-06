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
public class Duration {
    
    private String var;
    
    public Duration() {
        var = "";
    }

    public Duration(String var) {
        this.var = var;
    }
    
    public void setDuration(String var) {
        this.var = var;
    }
    
    public String getDuration() {
        return (this.var);
    }
}
