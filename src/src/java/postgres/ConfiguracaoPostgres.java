package postgres;

/**
 * Classe auxiliar que armazena e informa dados sobre a base Postgres
 * @author Luiz Rossi
 */
public class ConfiguracaoPostgres {

    //informacoes para conexao ao banco de dados
    String base = "federacao";
    String usuario = "cinted";
//    String usuario = "marcos";
    String senha = "teste";
    String ipPostgres = "143.54.95.20";
//    String ipMysql = "143.54.95.74";
    String portaPostgres = "5432";

    /**
     * Informa o nome da base que está sendo utilizada no projeto.
     * @return Nome da base utilizada.
     */
    public String getBase() {
        return base;
    }

    /**
     * Informa a senha da base Postgres.
     * @return Retorna uma String contendo a senha do Postgres.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Informa o usuário para conectar na base Postgres.
     * @return Retorna uma String contendo o nome do usuário do Postgres.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Informa o ip do banco de dados Postgres
     * @return Retorna uma String contendo o ip do Postgres
     */
    public String getIpPostgres() {
        return ipPostgres;
    }

    /**
     * Informa a porta do bando de dados Postgres
     * @return Retorna uma String contendo a porta do Postgres
     */
    public String getPortaPostgres() {
        return portaPostgres;
    }
}
