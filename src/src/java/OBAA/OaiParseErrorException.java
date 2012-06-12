package OBAA;

public class OaiParseErrorException extends OaiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4617149715766730997L;

	public OaiParseErrorException(String description, String url) {
		super("parseErrorException", description, url);
	}
	
	public OaiParseErrorException(String description, String url, Throwable cause) {
		super("parseErrorException", description, url, cause);
	}
}
