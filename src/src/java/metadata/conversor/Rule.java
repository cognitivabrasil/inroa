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
 */
public class Rule {
	private String from;
	private String to;
	Rule(String f, String t)	{
		from = f;
		to = t;
		
	}

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
			m2 = c2.getMethod("add" + to, String.class);
		} catch(NoSuchMethodException e) {
			throw new IllegalArgumentException("Cannot find method " + "add" + from 
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
