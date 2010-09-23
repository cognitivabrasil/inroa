package robo.importa_OAI.OBAA;

import robo.importa_OAI.OAI_Interface;
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
    private OAI_Interface oai_ObaaAux;
    private Header headerAux = new Header();
    //contantes que representam as tags do arquivo XML
    //tag do cabecalho
    private final String NO_IDENTIFIER_HEADER = "identifier";

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
        this.idRepositorio = idRep;

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

        if (tag.equalsIgnoreCase("oai_obaa:obaa")) {
            oai_ObaaAux = new OAI_Interface();

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
        if (tag.equalsIgnoreCase("oai_obaa:obaa")) {
            InsereLdap.insereObaatoObaa(oai_ObaaAux, headerAux, dn, conexaoLdap, indexa, idRepositorio);

        } //senao, seta os atributos
        //elementos da tag header
        else if (tag.equalsIgnoreCase(NO_IDENTIFIER_HEADER)) {
//            headerAux.setIdentifier(valorAtual.toString().trim()); //insere o valor na classer header

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
        } else if (tag.equalsIgnoreCase("obaa:Identifier") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setIdentifier(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Entry") && !valorAtual.toString().trim().isEmpty()) {
            headerAux.setIdentifier(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Catalog") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setCatalog(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Title") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setTitle(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Language") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setLanguage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Description") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setDescription(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Keyword") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setKeyword(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Coverage") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setCoverage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Structure") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setStructure(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:AggregationLevel") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setAggregationlevel(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Version") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setVersion(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Status") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setStatus(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Role") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setRole(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Entity") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setEntity(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Date") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setDate(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:MetaMetadataCatalog") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setMetaMetadataCatalog(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:MetaMetadataEntry") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setMetaMetadataEntry(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:MetaMetadataRole") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setMetametadatarole(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:MetaMetadataEntity") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setMetaMetadataEntity(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:MetaMetadataDate") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setMetaMetadataDate(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:MetadataSchema") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setMetadataschema(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:MetaMetadataLanguage") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setMetametadatalanguage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Format") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setFormat(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Size") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSize(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Location") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setLocation(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Type") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setType(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Name") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setName(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:MinimumVersion") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setMinimumversion(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:MaximumVersion ") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setMaximumVersion(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:InstallationRemarks") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setInstallationremarks(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:OtherPlatformRequirements") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setOtherplatformrequirements(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Duration") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setDuration(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SupportedPlatforms") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSupportedplatforms(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:PlatformType") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setPlatformtype(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SpecificFormat") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSpecificformat(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SpecificSize") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSpecificsize(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SpecificLocation") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSpecificlocation(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SpecificType") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSpecifictype(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SpecificName") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSpecificname(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SpecificMinimumVersion") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSpecificminimumversion(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SpecificMaximumVersion") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSpecificmaximumversion(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SpecificInstallationRemarks") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSpecificinstallationremarks(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SpecificOtherPlatformRequirements") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSpecificotherplatformrequirements(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:ServiceName") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setServicename(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:ServiceType") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setServicetype(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Provides") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setProvides(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Essential") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setEssential(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Protocol") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setProtocol(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:OntologyLanguage") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setOntologylanguage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:OntologyLocation") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setOntologylocation(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:ServiceLanguage") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setServicelanguage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:ServiceLocation") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setServicelocation(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:InteractivityType") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setInteractivitytype(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:LearningResourceType") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setLearningResourceType(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:InteractivityLevel") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setInteractivitylevel(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SemanticDensity") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSemanticdensity(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:IntendedEndUserRole") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setIntendedenduserrole(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Context") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setContext(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:TypicalAgeRange") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setTypicalagerange(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Difficulty") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setDifficulty(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:TypicalLearningTime") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setTypicallearningtime(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:EducationalDescription") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setEducationaldescription(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:EducationalLanguage") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setEducationallanguage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:LearningContentType") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setLearningContentType(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Perception") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setPerception(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Synchronism") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSynchronism(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:CoPresence") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setCopresence(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Reciprocity") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setReciprocity(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:DidacticStrategy") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setDidacticstrategy(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Cost") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setCost(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:CopyrightAndOtherRestrictions") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setCopyrightandotherrestrictions(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:RightsDescription") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setRightsdescription(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Kind") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setKind(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:ResourceCatalog") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setResourcecatalog(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:ResourceEntry") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setResourceentry(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:ResourceDescription") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setResourcedescription(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Purpose") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setPurpose(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Source") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSource(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:TaxonId") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setTaxonid(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:TaxonEntry") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setTaxonentry(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:TaxonPathDescription") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setTaxonpathdescription(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:TaxonPathKeyword") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setTaxonpathkeyword(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:HasVisual") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setHasvisual(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:HasAuditory") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setHasauditory(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:HasText") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setHastext(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:HasTactile") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setHastactile(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:DisplayTransformability") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setDisplaytransformability(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:ControlFlexibility") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setControlflexibility(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:EquivalentResource") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setEquivalentresource(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:IsSuplementary") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setIssuplementary(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:AudioDescription") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setAudiodescription(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:AudioDescriptionLanguage") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setAudiodescriptionlanguage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:AltTextLang") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setAlttextlang(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:LongDescriptionLang") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setLongDescriptionLang(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:ColorAvoidance") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setColoravoidance(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:GraphicAlternative") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setGraphicalternative(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SignLanguage") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSignlanguage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:CaptionTypeLanguage") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setCaptiontypelanguage(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:CaptionRate") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setCaptionrate(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentInformation") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentinformation(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentInformationIdentifier") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentinformationidentifier(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentInformationTitle") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentinformationtitle(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentInformationDescription") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentinformationdescription(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentInformationKeyword") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentinformationkeyword(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentMediaType") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentmediatype(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:Start") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setStart(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:End") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setEnd(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentGroupInformationIdentifier") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentgroupinformationidentifier(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:GroupType") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setGrouptype(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentGroupInformationTitle") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentgroupinformationtitle(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentGroupInformationDescription") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentgroupinformationdescription(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentGroupInformationKeyword") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentgroupinformationkeyword(valorAtual.toString().trim());
        } else if (tag.equalsIgnoreCase("obaa:SegmentsIdentifier") && !valorAtual.toString().trim().isEmpty()) {
            oai_ObaaAux.setSegmentsidentifier(valorAtual.toString().trim());
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
