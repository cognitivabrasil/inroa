/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.exceptions;


/**
 *
 * @author Marcos Nunes
 */
public class RepositoryException extends Exception {
	/**
     * Creates a new instance of
     * <code>RepositoriosException</code> without detail message.
     */
    public RepositoryException() {
    }

    /**
     * Constructs an instance of
     * <code>RepositoriosException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RepositoryException(String msg) {
        super(msg);
    }

}
