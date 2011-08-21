/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 *
 * @author paulo
 */

@Root
class Title
{
	@Text
	private String text;

	public String getText() {
		return text;
	}
}

@Root(strict=false)
//@Default(DefaultType.PROPERTY)
@Namespace(reference="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd", prefix="dc")
public class DublinCore {


	@ElementList(inline=true)
	private List<Title> title = new ArrayList<Title>();
	
	DublinCore() {
		super();
	}

	public String getTitle() {
		return title.get(0).getText();
	}
	
//	public void setTitle(String title) {
//		this.title.set(0,title);
//	}
}
