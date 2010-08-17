/**
 * o pacote indexador correspoonde as classes que fazem a preparaç&atilde;o dos dados
 */
package ferramentaBusca.indexador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Indexador é a classe que faz os processos de contruç&atilde;o da base de
 * dados para preparaç&atilde;o da posterior recuperaç&atilde;o de informações
 *
 * @author Luiz Rossi
 */
public class Indexador {

    public Indexador() {
    }

    /**
     * Testa se o obaaEntry já existe na base de dados
     * @param obaaEntry String contendo o obaaEntry
     * @return true se o objeto existe e false se não existir
     */
    public boolean testaEntry(String obaaEntry, Connection con) throws SQLException {

        Statement stm = con.createStatement();
        //fazer consulta sql
        String sql = "SELECT id FROM documentos where obaaEntry='" + obaaEntry + "'";
        ResultSet rs = stm.executeQuery(sql); //executa a consulta que esta na string sqlDadosLdap
        if (rs.next()) //testa se tem o proximo resultado
        {
            return true;
        } else {
            return false;
        }

    }

    /**
     * M&eacute;todo que adiciona o documento doc na base de dados MySQL
     * @param doc Docuemnto a ser adicionado
     * @param con A conex&atilde;o do banco de dados
     */
    public void addDoc(Documento doc, Connection con) throws SQLException {

        /*
         * field:               1 para titulo
         * 			2 para palavra chave
         * 			3 para entidade
         * 			4 para descriç&atilde;o
         */

        try {
            if (!testaEntry(doc.getObaaEntry(), con)) {

                //String sql = "INSERT INTO documentos (obaaEntry, titulo, resumo, data, localizacao, id_repositorio) VALUES ('" + doc.getObaaEntry() + "','"+doc.getTituloOriginal() + "','"+doc.getResumo()+ "','"+ doc.getData() + "','"+ doc.getLocalizacao() + "',"+ doc.getServidor() + ");";

                String sql2 = "INSERT INTO documentos (obaaEntry, titulo, resumo, data, localizacao, id_repositorio) VALUES (?,?,?,?,?,?);";
                
                int key = 0;
                
                PreparedStatement stmt1 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                stmt1.setString(1, doc.getObaaEntry());
                stmt1.setString(2, doc.getTituloOriginal());
                stmt1.setString(3, doc.getResumo());
                stmt1.setString(4, doc.getData());
                stmt1.setString(5, doc.getLocalizacao());
                stmt1.setInt(6, doc.getServidor());
                stmt1.execute();

                ResultSet rs = stmt1.getGeneratedKeys();
                rs.next();
                key = rs.getInt(1);
//        Statement stm = con.createStatement();
//
//        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS); //realiza no mysql o insert que esta na variavel sql, pedindo para retornar a key gerada automaticamente
//        ResultSet rs = stm.getGeneratedKeys();
//        rs.next();
//        key = rs.getInt(1);
                doc.setId(key);
                stmt1.close();

                //1 titulo
                ArrayList<String> tokens = doc.getTitulo();
                int id = doc.getId();

                for (int i = 0; i < tokens.size(); i++) {

                    String token = tokens.get(i);
                    String insert = "insert into r1tokens (token, id, field) VALUES ('" + token + "'," + id + ", 1)";
                    PreparedStatement stmt = con.prepareStatement(insert);
                    stmt.execute();
                    stmt.close();
                }

                //2 palavras chave
                tokens = new ArrayList<String>();
                tokens = doc.getPalavrasChave();

                for (int i = 0; i < tokens.size(); i++) {


                    String token = tokens.get(i);
                    String insert = "insert into r1tokens (token, id, field) VALUES ('" + token + "'," + id + ", 2)";
                    PreparedStatement stmt = con.prepareStatement(insert);
                    stmt.execute();
                    stmt.close();
                }

                //3 entidade
                tokens = new ArrayList<String>();
                tokens = doc.getEntidade();

                for (int i = 0; i < tokens.size(); i++) {


                    String token = tokens.get(i);
                    String insert = "insert into r1tokens (token, id, field) VALUES ('" + token + "'," + id + ", 3)";
                    PreparedStatement stmt = con.prepareStatement(insert);
                    stmt.execute();
                    stmt.close();
                }

                //4 descricao
                tokens = new ArrayList<String>();
                tokens = doc.getDescricao();

                for (int i = 0; i < tokens.size(); i++) {


                    String token = tokens.get(i);
                    String insert = "insert into r1tokens (token, id, field) VALUES ('" + token + "'," + id + ", 4)";
                    PreparedStatement stmt = con.prepareStatement(insert);
                    stmt.execute();
                    stmt.close();
                }
            }

        } catch (SQLException e) {
            System.out.println("\nErro no SQL ao indexar. Ao adicionar o objeto: " + doc.getObaaEntry());
            System.out.println("String Resumo: " + doc.getResumo());
            e.printStackTrace();
        }
    }

    /**
     * Esse metodo deverá ser executado sempre depois da adiç&atilde;o de todos os
     * documentos na base. Este que preenche as tabelas auxiliares.
     *
     * @param con a conex&atilde;o como banco de dados
     *
     */
    public void populateR1(Connection con) throws SQLException {

        //apaga as tabelas antes de inserir
        apagarCalculosIndice(con);

        PreparedStatement R1Size = con.prepareStatement("INSERT INTO r1size(size) SELECT COUNT(DISTINCT(id)) FROM r1tokens;");

        R1Size.execute();
        R1Size.close();

        PreparedStatement R1IDF = con.prepareStatement("INSERT INTO r1idf(token, idf) SELECT T.token, LOG(S.size)-LOG(COUNT(DISTINCT(id))) FROM r1tokens T, r1size S group by T.token, S.size;");
        R1IDF.execute();
        R1IDF.close();

        //PreparedStatement R1TF = con.prepareStatement("INSERT INTO r1tf(tid, token, tf) SELECT T.id, T.token, if(T.field=1||T.field=2,COUNT(*)*2, COUNT(*)) FROM r1tokens T GROUP BY T.id, T.token;");
        PreparedStatement R1TF = con.prepareStatement("INSERT INTO r1tf(tid, token, tf) SELECT T.id, T.token, if(T.field=1,3, 1) FROM r1tokens T GROUP BY T.id, T.token;");
        R1TF.execute();
        R1TF.close();

        PreparedStatement R1Lenght = con.prepareStatement("INSERT INTO r1length(tid, len) SELECT T.tid, SQRT(SUM(I.idf*I.idf*T.tf*T.tf)) FROM r1idf I, r1tf T WHERE I.token = T.token GROUP BY T.tid;");
        R1Lenght.execute();
        R1Lenght.close();

        PreparedStatement R1Weights = con.prepareStatement("INSERT INTO r1weights(tid, token, weight) SELECT T.tid, T.token, I.idf*T.tf/L.len FROM r1idf I, r1tf T, r1length L WHERE I.token = T.token AND T.tid = L.tid;");
        R1Weights.execute();
        R1Weights.close();

        PreparedStatement R1Sum = con.prepareStatement("INSERT INTO r1sum(token, total) SELECT R.token, SUM(R.weight) FROM r1weights R GROUP BY R.token;");
        R1Sum.execute();
        R1Sum.close();

    }

    /**
     * Apaga as tabelas r1idf, r1size, r1sum, r1tf e r1length do mysql. Estas tabelas armazenam os calculos do indice.
     * @param con  conex&atilde;o como banco de dados
     * @throws SQLException
     * @author Marcos Nunes
     */
    private static void apagarCalculosIndice(Connection con) throws SQLException {
        String sql1 = "DELETE FROM r1idf;";
        String sql2 = "DELETE FROM r1size;";
        String sql3 = "DELETE FROM r1sum;";
        String sql4 = "DELETE FROM r1tf;";
        String sql5 = "DELETE FROM r1length;";


        Statement stm = con.createStatement();
        stm.executeUpdate(sql1); //executa o que tem na variavel slq no mysql
        stm.executeUpdate(sql2);
        stm.executeUpdate(sql3);
        stm.executeUpdate(sql4);
        stm.executeUpdate(sql5);
    }
}
