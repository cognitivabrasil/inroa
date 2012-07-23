package feb.services;

import feb.data.entities.Usuario;
import feb.data.interfaces.FebDomainObject;


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
