/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robo.atualiza.subfedOAI;

import ferramentaBusca.indexador.Indexador;
import java.io.IOException;
import ferramentaBusca.indexador.Documento;
import ferramentaBusca.indexador.StopWordTAD;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import postgres.Excluir;
import robo.atualiza.importaOAI.DadosObjetos;
import robo.atualiza.importaOAI.Header;
import robo.atualiza.importaOAI.Indice;
import robo.atualiza.importaOAI.InsereObjetoBase;

/**
 * Classe que faz o parser/leitura de um arquivo XML
 *
 * @author Marcos Nunes
 *
 */
public class ParserObjetos extends DefaultHandler {
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
    private int idSubrep;
    private String namespace = "obaa";
    private String metadataPrefix = "obaa";
    private Connection Conexao;
    private DadosObjetos dadosObjetos;
    private Documento doc;
    private Indice indiceClass = new Indice();
    private HashMap<String, Integer> dadosSubRep;



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
    public void parser(
            String caminhoXML,
            Indexador indexar,
            Connection con,
            HashMap<String, Integer> subRep) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();

        this.indexa = indexar;
        this.dadosSubRep = subRep;
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
    }

    /**
     * Indica que o parser achou e fim do documento XML.
     */
    @Override
    public void endDocument() {
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


        }else if (tag.equalsIgnoreCase("header")) {
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
     * A cada tag encontrada armazena a informação, e ao final do xml chama o método insereObaa para inserir os dados encontrados na base de dados no padrão OBAA
     */
    @Override
    public void endElement(String uri, String localName, String tag) {


        if (tag.equalsIgnoreCase(metadataPrefix + ":" + namespace)) {
           
            //doc.setServidor(idSubrep); //adiciona no indice o id do repositorio

            try {
                int key = InsereObjetoBase.insereObaa(this.dadosObjetos, headerAux, this.Conexao, idSubrep, "subRep"); //chama classe que insere os dados na base de dados

                if (key > 0) { //se o documento foi inserido corretamente entra no if
                    doc.setId(key);
                    //adiciona na base os tokens
                    //System.out.println("adicionando documento ao indice");
                    indexa.addDoc(doc, this.Conexao); //adciona o documento no indice SQL
                }

            } catch (SQLException e) {
                System.err.println("Erro ao inserir o documento no indice: " + e.getMessage());
            }

            idSubrep=0;//quando fechar a tag com os metadados apaga o id do ultimo subrepositorio

        } //senao, seta os atributos
        //elementos da tag header
        else if (tag.equalsIgnoreCase(this.NO_IDENTIFIER_HEADER)) {

            this.dadosObjetos = new DadosObjetos();
            this.doc = new Documento(stWd);
            headerAux.setIdentifier(valorAtual.toString().trim()); //insere o valor na classer header
            doc.setObaaEntry(valorAtual.toString().trim()); //insere o a entry no indice

        }else if (tag.equalsIgnoreCase("setSpec")) {
            idSubrep = dadosSubRep.get(valorAtual.toString().trim()); //usa o nome do repositorio para pegar seu id

        }else if (tag.equalsIgnoreCase("header")) {
            if (statusDel == true) {
                headerAux.setidDeletado(valorAtual.toString().trim());
                System.out.println("deletar o objeto: " + headerAux.getIdentifier());
                try {
                    Excluir.removerDocumentoIndice(valorAtual.toString(), idSubrep, Conexao, "subRep");

                } catch (SQLException e) {
                    System.err.println("Erro no SQL ao apagar: " + e);
                }
                statusDel = false;
                idSubrep = 0;
            }


        }else if (!valorAtual.toString().trim().isEmpty() && tag.startsWith("obaa:obaa")) {

            String tagTratada = tag.replace(this.namespace + ":", "").trim();
            
            this.dadosObjetos.setAtributos(tagTratada, valorAtual.toString().trim());//adiciona as informações que serao inseridas na base

            if (tagTratada.equalsIgnoreCase("obaaTitle") || tagTratada.equalsIgnoreCase("obaaDescription") || tagTratada.equalsIgnoreCase("obaaKeyword") || tagTratada.equalsIgnoreCase("obaaEntity")) { //se for um dos atributos que compoe o indice entra no if
                //passa o atributo obaa correspondente e o seu valor lido do xml
                this.indiceClass.setIndice(tagTratada, valorAtual.toString().trim(), this.doc); //envia o atributo e o valor para a classe que idenfica e envia para a classe Documento
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
