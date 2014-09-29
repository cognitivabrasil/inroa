package com.cognitivabrasil.feb.metadata;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import net.sf.saxon.TransformerFactoryImpl;

import org.xml.sax.InputSource;

/**
 *
 * @author paulo
 */
public class XSLTUtil {

    public static String formatXml(String xml) {
        try {
            Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            //serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            //serializer.setOutputProperty("{http://xml.customer.org/xslt}indent-amount", "2");
            Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(xml.getBytes())));
            StreamResult res = new StreamResult(new ByteArrayOutputStream());
            serializer.transform(xmlSource, res);
            return new String(((ByteArrayOutputStream) res.getOutputStream()).toByteArray());
        } catch (Exception e) {
            //TODO log error
            return xml;
        }
    }

    /**
     * Transforms from filenames
     *
     * @param sourceXml xml filename do be transformed
     * @param xslt stylesheet that does the transformation
     * @return transformed XML
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws FileNotFoundException
     */
    public static String transformFilename(String sourceXml, String xslt) throws TransformerConfigurationException, TransformerException, FileNotFoundException {
        return transform(new FileInputStream(new File(sourceXml)), new FileInputStream(new File(xslt)));
    }

    public static String transformString(String sourceID, String xslID)
            throws TransformerException, TransformerConfigurationException {

        // Create a transform factory instance.
        TransformerFactory tfactory = new TransformerFactoryImpl();

        // Create a transformer for the stylesheet.
        Transformer transformer = tfactory.newTransformer(new StreamSource(new File(xslID)));
//		 transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//		 transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // Transform the source XML to System.out.
        StringWriter sw = new StringWriter();
        transformer.transform(new StreamSource(new StringReader(sourceID)),
                new StreamResult(sw));

        return sw.toString();
    }

    private XSLTUtil() {
    }

    public static String transform(InputStream xml, InputStream xsl) throws TransformerException, TransformerConfigurationException {

        // Create a transform factory instance.
        TransformerFactory tfactory = new TransformerFactoryImpl();

        // Create a transformer for the stylesheet.
        Transformer transformer = tfactory.newTransformer(new StreamSource(xsl));

        // Transform the source XML to System.out.
        StringWriter sw = new StringWriter();
        transformer.transform(new StreamSource(xml),
                new StreamResult(sw));

        return sw.toString();
    }

}
