/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import org.simpleframework.xml.Text;

/**
 *
 * @author paulo
 */
public class TextElement
{
	public TextElement(String t) {
		text = t;
	}
	
	public void setText(String t) {
		text = t;
	}

	public TextElement() {
	}
	
	@Text(required=false)
	private String text;

	public String getText() {
		return text.trim();
	}

	public String toString() {
		return getText();
	}
}
