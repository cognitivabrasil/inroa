/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ferramentaBusca;


import ferramentaBusca.recuperador.Recuperador;
import java.sql.*;
import java.util.ArrayList;
import mysql.Conectar;

/**
 * Disponibiliza realizar Busca por similaridade
 * @author Marcos
 */
public class Busca {

    /**
     * Realiza uma busca por similaridade e relev&acirc;ncia no indice criado.
     * @param termoBusca Palava ou express&atilde;o que ser&aacute; buscada.
     * @return ArrayList de Strings contendo os obaaEntry resultantes da busca.
     */
    public ArrayList<String> search(String termoBusca) throws SQLException{
    Conectar conectar = new Conectar(); //instancia uma variavel da classe mysql.conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar

        ArrayList<String> resultadoBusca = new ArrayList<String>();
        //resultadoBusca = TfIdf.search(termoBusca, con);
        Recuperador rep = new Recuperador();
        
        resultadoBusca = rep.search2(termoBusca, con);
        con.close(); //fecha a conexao com o mysql
        return resultadoBusca;
    }

    public static void main(String[] args) {
        Busca run = new Busca();
        try{
        System.out.println(run.search("couro"));
        }catch (SQLException e){
         e.printStackTrace();
        }

    }
}


