/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

/**
 *
 * @author paulo
 */
public class XsltConversorDefault implements MetadataConversorInterface {
    
    public String toObaa(String s) {
		String foo_xsl = "src/xslt/lom2obaa_full.xsl"; //input xsl

                try {
		return XSLTUtil.transformString(s, foo_xsl);//.replaceAll("lom:", "obaa:");
                }
                catch(Exception e) {
                    throw new RuntimeException("Error aplying XSLT");
                }
   
}
    
}
