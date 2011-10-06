package robo.atualiza.importaOAI;

import java.util.ArrayList;
import java.util.Set;
import java.sql.*;

/**
 *
 * @author Marcos
 */
public class InsereObjetoBase {

    /**
     * Método que insere dados na base de dados, recebendo metadados em OBAA e inserindo em OBAA.
     *
     * @param attributeSet Referência para a classe LDAPAttributeSet que contém as entradas a serem inseridas na base.
     * @param header Refêrencia para a classe Header que contém os dados da tag Header do XML.
     * @param con Conexão com a base de dados
     *  @param idDoc identificador do documento para aquele atributo na base de dados
     */
    public static int insereObaa(DadosObjetos dadosObjetos, Header header, Connection con, int idRep, String ondeInserir) {

        int idDoc = -1;
//        System.out.println(" =============================");
//        System.out.println("  Inserindo objeto: " + header.getIdentifier());
//        System.out.println(" =============================");


        try {
            if (!testaEntry(header.getIdentifier(), idRep, con, ondeInserir)) {

//if aqui para inserir no rep ou no subrep
                String sql2 = "";
                if (ondeInserir.equalsIgnoreCase("subRep")) {
                    sql2 = "INSERT INTO documentos (obaa_entry, id_rep_subfed)  VALUES (?,?);";
                } else {
                    sql2 = "INSERT INTO documentos (obaa_entry, id_repositorio) VALUES (?,?);";
                }


                PreparedStatement stmt1 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                stmt1.setString(1, header.getIdentifier());
                stmt1.setInt(2, idRep);

                stmt1.execute();

                ResultSet rs = stmt1.getGeneratedKeys();
                rs.next();
                idDoc = rs.getInt(1);
                stmt1.close();


                addBaseDados(dadosObjetos, con, idDoc); //adiciona os valores lidos do xml na base



                return idDoc;
            } else {

                /*************************************************************
                 * ATUALIZAR OS ATRIBUTOS DOS DOCUMENTOS QUE JA ESTAO NA BASE
                 *************************************************************/
                System.out.println("FEB: Ja existe um documento com o entry informado. Entry: " + header.getIdentifier() + "\n");
                return idDoc;
            }
        } catch (SQLException e) {
            System.out.println("FEB ERRO Inserindo dados na tabela documentos. ->" + e);
            return idDoc;
        }
    }

    private static boolean addBaseDados(DadosObjetos dadosObjetos, Connection con, int idDoc) {

        String insert = "INSERT INTO objetos (atributo, valor, documento) VALUES ";

        Set<String> chaves = dadosObjetos.getAtributos().keySet();
        //percorre todo o HashMap
        int numChave = 0;
        for (String chave : chaves) //enquanto tiver chaves chave recebe o conteudo de chaves
        {
            if (chave != null) { // testa se a string chave é diferente de nulo
                ArrayList<String> lista = dadosObjetos.getAtributos().get(chave); //adiciona no ArrayList todos os valores para a chave atual(do for)

                for (int i = 0; i < lista.size(); i++) {

                    if (numChave == 0 && i == 0) {
                        insert += " (?,?,?)";
                    } else {
                        insert += ", (?,?,?)";
                    }

                }
            }
            numChave++;
        }
        try {
            PreparedStatement stmt = con.prepareStatement(insert);

            int cont = 0;
            for (String chave : chaves) //enquanto tiver chaves chave recebe o conteudo de chaves
            {
                if (chave != null) { // testa se a string chave é diferente de nulo
                    ArrayList<String> lista = dadosObjetos.getAtributos().get(chave); //adiciona no ArrayList todos os valores para a chave atual(do for)

                    for (int i = 0; i < lista.size(); i++) {
                        int i2 = cont * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes

                        stmt.setString(i2 + 1, chave);
                        stmt.setString(i2 + 2, lista.get(i));
                        stmt.setInt(i2 + 3, idDoc);
                        cont++;
                    }
                }

            }

            stmt.executeUpdate();

            stmt.close();

        } catch (SQLException e) {
            System.out.println("ERRO AO ADICIONAR ATRIBUTO: " + e);
            return false;
        }

        return true;
    }

//    private static boolean addBaseDados(String atributo, String[] valores, Connection con, int idDoc) {
//
//
//        try {
//            String insert = "INSERT INTO objetos (atributo, valor, documento) VALUES ";
//
//            //adicionar 3 interrogacoes para cada novo valor a ser inserido na base
//            for (int i = 0; i < valores.length; i++) {
//                if (i == 0) {
//                    insert += " (?,?,?)";
//                } else {
//                    insert += ", (?,?,?)";
//                }
//            }
//
//            PreparedStatement stmt = con.prepareStatement(insert);
//
//            for (int i = 0; i < valores.length; i++) {
//                int i2 = i * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes
//
//                stmt.setString(i2 + 1, atributo);
//                stmt.setString(i2 + 2, valores[i]);
//                stmt.setInt(i2 + 3, idDoc);
//            }
//
//
//            stmt.executeUpdate();
//
//
//            stmt.close();
//
//        } catch (SQLException e) {
//            System.out.println("ERRO AO ADICIONAR ATRIBUTO: " + e);
//            return false;
//        }
//
//        return true;
//    }
    /**
     * Testa se o obaa_entry já existe na base de dados
     * @param obaa_entry String contendo o obaa_entry
     * @return true se o objeto existe e false se não existir
     */
    public static boolean testaEntry(String obaa_entry, int idRep, Connection con, String ondeTestar) throws SQLException {

        Statement stm = con.createStatement();
        String sql = "";
        //fazer consulta sql
        if (ondeTestar.equalsIgnoreCase("subRep")) {
            sql = "SELECT id FROM documentos WHERE obaa_entry='" + obaa_entry + "' AND id_rep_subfed=" + idRep;
        } else {
            sql = "SELECT id FROM documentos WHERE obaa_entry='" + obaa_entry + "' AND id_repositorio=" + idRep;
        }
        ResultSet rs = stm.executeQuery(sql); //executa a consulta que esta na string sqlDadosLdap
        if (rs.next()) //testa se tem o proximo resultado
        {
            return true;
        } else {
            return false;
        }

    }
//    private static int getDocID(String obaa_entry, Connection con) {
//
//
//
//            try {
//                String select = "SELECT id FROM objetos WHERE (obaa_entry='"+obaa_entry+")";
//                PreparedStatement stmt = con.prepareStatement(select);
//                stmt.execute();
//                ResultSet rs = stmt.executeQuery(entryQ);
//                stmt.close();
//
//            } catch (SQLException e) {
//                System.out.println("ERRO AO ADICIONAR ATRIBUTO: " + e);
//
//            }
//
//
//    }
}
