package postgres;
import java.sql.*;

/**
 * Disponibiliza método para conectar na base de dados mysql.
 * @author Marcos Nunes
 */

public class Conectar_mysql {

    Configuracao conf = new Configuracao();
    /**
     * Cria uma conexão com o mysql.
     * @return variável do tipo Connection com a conexão realizada com a base de dados mysql.
     */
    public Connection conectaBD(){
        Connection con = null;
        
        String baseDeDados = conf.getBase();
        String usuario = conf.getUsuario();
        String senha = conf.getSenha();
        String ipBase = conf.getIp();
        String portarBase = conf.getPorta();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+ipBase+":"+portarBase+"/" + baseDeDados, usuario, senha);
            
        } catch (ClassNotFoundException e) {
            System.out.println("excessão Classe não encontrada");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception... Erro ao efetuar a conexão na base de dados: ");
            e.printStackTrace();
        }
        return con;
    }

}
//*/