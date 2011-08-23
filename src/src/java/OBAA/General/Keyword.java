package OBAA.General;

/**
 * <div class="en">
 * A keyword or phrase describing the topic of this learning object.
 * This data element should not be used for characteristics that can be described by other data elements.
 * </div>
 *
 * <div class="br">
 *
 * 
 * </div>
 * @author LuizRossi
 */
public class Keyword {

    private String keyword;

    public Keyword() {
        keyword = "";
    }

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
    
}
