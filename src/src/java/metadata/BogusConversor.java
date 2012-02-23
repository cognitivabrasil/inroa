/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

/**
 *
 * @author paulo
 */
public class BogusConversor implements MetadataConversorInterface {
    
    public String toObaa(String s) {
        System.out.println("Converting to OBAA...");
        return s.replaceAll("<lom:title>", "<lom:title>Converted: ");
    }
}
