/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Accessibility;

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
public class Verbatim {
    
    private String var;
    
    public Verbatim() {
        var = "";
    }

    public Verbatim(String var) {
        this.var = var;
    }
    
    public void setVerbatim(String var) {
        this.var = var;
    }
    
    public String getVerbatim() {
        return (this.var);
    }
}
