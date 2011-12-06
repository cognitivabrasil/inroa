/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classification;

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
public class Source {
    
    private String var;
    
    public Source() {
        var = "";
    }

    public Source(String var) {
        this.var = var;
    }
    
    public void setSource(String var) {
        this.var = var;
    }
    
    public String getSource() {
        return (this.var);
    }
}
