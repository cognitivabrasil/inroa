/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata.conversor;

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
	MockTwo() {
		super();
	}
	
	public void addTitulo(String t) {
		titulo = t;
	}
	public String getTitulo() {
		return titulo;
	}
		
	
}
