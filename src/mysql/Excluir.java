package mysql;

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


        String sqlTeste = "select nome from repositorios where id=" + id;


        String sql = "delete from repositorios where id=" + id;

        int result = 0;
        try {
            Statement stm = con.createStatement();

            //testar conexao com mysql
            ResultSet testeMysql = stm.executeQuery(sqlTeste);

            if (testeMysql.next()) { //se positivo excluir o nodo
                Remover excluiNodo = new Remover();
                resultadoNodo = excluiNodo.removeNodo(id); //exclui nodo do ldap
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
                con.close();
            } catch (SQLException erro) {
                System.out.println("Erro no fechamento");
                erro.printStackTrace();
            }
        }



        return resultado;
    }

    public void removeStopWords() {
        String caminhoArquivo = "./data/stopwords.txt";
        String line;
        Conectar conectar = new Conectar();
        //chama metodo que conecta ao mysql
        Connection con = conectar.conectaBD();
        try {

            BufferedReader in = new BufferedReader(new FileReader(caminhoArquivo)); //le o arquivo

            Statement stm = con.createStatement();

            while ((line = in.readLine()) != null) {
                System.out.println("-" + line + "-");
                String sql = "delete from r1tokens where token='" + line.trim() + "'";

                //testar conexao com mysql
                stm.executeUpdate(sql); //executa o que tem na variavel slq no mysql
            }
            in.close();



        } catch (SQLFeatureNotSupportedException e) {
            System.err.println(e);

        } catch (SQLException k) {
            System.err.println(k);
        } catch (IOException e) {
            System.err.println(e); //imprime o erro
        } finally {
            try {
                con.close();
            } catch (SQLException erro) {
                System.out.println("Erro no fechamento da conexao mysql");
                erro.printStackTrace();
            }
        }

    }

    /**
     * Remove um documento (objeto) do indice de busca no mysql
     * @param obaaEntry String contendo o obaaEntry do documento a ser deletado
     * @return true se a exclus&atilde;o foi realizada com sucesso e false se n&atilde;o foi possível excluir
     * @throws SQLException
     */
    public static boolean removerDocumentoIndice(String obaaEntry) throws SQLException {
        
        boolean resultado = false;
        int result = 0;
        Conectar conectar = new Conectar();
        //chama metodo que conecta ao mysql
        Connection con = conectar.conectaBD();

        Statement stm = con.createStatement();
        String sql = "delete from documentos where obaaEntry='" + obaaEntry + "';";

        //testar conexao com mysql
        result = stm.executeUpdate(sql); //executa o que tem na variavel slq no mysql
        if (result > 0) {
            resultado = true;
        }
        return resultado;
    }

    public static void main(String[] args) {
        Excluir run = new Excluir();
        //run.removeStopWords();
        try{
        System.out.println(run.removerDocumentoIndice("obaa0003"));
        }catch(SQLException e){ System.out.println(e);}
    }
}
