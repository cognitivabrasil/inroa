/**
 * 
 */
package feb.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author paulo
 * 
 */
@Component
public class ServerInfo {
	Logger logger = Logger.getLogger(ServerInfo.class);

	private String version;
	private String build;

	public ServerInfo() {
		ApplicationContext appContext = ApplicationContextProvider
				.getApplicationContext();
		Resource resource = appContext.getResource("META-INF/MANIFEST.MF");

		try {
			InputStream is = resource.getInputStream();
			Manifest mf = new Manifest();
			mf.read(is);

			Attributes atts = mf.getMainAttributes();

			version = atts.getValue("Implementation-Version");
			build = atts.getValue("Implementation-Build");

			System.out.println("Version: "
					+ atts.getValue("Implementation-Version"));
			System.out.println("Build: "
					+ atts.getValue("Implementation-Build"));
			System.out.println("Manifest Version: "
					+ atts.getValue("Manifest-Version"));
			System.out.println("Built-By: " + atts.getValue("Built-By"));

		} catch (IOException e) {
			logger.error("Cannot read MANIFEST", e);
		}

	}

	public String getFullVersion() {
		return getVersion() + "-" + getBuild();
	}

	public String getVersion() {
		if (version != null) {
			return version;
		} else {
			return "???";
		}
	}

	public String getBuild() {
		if (build != null) {
			return build;
		} else {
			return "???";
		}

	}

}
