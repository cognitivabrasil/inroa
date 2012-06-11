/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OBAA.Technical;

/**
 *
 * <div class="en">
 *
 * Name of the required technology to use this learning object.

 * NOTE 1:--The value for this data element may be derived from 4.1:Technical.
 * Format automatically, e.g., "video/mpeg" implies "multi-os".
 * NOTE 2:--This vocabulary includes most values in common use at the time that 
 * this Standard was approved.
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

public class Name {
    
    private String name;
    private enum setOfTerms {
        pcDos, msWindows, macOS, unix, multiOs, none, any, netscapeComunicator, msInternetExplorer,
opera, amaya, mozillaFirefox, appleSafari, googleChrome };
    
    public Name() {
        name = "";
    }
    
   public void setName (String name) {
     try {
            setOfTerms.valueOf(name);
            this.name = name;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Name must be pcDos, msWindows, macOS, unix, multiOs, none, any, netscapeComunicator, msInternetExplorer, opera, amaya, mozillaFirefox, appleSafari or googleChrome");
        }
    }
    
    public String getName() {
        return (this.name);
    }
}
