/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ferramentaBusca;


import ferramentaBusca.recuperador.Recuperador;
import java.sql.*;
import java.util.ArrayList;
import postgres.ConectaPostgres;

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
    public ArrayList<String> search(String termoBusca, int idRep) throws SQLException{
        
    ConectaPostgres conectar = new ConectaPostgres(); //instancia uma variavel da classe Postgres.conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe Postgres.conectar

        ArrayList<Integer> resultadoBusca = new ArrayList<Integer>();
        ArrayList<String> entry = new ArrayList<String>();
        //resultadoBusca = TfIdf.search(termoBusca, con);

        Recuperador rep = new Recuperador();
        
        resultadoBusca = rep.search2(termoBusca, con, idRep);
        for (int i=0; i<resultadoBusca.size(); i++){
            
        String entryQ = "SELECT obaaEntry FROM documentos WHERE id=" + resultadoBusca.get(i);

				Statement stm = con.createStatement();
				ResultSet rs2 = stm.executeQuery(entryQ);
				rs2.next();
				String entryA = rs2.getString("obaaEntry");
                                entry.add(entryA);
        }
        con.close(); //fecha a conexao com o Postgres
        return entry;
    }

    public static void main(String[] args) {
        Busca run = new Busca();
        try{
        System.out.println(run.search("sapato de couro", 0));
        }catch (SQLException e){
         e.printStackTrace();
        }

    }
}


