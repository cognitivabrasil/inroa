/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OBAA.LifeCycle;

import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 *
 * This category describes the history and current state of this learning object and those
 * entities that have affected this learning object during its evolution.
 *
 * @author Jorjao
 */

@Root(strict=false)
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class LifeCycle {
	@Element(required=false)
	private Status status;

	@Element(required=false)
	private String version;

	@ElementList(required=false,inline=true)
	private List<Contribute> contribute;
	
	public LifeCycle() {
		super();
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
            if(status == null)
                return null;
            else
		return status.toString();
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status.setStatus(status);
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public List<Contribute> getContribute() {
		return contribute;
	}
	
}
