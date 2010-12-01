package postgres;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;


/**
 * Classe responsável por operações de exclusão no banco de dados
 *
 * @author Marcos
 */
public class Excluir {

    /**
     * Remover repositório da federação, remove repositório da federacação
     * Recebe o identificador do repositorio na base de dados, exclui seus dados da federacação no banco de dados
     *
     * @param id id da tabela repositorios do banco de dados
     * @return retorna true ou false, se o processo foi executado com sucesso ou não.
     */
    public boolean removeRepositorio(int id) {

        boolean resultado = false;
      //  boolean resultadoNodo = false;

        Conectar conectar = new Conectar();
        //chama metodo que conecta
        Connection con = conectar.conectaBD();


        String sql = "DELETE FROM repositorios WHERE id=" + id;

        int result = 0;
        try {
            Statement stm = con.createStatement();

            //testar conexao
            result = stm.executeUpdate(sql); //executa o que tem na variavel sql
            resultado = true;

            }

         catch (SQLFeatureNotSupportedException e) {
            resultado = false;

        } catch (SQLException k) {
            resultado = false;
        } finally {
            try {
                con.close(); //fechar conexao
                } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }



        return resultado;
    }

 

    /**
     * Remove um documento (objeto) do indice de busca
     * @param obaa_entry String contendo o obaa_entry do documento a ser deletado
     * @return true se a exclus&atilde;o foi realizada com sucesso e false se n&atilde;o foi possível excluir
     * @throws SQLException
     */
    public static boolean removerDocumentoIndice(String obaa_entry, int idRepositorio, Connection con) throws SQLException {

        int result = 0;
        
        Statement stm = con.createStatement();
        String sql = "DELETE FROM documentos where obaa_entry='" + obaa_entry + "' AND id_repositorio ="+idRepositorio;

        //testar conexao
        try {
            result = stm.executeUpdate(sql); //executa o que tem na variavel
            if (result > 0) {
                return true;
            } else
                return false;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar documento do indice " + e);
            return false;
        }
    }
    
    /**
     * Remove um mapeamento da base de dados. Deve ser informado o id do tipo do mapeamento e o id do padr&atilde;o de metadados
     * @param tipoMapId id do tipo do mapeamento
     * @param padraoId id do padr&atilde;o de metadados
     * @return true se removeu o mapeamento da base de dados ou false se n&atilde;o foi removido
     */
    public static boolean removeMapeamento(int tipoMapId, int padraoId){
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        int result = 0;
        try{
            Statement stm = con.createStatement();
            String sql = "DELETE FROM mapeamentos " +
                    " WHERE tipo_mapeamento_id=" +tipoMapId+
                    " AND padraometadados_id="+padraoId;
            result = stm.executeUpdate(sql); //executa o que tem na variavel
            if (result > 0) {
                return true;
            } else
                return false;
        } catch(SQLException s){
            System.out.println("ERRO ao remover um mapeamento. -> "+s);
            return false;
        }
    }

    public static void main(String[] args) {
        Excluir run = new Excluir();
        //run.removeStopWords();
        try {
            Conectar conn = new Conectar();
            Connection con = conn.conectaBD();
            System.out.println(run.removerDocumentoIndice("apagar", 8,con));
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
