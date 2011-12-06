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
public class SupportedPlatform {
    
    private String var;
    
    public SupportedPlatform() {
        var = "";
    }

    public SupportedPlatform(String var) {
        this.var = var;
    }
    
    public void setSupportedPlatform(String var) {
        this.var = var;
    }
    
    public String getSupportedPlatform() {
        return (this.var);
    }
}
