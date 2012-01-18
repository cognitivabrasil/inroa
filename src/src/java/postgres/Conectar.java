package postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import spring.ApplicationContextProvider;

/**
 * Disponibiliza método para conectar na base de dados Postgres.
 * @author LuizRossi
 */
public class Conectar {

    postgres.SingletonConfig conf;
    ApplicationContext ctx;

    //Configuracao conf;

    // para criar uma conexão o JSP que chamar essa função TEM de chamar antes o SigletonConfig.initConfig(c);
    public Conectar() {
     conf = SingletonConfig.getConfig();
     ctx = ApplicationContextProvider.getApplicationContext() ;
         if(ctx == null) {
     System.out.println("Could not get AppContext bean!");
     }
     else {
          System.out.println("AppContext bean retrieved!");
     }
    //conf = new Configuracao();
    }


    /**
     * Conecta na base de dados e retorna uma Connection.
     * @return Connection com a conex&atilde;o com o postgres
     */
    public Connection conectaBD() {

        Connection con = null;
        
        DataSource dataSource = (DataSource)ctx.getBean("dataSource");

        try {
                System.err.println("conectaDB");
               con = dataSource.getConnection();      
        } catch (SQLException e) {
            SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.err.println("FEB ERRO: Mensagem: " + e.getMessage());

//       esse codigo serve para imprimir o ClassPath do postgres
//            //Get the System Classloader
//            ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
//            //Get the URLs
//            URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();
//            System.err.println("FEB ERRO: ClassPath: ");
//            for (int i = 0; i < urls.length; i++) {
//                System.err.println(urls[i].getFile());
//            }

        }

        return con;
    }
}
