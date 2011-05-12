package postgres;

/**
 * Classe auxiliar que armazena e informa dados sobre o banco de dados a se conctar
 * @author Marcos Nunes
 */
public class Configuracao {

    //informacoes para conexao ao banco de dados
    String base;
    String usuario;
    String senha;
    String ip;
    int porta;

   /**
     * conexão com a base local
     */
    public Configuracao(){

    base = "federacao";
    usuario = "feb";
//    ip = "143.54.95.74";
    senha = "feb@RNP";
//    ip = "143.54.95.20";
    ip = "127.0.0.1";
//    senha = "12345";
//        porta = 5000;

    porta = 5432;
    }
    /**
     * Configuração
     * @param base nome da base de dados na subfederacao padrao do feb: 'federacao'
     * @param usuario usuario do banco de dados da subfederacao
     * @param senha senha do usuario da base de dados da subfederacao
     * @param ip ip do servidor da subfederacao
     * @param porta porta que responde o sql da subfederacao, padrao do postgres 2345
     */
     public Configuracao(String base, String usuario, String senha, String ip, int porta){

    this.base = base;
    this.usuario = usuario;
    this.senha = senha;
    this.ip = ip;
    this.porta = porta;
    }
    /**
     * Informa o nome da base que está sendo utilizada no projeto.
     * @return Nome da base utilizada.
     */
    public String getBase() {
        return base;
    }

    /**
     * Informa a senha da base.
     * @return Retorna uma String contendo a senha do banco de dados.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Informa o usuário para conectar na base.
     * @return Retorna uma String contendo o nome do usuário do banco de dados.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Informa o ip do banco de dados
     * @return Retorna uma String contendo o ip do banco de dados
     */
    public String getIp() {
        return ip;
    }

    /**
     * Informa a porta do banco de dados
     * @return Retorna uma String contendo a porta do banco de dados
     */
    public int getPorta() {
        return porta;
    }
}
