package robo.atualiza.importaOAI;

import ferramentaBusca.indexador.Indexador;
import java.io.IOException;
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
import postgres.Excluir;

/**
 * Classe que faz o parser/leitura de um arquivo XML
 *
 * @author Marcos Nunes
 *
 */
public class XmlSaxReader extends DefaultHandler {

    /** Buffer que guarda as informacoes quando um texto e encontrado */
    private StringBuffer valorAtual = new StringBuffer(50);

    private StopWordTAD stWd;
    //OAI_Interface que ira receber os dados do xml
    private Header headerAux = new Header();
    //contantes que representam as tags do arquivo XML
    //tag do cabecalho
    private final String NO_IDENTIFIER_HEADER = "identifier";
    boolean statusDel = false;
    private Indexador indexa;
    private int idRepositorio;
    private int tipo_mapeamento_id;
    private int padrao_metadados;
    private String namespace;
    private String metadataPrefix;
    private Connection Conexao;
    private HashMap listaMap = new HashMap();
    private HashMap<String, String> atributosIndice = new HashMap<String, String>();
    private DadosObjetos dadosObjetos;
    private Documento doc;
    private Indice indiceClass = new Indice();
    


    /**
     * Constutor que inicializa os objetos necessarios para fazer o parser
     * do arquivo contato.xml
     *
     * @param caminhoXML caminho completo para o arquivo xml que sera lido
     * @param indexar variavel do tipo Indexador
     * @param idRep id do repositorio na base de dados
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    @Deprecated
    public void parser(
            String caminhoXML,
            Indexador indexar,
            Connection con,
            int idRep)
            throws ParserConfigurationException,
            SAXException,
            IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();
        this.indexa = indexar;
        this.idRepositorio = idRep;
        this.Conexao = con;
        stWd = new StopWordTAD(con);
        parser.parse(caminhoXML, this);
    }

    /**
     * Indica que o parser achou o início do documento XML. Este evento não
     * lhe passa qualquer informação, apenas indica que o parser vai começar
     * a escanear o arquivo XML.
     */
    @Override
    public void startDocument() {
        System.out.println(" FEB: Iniciando a leitura do XML");
    }

    /**
     * Indica que o parser achou e fim do documento XML.
     */
    @Override
    public void endDocument() {

        System.out.println(" FEB: Fim da leitura/importação do XML");
    }

    /**
     * Indica que o parser achou o início de uma tag (tag de abertura/início).
     * Este evento fornece o nome do elemento, o nome e valor dos atributos
     * deste elemento, e também pode fornecer as informações sobre o namespace.
     */
    @Override
    public void startElement(String uri, String localName, String tag, Attributes atributos) throws SAXException {
    
        if (tag.equalsIgnoreCase("error")) {
            
            String erro = "";
            for (int i = 0; i < atributos.getLength(); i++) {
                erro = atributos.getValue(i);
            }
            throw new SAXException(erro);
        }else if (tag.equalsIgnoreCase("ListRecords")) { //se achar a tag ListRecords efetua a consulta na base
            try {
                Statement stm1 = Conexao.createStatement();
                //idTipoMapeamento;namespace;metadataPrefix;
                String sqlInfo = "SELECT i.padrao_metadados, i.tipo_mapeamento_id as tipo_map, i.name_space, i.metadata_prefix" +
                        " FROM info_repositorios i" +
                        " WHERE i.id_repositorio=" + this.idRepositorio + ";";
                ResultSet rsInfo = stm1.executeQuery(sqlInfo);
                if (rsInfo.next()) {
                    this.padrao_metadados = rsInfo.getInt("padrao_metadados");
                    this.tipo_mapeamento_id = rsInfo.getInt("tipo_map");
                    this.namespace = rsInfo.getString("name_space");
                    this.metadataPrefix = rsInfo.getString("metadata_prefix");
                }
                stm1.close();

            } catch (SQLException e) {
                System.err.println("FEB ERRO: XmlSaxReader.java SQL Exception... Erro ao carregar as informações iniciais do banco de dados. Mensagem: "+e.getMessage());
            }
        }else if (tag.equalsIgnoreCase("header")) {
            //se o elemento possui atributos, imprime
            for (int i = 0; i < atributos.getLength(); i++) {

                if (atributos.getQName(i).equalsIgnoreCase("status")) {
                    headerAux.setStatus(atributos.getValue(i));
                    statusDel = true;
                }
            }
        }else if (tag.equalsIgnoreCase(metadataPrefix + ":" + namespace)) {
            try {
                Statement stm = Conexao.createStatement();
                String[] indiceVet = {"Title", "Description", "Keyword", "Date", "Entity", "Location"};

                //recupera o atributo referente ao indiceVet[i] do padrao que esta sendo utilizado
                //ex. padrao dublin core indiveVet[i]=Keyword mapeamento referente = Subject
                String sqlAtrib = "SELECT a1.atributo as mapeado, a2.atributo as origem" +
                        " FROM atributos a1, mapeamentos m, atributos a2" +
                        " WHERE a1.id=m.origem_id" +
                        " AND a2.id=m.destino_id" +
                        " AND m.tipo_mapeamento_id=" + tipo_mapeamento_id +
                        " AND a1.id_padrao=" + this.padrao_metadados +
                        " AND (";
                for (int i = 0; i < indiceVet.length; i++) {
                    if (i == 0) {
                        sqlAtrib += "a2.atributo='" + indiceVet[i] + "'";
                    } else {
                        sqlAtrib += "OR a2.atributo='" + indiceVet[i] + "'";
                    }
                }
                sqlAtrib += ")";

                ResultSet res = stm.executeQuery(sqlAtrib);
                while (res.next()) {
                    //armazena o mapeamento dos atributos que sera inseridos no indice
                    this.atributosIndice.put(res.getString("mapeado").toLowerCase(), res.getString("origem")); //adiciona ao HashMap os atributos correspondentes que devem ser adiocionados ao indice
                    }

                String sql = "SELECT a1.atributo as origem, a2.atributo as destino, m.mapeamento_composto_id" +
                        " FROM atributos a1, mapeamentos m, atributos a2" +
                        " WHERE a1.id=m.origem_id and a2.id=m.destino_id and m.tipo_mapeamento_id=" + tipo_mapeamento_id + " AND m.padraometadados_id=" + this.padrao_metadados + ";";
                ResultSet rs = stm.executeQuery(sql);

                while (rs.next()) { //percorre todos os resultados do sql
//colocar em uma estrutura todos os atributos do padrao selecionado
                    String origem = rs.getString("origem");
                    String destino = rs.getString("destino");
                    int idComplementar = rs.getInt("mapeamento_composto_id");
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
                System.err.println("FEB ERRO: XmlSaxReader.java SQL Exception... Erro na consulta: "+e.getMessage());
            }

        }

    }

    /**
     * Indica que o parser achou o fim de uma tag/elemento.
     * A cada tag encontrada armazena a informação, e ao final do xml chama o método insereObaa para inserir os dados encontrados na base de dados no padrão OBAA
     */
    @Override
    public void endElement(String uri, String localName, String tag) {

        if(tag.equalsIgnoreCase("responseDate")){
            indexa.setDataXML(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(metadataPrefix + ":" + namespace)) {

            //System.out.println("Fim elemento...");
            doc.setServidor(idRepositorio); //adiciona no indice o id do repositorio

            try {

                int key = InsereObjetoBase.insereObaa(this.dadosObjetos, headerAux, this.Conexao, idRepositorio, "rep"); //chama classe que insere os dados na base de dados

                if (key > 0) { //se o documento foi inserido corretamente entra no if
                    doc.setId(key);
                    //adiciona na base os tokens
                    //System.out.println("adicionando documento ao indice");
                    indexa.addDoc(doc, this.Conexao); //adciona o documento no indice SQL
                }

            } catch (SQLException e) {
                System.err.println("FEB ERRO: XmlSaxReader.java Erro ao inserir o documento no indice: " + e.getMessage());
            }

        } //senao, seta os atributos
        //elementos da tag header
        else if (tag.equalsIgnoreCase(NO_IDENTIFIER_HEADER)) {

            this.dadosObjetos = new DadosObjetos();
            this.doc = new Documento(stWd);

            headerAux.setIdentifier(valorAtual.toString().trim()); //insere o valor na classer header
            doc.setObaaEntry(valorAtual.toString().trim()); //insere o a entry no indice

            if (statusDel == true) {
                headerAux.setidDeletado(valorAtual.toString().trim());
                System.out.println("deletar o objeto: " + valorAtual.toString().trim());
                try {
                    Excluir.removerDocumentoIndice(valorAtual.toString(), idRepositorio, Conexao, "rep");

                } catch (SQLException e) {
                    System.err.println("FEB ERRO: XmlSaxReader.java Erro no SQL ao apagar: " + e);
                }
                statusDel = false;
            }


        } else if (this.listaMap.containsKey(tag.replace(this.namespace + ":", "").toLowerCase().trim()) && !valorAtual.toString().trim().isEmpty()) {

            String tagTratada = tag.replace(this.namespace + ":", "").toLowerCase();
            //mapeamento
            HashMap resultado = (HashMap) listaMap.get(tagTratada); //cria um HashMap para receber os valores do HashMap listaMap que contem os mapeamentos lidos do banco de dados
            String destino = resultado.get("destino").toString(); //armazena o atributo de destino correspondente ao lido
            int idComplementar = Integer.parseInt(resultado.get("idComplementar").toString()); //armazena o id do mapeamento complementar

            this.dadosObjetos.setAtributos("obaa" + destino, valorAtual.toString().trim());//adiciona as informações que serao inseridas na base

            if (this.atributosIndice.containsKey(tagTratada)) { //se for um dos atributos que compoe o indice entra no if
                //passa o atributo obaa correspondente e o seu valor lido do xml
                this.indiceClass.setIndice(this.atributosIndice.get(tagTratada), valorAtual.toString().trim(), this.doc); //envia o atributo e o valor para a classe que idenfica e envia para a classe Documento
            }

            if (idComplementar > 0) { //se tiver mapeamento complementar
                String sql = "SELECT a.atributo as destino, m.valor " + "FROM mapeamentocomposto m, atributos a " + "WHERE m.id_origem=a.id " + "AND m.id=" + idComplementar + ";";
                try {
                    Statement stm = Conexao.createStatement();
                    ResultSet rs = stm.executeQuery(sql);
                    while (rs.next()) {
                        this.dadosObjetos.setAtributos("obaa" + rs.getString("destino"), rs.getString("valor")); //adiciona o mapeamento complementar
                        }
                } catch (SQLException e) {
                    System.err.println("FEB ERRO: XmlSaxReader.java Erro ao fazer a consulta do mapeamento composto. Mensagem: "+e.getMessage());
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
