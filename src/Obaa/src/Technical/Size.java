/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Technical;

/**
 *
 * <div class="en">
 * The size of the digital learning object in bytes (octets). The size is 
 * represented as a decimal value (radix 10). Consequently, only the digits "0" 
 * through "9" should be used. The unit is bytes, not Mbytes, GB, etc.
 * 
 * This data element shall refer to the actual size of this learning object. 
 * If the learning object is compressed, then this data element shall refer to 
 * the uncompressed size.
 * 
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
public class Size {
    
    private String size;
    
    public Size() {
        size = "";
    }

    public Size(String size) {
        this.size = size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public String getSize() {
        return (this.size);
    }
}
