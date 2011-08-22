/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata.conversor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Schreiner
 * 
 * Essa classe simula um mini-objeto de metadados.
 * Usada somente para testes.
 */
public class MockOne {
	private String title;
	private List<String> creators;
	MockOne() {
		super();
		creators = new ArrayList<String>();
	}

	public void addCreator(String c) {
		creators.add(c);
	}

	public void setCreators(List<String> c) {
		creators = c;
	}

	public List<String> getCreators() {
		return creators;
	}
	
	public void setTitle(String t) {
		title = t;
	}
	public String getTitle() {
		return title;
	}
	
}