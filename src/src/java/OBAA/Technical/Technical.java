/**
 * 
 */
package OBAA.Technical;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


/**
 * @author Paulo Schreiner
 * 
 * Stub implementation, only implemented the location subfield.
 * 
 * TODO: Implement everything.
 *
 */
@Root(strict=false)
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Technical {
	@ElementList(required=false, inline=true)
	private List<Location> location;

	/**
	 * @return the first location that starts with http://,
	 * null otherwise
	 */
	public String getLocation() {
		for(Location l : location) {
			if(l.getText().startsWith("http://")) {
				return l.getText();
			}
		}
		return null;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(List<Location> location) {
		this.location = location;
	}
	
	
}
