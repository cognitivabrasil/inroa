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
 */
public class Conversor {
	List<Rule> rules;

	Conversor() {
		rules = new ArrayList<Rule>();
	}
	
	public void add(Rule r) {
		rules.add(r);
	}

	public void convert(Object ob1, Object ob2) {
		for(Rule r : rules) {
			r.apply(ob1, ob2);
		}	
	}
	
}
