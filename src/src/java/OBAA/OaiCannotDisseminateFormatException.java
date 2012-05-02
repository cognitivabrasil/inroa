/**
 * 
 */
package OBAA;

/**
 * @author paulo
 *
 */
public class OaiCannotDisseminateFormatException extends OaiException {

	public OaiCannotDisseminateFormatException(String description, String url) {
		super("cannotDisseminateFormat", description, url);
	}
}
