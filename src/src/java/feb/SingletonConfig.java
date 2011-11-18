/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 *
 * @author paulo
 */
public class SingletonConfig {

	private static SingletonConfig singletonObject;
        
        static ServletContext servletContext;
        private String usuario;
        private String senha;
	// Note that the constructor is private
	private SingletonConfig() {
		// Optional Code
	}
        
	public static SingletonConfig getConfig() {
		if (singletonObject == null) {
			singletonObject = new SingletonConfig();
		
                            System.out.println("Teste 2");
                 try {
            HashMap attributes = null;
            ServletContext context = servletContext;
            Properties properties = (Properties) context.getAttribute("febproperties");
            
            if (properties == null) {
             
                String fileName = servletContext.getInitParameter("febproperties");
                InputStream in;
                try {
                    System.out.println("fileName=" + fileName);
                    in = new FileInputStream(fileName);
                } catch (FileNotFoundException e2) {
                    System.out.println("file not found. Try the classpath: " + fileName);
                    in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
                }
                if (in != null) {
                    System.out.println("file was found: Load the properties");
                    properties = new Properties();
                    properties.load(in);
                    //attributes = getAttributes(properties);
                    System.out.println("OAIHandler.init: fileName=" + fileName);

                }
            } else {
                System.out.println("Load context properties");
            }

            SingletonConfig c = SingletonConfig.getConfig();
            
            System.out.println("Store global properties");
            System.out.println("Usuario: " + properties.getProperty("Postgres.usuario"));
            c.setUsuario(properties.getProperty("Postgres.usuario"));
            System.out.println("Senha: " + properties.getProperty("Postgres.senha"));
            c.setSenha(properties.getProperty("Postgres.senha"));

        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
          //  throw new ServletException(e2.getMessage());
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
          //  throw new ServletException(e2.getMessage());
        } catch (IOException e2) {
            e2.printStackTrace();
          //  throw new ServletException(e2.getMessage());
        } catch (Throwable e2) {
            e2.printStackTrace();
           // throw new ServletException(e2.getMessage());
        }
        }
         
           
                singletonObject.print();
		return singletonObject;
	}
        
        public static void initConfig(ServletContext c) {
            servletContext = c;
        }
        
        public void print() {
             System.out.println("Usuario: " + usuario);
         
            System.out.println("Senha: " + senha);
        }
    

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
        
}