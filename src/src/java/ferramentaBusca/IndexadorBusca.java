/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramentaBusca;

import ferramentaBusca.indexador.*;
import mysql.Conectar;
import java.sql.*;
import com.novell.ldap.*;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import mysql.ConsultaNomeFederacao;

/**
 * Disponibiliza metodos para indexar a base de busca
 * @author Marcos
 */
public class IndexadorBusca {

   
    Indexador indexar = new Indexador();

    /**
     * Método responsável por indexar a base de dados LDAP.
     * Efetua uma busca no metadiretorio do LDAP procurando por todos os objetos do repositorio informado e passa para a ferramenta de Recuperação de Informações (RI) para indexar os termos.
     * @param nomeRepMeta Nome do repositorio ou do metadiretorio contido da tabela repositorios do mysql. O padrão para o metadiret&oacute;rio é 'todos'
     * @param con conex&atilde;o com mysql.
     */
    public void IndexaRep(int repositorio, Connection con) {
        
        Long inicio = System.currentTimeMillis();
        int searchScope = LDAPConnection.SCOPE_SUB;//buscar nos nodos filhos tb
        int ldapVersion = LDAPConnection.LDAP_V3;

        LDAPConnection lc = new LDAPConnection();

        String attrs[] = {"obaaEntry", "obaaTitle", "obaaKeyword", "obaaEntity", "obaaDescription", "obaaEducationalDescription", "obaaDate", "obaaLocation", "obaaIdentifier"};

        String sqlDadosLdap = "select l.ip, if(r.nome='todos',l.dn, concat('ou=',i.nome_na_federacao,',',l.dn)) as dn, l.login, l.senha, l.porta from ldaps l, info_repositorios i, repositorios r " +
                "WHERE r.id=i.id_repositorio " +
                "AND i.ldapDestino=l.id " +
                "and r.id=" + repositorio + ";";
        //sqlDadosLdap = "select ip, dn, login, porta, senha from ldaps where nome='ldap1';";
        try {

            Statement stmDadosLdap = con.createStatement();
            ResultSet rs = stmDadosLdap.executeQuery(sqlDadosLdap); //executa a consulta que esta na string sqlDadosLdap
            rs.next(); //chama o proximo resultado do sql

            //coloca nas variaveis as informacoes coletadas do mysql
            String ldapHost = rs.getString("ip");
            String searchBase = rs.getString("dn");
            String loginDN = rs.getString("login");
            String password = rs.getString("senha");
            int ldapPort = rs.getInt("porta");
            System.out.println("ip: " + ldapHost + "\ndn: " + searchBase);
            int numDoObj = 0;

            String titulo = "";
            String palavraChave = "";
            String entity = "";
            String identificador = "";
            String descricao = "";
            String resumo = "";
            String data = "";
            String localizacao = "";
            int servidor = 0;

            String searchFilter = "obaaEntry=*";//recebe como parametro a palavra chave ou frase que sera buscada

            lc.connect(ldapHost, ldapPort); //conecta no ldap
            lc.bind(ldapVersion, loginDN, password.getBytes("UTF8")); //autentica no ldap

            LDAPSearchConstraints cons = lc.getSearchConstraints();
            cons.setMaxResults(0);//seta o limite de resultados. zero significa sem limites


            // busca em lista, sem colocar todos resultados na memoria
            LDAPSearchQueue queue =
                    lc.search(searchBase, // container to search
                    searchScope,
                    searchFilter, // search filter
                    attrs, // retorna apenas os atributos informados em attrs
                    false, // return attrs and values
                    (LDAPSearchQueue) null, // use default search queue
                    cons); //usa a Contraints criada que seta o numero maximo de resultados

            LDAPMessage message;

            while ((message = queue.getResponse()) != null) {

                // the message is a search result reference


                if (message instanceof LDAPSearchResultReference) {

                    String urls[] =
                            ((LDAPSearchResultReference) message).getReferrals();

                    System.out.println("Search result references:");

                    for (int i = 0; i < urls.length; i++) {
                        System.out.println(urls[i]);
                    }

                } // the message is a search result
                else if (message instanceof LDAPSearchResult) {
                    //apaga todas as variaveis
                    titulo = "";
                    palavraChave = "";
                    entity = "";
                    identificador = "";
                    descricao = "";
                    resumo = "";
                    data = "";
                    localizacao = "";
                    servidor = 0;

                    numDoObj++;
                    System.out.print(numDoObj + ";");//imprime um o numero e o ";" para cada documento
                    if (numDoObj % 100 == 0) {//se for multiplo de 100 quebra a linha
                        System.out.println("");
                    }
                    LDAPEntry entry = ((LDAPSearchResult) message).getEntry();

                    LDAPAttributeSet attributeSet = entry.getAttributeSet();


                    //nome do servidor
                    String nomeServidorResultado = null;
                    String idRaiz = entry.getDN(); //armazena na variavel o identificador com o caminho da arvore
                    String[] idRaizVet = idRaiz.split(","); //cria um vetor com os dados do dn
                    idRaizVet = idRaizVet[1].split("="); //string que recebera o caminho da arvore ldap sem o identificador e separa pelo =

                    ConsultaNomeFederacao nomeBase = new ConsultaNomeFederacao(idRaizVet[1], con); //estancia uma variavel da classe testaNaBase passando o nome do repositorio, ex. lume
                    nomeServidorResultado = nomeBase.getNomeEncontrado(); //recebe o nome do repositio onde o objeto foi encontrado

                    String consultaSQL = "select id_repositorio from info_repositorios where nome_na_federacao='" + nomeServidorResultado.trim() + "';";
                    
                    ResultSet rs2 = stmDadosLdap.executeQuery(consultaSQL);
                    rs2.next();
                    servidor = rs2.getInt("id_repositorio"); //coloca o id do servidor da variavel servidor

                    Iterator allAttributes = attributeSet.iterator();


                    while (allAttributes.hasNext()) {

                        LDAPAttribute attribute =
                                (LDAPAttribute) allAttributes.next();

                        String attributeName = attribute.getName();

                        Enumeration allValues = attribute.getStringValues();


                        if (allValues != null) {
                            while (allValues.hasMoreElements()) {

                                String Value = (String) allValues.nextElement();
                                if (attributeName.equalsIgnoreCase("obaaEntry")) {//se for obaaEntry adiciona na variavel entry
                                    identificador = Value;
                                }
                                if (attributeName.equalsIgnoreCase("obaaTitle")) {//se for obaaTitle adiocina na variavel title
                                    if (titulo.isEmpty()) {
                                        titulo = Value;
                                    } else {
                                        titulo += ";; " + Value;
                                    }
                                }
                                if (attributeName.equalsIgnoreCase("obaaKeyword")) {//se for obaaKeyword adiocina na variavel palavraChave
                                    if (palavraChave.isEmpty()) {
                                        palavraChave = Value;
                                    } else {
                                        palavraChave += ";; " + Value;
                                    }
                                }
                                if (attributeName.equalsIgnoreCase("obaaEntity")) {//se for obaaEntity adiocina na variavel entity
                                    if (entity.isEmpty()) {
                                        entity = Value;
                                    } else {
                                        entity += ";; " + Value;
                                    }
                                }
                                if (attributeName.equalsIgnoreCase("obaaDescription")) {//se for obaaDescription adiocina na variavel descricao
                                    if (descricao.isEmpty()) {
                                        descricao = Value;
                                    } else {
                                        descricao += ";; " + Value;
                                    }
                                }
                                if (attributeName.equalsIgnoreCase("obaaEducationalDescription")) {//se for xx adiocina na variavel descricao
                                    if (resumo.isEmpty()) {
                                        resumo = Value;
                                    } else {
                                        resumo += ";; " + Value;
                                    }
                                }
                                if (attributeName.equalsIgnoreCase("obaaDate")) {//se for xx adiocina na variavel descricao
                                    if (data.isEmpty()) {
                                        data = Value;
                                    } else {
                                        data += ";; " + Value;
                                    }
                                }
                                if (attributeName.equalsIgnoreCase("obaaLocation")) {//se for xx adiocina na variavel descricao
                                    if (localizacao.isEmpty()) {
                                        localizacao = Value;
                                    } else {
                                        localizacao += ";; " + Value;
                                    }
                                }
                                if (attributeName.equalsIgnoreCase("obaaIdentifier")) {//se for identifier e contiver "http:" na string adiocina na variavel localizacao
                                    if (Value.contains("http:")) {
                                        if (localizacao.isEmpty()) {
                                            localizacao = Value;
                                        } else {
                                            localizacao += ";; " + Value;
                                        }
                                    }
                                }


                            }//fim while que percorre os valores dos atributos
                            }//fim allValues != null

                    }//fim while que percorre todos os atributos

                    populaRI(titulo, palavraChave, entity, identificador, descricao, resumo, data, localizacao, servidor, con);//adiciona na base de dados as informacoes necessarias para a ferramenta de RI

                } // the message is a search response
                else {

                    LDAPResponse response = (LDAPResponse) message;

                    int status = response.getResultCode();

                    // the return code is LDAP success
                    if (status == LDAPException.SUCCESS) {
                        //fez corretamente a consulta
                        } else {
                        throw new LDAPException(response.getErrorMessage(), status, response.getMatchedDN());
                    }

                }

            }
            lc.disconnect(); //disconecta do servidor LDAP
            Long meio = System.currentTimeMillis();
            System.out.println("\n- Levou " + ((meio - inicio) / 1000) + " segundos inserindo objetos.");
            System.out.println("\nInseriu todos. Agora está preenchedo tabelas auxiliares.");
                indexar.populateR1(con); //preenche as tabelas auxiliares

            Long fim = System.currentTimeMillis();
            System.out.println("- Levou " + ((fim - meio) / 1000) + " segundos calculando tabelas auxiliares.");

            stmDadosLdap.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception... Erro no SQL:");
            e.printStackTrace();
        } catch (LDAPException e) {

            System.out.println("Erro LDAP: " + e.toString());

        } catch (UnsupportedEncodingException e) {

            System.out.println("Erro LDAP: " + e.toString());

        } 
    }

    /**
     * Metodo que recebe como entrada o titulo, palavrachave, entidade, ObaaEntry e a descrição,
     * efetua testes, armazena-os na classe Documentos, e passa o documentos para a classe Indexador.
     * @param titulo Titulo do documento.
     * @param palavraChave palavraschave do documento. Contatenadas por um " " na string.
     * @param entidade Entidades do documentos. Contatenadas por um " " na string.
     * @param entry ObaaEntry, ou seja o identificador &uacute;nico do documento.
     * @param descricao Descrição do documento.
     * @param con conex&atilde;o com mysql.
     * @throws SQLException Exce&ccedil;&atilde;o do mysql.
     */
    public void populaRI(String titulo, String palavraChave, String entidade, String entry, String descricao, String resumo, String data, String localizacao, int servidor, Connection con) throws SQLException {
        
        StopWordTAD stWd = new StopWordTAD();
        stWd.load(con);
        
        Documento doc = new Documento(stWd);

        if (testaEntry(entry, con)) {
            //System.out.println("Este documento já se encontra na base. "+entry);
        } else {

            //System.out.println(entry+"\n"+titulo +"\n"+palavraChave+"\n"+entidade+"\n"+descricao);

            doc.setObaaEntry(entry);//aciona o entry para o tipo abstato Documento

            if (!titulo.isEmpty()) {
                doc.setTitulo(titulo); //adiciona o titulo para o tipo abstrato Documento
            }
            if (!palavraChave.isEmpty()) {
                doc.setPalavrasChave(palavraChave);
            }
            if (!entidade.isEmpty()) {
                doc.setEntidade(entidade);
            }
            if (!descricao.isEmpty()) {
                doc.setDescricao(descricao);
            }
            if (!resumo.isEmpty()) {
                doc.setResumo(resumo);
            }
            if (!data.isEmpty()) {
                doc.setData(data);
            }
            if (!localizacao.isEmpty()) {
                doc.setLocalizacao(localizacao);
            }
            if (servidor != 0) {
                doc.setServidor(servidor);
            }

            indexar.addDoc(doc, con); //adiciona o documento doc na base de dados da conexao con
        }
    }

    /**
     * Testa se o obaaEntry já existe na base de dados
     * @param obaaEntry String contendo o obaaEntry
     * @param con conexão com mysql
     * @return true se o objeto existe e false se não existir
     * @param con Conexao com o mysql
     */
    public boolean testaEntry(String obaaEntry, Connection con) throws SQLException {
        boolean resultado;

        Statement stm = con.createStatement();
        //fazer consulta sql
        String sql = "SELECT id FROM documentos where obaaEntry='" + obaaEntry + "'";
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
     * M&eacute;todo respons&aacute;vel por indexar todos reposit&oacute;rios cadastrados no mysql
     * @param con Conexao com o mysql
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
            System.out.println("Calculando o indice....");
            indexar.populateR1(con); //calcula/preeche as tabelas auxiliares
            System.out.println("Indice calculado!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * M&eacute;todo respons&aacute;vel por indexar um reposit&oacute;rio espec&iacute;fico
     * @param nome Nome do repositorio que sera indexado
     * @param con Conexao com o mysql
     */
    public void indexarRepositorioEspecifico(String nome, Connection con) {
        String sql = "select id, nome from repositorios where nome= '" + nome + "';";

        try {
            Statement stmDadosLdap = con.createStatement();
            ResultSet rs = stmDadosLdap.executeQuery(sql); //executa a consulta que esta na string sqlDadosLdap
            if (rs.next()) {
                System.out.println("Indexando repositorio " + rs.getString("nome"));
                IndexaRep(rs.getInt("id"), con); //indexa o repositorio informado

            }
            System.out.println("Calculando o indice....");
            indexar.populateR1(con); //calcula/preeche as tabelas auxiliares
            System.out.println("Indice calculado!");
        } catch (SQLException e) {
            System.out.println("Nome informado nao foi encontrado na base de dados");
            e.printStackTrace();
        }
    }

    public void reindexarTudo(Connection con) {
        System.out.println("Deletando tabela documentos");
        String sql = "delete from documentos;";

        try {
            Statement stmDadosLdap = con.createStatement();
            stmDadosLdap.execute(sql); //executa a consulta que esta na string sqlDadosLdap

                System.out.println("Foi apagado o indice do mysql.");
                indexarTodosRepositorios(con);//reindexar todos os repositorios
           
        } catch (SQLException e) {
            System.out.println("Erro ao apagar os dados do indice no mysql");
            e.printStackTrace();
        }

    }
    public void recalcularIndice(Connection con){
        try{
            System.out.println("recalculando o indice...");
        indexar.populateR1(con); //calcula/preeche as tabelas auxiliares
        System.out.println("indice recalculado.");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Main que chama o metodo para indexar todos os repositorios     *
     */
    public static void main(String args[]) {
        IndexadorBusca run = new IndexadorBusca();
        Conectar conectar = new Conectar(); //instancia uma variavel da classe mysql.conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar

//          run.recalcularIndice(con); //recalcula o indice
//        run.indexarRepositorioEspecifico("engeo", con);
//        run.reindexarTudo(con); //reindexa toda a base de dados. Apaga o indice e cria novamente
        run.indexarTodosRepositorios(con); //cria o indice com todos os repositorios

        try {
            con.close(); //fechar conexao com o mysql
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexao com o mysql");
            e.printStackTrace();
        }

    }
}
