/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import operacoesPostgre.Remover;
import postgres.Conectar;
import postgres.Configuracao;
import robo.util.Operacoes;

/**
 *
 * @author Marcos
 */
public class SubFederacao {

    /**
     * Testa se alguma subfedera&ccedil;&atilde;o precisa ser atualizada, se sim chama o m&etodo respons&aacute;vel por isso.
     * @param con Conex&atilde;o com a base de dados local.
     * @return true ou false indicando se alguma subfedera&ccedil;&atilde;o foi atualizada ou n&atilde;
     */
    public boolean atualiza_subFederacao(Connection con) {
        boolean atualizou = false;
        String sql = "SELECT id, login, senha, porta, ip, base, nome, data_ultima_atualizacao FROM dados_subfederacoes where data_ultima_atualizacao <= (now() - ('24 HOUR')::INTERVAL);";

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String base = rs.getString("base");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                String ip = rs.getString("ip");
                String nome = rs.getString("nome");
                int porta = rs.getInt("porta");
                Timestamp dataAtualizacao = rs.getTimestamp("data_ultima_atualizacao");


                //se a data da ultima atualização for inferior a 01/01/1000 apaga todos as informacoes do repositorio
                if (Operacoes.testarDataAnteriorMil(rs.getDate("data_ultima_atualizacao"))) {
                    Remover deleta = new Remover();
                    System.out.println("FEB: Deletando toda a base de dados da Subfederação: " + nome.toUpperCase());
                    deleta.setDebugOut(false); //seta que nao e para imprimir mensagens de erro
                    try {
                        deleta.apagaObjetosSubfederacao(id, con);
                        System.out.println("FEB: Base deletada!");
                    } catch (Exception e) {
                        System.out.println("FEB: Error: " + e.toString());
                    }
                }

                Configuracao conf = new Configuracao(base, login, senha, ip, porta);

                Conectar conecta = new Conectar(conf);
                Connection conSub = conecta.conectaBD(); //chama o metodo conectaBD da classe conectar
                if (!conSub.isClosed()) {
                    atualizaRepSubfed(id, con, conSub); //atualiza os repositorios da subfederacao
                    verificaObjetosDeletados(con, conSub); //verifica na subfederacao se tem objetos a serem deletados
                    documentos(id, dataAtualizacao, con, conSub);
                    conSub.close();//fecha conexao com a subfederacao

                    atualizaTimestampSubFed(con, id); //atualiza a hora da ultima atualizacao
                    atualizou = true;
                } else {
                    System.err.println("FEB: ERRO. Nao foi possivel conectar na base da Subfederacao!!");
                }
            }



        } catch (SQLException e) {
            System.err.println("FEB: SQL Exception... Erro na classe importaSubFederacao:" + e.getMessage());

        } finally {
            return atualizou;
        }
    }

    /**
     * Sincroniza os objetos da tabela documento da subfedera&ccedil;&atilde;o com a base local
     * @param idSubFed id da subfedera&ccedil;&atilde;o
     * @param ultimaAtualizacao data da &uacute;ltima vez que a subfedera&ccedil;&atilde;o foi atualizada
     * @param con Conex&atilde;o com a base de dados local.
     * @param conSub con Conex&atilde;o com a base de dados da subfedera&ccedil;&atilde;o.
     * @throws SQLException
     */
    private void documentos(int idSubFed, Timestamp ultimaAtualizacao, Connection con, Connection conSub) throws SQLException {
        System.out.println("FEB: Atualizando Subfederacao");
        String sqlSub = "SELECT d.id_repositorio||';FEB;'||d.obaa_entry as entry, r.nome from  documentos d, repositorios r where d.id_repositorio=r.id AND d.timestamp >= '" + ultimaAtualizacao + "'";

        Statement stmSub = conSub.createStatement();
        ResultSet rs = stmSub.executeQuery(sqlSub);

        while (rs.next()) {

            String nomeRep = rs.getString("nome");

            String obaaEntry = rs.getString("entry");
            String insertSelect = "INSERT INTO documentos (obaa_entry, id_rep_subfed) "
                    + "SELECT '" + obaaEntry + "', id FROM repositorios_subfed WHERE id_subfed=" + idSubFed + " AND nome='" + nomeRep + "'";

            try {
                Statement stmLocal = con.createStatement();
                stmLocal.execute(insertSelect, Statement.RETURN_GENERATED_KEYS);
                ResultSet rsInsert = stmLocal.getGeneratedKeys();
                rsInsert.next();

                objetos(rsInsert.getInt(1), obaaEntry, con, conSub);
                stmLocal.close();

            } catch (SQLException s) {
                if (s.getMessage().contains("unicidade")) {
                    //se ja existir entao deleta o documento e depois insere o novo
                    String sqlDelete = "DELETE FROM documentos WHERE obaa_entry='" + obaaEntry;
                    Statement stmException = con.createStatement();
                    int result = stmException.executeUpdate(sqlDelete);
                    if (result > 0) {
                        Statement stmLocal = con.createStatement();
                        stmLocal.execute(insertSelect, Statement.RETURN_GENERATED_KEYS);

                        ResultSet rsInsert = stmLocal.getGeneratedKeys();
                        rsInsert.next();

                        objetos(rsInsert.getInt(1), obaaEntry, con, conSub);
                        stmLocal.close();
                    }

                } else {
                    System.err.println("Erro no sql metodo documentos da classe SubFederacao: " + s.getMessage());
                }
            }
        }
        System.out.println("FEB: Subfederacao atualizada.");

    }

    /**
     * Insere coleta os metadados dos objetos atualizados no metodo "documentos" da subfedera&ccedil;&atilde;o e insere na base local
     * @param idDoc id do documento (da tabela documento)
     * @param obaaEntry obaaEntry do documento
     * @param con Conex&atilde;o com a base de dados local.
     * @param conSub con Conex&atilde;o com a base de dados da subfedera&ccedil;&atilde;o.
     * @throws SQLException
     */
    private void objetos(int idDoc, String obaaEntry, Connection con, Connection conSub) throws SQLException {

        String idEntry[] = obaaEntry.split(";FEB;");
        String consultaSub = "SELECT o.atributo, o.valor FROM documentos d, objetos o"
                + " WHERE o.documento=d.id"
                + " AND d.obaa_entry='" + idEntry[1] + "'"
                + " AND d.id_repositorio=" + idEntry[0]
                + " AND (atributo ~* '^obaaTitle$' OR atributo ~* '^obaaDescription$' OR atributo ~* '^obaaKeyword$' OR atributo ~* '^obaaDate$' OR atributo ~* '^obaaLocation$')";

        Statement stmSub = conSub.createStatement();
        ResultSet rsSub = stmSub.executeQuery(consultaSub);
        ArrayList<String> atributos = new ArrayList<String>();
        ArrayList<String> valores = new ArrayList<String>();
        while (rsSub.next()) {

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
     * Verifica se na subfedera&ccedil;&atilde;o existem objetos deletados, e deleta na federa&ccedil;&atilde;o local
     * @param conLocal conex&atilde;o com a base de dados local
     * @param conSub conex&atilde;o com a base de dados da subfedera&ccedil;&atilde;o
     * @throws SQLException
     */
    private void verificaObjetosDeletados(Connection conLocal, Connection conSub) {
        String sql = "SELECT obaa_entry, id_repositorio FROM excluidos WHERE data >= (now() - ('30 HOUR')::INTERVAL);";
        try {
            Statement stmSub = conSub.createStatement();
            ResultSet rsSub = stmSub.executeQuery(sql);
            while (rsSub.next()) {
                String obaaEntry = rsSub.getString("id_repositorio") + ";FEB;" + rsSub.getString("obaa_entry");

                System.out.println("FEB: Deletando objeto com obaaEntry: '" + obaaEntry + "'");

                String sqlDelete = "DELETE FROM documentos WHERE obaa_entry='" + obaaEntry + "'";
                System.out.println(sqlDelete);
                Statement stmLocal = conLocal.createStatement();
                stmLocal.executeUpdate(sqlDelete);//executa o sql que tem na variavel sqlDelete
            }
        } catch (SQLException s) {
            System.err.println("FEB: Exluindo objetos... ");
            s.printStackTrace();
        }

    }

    /**
     * M&eacute;todo que atualiza a data da &uacute;ltima atualiza&ccedil;&atilde;o para a hora atual
     * @param con conex&atilde;o com a base de dados local
     * @param idSubFed id da subfedera&ccedil;&atilde;o que foi atualizada
     * @throws SQLException
     */
    private void atualizaTimestampSubFed(Connection con, int idSubFed) throws SQLException {
        String sql = "UPDATE dados_subfederacoes set data_ultima_atualizacao = now() WHERE id=" + idSubFed;
        Statement stm = con.createStatement();
        stm.executeUpdate(sql);
    }

    /**
     * M&eacute;todo que atualiza os reposit&oacute;rios da subfedera&ccedil;&atilde;o
     * @param conLocal conex&atilde;o com a base de dados local
     * @param conSub conex&atilde;o com a base de dados da subfedera&ccedil;&atilde;o
     * @throws SQLException
     */
    private void atualizaRepSubfed(int idSubFed, Connection conLocal, Connection conSub) throws SQLException {
        String selectSub = "SELECT nome FROM repositorios";
        String selectLocal = "SELECT nome FROM repositorios_subfed";

        Statement stmSub = conSub.createStatement();
        ResultSet rsSub = stmSub.executeQuery(selectSub);

        Statement stmLocal = conLocal.createStatement();
        ResultSet rsLocal = stmLocal.executeQuery(selectLocal);

        ArrayList<String> listaSub = new ArrayList<String>();
        ArrayList<String> listaLocal = new ArrayList<String>();
        while (rsLocal.next()) {
            listaLocal.add(rsLocal.getString(1));
        }
        rsLocal.close();

        while (rsSub.next()) {
            String nomeSub = rsSub.getString(1);
            listaSub.add(nomeSub);
            if (!listaLocal.contains(nomeSub)) {
                String insert = "INSERT INTO repositorios_subfed (id_subfed, nome) VALUES (" + idSubFed + ", '" + nomeSub + "')";
                stmLocal.executeUpdate(insert);
            }
        }
        rsSub.close();

        for (int i = 0; i < listaLocal.size(); i++) {
            if (!listaSub.contains(listaLocal.get(i))) {
                String delete = "DELETE FROM repositorios_subfed WHERE nome='" + listaLocal.get(i) + "'";
                stmLocal.executeUpdate(delete);
            }
        }

    }
}
