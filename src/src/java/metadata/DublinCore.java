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

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Title
{
	Title(String t) {
		text = t;
	}

	Title() {
	}
	
	@Text
	private String text;

	public String getText() {
		return text;
	}
}

@Root(name="oai_dc:dc", strict=false)
@NamespaceList({
@Namespace(reference="http://www.openarchives.org/OAI/2.0/oai_dc/", prefix="oai_dc"),
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc"),
@Namespace(reference="http://www.w3.org/2001/XMLSchema-instance", prefix="xsi")})

public class DublinCore {
	
@Attribute(name="xsi:schemaLocation", empty="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd", required=false)
	private String xsi_schema; //não é muito elegante, mas funciona.
	

	@ElementList(inline=true)
	private List<Title> title = new ArrayList<Title>();
	
	DublinCore() {
		super();
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
		

	public String getTitle() {
		return title.get(0).getText();
	}


	
	public void addTitle(String title) {
		this.title.add(new Title(title));
	}

	public String toXml() throws Exception {
		OutputStream o = new ByteArrayOutputStream();
		Serializer serializer = new Persister();
		serializer.write(this, o);
		
		return o.toString();
	}
}
