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
public class Protocol {
    
    private String var;
    
    public Protocol() {
        var = "";
    }

    public Protocol(String var) {
        this.var = var;
    }
    
    public void setProtocol(String var) {
        this.var = var;
    }
    
    public String getProtocol() {
        return (this.var);
    }
}
