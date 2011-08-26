/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * TODO: Converter parar JAXB
 */
package metadata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;

/**
 *
 * @author paulo
 */

class TextElement
{
	TextElement(String t) {
		text = t;
	}

	TextElement() {
	}
	
	@Text
	private String text;

	public String getText() {
		return text.trim();
	}
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Title extends TextElement {
	Title() {}
	Title(String t) {super(t);}
	
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Description extends TextElement {
	Description() {}
	Description(String s) { super(s); }
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Contributor extends TextElement {
	Contributor() {}
	Contributor(String s) { super(s); }
}


@Root(name="oai_dc:dc", strict=false)
@NamespaceList({
@Namespace(reference="http://www.openarchives.org/OAI/2.0/oai_dc/", prefix="oai_dc"),
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc"),
@Namespace(reference="http://www.w3.org/2001/XMLSchema-instance", prefix="xsi")})

public class DublinCore {
	
@Attribute(name="xsi:schemaLocation", empty="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd", required=false)
	private String xsi_schema; //não é muito elegante, mas funciona.
	

	DublinCore() {
		super();
	}

	private List<String> toStringList(List<? extends TextElement> elements) {
		List<String> s = new ArrayList<String>();
		for(TextElement e : elements) {
			s.add(e.getText());
		}
		return s;
	}
	
	public static DublinCore fromFilename(String filename) {
		DublinCore dc = new DublinCore();
		File xml = new File(filename);
		Serializer serializer = new Persister();

		try {	
			dc = serializer.read(DublinCore.class, xml);
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
		}
		
		return dc;
	}

	/* Title */
	@ElementList(inline=true)
	private List<Title> title = new ArrayList<Title>();
	

	public String getTitle() {
		return title.get(0).getText();
	}

	public List<String> getTitles() {
		return toStringList(title);
	}
	
	public void addTitle(String title) {
		this.title.add(new Title(title));
	}

	/* Description */
	@ElementList(inline=true)
	private List<Description> description = new ArrayList<Description>();

	public List<String> getDescriptions() {
		return toStringList(description);
	}
	
	public void addDescription(String s) {
		this.description.add(new Description(s));
	}

	/* Contributor */
	@ElementList(inline=true)
	private List<Contributor> contributor = new ArrayList<Contributor>();

	public void addContributor(String s) {
		this.contributor.add(new Contributor(s));
	}
	
	public List<String> getContributors() {
		return toStringList(contributor);
	}
		

	public String toXml() throws Exception {
		OutputStream o = new ByteArrayOutputStream();
		Serializer serializer = new Persister();
		serializer.write(this, o);
		
		return o.toString();
	}
}
