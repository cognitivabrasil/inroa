package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.Usuario;
import com.cognitivabrasil.feb.data.interfaces.FebDomainObject;


/**
 * This interface represents a facility to log user actions.
 * 
 * Interface to allow use actions to be logged.
 * 
 * @author Paulo Schreiner <paulo@cognitivabrasil.com.br>
 *
 */
public interface UserActionLogger {
	
	public void log(Usuario user, FebDomainObject dom, String text);
	
	public void log(Usuario user, String text);
}
