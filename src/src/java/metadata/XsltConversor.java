
package metadata;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * This class is a basic implementation of {@link MetadataConversorInterface}
 * using XSLT as the transformation engine.
 * A new conversor receives a XSLT string as input.
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
public class XsltConversor implements MetadataConversorInterface {

	Transformer t;

	/**
	 * Instantiates a new xslt conversor.
	 *
	 * @param xsl The XSLT (as a String) to be used in this conversor. 
	 * Should be able to convert from the input format to OBAA.
	 */
	public XsltConversor(String xsl) {
		// Create a transform factory instance.
		TransformerFactory tfactory = TransformerFactory.newInstance();

		// Create a transformer for the stylesheet.
		try {
			t = tfactory.newTransformer(new StreamSource(new StringReader(xsl)));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao configurar conversor XSLT");
		}

	}

	/* (non-Javadoc)
	 * @see metadata.MetadataConversorInterface#toObaa(java.lang.String)
	 */
	@Override
	public final String toObaa(final String s) {
		// Transform the source XML to System.out.
		StringWriter sw = new StringWriter();
		try {
			t.transform(new StreamSource(new StringReader(s)),
					new StreamResult(sw));
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao realizar a conversão com XSLT", e);
		}

		return sw.toString();
	}

	public String toObaaFromFile(File inputXmlFile) {
		// Transform the source XML to System.out.
		StringWriter sw = new StringWriter();
		try {
			t.transform(new StreamSource(inputXmlFile),
					new StreamResult(sw));
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao realizar a conversão com XSLT", e);
		}
		return sw.toString();
	}

}
