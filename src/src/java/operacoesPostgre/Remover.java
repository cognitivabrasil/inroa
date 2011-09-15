package operacoesPostgre;

import java.sql.*;

/**
 * Classe com metodos responsáveis por remover dados da base.
 *
 * @author Marcos Nunes
 */
public class Remover {


    private static boolean debugOut = false;
    private static boolean debugErr = true;

    /**
     * Método respons&aacute;vel por apagar todos objetos de um reposit&oacute;rio
     * @param idRepositorio o id do reposit&oacute;rio a ser apagado
     * @param con Conex&atilde;o com a base a ser apagada.
     * @return Retorna true se não ocorreu nenhum erro ao apagar os objetos ou false se ocorreu algum erro.
     */
    public void apagaObjetosRepositorio(int idRepositorio, Connection con) {

        try {
            String sql = "DELETE FROM documentos WHERE id_repositorio = "+idRepositorio;
            Statement stm = con.createStatement();
            int res = stm.executeUpdate(sql);

        } catch (SQLException e){
            System.err.println("FEB ERRO: metodo apagaObjetosRepositorio. Nao foi possivel deletar a base de dados: "+e.getMessage());
        }       
    }
    /**
     * Método respons&aacute;vel por apagar todos objetos de uma Subfedera&ccedil;&atilde;o.
     * @param idSubfed o id da Subfedera&ccedil;&atilde;o a ser apagada
     * @param con Conex&atilde;o com a base a ser apagada.
     * @return Retorna true se não ocorreu nenhum erro ao apagar os objetos ou false se ocorreu algum erro.
     */
    public boolean apagaObjetosSubfederacao(int idSubfed, Connection con) {

        try {
            String sql = "DELETE FROM documentos WHERE id IN (SELECT d.id WHERE documentos d, repositorios_subfed r WHERE d.id_rep_subfed=r.id AND r.id_subfed="+idSubfed+")";
            Statement stm = con.createStatement();
            int res = stm.executeUpdate(sql);

            if (res>0)
                return true;
             else
                return false;

        } catch (SQLException e){
            return false;
        }
    }

    /**
     *  Imprime o texto com o System.out.println, se debug estiver setado com true.
     *
     * @param  s  A string que será impressa.
     */
    private final static void prtln(String s) {
        if (debugOut) {
            System.out.println(s);
        }
    }

    /**
     *  Imprime o texto com o prtlnErr, se debug estiver setado com true.
     *
     * @param  s  A string que será impressa.
     */
    private final static void prtlnErr(String s) {
        if (debugErr) {
            System.err.println(s);
        }
    }

    /**
     *  Define o boolean debugOut. Se for true ao executar o programa as mensagens System.out serão impressas e vice-versa.
     *
     * @param  db  O novo valor de debug
     */
    public void setDebugOut(boolean db) {
        debugOut = db;
    }

    /**
     *  Define o boolean debugErr. Se for true ao executar o programa as mensagens de erro System.err serão impressas e vice-versa.
     *
     * @param  db  O novo valor de debug
     */
    public void setDebugErr(boolean db) {
        debugErr = db;
    }


}
