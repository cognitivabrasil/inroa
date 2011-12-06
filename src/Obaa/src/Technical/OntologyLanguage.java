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
public class OntologyLanguage {
    
    private String var;
    
    public OntologyLanguage() {
        var = "";
    }

    public OntologyLanguage(String var) {
        this.var = var;
    }
    
    public void setOntologyLanguage(String var) {
        this.var = var;
    }
    
    public String getOntologyLanguage() {
        return (this.var);
    }
}
