package General;

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
public class Identifier {
    private Catalog catalog;
    private Entry entry;

    public Identifier() {
        catalog = new Catalog();
        entry = new Entry();
    }

    public Identifier(String catalog, String entry) {
        this.catalog = new Catalog (catalog);
        this.entry = new Entry (entry);
    }

    public void setCatalog(String catalog) {
        this.catalog = new Catalog (catalog);
    }

    public void setEntry(String entry) {
        this.entry = new Entry (entry);
    }

    public String getCatalog() {
        return catalog.getCatalog();
    }

    public String getEntry() {
        return entry.getEntry();
    }
}
