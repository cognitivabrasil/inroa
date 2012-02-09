/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author paulo
 */
@Root(name="header", strict=false)
public class Header {
	
	@Element
	private String identifier;

	@Element
	private String datestamp;

	@ElementList(inline=true,required=false)
	private List<Spec> setSpec;
	

	Header() {
		super();
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the datestamp
	 */
	public String getDatestamp() {
		return datestamp;
	}

	/**
	 * @param datestamp the datestamp to set
	 */
	public void setDatestamp(String datestamp) {
		this.datestamp = datestamp;
	}

	/**
	 * @return the setSpec
	 */
	//public String getSetSpec() {
	//	return setSpec;
	//}

	/**
	 * @param setSpec the setSpec to set
	 */
	//public void setSetSpec(String setSpec) {
	//	this.setSpec = setSpec;
	//}

}
