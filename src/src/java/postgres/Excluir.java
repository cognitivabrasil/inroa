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

      //      if (testeMysql.next()) { //se positivo excluir o nodo
                //Remover excluiNodo = new Remover();
                //resultadoNodo = excluiNodo.removeNodo(id, con); //exclui nodo do ldap
        //        if (resultadoNodo) {//se positivo excluir a base
                   
        //            if (result > 0) {//se a exclusao ocorreu entra no if
        //                
        //            }
       //         }
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
    public static boolean removerDocumentoIndice(String obaa_entry) throws SQLException {

        boolean resultado = false;
        int result = 0;
        Conectar conectar = new Conectar();
        //chama metodo que conecta ao banco de dados
        Connection con = conectar.conectaBD();

        Statement stm = con.createStatement();
        String sql = "DELETE FROM documentos where obaa_entry='" + obaa_entry + "';";

        //testar conexao
        try {
            result = stm.executeUpdate(sql); //executa o que tem na variavel
            if (result > 0) {
                resultado = true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar documento do indice " + e);
        } finally {
            try {
                con.close(); //fechar conexao
                } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
            return resultado;
        }
    }

    public static void main(String[] args) {
        Excluir run = new Excluir();
        //run.removeStopWords();
        try {
            System.out.println(run.removerDocumentoIndice("obaa0003"));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
