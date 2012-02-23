/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.Attribute;

/**
 *
 * @author paulo
 */
@Root(name="request", strict=false)
public class Request {
	
	@Text(required=false)
	private String uri;

	@Attribute(required=false)
	private String metadataPrefix;

	@Attribute(required=false)
	private String verb;

	
	Request() {
		super();
	}

	/**
	 * @return the uri
	 */
	public String getURI() {
		return uri;
	}

	/**
	 * @return the metadataPrefix
	 */
	public String getMetadataPrefix() {
		return metadataPrefix;
	}

	/**
	 * @return the verb
	 */
	public String getVerb() {
		return verb;
	}

}
