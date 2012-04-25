/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

/**
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */


public interface MetadataConversorInterface {
    
    /**
     * Converts the XML represent by the string to the OBAA format.
     *
     * @param XML in some metadata formata
     * @return XML in the OBAA format
     */
    String toObaa(String s);
    
}
