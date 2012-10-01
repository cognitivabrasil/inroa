package feb.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.LazyInitializationException;

import feb.data.entities.Usuario;
import feb.data.interfaces.FebDomainObject;

/**
 * UserActionLogger implementation that writes the info to a File.
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 *
 */
public class UserActionLoggerImpl implements UserActionLogger {
	private Logger logger = Logger.getLogger(UserActionLoggerImpl.class);
	private FileWriter fw;

	/**
	 * @param f hte file where we want to write the user actions
	 */
	public void setFile(File f) {
		try {
			fw = new FileWriter(f, true);
			fw.write("========INICIO==========\n");
			fw.flush();
		} catch (IOException e) {
			logger.error("Can't open user action log");

		}
	}

	@Override
	public void log(Usuario user, FebDomainObject dom, String text) {
		if (fw != null) {
			String s = dom.toString();
			try {
				s = dom.toStringDetailed();
			}
			catch(LazyInitializationException e) {
				logger.info("Got LazyInitializationException while trying to log an action");
			}
			
			try {
				fw.write("==========================\n");
				fw.write("Usuario: " + user.getUsername() + "\n");
				fw.write("Ação: " + text + "\n");
				fw.write("Data: " + new Date() + "\n");
				fw.write("Objeto: " + s + "\n");
				fw.flush();

			} catch (IOException e) {
				logger.error("Can't write UserAction log");
			}
		}
	}

	@Override
	public void log(Usuario user, String text) {
		if (fw != null) {
			try {
				fw.write("==========================\n");
				fw.write("Usuario: " + user.getUsername() + "\n");
				fw.write("Data: " + new Date() + "\n");
				fw.write("Ação: " + text + "\n");
				
				fw.flush();

			} catch (IOException e) {
				logger.error("Can't write UserAction log");
			}
		}
	}

}
