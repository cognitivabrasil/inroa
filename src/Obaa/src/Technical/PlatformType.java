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
public class PlatformType {
    
    private String var;
    
    public PlatformType() {
        var = "";
    }

    public PlatformType(String var) {
        this.var = var;
    }
    
    public void setPlatformType(String var) {
        this.var = var;
    }
    
    public String getPlatformType() {
        return (this.var);
    }
}
