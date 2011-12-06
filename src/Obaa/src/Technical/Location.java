/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Technical;

/**
 *
 * <div class="en">
 *
 * A string that is used to access this learning object. It may be a location 
 * (e.g., Universal Resource Locator), or a method that resolves to a location 
 * (e.g., Universal Resource Identifier). The first element of this list shall 
 * be the preferable location.
 * 
 * NOTE:--This is where the learning object described by this metadata instance 
 * is physically located.
 * 
 * Example: "http://host/id"
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
public class Location {
    
    private String location;
    
    public Location() {
        location = "";
    }

    public Location(String location) {
        this.location = location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getLocation() {
        return (this.location);
    }
}
