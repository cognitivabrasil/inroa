package postgres;

import java.sql.*;

/**
 * Disponibiliza método para conectar na base de dados Postgres.
 * @author LuizRossi
 */
public class Conectar {

    Configuracao conf = new Configuracao();
    
    public Connection conectaBD() {

        Connection con = null;
        

        String baseDeDados = conf.getBase();
        String usuario = conf.getUsuario();
        String senha = conf.getSenha();
        String ipBase = conf.getIp();
        String portarBase = conf.getPorta();

        try {
            // Este é um dos meios para registrar um driver
            Class.forName("org.postgresql.Driver");

            // Registrado o driver, vamos estabelecer uma conexão
            con = DriverManager.getConnection("jdbc:postgresql://" + ipBase + ":" + portarBase + "/" + baseDeDados, usuario, senha);


   //         return con;

        } catch (ClassNotFoundException e) {
            System.out.println("excessão Classe não encontrada");
            e.printStackTrace();
 //           return null;
        } catch (SQLException e) {
            System.out.println("SQL Exception... Erro ao efetuar a conexão na base de dados: ");
            e.printStackTrace(); //vejamos que erro foi gerado e quem o gerou
//            return null;
        }

        return con;
    }
}
