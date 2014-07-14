/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.metadata;

import metadata.MetadataConversorInterface;

/**
 * 
 * @author paulo
 */
public class XsltConversorDefault implements MetadataConversorInterface {

	@Override
	public final String toObaa(final String s) {
		String fooXSL = "src/xslt/lom2obaa_full.xsl"; // input xsl

		try {
			return XSLTUtil.transformString(s, fooXSL);
		} catch (Exception e) {
			throw new RuntimeException("Error aplying XSLT");
		}

	}

}
