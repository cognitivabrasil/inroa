package postgres;

/**
 * Classe auxiliar que armazena e informa dados sobre a base Mysql
 * @author Marcos Nunes
 */
public class Configuracao {

    //informacoes para conexao ao banco de dados
    String base = "federacao";
    String usuario = "feb";
//    String usuario = "cinted";
//    String senha = "teste";
    String senha = "12345";
//    String ipMysql = "localhost";
    String ip = "143.54.95.20";
//    String ipMysql = "143.54.95.74";
    //String portaMysql = "3306";
    String porta = "5432";

    /**
     * Informa o nome da base que está sendo utilizada no projeto.
     * @return Nome da base utilizada.
     */
    public String getBase() {
        return base;
    }

    /**
     * Informa a senha da base Mysql.
     * @return Retorna uma String contendo a senha do mysql.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Informa o usuário para conectar na base Mysql.
     * @return Retorna uma String contendo o nome do usuário do Mysql.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Informa o ip do banco de dados mysql
     * @return Retorna uma String contendo o ip do mysql
     */
    public String getIp() {
        return ip;
    }

    /**
     * Informa a porta do bando de dados mysql
     * @return Retorna uma String contendo a porta do mysql
     */
    public String getPorta() {
        return porta;
    }


}
