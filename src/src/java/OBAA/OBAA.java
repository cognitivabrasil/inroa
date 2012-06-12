/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * 
 */
package OBAA;

import OBAA.Accessibility.Accessibility;
import OBAA.Classification.Classification;
import OBAA.Educational.Educational;
import OBAA.LifeCycle.LifeCycle;
import OBAA.General.General;
import OBAA.Rights.Rights;
import OBAA.Technical.Technical;
import OBAA.Metametadata.Metametadata;
import OBAA.Relation.Relation;
import OBAA.SegmentInformationTable.SegmentInformationTable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import metadata.TextElement;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
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


@Root(name="obaa:obaa", strict=false)
@NamespaceList({
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa"),
@Namespace(reference="http://www.w3.org/2001/XMLSchema-instance", prefix="xsi")})
public class OBAA {
	
@Attribute(name="xsi:schemaLocation", empty="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/obaav1.0/lom.xsd", required=false)
	private String xsi_schema; //não é muito elegante, mas funciona.

	@Element(required=true)
	private General general;
	
	@Element(required=false)
	private LifeCycle lifeCycle;

	@Element(required=false)
	private Rights rights;

	@Element(required=false)
	private Educational educational;
	
	@Element(required=false)
	private Technical technical;
        
        @Element(required=false)
        Metametadata metametadata;    
        
        @ElementList (name = "Relations", required = false) // TODO: tem certeza que não tem q ser inline?
        Set<Relation> relations;
        
        
        Set<Annotation> annotations;
        Set<Classification> classifications;
        Accessibility accessibility;
        Set<SegmentInformationTable> segmentsInformationTable;

	/**
 	* Creates an empty DublinCore objetct.
	 */
	public OBAA() {
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
	 * @param filename OBAA XML file
	 * @return {@link OBAAA} object generated by unserializing filename.
	 */

	public static OBAA fromFilename(String filename) throws FileNotFoundException {
		return fromReader(new FileReader(new File(filename)));
	}
	
	public static OBAA fromString(String s) {
		return fromReader(new StringReader(s));
	}
	
	
	public static OBAA fromReader(Reader s) {
		OBAA dc = new OBAA();
		Serializer serializer = new Persister();

		try {	
			dc = serializer.read(OBAA.class, s);
		}
		catch(java.lang.Exception e) {
			throw new RuntimeException(e);
		}		
		return dc;	}

	
	public General getGeneral() {
		return general;
	}
        
        public void setGeneral(General g) {
            general = g;
        }

	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	public List<String> getTitles() {
		return toStringList(getGeneral().getTitles());
	}
        
	
	public List<String> getKeywords() {
		return getGeneral().getKeywords();
	}
        

	//public List<String> getTitles() {
	//	return toStringList(title);
	//}
	
	//public void addTitle(String title) {
	//	this.title.add(new Title(title));
	//}


	/**
	 * 
	 * @return {@link String} containing the XML correspondin to the current object.
	 * @throws Exception In case a serializing error occurs
	 */
	public String toXml() {
		OutputStream o = new ByteArrayOutputStream();
		Serializer serializer = new Persister();
		// TODO: Better exceptions
		try {
			serializer.write(this, o);
		} catch (Exception e) {
			throw new RuntimeException("Error while serializing OBAA", e);
		}
		
		return o.toString();
	}

	/**
	 * @return the rights
	 */
	public Rights getRights() {
		return rights;
	}

	/**
	 * @param rights the rights to set
	 */
	public void setRights(Rights rights) {
		this.rights = rights;
	}

	/**
	 * @return the educational
	 */
	public Educational getEducational() {
		return educational;
	}

	/**
	 * @param educational the educational to set
	 */
	public void setEducational(Educational educational) {
		this.educational = educational;
	}

	/**
	 * @return the technical
	 */
	public Technical getTechnical() {
		return technical;
	}

	/**
	 * @param technical the technical to set
	 */
	public void setTechnical(Technical technical) {
		this.technical = technical;
	}


}
