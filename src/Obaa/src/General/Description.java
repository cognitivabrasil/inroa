package General;

/**
 *
 * <div class="en">
 * A textual description of the content of this learning object.
 *
 * NOTE:--This description need not be in language
 * and terms appropriate for the users of the learning
 * object being described. The description should be
 * in language and terms appropriate for those that
 * decide whether or not the learning object being
 * described is appropriate and relevant for the users.
 *
 * There is no language treatment implemented in this metadata.
 *
 * </div>
 *
 * <div class="br">
 *
 * </div>
 * @author LuizRossi
 */
public class Description {

    private String description;

    public Description() {
        description = "";
    }

    public Description(String description) {
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
