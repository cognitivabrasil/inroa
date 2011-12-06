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
public class TaxonId {
    
    private String var;
    
    public TaxonId() {
        var = "";
    }

    public TaxonId(String var) {
        this.var = var;
    }
    
    public void setTaxonId(String var) {
        this.var = var;
    }
    
    public String getTaxonId() {
        return (this.var);
    }
}
