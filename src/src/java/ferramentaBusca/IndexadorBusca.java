package ferramentaBusca;

import ferramentaBusca.indexador.*;
import postgres.Conectar;
import java.sql.*;
import robo.importa_OAI.Indice;

/**
 * Disponibiliza metodos para indexar a base de busca
 * @author Marcos
 */
public class IndexadorBusca {

    Indexador indexar = new Indexador();

    /**
     * M&aacute;todo respons&aacute;vel por indexar a base de dados LDAP.
     * Efetua uma busca no metadiretorio do LDAP procurando por todos os objetos do repositorio informado e passa para a ferramenta de Recuperação de Informações (RI) para indexar os termos.
     * @param nomeRepMeta Nome do repositorio ou do metadiretorio contido da tabela repositorios do banco de dados. O padrão para o metadiret&oacute;rio é 'todos'
     * @param con conex&atilde;o com banco de dados.
     */
    public void IndexaRep(int repositorio, Connection con) {

        Long inicio = System.currentTimeMillis();
        StopWordTAD stWd = new StopWordTAD();
        Indice indice = new Indice();
        String atributos[] = {"obaaEntry", "obaaTitle", "obaaKeyword", "obaaEntity"};

        try {
            String sqlDoc = "SELECT id, obaa_entry FROM documentos WHERE id_repositorio=" + repositorio;
            Statement stmtDoc = con.createStatement();
            ResultSet resDoc = stmtDoc.executeQuery(sqlDoc);
            while (resDoc.next()) { //percorre os documentos do repositorio informado
            int idDoc = resDoc.getInt("id");
                String sql = "SELECT o.valor, o.atributo FROM objetos o, documentos d WHERE d.id=" + idDoc + " AND d.id=o.documento AND (";
                for (int i = 0; i < atributos.length; i++) {
                    if (i == 0) {
                        sql += " o.atributo = '" + atributos[i] + "'";
                    } else {
                        sql += " OR o.atributo = '" + atributos[i] + "'";
                    }
                }
                sql += ")";

                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery(sql);

                Documento doc = new Documento(stWd);
                doc.setId(idDoc);//adiciona o id do documento
                doc.setObaaEntry(resDoc.getString("obaa_entry"));
                while (rs.next()) {
                    String atributo = rs.getString("atributo").replace("obaa", "").toLowerCase();
                    String valor = rs.getString("valor");
                    indice.setIndice(atributo, valor, doc);
                }
                //apagar os tokens existentes
                indexar.addDocLimpantoTokens(doc, con);
            }



            Long meio = System.currentTimeMillis();
            System.out.println("\n- Levou " + ((meio - inicio) / 1000) + " segundos inserindo objetos.");
//            System.out.println("\nInseriu todos. Agora está preenchedo tabelas auxiliares.");
//
//            indexar.populateR1(con); //preenche as tabelas auxiliares
//
//            Long fim = System.currentTimeMillis();
//            System.out.println("- Levou " + ((fim - meio) / 1000) + " segundos calculando tabelas auxiliares.");


        } catch (SQLException e) {
            System.out.println("SQL Exception... Erro no SQL: ");
            e.printStackTrace();
        }
    }


    /**
     * Testa se o obaa_entry já existe na base de dados
     * @param obaa_entry String contendo o obaa_entry
     * @param con conexão com banco de dados
     * @return true se o objeto existe e false se não existir
     */
    public boolean testaEntry(String obaa_entry, Connection con) throws SQLException {
        boolean resultado;

        Statement stm = con.createStatement();
        //fazer consulta sql
        String sql = "SELECT id FROM documentos where obaa_entry='" + obaa_entry + "'";
        ResultSet rs = stm.executeQuery(sql); //executa a consulta que esta na string sqlDadosLdap
        if (rs.next()) //testa se tem o proximo resultado
        {
            resultado = true;
        } else {
            resultado = false;
        }

        return resultado;
    }

    /**
     * M&eacute;todo respons&aacute;vel por indexar todos reposit&oacute;rios cadastrados no banco de dados
     * @param con Conexao com o banco de dados
     */
    public void indexarTodosRepositorios(Connection con) {
        String sql = "select id, nome from repositorios where nome != 'todos';";

        try {
            Statement stmDadosLdap = con.createStatement();
            ResultSet rset = stmDadosLdap.executeQuery(sql); //executa a consulta que esta na string sqlDadosLdap
            while (rset.next()) {
                System.out.println("Indexando repositorio " + rset.getString("nome"));
                IndexaRep(rset.getInt("id"), con); //indexa o repositorio informado

            }
           System.out.println("\nInseriu todos. Agora está preenchedo tabelas auxiliares.");
            System.out.println("Calculando o indice....");
           Long meio = System.currentTimeMillis();

            indexar.populateR1(con); //preenche as tabelas auxiliares

            Long fim = System.currentTimeMillis();
            System.out.println("- Levou " + ((fim - meio) / 1000) + " segundos calculando tabelas auxiliares.");
            System.out.println("Indice calculado!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void recalcularIndice(Connection con) {
        try {
            System.out.println("recalculando o indice...");
            indexar.populateR1(con); //calcula/preeche as tabelas auxiliares
            System.out.println("indice recalculado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main que chama o metodo para indexar todos os repositorios     *
     */
    public static void main(String args[]) {
        IndexadorBusca run = new IndexadorBusca();
        Conectar conectar = new Conectar(); //instancia uma variavel da classe Postgres.conectar
        Connection con = conectar.conectaBD();

        //run.indexarTodosRepositorios(con); //cria o indice com todos os repositorios
//        run.IndexaRep(7, con);
        run.recalcularIndice(con);
//        run.indexarTodosRepositorios(con);

        
        try {
            con.close(); //fechar conexao com o banco de dados
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexao com o Postgres: ");
            e.printStackTrace();
        }

    }
}
