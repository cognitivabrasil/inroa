package postgres;

import java.sql.*;

/**
 * Atualiza informações na base de dados.
 * @author Marcos
 */
public class AtualizaBase {
    Configuracao conf = new Configuracao();
    String baseDeDados = conf.getBase();
    String usuario = conf.getUsuario();
    String senha = conf.getSenha();
    String ipBase = conf.getIp();
    int portaBase = conf.getPorta();

    /**
     * Atualiza a hora do campo data_ultima_atualizacao da tabela info_repositorios para a hora atual.
     * @param id Identificador do repositorio que deseja atualizar a data_ultima_atualizacao.
     * @return Retorna true se a alteração foi realizada ou false se ocorreu algum erro ao atualizar a data.
     */
    public static boolean atualizaHora(int id){
        //Connection con = null;
        Conectar conectar = new Conectar(); //instancia uma variavel da classe conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
        try {

            Statement stmUpdt = con.createStatement();

    int result = 0;
        result = stmUpdt.executeUpdate("UPDATE info_repositorios SET data_ultima_atualizacao=now() WHERE id_repositorio=" + id + ";");
        
        if (result > 0) {
                    return true;
                } else {
                    return false;
                }


        } catch (SQLException e) {
            System.err.println("FEB ERRO: Erro no sql ao atualizar data de ultima atualizaçao... Mensagem:" +e.getMessage());
            return false;
        }finally {
            try {
                con.close(); //fechar conexao
                } catch (SQLException e) {
                System.out.println("FEB ERRO: Erro ao fechar a conexão: " + e.getMessage());
            }
        }

    }


}
