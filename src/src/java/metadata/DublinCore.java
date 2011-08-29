/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * 
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
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;

/**
 *
 * @author paulo
 * 
 */

/* TODO: Usar um gerador de código */

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

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Date extends TextElement {
	Date() {}
	Date(String s) { super(s); }
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Identifier extends TextElement {
	Identifier() {}
	Identifier(String s) { super(s); }
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Publisher extends TextElement {
	Publisher() {}
	Publisher(String s) { super(s); }
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Format extends TextElement {
	Format() {}
	Format(String s) { super(s); }
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Subject extends TextElement {
	Subject() {}
	Subject(String s) { super(s); }
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Type extends TextElement {
	Type() {}
	Type(String s) { super(s); }
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Relation extends TextElement {
	Relation() {}
	Relation(String s) { super(s); }
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Coverage extends TextElement {
	Coverage() {}
	Coverage(String s) { super(s); }
}

@Root
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc")
class Right extends TextElement {
	Right() {}
	Right(String s) { super(s); }
}

/**
 * <div class="en">
 * A simple implementation of the Dublin Core metadata
 * format, according to {@url http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd}.
 * 
 * All elements may appear 0 or more times, and are (AFAIK) free format. Therefore, we return
 * a <pre>{@code List<String> } </pre> with each occurance.
 * 
 * </div>
 * 
 * @author Paulo Schreiner
 */
@Root(name="oai_dc:dc", strict=false)
@NamespaceList({
@Namespace(reference="http://www.openarchives.org/OAI/2.0/oai_dc/", prefix="oai_dc"),
@Namespace(reference="http://purl.org/dc/elements/1.1/", prefix="dc"),
@Namespace(reference="http://www.w3.org/2001/XMLSchema-instance", prefix="xsi")})

public class DublinCore {
	
@Attribute(name="xsi:schemaLocation", empty="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd", required=false)
	private String xsi_schema; //não é muito elegante, mas funciona.
	

	/**
 	* Creates an empty DublinCore objetct.
	 */
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
	
	/**
	 * 
	 * @param filename Dublin Core XML file
	 * @return {@link DublinCore} object generated by unserializing filename.
	 */
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
	
	/* Subject */
	@ElementList(inline=true)
	private List<Subject> subject = new ArrayList<Subject>();

	public void addSubject(String s) {
		this.subject.add(new Subject(s));
	}
	
	public List<String> getSubjects() {
		return toStringList(subject);
	}
	
	/* Publisher */
	@ElementList(inline=true)
	private List<Publisher> publisher = new ArrayList<Publisher>();

	public void addPublisher(String s) {
		this.publisher.add(new Publisher(s));
	}
	
	public List<String> getPublishers() {
		return toStringList(publisher);
	}
	
	/* Format */
	@ElementList(inline=true)
	private List<Format> format = new ArrayList<Format>();

	public void addFormat(String s) {
		this.format.add(new Format(s));
	}
	
	public List<String> getFormats() {
		return toStringList(format);
	}


	/* Type */
	@ElementList(inline=true)
	private List<Type> type = new ArrayList<Type>();

	public void addType(String s) {
		this.type.add(new Type(s));
	}
	
	public List<String> getTypes() {
		return toStringList(type);
	}
	
	/* Identifier */
	@ElementList(inline=true)
	private List<Identifier> identifier = new ArrayList<Identifier>();

	public void addIdentifier(String s) {
		this.identifier.add(new Identifier(s));
	}
	
	public List<String> getIdentifiers() {
		return toStringList(identifier);
	}

	/* Relation */
	@ElementList(inline=true)
	private List<Relation> relation = new ArrayList<Relation>();

	public void addRelation(String s) {
		this.relation.add(new Relation(s));
	}
	
	public List<String> getRelations() {
		return toStringList(relation);
	}

	/* Date */
	@ElementList(inline=true)
	private List<Date> date = new ArrayList<Date>();

	public void addDate(String s) {
		this.date.add(new Date(s));
	}
	
	public List<String> getDates() {
		return toStringList(date);
	}
	
	/* Coverage */
	@ElementList(inline=true)
	private List<Coverage> coverage = new ArrayList<Coverage>();

	public void addCoverage(String s) {
		this.coverage.add(new Coverage(s));
	}
	
	public List<String> getCoverages() {
		return toStringList(coverage);
	}

	/* Right */
	@ElementList(inline=true)
	private List<Right> right = new ArrayList<Right>();

	public void addRight(String s) {
		this.right.add(new Right(s));
	}
	
	public List<String> getRights() {
		return toStringList(right);
	}
		

	/**
	 * 
	 * @return {@link String} containing the XML correspondin to the current object.
	 * @throws Exception In case a serializing error occurs
	 */
	public String toXml() throws Exception {
		OutputStream o = new ByteArrayOutputStream();
		Serializer serializer = new Persister();
		serializer.write(this, o);
		
		return o.toString();
	}
}
