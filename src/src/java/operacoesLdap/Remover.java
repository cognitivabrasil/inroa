package operacoesLdap;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe com metodos responsáveis por remover dados de uma base Ldap
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
    public boolean apagaTodosObjetos(int idRepositorio, Connection con) {

        try {
            String sql = "DELETE FROM documentos WHERE id_repositorio = "+idRepositorio;
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
