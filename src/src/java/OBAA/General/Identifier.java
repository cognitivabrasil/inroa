package OBAA.General;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * <div class="en">
 * A globally unique label that identifies this
 * learning object.
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 *
 * <div class="br">
 * Identificador único do objeto de aprendizagem.
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossi
 */

@Root(strict=false)
@Namespace(reference="http://ltsc.ieee.org/xsd/LOM", prefix="obaa")
public class Identifier {
    @Element(required=false)	
    private String catalog;

    @Element(required=false)
    private String entry;

    public Identifier() {
    }

    public Identifier(String catalog, String entry) {
        this.catalog = catalog;
        this.entry = entry;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getEntry() {
        return entry;
    }
}
