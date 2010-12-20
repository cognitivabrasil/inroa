/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.importaSubFederacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import postgres.Conectar;
import postgres.Configuracao;

/**
 *
 * @author Marcos
 */
public class ImportaSubFederacao {

    public boolean atualiza_subFederacao(Connection con) {
        boolean atualizou = false;
        String sql = "SELECT id, login, senha, porta, ip, base, data_ultima_atualizacao FROM dados_subfederacoes where data_ultima_atualizacao <= (now() - ('24 HOUR')::INTERVAL);";

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("id");
                String base = rs.getString("base");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                String ip = rs.getString("ip");
                int porta = rs.getInt("porta");
                Timestamp dataAtualizacao = rs.getTimestamp("data_ultima_atualizacao");


                Configuracao conf = new Configuracao(base, login, senha, ip, porta);

                Conectar conecta = new Conectar(conf);
                Connection conSub = conecta.conectaBD(); //chama o metodo conectaBD da classe conectar

                documentos(id, dataAtualizacao, con, conSub);
                conSub.close();//fecha conexao com a subfederacao

                atualizaTimestampSubFed(con, id); //atualiza a hora da ultima atualizacao
                atualizou = true;
            }



        } catch (SQLException e) {
            System.err.println("SQL Exception... Erro na classe importaSubFederacao:" + e.getMessage());
            e.printStackTrace();
        }
        return atualizou;
    }

    public void documentos(int idSubFed, Timestamp ultimaAtualizacao, Connection con, Connection conSub) throws SQLException {
        System.out.println("FEB: Atualizando Subfederacao");
        String sqlSub = "SELECT id_repositorio||';FEB;'||obaa_entry as entry from  documentos d where timestamp >= '" + ultimaAtualizacao + "'";

        Statement stm = conSub.createStatement();
        ResultSet rs = stm.executeQuery(sqlSub);

        while (rs.next()) {
            String obaaEntry = rs.getString("entry");
            String insert = "INSERT INTO documentos (obaa_entry, id_subfed) VALUES (?,?);";

            try {
                PreparedStatement stmt1 = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                stmt1.setString(1, obaaEntry);
                stmt1.setInt(2, idSubFed);
                stmt1.execute();

                ResultSet rsInsert = stmt1.getGeneratedKeys();
                rsInsert.next();

                objetos(rsInsert.getInt(1), obaaEntry, con, conSub);
                stmt1.close();
            } catch (SQLException s) {
                if (s.getMessage().contains("unicidade")) {
                    //se ja existir entao deleta o documento e depois insere o novo
                    String sqlDelete = "DELETE FROM documentos WHERE obaa_entry='" + obaaEntry + "' AND id_subfed=" + idSubFed;
                    Statement stmException = con.createStatement();
                    int result = stmException.executeUpdate(sqlDelete);
                    if (result > 0) {
                        String sqlInsert = "INSERT INTO documentos (obaa_entry, id_subfed) VALUES (?,?);";
                        PreparedStatement stmt1 = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                        stmt1.setString(1, obaaEntry);
                        stmt1.setInt(2, idSubFed);
                        stmt1.execute();

                        ResultSet rsInsert = stmt1.getGeneratedKeys();
                        rsInsert.next();

                        objetos(rsInsert.getInt(1), obaaEntry, con, conSub);
                        stmt1.close();
                    }

                }
            }
        }
        System.out.println("FEB: Subfederacao atualizada.");

    }

    public void objetos(int idDoc, String obaaEntry, Connection con, Connection conSub) throws SQLException {

        String idEntry[] = obaaEntry.split(";FEB;");
        String consultaSub = "SELECT atributo, valor, r.nome FROM documentos d, objetos o, repositorios r" +
                " WHERE o.documento=d.id" +
                " AND d.id_repositorio=r.id" +
                " AND d.obaa_entry='" + idEntry[1] + "'" +
                " AND d.id_repositorio=" + idEntry[0] +
                " AND (atributo ~* '^obaaTitle$' OR atributo ~* '^obaaKeyword$')";
//                " AND (atributo ~* '^obaaTitle$' OR atributo ~* '^obaaDescription$' OR atributo ~* '^obaaKeyword$')";
        Statement stmSub = conSub.createStatement();
        ResultSet rsSub = stmSub.executeQuery(consultaSub);
        ArrayList<String> atributos = new ArrayList<String>();
        ArrayList<String> valores = new ArrayList<String>();
        while (rsSub.next()) {
            //necessario para aparecer o nome do repositorio na busca da confederacao
            if(rsSub.isFirst()){
                atributos.add("nomeRepositorio");
                valores.add(rsSub.getString("nome"));
            }
            atributos.add(rsSub.getString("atributo"));
            valores.add(rsSub.getString("valor"));
        }
        String insertObjeto = "INSERT INTO objetos (atributo, valor, documento) VALUES";

        //for para preencher as interrogacoes
        for (int i = 0; i < atributos.size(); i++) {
            if (i == 0) {
                insertObjeto += " (?,?,?)";
            } else {
                insertObjeto += ", (?,?,?)";
            }
        }
        PreparedStatement stmt = con.prepareStatement(insertObjeto);

        //form para preencher os valores a serem inseridos
        for (int i = 0; i < atributos.size(); i++) {
            int i2 = i * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes

            stmt.setString(i2 + 1, atributos.get(i));
            stmt.setString(i2 + 2, valores.get(i));
            stmt.setInt(i2 + 3, idDoc);
        }

        stmt.executeUpdate();
        stmt.close();


    }

    /**
     * M&eacute;todo que atualiza a data da &uacute;ltima atualiza&ccedil;&atilde;o para a hora atual
     * @param con conex&atilde;o com a base de dados local
     * @param idSubFed id da subfedera&ccedil;&atilde;o que foi atualizada
     * @throws SQLException
     */
    public void atualizaTimestampSubFed(Connection con, int idSubFed) throws SQLException {
        String sql = "UPDATE dados_subfederacoes set data_ultima_atualizacao = now() WHERE id=" + idSubFed;
        Statement stm = con.createStatement();
        stm.executeUpdate(sql);
    }

}
