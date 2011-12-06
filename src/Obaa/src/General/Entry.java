package General;

/**
 *
 * <div class="en">
 * The value of the identifier within the
 * identification or cataloging scheme that
 * designates or identifies this learning object. A
 * namespace specific string.
 *
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 *
 * </div>
 * <div class="br">
 * Valor da identificação.
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossi
 */
public class Entry {

    private String entry;

    public Entry() {
        entry = "";
    }

    public Entry(String entry) {
        this.entry = entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getEntry() {
        return entry;
    }

}
