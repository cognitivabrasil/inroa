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
import java.util.HashMap;
import java.util.Set;

/**
 * Indexador é a classe que faz os processos de contruç&atilde;o da base de
 * dados para preparaç&atilde;o da posterior recuperaç&atilde;o de informações
 *
 * @author Luiz Rossi
 */
public class Indexador {
    private String dataXML;

    public Indexador() {
        dataXML = "0";
    }

    /**
     * M&eacute;todo que adiciona o documento doc na base de dados Postgres
     * @param doc Docuemnto a ser adicionado
     * @param con A conex&atilde;o do banco de dados
     */
    public void addDocLimpantoTokens(Documento doc, Connection con) throws SQLException {
        String sqlDel = "DELETE FROM r1tokens where id=" + doc.getId();
        Statement stm = con.createStatement();
        stm.executeUpdate(sqlDel); //executa o que tem na variavel sql1
        addDoc(doc, con);
    }

    /**
     * M&eacute;todo que adiciona o documento doc na base de dados Postgres
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


        int id = doc.getId(); //recebe o id do documento que foi inserido na tabela documentos

        try {

            //1 titulo
            ArrayList<String> tokensTitulo = doc.getTitulo();
            ArrayList<String> tokensPChave = doc.getPalavrasChave();
            ArrayList<String> tokensEntidade = doc.getEntidade();
            ArrayList<String> tokensDescricao = doc.getDescricao();

            if (!(tokensDescricao.isEmpty() && tokensEntidade.isEmpty() && tokensPChave.isEmpty() && tokensTitulo.isEmpty())) {
                String insert = "INSERT INTO r1tokens (token, id, field) VALUES";

                int cont = 0;
                //for para preencher as interrogacoes dos titulos
                for (int i = 0; i < tokensTitulo.size(); i++) {
                    if (cont == 0) {
                        insert += " (?,?,?)";
                    } else {
                        insert += ", (?,?,?)";
                    }
                    cont++;
                }
                //for para preencher as interrogacoes das palavras chaves
                for (int i = 0; i < tokensPChave.size(); i++) {
                    if (cont == 0) {
                        insert += " (?,?,?)";
                    } else {
                        insert += ", (?,?,?)";
                    }
                    cont++;
                }
                //for para preencher as interrogacoes das entidades
                for (int i = 0; i < tokensEntidade.size(); i++) {
                    if (cont == 0) {
                        insert += " (?,?,?)";
                    } else {
                        insert += ", (?,?,?)";
                    }
                    cont++;
                }
                //for para preencher as interrogacoes das descricoes
                for (int i = 0; i < tokensDescricao.size(); i++) {
                    if (cont == 0) {
                        insert += " (?,?,?)";
                    } else {
                        insert += ", (?,?,?)";
                    }
                    cont++;
                }

                PreparedStatement stmt = con.prepareStatement(insert);

                cont = 0;
                //1 titulo
                int atributo = 1;
                //for para preencher os values do titulo
                for (int i = 0; i < tokensTitulo.size(); i++) {
                    int i2 = cont * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes
                    String token = tokensTitulo.get(i);
                    stmt.setString(i2 + 1, token);
                    stmt.setInt(i2 + 2, id);
                    stmt.setInt(i2 + 3, atributo);
                    cont++;
                }
                //for para preencher os values das palavras chaves
                //2 palavras chave
                atributo = 2;
                for (int i = 0; i < tokensPChave.size(); i++) {
                    int i2 = cont * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes
                    String token = tokensPChave.get(i);
                    stmt.setString(i2 + 1, token);
                    stmt.setInt(i2 + 2, id);
                    stmt.setInt(i2 + 3, atributo);
                    cont++;
                }
                //for para preencher os values das Entidades
                //3 entidade
                atributo = 3;
                for (int i = 0; i < tokensEntidade.size(); i++) {
                    int i2 = cont * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes
                    String token = tokensEntidade.get(i);
                    stmt.setString(i2 + 1, token);
                    stmt.setInt(i2 + 2, id);
                    stmt.setInt(i2 + 3, atributo);
                    cont++;
                }
                //for para preencher os values do xx
                //4 descricao
                atributo = 4;
                for (int i = 0; i < tokensDescricao.size(); i++) {
                    int i2 = cont * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes
                    String token = tokensDescricao.get(i);
                    stmt.setString(i2 + 1, token);
                    stmt.setInt(i2 + 2, id);
                    stmt.setInt(i2 + 3, atributo);
                    cont++;
                }

                
                stmt.executeUpdate();
                stmt.close();
            }

        }

        catch (SQLException e) {
            System.out.println("\nErro no SQL ao indexar. Ao adicionar o objeto: " + doc.getObaaEntry());
            System.out.println("");
//            System.out.println("String Resumo: " + doc.getResumo());
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

        preencheR1Tokens(con); //procura por documentos que nao foram tokenizados e tokeniza.
        
        PreparedStatement R1Size = con.prepareStatement("INSERT INTO r1size(size) SELECT COUNT(id) FROM documentos;");

        R1Size.execute();
        R1Size.close();

        PreparedStatement R1IDF = con.prepareStatement("INSERT INTO r1idf(token, idf) SELECT T.token, LOG(S.size)-LOG(COUNT(DISTINCT(id))) FROM r1tokens T, r1size S group by T.token, S.size;");
        R1IDF.execute();
        R1IDF.close();

        //PreparedStatement R1TF = con.prepareStatement("INSERT INTO r1tf(tid, token, tf) SELECT T.id, T.token, if(T.field=1||T.field=2,COUNT(*)*2, COUNT(*)) FROM r1tokens T GROUP BY T.id, T.token;");


        PreparedStatement R1TF = con.prepareStatement("INSERT INTO r1tf(tid, token, tf) SELECT T.id, T.token, sum(CASE t.field WHEN 1 THEN 3 ELSE 1 END)FROM r1tokens t GROUP BY T.id, T.token;");
        R1TF.execute();
        R1TF.close();

        PreparedStatement R1Lenght = con.prepareStatement("INSERT INTO r1length(tid, len) SELECT T.tid, SQRT(SUM(I.idf*I.idf*T.tf*T.tf)) FROM r1idf I, r1tf T WHERE I.token = T.token GROUP BY T.tid;");
        R1Lenght.execute();
        R1Lenght.close();

        String sqlDeletar1weights = "DELETE FROM r1weights";
        PreparedStatement deletar1weights = con.prepareStatement(sqlDeletar1weights);
        deletar1weights.executeUpdate();

        PreparedStatement R1Weights = con.prepareStatement("INSERT INTO r1weights(tid, token, weight)  SELECT  T.tid, T.token, (CASE L.len WHEN 0 THEN 0 ELSE I.idf*T.tf/L.len END) as weight FROM r1idf I, r1tf T, r1length L  WHERE I.token = T.token AND T.tid = L.tid;");
        R1Weights.execute();
        R1Weights.close();

//////        PreparedStatement R1Sum = con.prepareStatement("INSERT INTO r1sum(token, total) SELECT R.token, SUM(R.weight) FROM r1weights R GROUP BY R.token;");
//////        R1Sum.execute();
//////        R1Sum.close();

//        apagarCalculosIndice(con);

    }

    /**
     * Apaga as tabelas r1idf, r1size, r1sum, r1tf e r1length da base de dados. Estas tabelas armazenam os calculos do indice.
     * @param con  conex&atilde;o como banco de dados
     * @throws SQLException
     * @author Marcos Nunes
     */
    private static void apagarCalculosIndice(Connection con) throws SQLException {
        String sql1 = "DELETE FROM r1idf;";
        String sql2 = "DELETE FROM r1size;";
//        String sql3 = "DELETE FROM r1sum;";
        String sql4 = "DELETE FROM r1tf;";
        String sql5 = "DELETE FROM r1length;";
        String sql6 = "DELETE FROM consultas;";


        Statement stm = con.createStatement();
        stm.executeUpdate(sql1); //executa o que tem na variavel sql1
        stm.executeUpdate(sql2);
//        stm.executeUpdate(sql3);
        stm.executeUpdate(sql4);
        stm.executeUpdate(sql5);
        stm.executeUpdate(sql6);
    }

    public void preencheR1Tokens(Connection con) throws SQLException {
        //retorna todos documentos que nao possuem r1tokens preenchido
        String sql = "select d.id, d.obaa_entry, d.id_repositorio from documentos d left join r1tokens r on r.id = d.id where r.id IS NULL AND d.deleted = FALSE";
        StopWordTAD stWd = new StopWordTAD(con);

        
        Statement stm = con.createStatement();
        ResultSet rs1 = stm.executeQuery(sql);
        ArrayList<Integer> idsApagar = new ArrayList<Integer>();
        HashMap<String, Integer> docErro = new HashMap<String, Integer>();
        while (rs1.next()) {
            Documento doc = new Documento(stWd);
            int id = rs1.getInt("id");
            String obaaEntry = rs1.getString("obaa_entry");
            int repositorio = rs1.getInt("id_repositorio");

            Statement stm2 = con.createStatement();
            String sqlDoc = "SELECT atributo, valor"
                    + " FROM objetos"
                    + " WHERE documento=" + id
                    + " AND (atributo ~* '^obaaTitle$' OR atributo ~* '^obaaKeyword$' OR atributo ~* '^obaaDescription$')";

            ResultSet rsDoc = stm2.executeQuery(sqlDoc);
            boolean semAtributo = true;
            while (rsDoc.next()) {
                semAtributo = false;
                String atributo = rsDoc.getString("atributo");
                String valor = rsDoc.getString("valor");

                if (atributo.equalsIgnoreCase("obaaTitle")) {
                    doc.setTitulo(valor);
                } else if (atributo.equalsIgnoreCase("obaaDescription")) {
                    doc.setDescricao(valor);
                } else if (atributo.equalsIgnoreCase("obaaKeyword")) {
                    doc.setPalavrasChave(valor);
                }
            }
            if (semAtributo) { //se nao entrou no while insere o id no arraylist para o log
                docErro.put(obaaEntry, repositorio);
                idsApagar.add(id);
            } else {
                doc.setId(id);
                addDoc(doc, con); //preenche r1tokens
            }
        }


        if (!docErro.isEmpty()) {
            System.err.println("FEB: Foram encontrados documentos sem nenhum atributo. \n Lista de obaaEntrys e ids dos repositorios:");

            Set<String> chaves = docErro.keySet();
//                //percorre todo o HashMap

            for (String chave : chaves) //enquanto tiver chaves chave recebe o conteudo de chaves
                {
                   System.err.println("id repositorio: "+docErro.get(chave)+" ObaaEntry: "+chave);
               }
            
            String sqlDelete = "DELETE FROM documentos WHERE id=";
            for (int i = 0; i < idsApagar.size(); i++) {
                if(i==0){
                    sqlDelete+=idsApagar.get(i);
                }else{
                    sqlDelete+=" OR id="+idsApagar.get(i);
                }

            }
           stm.executeUpdate(sqlDelete);
            System.err.println("Estes documentos foram excluidos agora.");
        }

    }

    public void setDataXML(String dataXML) {
        this.dataXML = dataXML;
    }

    public String getDataXML() {
        return dataXML;
    }

}
