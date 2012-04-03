package OBAA.General;

import java.util.ArrayList;
import java.util.List;
import metadata.TextElement;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * <div class="en"> This category groups the general information that describes
 * this learning object as a whole.
 *
 * according to IEEE LOM http://ltsc.ieee.org/ </div>
 *
 * <div class="br"> Conjunto de metadados gerais.
 *
 * Adaptado de http://www.portalobaa.org </div>
 *
 * @author LuizRossi
 */
@Root(strict = false)
@Namespace(reference = "http://ltsc.ieee.org/xsd/LOM", prefix = "obaa")
public class General {
    /*
     * Title
     */

    @ElementList(inline = true)
    private List<Title> title = new ArrayList<Title>();
    @ElementList(inline = true, required = false)
    private List<Keyword> keyword = new ArrayList<Keyword>();
    @ElementList(inline = true, required = false)
    private List<Description> description = new ArrayList<Description>();
    @ElementList(inline = true, required = false)
    private List<Coverage> coverage = new ArrayList<Coverage>();
    @Element(required = false)
    private int aggregationLevel;
    @Element(required = false)
    private Identifier identifier;
    @Element(required = false)
    private String language;
    @Element(required = false)
    private Structure structure;

    public General() {
    }

    private List<String> toStringList(List<? extends TextElement> elements) {
        List<String> s = new ArrayList<String>();
        for (TextElement e : elements) {
            s.add(e.getText());
        }
        return s;
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

    public List<String> getDescriptions() {
        return toStringList(description);
    }

    public void addDescription(String title) {
        this.description.add(new Description(title));
    }

    public List<String> getCoverages() {
        return toStringList(coverage);
    }

    //Keywords
    public void setKeywords(List<Keyword> keywords) {
        this.keyword = keywords;
    }

    public List<String> getKeywords() {
        return toStringList(keyword);
    }

    public void addKeyword(String keyword) {
        this.keyword.add(new Keyword(keyword));
    }

    /**
     * @return the aggregationLevel
     */
    public int getAggregationLevel() {
        return aggregationLevel;
    }

    /**
     * @param aggregationLevel the aggregationLevel to set
     */
    public void setAggregationLevel(int aggregationLevel) {
        this.aggregationLevel = aggregationLevel;
    }

    /**
     * @return the identifier
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the structure
     */
    public String getStructure() {
        return structure.getStructure();
    }

    /**
     * @param structure the structure to set
     */
    public void setStructure(String structure) {
        this.structure.setStructure(structure);
    }
}
