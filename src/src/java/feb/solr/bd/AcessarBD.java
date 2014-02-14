package feb.solr.bd;

import feb.spring.FebConfig;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

public class AcessarBD {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private Connection connection;
    public static final int PAGE = 1000;
   
    public AcessarBD() {


       /*
        ConfiguracaoBancoDados c = new ConfiguracaoBancoDados();
        host = c.getHost();
        port = c.getPort();
        username = c.getUsername();
        database = c.getDatabase();
       // password = c.getPassword();
        */
        
        host = "127.0.0.1";
        port = 5432;
        username = "feb";
        database = "federacao";
        password = "feb@RNP";
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;

        try {
            connection = DriverManager.getConnection(url, username, password);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    public AcessarBD(String... banco) {

        String URL = banco.length > 0 ? banco[0] : "jdbc:postgresql://localhost:5432/FEBB";
        String userBD = banco.length > 1 ? banco[1] : "postgres";
        String passwordBD = banco.length > 2 ? banco[2] : "123456";

        connection = null;
    }

    public ResultSet BuscarTodosObjetos() {
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement pst = null;


        try {
            st = connection.createStatement();

            pst = connection.prepareStatement("select * from documentos");
            rs = pst.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;

    }

    public ResultSet BuscarObjetos(int i) {
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement pst = null;


        try {
            st = connection.createStatement();

            pst = connection.prepareStatement("select * from documentos LIMIT " + PAGE + " OFFSET " + (i) * PAGE);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;

    }

    public int numRowsDocumentos() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            st = connection.createStatement();

            pst = connection.prepareStatement("SELECT COUNT(*) FROM documentos");
            rs = pst.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        rs.next();
        System.out.println(rs.getInt(1));
        return rs.getInt(1);
    }

    public void Encerra() throws SQLException {

        if (connection != null) {
            connection.close();
        }

    }

    /**
     * Nao está implementado na versao jdbc que o FEB utiliza...
     * @return 
     */
    public boolean estaConectado() {
     /*
        try {
            return connection.isValid(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;*/
        return true;
    }
}
