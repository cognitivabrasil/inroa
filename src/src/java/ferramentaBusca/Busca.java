/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ferramentaBusca;


import ferramentaBusca.recuperador.Recuperador;
import java.sql.*;
import java.util.ArrayList;
import postgres.Conectar;

/**
 * Disponibiliza realizar Busca por similaridade
 * @author Marcos
 */
public class Busca {

    /**
     * Realiza uma busca por similaridade e relev&acirc;ncia no indice criado.
     * @param termoBusca Palava ou express&atilde;o que ser&aacute; buscada.
     * @param idRep identificador do reposit&oacute;rio onde ser&aacute; feita a busca. Usar 0 caso se queira buscar todos em todos os reposit&oacute;rios
     * @return ArrayList de Strings contendo os obaa_entry resultantes da busca.
     * @throws SQLException
     */
    public ArrayList<String> search(String termoBusca, int idRep) throws SQLException{

    Conectar conectar = new Conectar(); //instancia uma variavel da classe Postgres.conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe Postgres.conectar

        ArrayList<Integer> resultadoBusca = new ArrayList<Integer>();
        ArrayList<String> entry = new ArrayList<String>();
        //resultadoBusca = TfIdf.search(termoBusca, con);

        Recuperador rep = new Recuperador();

        resultadoBusca = rep.search2(termoBusca, con, idRep);
        for (int i=0; i<resultadoBusca.size(); i++){

        String entryQ = "SELECT obaa_entry FROM documentos WHERE id=" + resultadoBusca.get(i);

				Statement stm = con.createStatement();
				ResultSet rs2 = stm.executeQuery(entryQ);
				rs2.next();
				String entryA = rs2.getString("obaa_entry");
                                entry.add(entryA);
        }
        con.close(); //fecha a conexao com o Postgres
        return entry;
    }


    /**
     * Realiza uma busca por similaridade e relev&acirc;ncia no indice criado.
     * @param termoBusca Palava ou express&atilde;o que ser&aacute; buscada.
     * @param idRep ArrayList de reposit&oacute;rios onde ser&aacute; feita a busca.
     * @param idSubfed ArrayList de Subfedera&ccedil;&otilde;es onde ser&aacute; feita a busca.
     * @return ArrayList de Strings contendo os obaa_entry resultantes da busca.
     * @throws SQLException
     */
    public ArrayList<String> search(String termoBusca, ArrayList <Integer>idRep, ArrayList <Integer>idSubfed) throws SQLException{

        Conectar conectar = new Conectar(); //instancia uma variavel da classe Postgres.conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe Postgres.conectar

        ArrayList<Integer> resultadoBusca = new ArrayList<Integer>();
        ArrayList<String> entry = new ArrayList<String>();
        //resultadoBusca = TfIdf.search(termoBusca, con);

        Recuperador rep = new Recuperador();

        resultadoBusca = rep.search2(termoBusca, con, idRep, idSubfed);
        for (int i=0; i<resultadoBusca.size(); i++){

        String entryQ = "SELECT obaa_entry FROM documentos WHERE id=" + resultadoBusca.get(i);

				Statement stm = con.createStatement();
				ResultSet rs2 = stm.executeQuery(entryQ);
				rs2.next();
				String entryA = rs2.getString("obaa_entry");
                                entry.add(entryA);
        }
        con.close(); //fecha a conexao com o Postgres
        return entry;
    }


}