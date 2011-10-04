package ferramentaBusca;

import ferramentaBusca.indexador.*;
import postgres.Conectar;
import java.sql.*;
import robo.atualiza.importaOAI.Indice;

/**
 * Disponibiliza metodos para indexar a base de busca
 * @author Marcos
 */
public class IndexadorBusca {

    Indexador indexar = new Indexador();
    String atributos[] = {"obaaEntry", "obaaTitle", "obaaKeyword", "obaaEntity"}; //atributos para o criar o indice

    /**
     * M&eacute;todo respons&aacute;vel por indexar um reposit&oacite;rio.
     * Busca na base de dados os atributos que compoem o indice, do reposit&oacite;rio informado, efetua a tokeniza&ccedil&atilde;o, armazena na base e recalcula o indice
     * @param repositorio id do reposit&oacute;rio que ser&aacute; indexado.
     * @param con conex&atilde;o com banco de dados.
     */
    public void IndexaRep(int repositorio, Connection con) {

        indexaDocumentos(0, repositorio, con);
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

    
    /**
     * M&eacute;todo respons&aacute;vel por indexar uma subfedera&ccedil;&atilde;o.
     * Busca na base de dados os atributos que compoem o indice, efetua a tokeniza&ccedil&atilde;o, armazena na base e recalcula o indice
     * @param idSubFed id da subFedera&ccedil;&atilde;o que ser&aacute; indexada.
     * @param con conex&atilde;o com banco de dados.
     */
    public void indexaSubFed(int idSubFed, Connection con) {

        indexaDocumentos(idSubFed, 0, con);
        recalcularIndice(con);
    }

    /**
     * M&eacute;todo respons&aacute;vel por indexar documentos de um reposit&oacute;rio ou de uma Subfedera&ccedil;&atilde;o. Se for informado um id do reposit&oacute;rio maior que zero, &eacute; indexado o reposit&oacute;rio, sen&atilde;o &eacute; indexada a Subfedera&ccedil;&atilde;o informada.
     * Busca na base de dados os atributos que compoem o indice, efetua a tokeniza&ccedil&atilde;o, armazena na base e recalcula o indice
     * @param Opcional. idSubFed id da subFedera&ccedil;&atilde;o que ser&aacute; indexada.
     * @param Opcional. idRepositorio id do reposit&oacute;rio que ser&aacute; indexado. Se for para indexar a subFedera&ccedil;&atilde;o informar zero.
     * @param con conex&atilde;o com banco de dados.
     */
    public void indexaDocumentos(int idSubFed, int idRepositorio, Connection con) {
        if (idSubFed > 0 || idRepositorio > 0) {
            Long inicio = System.currentTimeMillis();
            StopWordTAD stWd = new StopWordTAD(con);
            Indice indice = new Indice();
            System.out.println("FEB: Inicio da indexacao...");
            try {
                String sqlDoc = "";
                if (idRepositorio > 0) {
                    sqlDoc = "SELECT id, obaa_entry FROM documentos WHERE id_repositorio=" + idRepositorio;
                } else {
                    sqlDoc = "SELECT d.id, d.obaa_entry FROM documentos d, repositorios_subfed repsf, dados_subfederacoes dsf WHERE d.id_rep_subfed=repsf.id AND repsf.id_subfed=dsf.id AND dsf.id=" + idSubFed;
                }
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
                System.out.println("\n FEB: Levou " + ((meio - inicio) / 1000) + " segundos inserindo objetos.");


            } catch (SQLException e) {
                System.out.println("FEB: SQL Exception... Erro no SQL: ");
                e.printStackTrace();
            }
        }else
            System.err.println("FEB - ERRO: Deve ser informado o id da subFedera&ccedil;&atilde;o ou do reposit&oacute;rio.");
    }


    public void recalcularIndice(Connection con) {
        try {
            System.err.println("recalculando o indice...");
            Long inicio = System.currentTimeMillis();
            indexar.populateR1(con); //calcula/preeche as tabelas auxiliares
            Long fim = System.currentTimeMillis();
            System.out.println("indice recalculado. Levou " + ((fim - inicio) / 1000) + " segundos inserindo objetos.");
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
//        run.indexaSubFed(7, con);
//        run.indexarTodosRepositorios(con);


        try {
            con.close(); //fechar conexao com o banco de dados
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexao com o Postgres: ");
            e.printStackTrace();
        }

    }
}
