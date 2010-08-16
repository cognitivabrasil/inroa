package mysql;

import java.sql.*;

/**
 * Atualiza informações na base de dados mysql.
 * @author Marcos
 */
public class AtualizaBase {
    Configuracao conf = new Configuracao();
    String baseDeDados = conf.getBase();
    String usuario = conf.getUsuario();
    String senha = conf.getSenha();
    String ipBase = conf.getIpMysql();
    String portarBase = conf.getPortaMysql();

    /**
     * Atualiza a hora do campo dataUltimaatualizacao da tabela info_repositorios para a hora atual.
     * @param id Identificador do repositorio que deseja atualizar a dataUltimaAtualizacao.
     * @return Retorna true se a alteração foi realizada ou false se ocorreu algum erro ao atualizar a data.
     */
    public boolean atualizaHora(int id){
        //Connection con = null;
        Conectar conectar = new Conectar(); //instancia uma variavel da classe mysql.conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar
        try {

           // Class.forName("com.mysql.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:mysql://"+ipBase+":"+portarBase+"/" + baseDeDados, usuario, senha);
            Statement stmUpdt = con.createStatement();

    int result = 0;
        result = stmUpdt.executeUpdate("UPDATE info_repositorios SET dataUltimaAtualizacao=now() WHERE id_repositorio=" + id + ";");
        
        if (result > 0) {
                    return true;
                } else {
                    return false;
                }


        } catch (SQLException e) {
            System.out.println("SQL Exception... Erro no SQL:");
            e.printStackTrace();
            return false;
        }finally {
            try {
                con.close(); //fechar conexao mysql
                } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }

    }


}
