/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata.conversor;

/**
 *
 * @author Paulo Schreiner
 * 
 * Essa classe simula um mini-objeto de metadados.
 * Usada somente para testes.
 */
public class MockOne {
	private String title;
	MockOne() {
		super();
	}
	
	public void addTitle(String t) {
		title = t;
	}
	public String getTitle() {
		return title;
	}
	
}