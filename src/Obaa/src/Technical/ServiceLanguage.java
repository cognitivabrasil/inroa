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
public class ServiceLanguage {
    
    private String var;
    
    public ServiceLanguage() {
        var = "";
    }

    public ServiceLanguage(String var) {
        this.var = var;
    }
    
    public void setServiceLanguage(String var) {
        this.var = var;
    }
    
    public String getServiceLanguage() {
        return (this.var);
    }
}
