/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata.conversor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 * @author Paulo Schreiner
 * 
 * Essa classe simula um mini-objeto de metadados.
 * Usada somente para testes.
 */
public class MockTwo {			
	private String titulo;
	private List<String> authors;
	MockTwo() {
		super();
		authors = new ArrayList<String>();
	}

	public void addAuthor(String c) {
		authors.add(c);
	}

	public void setAuthors(List<String> c) {
		authors = c;
	}

	public List<String> getAuthors() {
		return authors;
	}
	
	public void setTitulo(String t) {
		titulo = t;
	}
	public String getTitulo() {
		return titulo;
	}
		
	
}
