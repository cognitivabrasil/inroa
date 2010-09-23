package robo.importa_OAI.DC;

import robo.importa_OAI.Header;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import ferramentaBusca.indexador.Indexador;
import java.io.IOException;

import java.io.UnsupportedEncodingException;

import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import operacoesLdap.Remover;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import robo.importa_OAI.OAI_Interface;

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
    
    private OAI_Interface oai_dcAux;
    
    
    private Header headerAux = new Header();
    //contantes que representam as tags do arquivo XML
    //tag do cabecalho
    private final String NO_IDENTIFIER_HEADER = "identifier";
    //tag do oai_dc:dc
    private final String NO_DCIDENTIFIER = "dc:identifier";
    private final String NO_DCTITLE = "dc:title";
    private final String NO_DCLANGUAGE = "dc:language";
    private final String NO_DCDESCRIPTION = "dc:description";
    private final String NO_DCSUBJECT = "dc:subject";
    private final String NO_DCCOVERAGE = "dc:coverage";
    private final String NO_DCTYPE = "dc:type";
    private final String NO_DCDATE = "dc:date";
    private final String NO_DCCREATOR = "dc:creator";
    private final String NO_DCOTHERCONTRIBUTOR = "dc:othercontributor";
    private final String NO_DCPUBLISHER = "dc:publisher";
    private final String NO_DCFORMAT = "dc:format";
    private final String NO_DCRIGHTS = "dc:rights";
    private final String NO_DCRELATION = "dc:relation";
    private final String NO_DCSOURCE = "dc:source";
    boolean statusDel = false;
    private Indexador indexa;
    private LDAPConnection conexaoLdap;
    private int idRepositorio;

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
            int idRep)
            throws ParserConfigurationException,
            SAXException,
            IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();
        this.indexa = indexar;
        this.dn = dnRec;
        this.conexaoLdap = lc;
        this.idRepositorio=idRep;
        
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

        if (tag.equalsIgnoreCase("oai_dc:dc")) {
            oai_dcAux = new OAI_Interface();

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
        
        //adiciona o contato na lista
        if (tag.equalsIgnoreCase("oai_dc:dc")) {
            InsereLdap.insereDCtoObaa(oai_dcAux, headerAux, dn, conexaoLdap, indexa, idRepositorio);
            
        } //senao, seta os atributos
        //elementos da tag header

        else if (tag.equalsIgnoreCase(NO_IDENTIFIER_HEADER)) {
            headerAux.setIdentifier(valorAtual.toString().trim()); //insere o valor na classer header

            if (statusDel == true) {
                headerAux.setidDeletado(valorAtual.toString().trim());
                System.out.println("deletar o objeto: " + valorAtual.toString().trim());
                try {
                    funcLdap.apagaObjeto("obaa_entry", valorAtual.toString(), dn, conexaoLdap);
                } catch (LDAPException e) {
                    if (e.getResultCode() == LDAPException.NO_SUCH_OBJECT) {
                        System.err.println("Erro ao apagar: Não foi encontrado o objeto: " + "obaa_entry=" + valorAtual.toString() + "," + dn);
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
            //elementos da tag oai_dc:dc
        } else if (tag.equalsIgnoreCase(NO_DCIDENTIFIER) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setIdentifier(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCTITLE) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setTitle(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCLANGUAGE) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setLanguage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCDESCRIPTION) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setDescription(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCSUBJECT) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setSubject(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCCOVERAGE) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setCoverage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCTYPE) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setType(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCDATE) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setDate(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCCREATOR) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setCreator(valorAtual.toString().trim());            
        } else if (tag.equalsIgnoreCase(NO_DCOTHERCONTRIBUTOR) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setOtherContributor(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCPUBLISHER) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setPublisher(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCFORMAT) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setFormat(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCRIGHTS) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setRigths(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCRELATION) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setRelation(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase(NO_DCSOURCE) && !valorAtual.toString().trim().isEmpty()) {
            oai_dcAux.setSource(valorAtual.toString().trim());            
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
