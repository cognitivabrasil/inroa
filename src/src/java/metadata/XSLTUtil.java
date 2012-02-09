/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.io.File;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author paulo
 */
public class XSLTUtil {
	public static void handleException(Exception ex) {

     	System.out.println("EXCEPTION: " + ex);
     	ex.printStackTrace();
}

	public static String transform (String sourceID, String xslID)
		throws TransformerException, TransformerConfigurationException {

	        // Create a transform factory instance.
       		 TransformerFactory tfactory = TransformerFactory.newInstance();

		 // Create a transformer for the stylesheet.
		 Transformer transformer = tfactory.newTransformer(new StreamSource(new File(xslID)));

		 // Transform the source XML to System.out.
		 StringWriter sw = new StringWriter();
 		 transformer.transform(new StreamSource(new File(sourceID)),
 		 new StreamResult(sw));

		return sw.toString();
	}
	
	private XSLTUtil() {
	}
	
}
