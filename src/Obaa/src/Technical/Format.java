/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Technical;

/**
 *
 * <div class="en">
 *Technical datatype(s) of (all the components of) this learning object.
 * This data element shall be used to identify the software needed to access the 
 * learning object.
 * 
 * Example: "video/mpeg", "application/x-toolbook", "text/html"
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
public class Format {
    
    private String format;
    
    public Format() {
        format = "";
    }

    public Format(String format) {
        this.format = format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    public String getFormat() {
        return (this.format);
    }
}
