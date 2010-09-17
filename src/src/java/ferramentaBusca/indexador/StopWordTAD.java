package ferramentaBusca.indexador;

import java.util.HashSet;
import java.sql.*;

/**
 *
 * @author Luiz Rossi
 */
public class StopWordTAD {

    private HashSet<String> res;
    private String idioma;

    public StopWordTAD(){
        res = new HashSet<String>();
        idioma="portugues";
    }
    public StopWordTAD(HashSet<String> lista){
        res = lista;
    }

    public void setRes(HashSet<String> res) {
        this.res = res;
    }

    public HashSet<String> getRes() {
        return res;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

/**
 * Função para carregar as stop words do banco de dados
 * @return
 */
    public boolean load(Connection con) {
        try {

            Statement stm = con.createStatement();

            String sql = "SELECT stopword FROM stopwords;";
            ResultSet rs = stm.executeQuery(sql); //executa a consulta que esta na string sql
            while (rs.next()){ //testa se tem o proximo resultado
                res.add(rs.getString("stopword"));
        }
            return true;

        } catch (SQLException e){
            System.out.println("ERRO ao carregar a lista de stop Words: "+e);
            return false;
        }
    }
}