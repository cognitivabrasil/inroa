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
public class Version {
    
    private String var;
    
    public Version() {
        var = "";
    }

    public Version(String var) {
        this.var = var;
    }
    
    public void setMinimumVersion(String var) {
        this.var = var;
    }
    
    public String getMinimumVersion() {
        return (this.var);
    }
}
