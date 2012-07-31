/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.exceptions;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class FederationException extends Exception {

    /**
     * Creates a new instance of
     * <code>FederacaoException</code> without detail message.
     */
    public FederationException() {
    }

    /**
     * Constructs an instance of
     * <code>FederacaoException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public FederationException(String msg) {
        super(msg);
    }
}
