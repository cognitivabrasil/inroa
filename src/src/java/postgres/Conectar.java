package postgres;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Disponibiliza método para conectar na base de dados Postgres.
 * @author LuizRossi
 */
public class Conectar {

    Configuracao conf;

    public Conectar() {
        conf = new Configuracao();
    }

    /**
     * Conexão com uma subfederacao
     * @param subfed arquivo da configuracao da base federada
     * @return
     */
    public Conectar(Configuracao subfed) {
        conf = subfed;
    }

    /**
     * Conecta na base de dados e retorna uma Connection.
     * @return Connection com a conex&atilde;o com o postgres
     */
    public Connection conectaBD() {

        Connection con = null;


        String baseDeDados = conf.getBase();
        String usuario = conf.getUsuario();
        String senha = conf.getSenha();
        String ipBase = conf.getIp();
        int portaBase = conf.getPorta();

        try {
            // Este é um dos meios para registrar um driver
            Class.forName("org.postgresql.Driver");



            // Registrado o driver, vamos estabelecer uma conexão
            con = DriverManager.getConnection("jdbc:postgresql://" + ipBase + ":" + portaBase + "/" + baseDeDados, usuario, senha);


            //         return con;

        } catch (ClassNotFoundException e) {
            System.err.println("FEB: excessão Classe não encontrada ao conectar na base de dados");
            e.printStackTrace();
            //           return null;
        } catch (SQLException e) {
            System.err.println("FEB: SQL Exception... Erro ao efetuar a conexão na base de dados no ip " + ipBase + ". Mensagem: " + e.getMessage());
            //Get the System Classloader
            ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();

            //Get the URLs
            URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();
            System.err.println("ClassPath: ");
            for (int i = 0; i < urls.length; i++) {
                System.err.println(urls[i].getFile());
            }
            //e.printStackTrace(); //vejamos que erro foi gerado e quem o gerou
//            return null;
        } catch (NullPointerException n) {
            SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.err.println("FEB: " + dataFormat.format(new Date()) + " NullPointer ao efetuar conexao na base de dados. Classe Conectar: " + n);
            if (ipBase == null) {
                System.out.println("FEB: Variavel ipBase é null");
            }
            if (baseDeDados == null) {
                System.out.println("FEB: Variavel baseDeDados é null");
            }
            if (usuario == null) {
                System.out.println("FEB: Variavel usuario é null");
            }
            if (senha == null) {
                System.out.println("FEB: Variavel senha é null");
            }
        }

        return con;
    }
}
