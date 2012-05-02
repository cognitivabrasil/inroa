/**
 * 
 */
package OBAA.Technical;

import metadata.TextElement;

import org.simpleframework.xml.Namespace;

/**
 * @author paulo
 *
 */

@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Location extends TextElement {
	Location() {}
	Location(String t) {super(t);}
	
}