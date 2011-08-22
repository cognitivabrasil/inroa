/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata.conversor;

import java.lang.reflect.Method;
import java.lang.IllegalArgumentException;

/**
 *
 * @author paulo
 * 
 * Classe para criar regras de conversão.
 */
public class Rule {
	private String from;
	private String to;

	/**
	 * 
	 * @param from 
	 * @param to
	 */
	Rule(String f, String t)	{
		from = f;
		to = t;
		
	}
/**
	 * 
	 * @param objeto1 Objeto origem da transformacao. Neste objeto será chamada a função "get" + from
	 * @param objeto2 Objeto destino. Neste objeto será chamado o método "set" + from, com o resultado
	 * do get do objeto 1
	 * @throws IllegalArgumentException Caso um dos métodos não exista.
	 */
	public void apply(Object ob1, Object ob2) throws IllegalArgumentException {
		Class c1 = ob1.getClass();
		Class c2 = ob2.getClass();
		Method m1, m2;

		try { 
			m1 = c1.getMethod("get" + from);
		} catch(NoSuchMethodException e) {
			throw new IllegalArgumentException("Cannot find method " + "get" + from 
				+ " in class" + c1.getName());
		}

		try {
			m2 = c2.getMethod("set" + to, m1.getReturnType());
		} catch(NoSuchMethodException e) {
			throw new IllegalArgumentException("Cannot find method " + "set" + from 
				+ " in class" + c2.getName());
		}
		

		try {
			m2.invoke(ob2, m1.invoke(ob1));
		}
		catch(Exception e ) {
			System.err.print(e);
			e.printStackTrace();
		}
		
	}
}
