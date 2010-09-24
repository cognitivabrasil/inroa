package postgres;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import operacoesLdap.Remover;

/**
 * Classe responsável por operações de exclusão no mysql
 *
 * @author Marcos
 */
public class Excluir {

    /**
     * Remover repositório da federação, remove repositório do mysql e do ldap.
     * Recebe o identificador do repositorio na base mysql, exclui o seu nodo no ldap e as suas entradas no mysql
     *
     * @param id id da tabela repositorios do mysql
     * @return retorna true ou false, se o processo foi executado com sucesso ou não.
     */
    public boolean removeRepositorio(int id) {

        boolean resultado = false;
        boolean resultadoNodo = false;

        Conectar conectar = new Conectar();
        //chama metodo que conecta ao mysql
        Connection con = conectar.conectaBD();


        String sqlTeste = "SELECT nome FROM repositorios WHERE id=" + id;


        String sql = "DELETE FROM repositorios WHERE id=" + id;

        int result = 0;
        try {
            Statement stm = con.createStatement();

            //testar conexao com mysql
            ResultSet testeMysql = stm.executeQuery(sqlTeste);

            if (testeMysql.next()) { //se positivo excluir o nodo
                Remover excluiNodo = new Remover();
                resultadoNodo = excluiNodo.removeNodo(id, con); //exclui nodo do ldap
                if (resultadoNodo) {//se positivo excluir a base
                    result = stm.executeUpdate(sql); //executa o que tem na variavel slq no mysql
                    if (result > 0) {//se a exclusao ocorreu entra no if
                        resultado = true;
                    }
                }
            }

        } catch (SQLFeatureNotSupportedException e) {
            resultado = false;

        } catch (SQLException k) {
            resultado = false;
        } finally {
            try {
                con.close(); //fechar conexao mysql
                } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }



        return resultado;
    }

 

    /**
     * Remove um documento (objeto) do indice de busca no mysql
     * @param obaa_entry String contendo o obaa_entry do documento a ser deletado
     * @return true se a exclus&atilde;o foi realizada com sucesso e false se n&atilde;o foi possível excluir
     * @throws SQLException
     */
    public static boolean removerDocumentoIndice(String obaa_entry) throws SQLException {

        boolean resultado = false;
        int result = 0;
        Conectar conectar = new Conectar();
        //chama metodo que conecta ao mysql
        Connection con = conectar.conectaBD();

        Statement stm = con.createStatement();
        String sql = "DELETE FROM documentos where obaa_entry='" + obaa_entry + "';";

        //testar conexao com mysql
        try {
            result = stm.executeUpdate(sql); //executa o que tem na variavel slq no mysql
            if (result > 0) {
                resultado = true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar documento do indice " + e);
        } finally {
            try {
                con.close(); //fechar conexao mysql
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
