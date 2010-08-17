package robo.importa_OAI;

import ferramentaBusca.indexador.Indexador;
import operacoesLdap.Remover;
import mysql.Conectar;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import ferramentaBusca.indexador.Documento;
import ferramentaBusca.indexador.StopWordTAD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Classe que faz o parser/leitura de um arquivo XML
 * 
 * @author Marcos Nunes
 *
 */
public class XmlSaxReader extends DefaultHandler {

    /** Buffer que guarda as informacoes quando um texto e encontrado */
    private StringBuffer valorAtual = new StringBuffer(50);
    //InsereLdap inserir = new InsereLdap();
    Remover funcLdap = new Remover();
    private String dn;
    //OAI_Interface que ira receber os dados do xml
    private Header headerAux = new Header();
    //contantes que representam as tags do arquivo XML
    //tag do cabecalho
    private final String NO_IDENTIFIER_HEADER = "identifier";
    boolean statusDel = false;
    private Indexador indexa;
    private LDAPConnection conexaoLdap;
    private int idRepositorio;
    private int tipoMapeamentoId;
    private int padraoMetadados;
    private String namespace;
    private String metadataPrefix;
    private Connection ConMysql;
    private HashMap listaMap = new HashMap();
    private HashMap<String, String> atributosIndice = new HashMap<String, String>();
    private DadosParaLdap dadosLDAP;
    private Documento doc;
    private Indice indiceClass = new Indice();

    /**
     * Constutor que inicializa os objetos necessarios para fazer o parser
     * do arquivo contato.xml
     *
     * @param dnRec dn do servidor LDAP
     * @param caminhoXML caminho completo para o arquivo xml que sera lido
     * @param indexar variavel do tipo Indexador
     * @param lc Conex&atilde;o com o ldap. Deve ter conexão e bind realizados.
     * @param idRep id do repositorio no MySQL
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void parser(
            String dnRec,
            String caminhoXML,
            Indexador indexar,
            LDAPConnection lc,
            Connection con,
            int idRep)
            throws ParserConfigurationException,
            SAXException,
            IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();
        this.indexa = indexar;
        this.dn = dnRec;
        this.conexaoLdap = lc;
        this.idRepositorio = idRep;
        this.ConMysql = con;
        StopWordTAD stWd = new StopWordTAD();
        stWd.load(con);
        doc = new Documento(stWd);
        parser.parse(caminhoXML, this);

    }

    /**
     * Indica que o parser achou o início do documento XML. Este evento não
     * lhe passa qualquer informação, apenas indica que o parser vai começar
     * a escanear o arquivo XML.
     */
    @Override
    public void startDocument() {
        System.out.println(" Iniciando a leitura do XML");
    }

    /**
     * Indica que o parser achou e fim do documento XML.
     */
    @Override
    public void endDocument() {

        System.out.println(" Acabou a leitura/importação do XML");
    }

    /**
     * Indica que o parser achou o início de uma tag (tag de abertura/início).
     * Este evento fornece o nome do elemento, o nome e valor dos atributos
     * deste elemento, e também pode fornecer as informações sobre o namespace.
     */
    @Override
    public void startElement(String uri, String localName, String tag, Attributes atributos) {
        
        if(tag.equalsIgnoreCase("OAI-PMH")){ //se achar a tag OAI-PMH efetua a consulta na base
            this.dadosLDAP = new DadosParaLdap();
            Conectar conectar = new Conectar();
            ConMysql = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar
            try{
                Statement stm1 = ConMysql.createStatement();
                //idTipoMapeamento;namespace;metadataPrefix;
                String sqlInfo = "SELECT i.padraoMetadados, i.tipoMapeamento_id as tipoMap, p.nameSpace, metadataPrefix" +
                        " FROM info_repositorios i, padraometadados p" +
                        " WHERE i.padraoMetadados=p.id AND i.id_repositorio="+this.idRepositorio+";";
                ResultSet rsInfo = stm1.executeQuery(sqlInfo);
                if(rsInfo.next()){
                    this.padraoMetadados = rsInfo.getInt("padraoMetadados");
                    this.tipoMapeamentoId = rsInfo.getInt("tipoMap");
                    this.namespace = rsInfo.getString("nameSpace");
                    this.metadataPrefix = rsInfo.getString("metadataPrefix");
                }
                stm1.close();
                
                } catch (SQLException e) {
                System.err.println("SQL Exception... Erro ao carregar as informações iniciais do banco de dados");
                e.printStackTrace();
            }
        }
        if (tag.equalsIgnoreCase(metadataPrefix + ":" + namespace)) {
            
            
            try {
                Statement stm = ConMysql.createStatement();
                
                String[] indiceVet = {"title", "description", "keyword", "date", "entity", "location"};

                for (int i = 0; i < indiceVet.length; i++) {

                    String sqlAtrib = "SELECT a1.atributo as mapeado" +
                            " FROM atributos a1, mapeamentos m, atributos a2" +
                            " WHERE a1.id=m.origem_id" +
                            " AND a2.id=m.destino_id" +
                            " AND a2.atributo='" + indiceVet[i] + "'" +
                            " AND m.tipoMapeamento_id=" + tipoMapeamentoId+
                            " AND a1.idPadrao=" + this.padraoMetadados + ";";
                    
                    ResultSet res = stm.executeQuery(sqlAtrib);
                    while (res.next()) {
                        this.atributosIndice.put(res.getString("mapeado").toLowerCase(), indiceVet[i]); //adiciona ao HashMap os atributos correspondentes que devem ser adiocionados ao indice
                        
                    }
                }

                String sql = "SELECT a1.atributo as origem, a2.atributo as destino, m.mapeamentoComposto_id" +
                        " FROM atributos a1, mapeamentos m, atributos a2" +
                        " WHERE a1.id=m.origem_id and a2.id=m.destino_id and m.tipoMapeamento_id=" + tipoMapeamentoId + " AND m.padraometadados_id="+this.padraoMetadados+";";
                ResultSet rs = stm.executeQuery(sql);

                while (rs.next()) { //percorre todos os resultados do sql
//colocar em uma estrutura todos os atributos do padrao selecionado                    
                    String origem = rs.getString("origem");
                    String destino = rs.getString("destino");
                    int idComplementar = rs.getInt("mapeamentoComposto_id");
                    if (!origem.isEmpty() && !destino.isEmpty()) {
                        HashMap conteudo = new HashMap();
                        conteudo.put("origem", origem);
                        conteudo.put("destino", destino);
                        if (idComplementar > 0) {
                            //fazer uma consulta retornando os valores do mapeamentocomposto
                        }
                        conteudo.put("idComplementar", idComplementar);
                        this.listaMap.put(origem.toLowerCase(), conteudo);
                       // System.out.println("->"+origem.toLowerCase()+" "+conteudo+"<-");

                    }
                }
                
            } catch (SQLException e) {
                System.err.println("SQL Exception... Erro na consulta:");
                e.printStackTrace();
            }

        }
        if (tag.equalsIgnoreCase("header")) {
            //se o elemento possui atributos, imprime
            for (int i = 0; i < atributos.getLength(); i++) {

                if (atributos.getQName(i).equalsIgnoreCase("status")) {
                    headerAux.setStatus(atributos.getValue(i));
                    statusDel = true;
                }
            }
        }

    }

    /**
     * Indica que o parser achou o fim de uma tag/elemento.
     * A cada tag encontrada armazena a informação, e ao final do xml chama o método insereDctoObaa para inserir os dados encontrados no ldap no padrão OBAA
     */
    @Override
    public void endElement(String uri, String localName, String tag) {
        
        if (tag.equalsIgnoreCase(metadataPrefix + ":" + namespace)) {
            
            doc.setServidor(idRepositorio); //adiciona no indice o id do repositorio
            InsereLdap.insereObaa(this.dadosLDAP, headerAux, dn, conexaoLdap); //chama classe que insere os dados no ldap
            
            try {
                System.out.println("adicionando documento ao indice");
                indexa.addDoc(doc, this.ConMysql); //adciona o documento no indice mysql
            } catch (SQLException e) {
                System.err.println("Erro ao inserir o documento no indice: " + e.getMessage());
            }

        } //senao, seta os atributos
        //elementos da tag header
        else if (tag.equalsIgnoreCase(NO_IDENTIFIER_HEADER)) {
            headerAux.setIdentifier(valorAtual.toString().trim()); //insere o valor na classer header
            doc.setObaaEntry(valorAtual.toString().trim()); //insere o a entry no indice

            if (statusDel == true) {
                headerAux.setidDeletado(valorAtual.toString().trim());
                System.out.println("deletar o objeto: " + valorAtual.toString().trim());
                try {
                    funcLdap.apagaObjeto("obaaEntry", valorAtual.toString(), dn, conexaoLdap);
                } catch (LDAPException e) {
                    if (e.getResultCode() == LDAPException.NO_SUCH_OBJECT) {
                        System.err.println("Erro ao apagar: Não foi encontrado o objeto: " + "obaaEntry=" + valorAtual.toString() + "," + dn);
                    } else if (e.getResultCode() ==
                            LDAPException.INSUFFICIENT_ACCESS_RIGHTS) {
                        System.err.println("Erro ao apagar: Insufficient rights");
                    } else {
                        System.err.println("Erro ao apagar: " + e.toString());
                    }
                } catch (UnsupportedEncodingException e) {
                    System.err.println("Erro ao apagar: " + e.toString());
                } catch (SQLException e) {
                    System.err.println("Erro no SQL ao apagar: " + e);
                }
                statusDel = false;
            }


        } else if (this.listaMap.containsKey(tag.replace(this.namespace + ":", "").toLowerCase().trim()) && !valorAtual.toString().trim().isEmpty()) {

            String tagTratada = tag.replace(this.namespace + ":", "").toLowerCase();
            HashMap resultado = (HashMap) listaMap.get(tagTratada); //cria um HashMap para receber os valores do HashMap listaMap que contem os mapeamentos lidos do banco de dados
            String destino = resultado.get("destino").toString(); //armazena o atributo de destino correspondente ao lido
            int idComplementar = Integer.parseInt(resultado.get("idComplementar").toString()); //armazena o id do mapeamento complementar

            this.dadosLDAP.setAtributos("obaa"+destino, valorAtual.toString().trim());//adiciona as informações que serao inseridas no ldap
            
            if (this.atributosIndice.containsKey(tagTratada)) { //se for um dos atributos que compoe o indice entra no if
                this.indiceClass.setIndice(this.atributosIndice.get(tagTratada), valorAtual.toString().trim(), this.doc); //envia o atributo e o valor para a classe que idenfica e envia para a classe Documento
            }

            if (idComplementar > 0) { //se tiver mapeamento complementar
                String sql = "SELECT a.atributo as destino, m.valor " + "FROM mapeamentoComposto m, atributos a " + "WHERE m.id_origem=a.id " + "AND m.id=" + idComplementar + ";";
                try {
                    Statement stm = ConMysql.createStatement();
                    ResultSet rs = stm.executeQuery(sql);
                    while (rs.next()) {
                        this.dadosLDAP.setAtributos("obaa"+rs.getString("destino"), rs.getString("valor")); //adiciona o mapeamento complementar
                        }
                } catch (SQLException e) {
                    System.err.println("Erro ao fazer a consulta do mapeamento composto");
                    e.printStackTrace();
                }
            }
        }


        //limpa o valor Atual
        valorAtual.delete(0, valorAtual.length());
    }

    /**
     * Indica que o parser achou algum Texto (Informação).
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        //System.out.print(String.copyValueOf(ch,start,length).trim());

        valorAtual.append(ch, start, length);

    }
}
