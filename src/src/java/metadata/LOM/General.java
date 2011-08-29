package metadata.LOM;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


/**
 * <div class="en">
 * This category groups the general information
 * that describes this learning object as a whole.
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 *</div>
 *
 * <div class="br">
 * Conjunto de metadados gerais.
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 *
 * @author LuizRossi
 */

@Root(strict=false)
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="lom")
public class General {
	/* Title */
	@ElementList(inline=true)
	private List<Title> title = new ArrayList<Title>();

    public General() {

    }


    //Titles
    public void setTitles(List<Title> titles) {
        this.title = titles;
    }
    public List<Title> getTitles() {
        return title;
    }
    public void addTitle(String title) {
        this.title.add(new Title(title));
    }


}
