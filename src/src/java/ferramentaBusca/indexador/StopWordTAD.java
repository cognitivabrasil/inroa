package ferramentaBusca.indexador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import org.apache.log4j.Logger;
import postgres.Conectar;

/**
 *
 * @author Luiz Rossi
 */
public class StopWordTAD {

    private HashSet<String> res;
    private String idioma;
    static Logger log = Logger.getLogger(StopWordTAD.class);

    public StopWordTAD() {
        res = new HashSet<String>();
        idioma = "portugues";
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        //apaga as tabelas antes de inserir

        load(con);

        try {
            con.close(); //fechar conexao com o banco de dados
        } catch (SQLException e) {
            log.error("Erro ao fechar conexao com o Postgres", e);
        }
    }

    public StopWordTAD(HashSet<String> lista) {
        res = lista;
        idioma = "portugues";
    }

    public void setRes(HashSet<String> res) {
        this.res = res;
    }

    public HashSet<String> getRes() {
        if (res == null) {
            log.warn("Não foi encontrada nenhuma StopWords na base de dados");
            return new HashSet<String>();
        } else {
            return res;
        }
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Função para carregar as stop words do banco de dados
     *
     * @return
     */
    private boolean load(Connection con) {
        try {

            Statement stm = con.createStatement();

            String sql = "SELECT stopword FROM stopwords;";
            ResultSet rs = stm.executeQuery(sql); //executa a consulta que esta na string sql
            while (rs.next()) { //testa se tem o proximo resultado
                res.add(rs.getString("stopword"));
            }
            return true;

        } catch (SQLException e) {
            System.out.println("ERRO ao carregar a lista de stop Words: " + e);
            return false;
        }
    }
}