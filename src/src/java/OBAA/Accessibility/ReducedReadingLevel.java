/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OBAA.Accessibility;

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
public class ReducedReadingLevel {
    
    private String var;
    
    public ReducedReadingLevel() {
        var = "";
    }

    public ReducedReadingLevel(String var) {
        this.var = var;
    }
    
    public void setReducedReadingLevel(String var) {
        this.var = var;
    }
    
    public String getReducedReadingLevel() {
        return (this.var);
    }
}
