/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OBAA.General;

import metadata.TextElement;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 *
 * @author paulo
 */
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Keyword extends TextElement {
	Keyword() {}
	Keyword(String t) {super(t);}
	
}
