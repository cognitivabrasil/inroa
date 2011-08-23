package OBAA.General;

/**
 * <div class="en">
 * The name or designator of the identification
 * or cataloging scheme for this entry. A
 * namespace scheme.
 *
 * Example: "ISBN", "ARIADNE", "URI"
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 *
 * <div class="br">
 * Responsável pela identificação.
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossi
 */
public class Catalog {
    private String catalog;

    public Catalog() {
        catalog = "";
    }

    public Catalog(String catalog) {
        this.catalog = catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCatalog() {
        return catalog;
    }

}
