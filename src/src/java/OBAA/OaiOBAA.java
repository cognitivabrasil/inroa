/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * 
 */
package OBAA;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import metadata.DublinCore;
import metadata.Header;
import metadata.Request;
import metadata.TextElement;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Text;

/**
 *
 * @author paulo
 * 
 */


/* TODO: Usar um gerador de código */
@Root(name="metadata", strict=false)
class Metadata {
	
	@Element
	OBAA obaa;

	Metadata() {
		super();
	}

	OBAA getLom() {
		return obaa;
	}
}

@Root(name="resumptionToken", strict=false)
class ResumptionToken {
	
	@Attribute(required=false)
	private String expirationDate;

	@Text
	private String token;

	ResumptionToken() {
	}

	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

}

@Root(name="record", strict=false)
class Record {

	@Element(required=false)
	private metadata.Header header;
	
	@Element(required=false)
	Metadata metadata;

	Record() {
		super();
	}

	Metadata getMetadata() {
		return metadata;
	}

	/**
	 * @return the header
	 */
	public metadata.Header getHeader() {
		return header;
	}

}


@Root(name="OAI-PMH", strict=false)
@NamespaceList({
@Namespace(reference="http://www.openarchives.org/OAI/2.0/"),
@Namespace(reference="http://www.w3.org/2001/XMLSchema-instance", prefix="xsi")})
public class OaiOBAA {
	@Element
	private String responseDate;
	
	@Element
	Request request;

	@ElementList
	private List<Record> ListRecords;
	
	OaiOBAA() {
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
	public static OaiOBAA fromFilename(String filename) throws FileNotFoundException {
		return fromReader(new FileReader(new File(filename)));
	}
	
	public static OaiOBAA fromString(String s) {
		return fromReader(new StringReader(s));
	}
	
	public static OaiOBAA fromReader(Reader s) {
		OaiOBAA dc = new OaiOBAA();
		Serializer serializer = new Persister();

		try {	
			dc = serializer.read(OaiOBAA.class, s);
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
		}
		
		return dc;
	}
	
	public void toFilename(String filename) throws FileNotFoundException {
		try {
			toWriter(new FileWriter(new File(filename)));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		StringWriter s = new StringWriter();
		toWriter(s);
		return s.toString();
	}
	
	public void toWriter(Writer s) {
		Serializer serializer = new Persister();
		try {	
			serializer.write(this, s);
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
		}
	}
        
        /**
         * Gets the size.
         *
         * @return the number of ListRecords present in the OAI File.
         */
        public int getSize() {
            return ListRecords.size();
        }

	/**
	 * Gets the metadata (OBAA).
	 *
	 * @param index the index
	 * @return the metadata in OBAA format or NULL in case it represents a deleted document.
	 */
	public OBAA getMetadata(int index) {
		if(ListRecords.get(index).getMetadata() != null) {
			return ListRecords.get(index).getMetadata().getLom();
		}
		else {
			return null;
		}
	}

	public Header getHeader(int index) {
		return ListRecords.get(index).getHeader();
	}
	
	public Request getRequest() {
		return request;
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

	/**
	 * @return the responseDate
	 */
	public String getResponseDate() {
		return responseDate;
	}

	/**
	 * @param responseDate the responseDate to set
	 */
	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}
}
