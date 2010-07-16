package robo.importa_OAI.OBAA;

import robo.importa_OAI.OAI_Interface;
import robo.importa_OAI.Header;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import ferramentaBusca.indexador.Documento;
import ferramentaBusca.indexador.Indexador;
import ferramentaBusca.indexador.StopWordTAD;
import java.sql.Connection;
import java.sql.SQLException;
import mysql.Conectar;

/**
 *
 * @author Marcos
 */
public class InsereLdap {

    /**
     * Método que insere dados no LDAP, recebendo metadados em OBAA e inserindo em OBAA.
     *
     * @param oai_obaa Referência para a classe OAI_Interface que contém os objetos lidos do XML.
     * @param header Refêrencia para a classe Header que contém os dados da tag Header do XML.
     * @param dnRecebido dn do LDAP onde serão armazenados os objetos.
     * @param lc Conex&atild;o com o Ldap. Conex&atilde;o e bind.
     */
    public static void insereObaatoObaa(OAI_Interface oai_obaa, Header header, String dnRecebido, LDAPConnection lc, Indexador indexar, int idRep) {


        String[] obaaIdentifier = oai_obaa.getIdentifier().split(";;");
        String[] obaaCatalog = oai_obaa.getCatalog().split(";;");
        String[] obaaTitle = oai_obaa.getTitle().split(";;");
        String[] obaaLanguage = oai_obaa.getLanguage().split(";;");
        String[] obaaDescription = oai_obaa.getDescription().split(";;");
        String[] obaaKeyword = oai_obaa.getKeyword().split(";;");
        String[] obaaCoverage = oai_obaa.getCoverage().split(";;");
        String[] obaaStructure = oai_obaa.getStructure().split(";;");
        String[] obaaAggregationLevel = oai_obaa.getAggregationlevel().split(";;");
        String[] obaaVersion = oai_obaa.getVersion().split(";;");
        String[] obaaStatus = oai_obaa.getStatus().split(";;");
        String[] obaaRole = oai_obaa.getRole().split(";;");
        String[] obaaEntity = oai_obaa.getEntity().split(";;");
        String[] obaaDate = oai_obaa.getDate().split(";;");
        String[] obaaMetaMetadataCatalog = oai_obaa.getMetametadatacatalog().split(";;");
        String[] obaaMetaMetadataEntry = oai_obaa.getMetametadataentry().split(";;");
        String[] obaaMetaMetadataRole = oai_obaa.getMetametadatarole().split(";;");
        String[] obaaMetaMetadataEntity = oai_obaa.getMetametadataentity().split(";;");
        String[] obaaMetaMetadataDate = oai_obaa.getMetametadatadate().split(";;");
        String[] obaaMetadataSchema = oai_obaa.getMetadataschema().split(";;");
        String[] obaaMetaMetadataLanguage = oai_obaa.getMetametadatalanguage().split(";;");
        String[] obaaFormat = oai_obaa.getFormat().split(";;");
        String[] obaaSize = oai_obaa.getSize().split(";;");
        String[] obaaLocation = oai_obaa.getLocation().split(";;");
        String[] obaaType = oai_obaa.getType().split(";;");
        String[] obaaName = oai_obaa.getName().split(";;");
        String[] obaaMinimumVersion = oai_obaa.getMinimumversion().split(";;");
        String[] obaaMaximumVersion = oai_obaa.getMaximumVersion().split(";;");
        String[] obaaInstallationRemarks = oai_obaa.getInstallationremarks().split(";;");
        String[] obaaOtherPlatformRequirements = oai_obaa.getOtherplatformrequirements().split(";;");
        String[] obaaDuration = oai_obaa.getDuration().split(";;");
        String[] obaaSupportedPlatforms = oai_obaa.getSupportedplatforms().split(";;");
        String[] obaaPlatformType = oai_obaa.getType().split(";;");
        String[] obaaSpecificFormat = oai_obaa.getSpecificformat().split(";;");
        String[] obaaSpecificSize = oai_obaa.getSpecificsize().split(";;");
        String[] obaaSpecificLocation = oai_obaa.getSpecificlocation().split(";;");
        String[] obaaSpecificType = oai_obaa.getSpecifictype().split(";;");
        String[] obaaSpecificName = oai_obaa.getSpecificname().split(";;");
        String[] obaaSpecificMinimumVersion = oai_obaa.getSpecificminimumversion().split(";;");
        String[] obaaSpecificMaximumVersion = oai_obaa.getSpecificmaximumversion().split(";;");
        String[] obaaSpecificInstallationRemarks = oai_obaa.getSpecificinstallationremarks().split(";;");
        String[] obaaSpecificOtherPlatformRequirements = oai_obaa.getSpecificotherplatformrequirements().split(";;");
        String[] obaaServiceName = oai_obaa.getServicename().split(";;");
        String[] obaaServiceType = oai_obaa.getServicetype().split(";;");
        String[] obaaProvides = oai_obaa.getProvides().split(";;");
        String[] obaaEssential = oai_obaa.getEssential().split(";;");
        String[] obaaProtocol = oai_obaa.getProtocol().split(";;");
        String[] obaaOntologyLanguage = oai_obaa.getOntologylanguage().split(";;");
        String[] obaaOntologyLocation = oai_obaa.getOntologylocation().split(";;");
        String[] obaaServiceLanguage = oai_obaa.getServicelanguage().split(";;");
        String[] obaaServiceLocation = oai_obaa.getServicelocation().split(";;");
        String[] obaaInteractivityType = oai_obaa.getInteractivitytype().split(";;");
        String[] obaaLearningResourceType = oai_obaa.getLearningResourceType().split(";;");
        String[] obaaInteractivityLevel = oai_obaa.getInteractivitylevel().split(";;");
        String[] obaaSemanticDensity = oai_obaa.getSemanticdensity().split(";;");
        String[] obaaIntendedEndUserRole = oai_obaa.getIntendedenduserrole().split(";;");
        String[] obaaContext = oai_obaa.getContext().split(";;");
        String[] obaaTypicalAgeRange = oai_obaa.getTypicalagerange().split(";;");
        String[] obaaDifficulty = oai_obaa.getDifficulty().split(";;");
        String[] obaaTypicalLearningTime = oai_obaa.getTypicallearningtime().split(";;");
        String[] obaaEducationalDescription = oai_obaa.getEducationaldescription().split(";;");
        String[] obaaEducationalLanguage = oai_obaa.getEducationallanguage().split(";;");
        String[] obaaLearningContentType = oai_obaa.getLearningContentType().split(";;");
        String[] obaaPerception = oai_obaa.getPerception().split(";;");
        String[] obaaSynchronism = oai_obaa.getSynchronism().split(";;");
        String[] obaaCoPresence = oai_obaa.getCopresence().split(";;");
        String[] obaaReciprocity = oai_obaa.getReciprocity().split(";;");
        String[] obaaDidacticStrategy = oai_obaa.getDidacticstrategy().split(";;");
        String[] obaaCost = oai_obaa.getCost().split(";;");
        String[] obaaCopyrightAndOtherRestrictions = oai_obaa.getCopyrightandotherrestrictions().split(";;");
        String[] obaaRightsDescription = oai_obaa.getRightsdescription().split(";;");
        String[] obaaKind = oai_obaa.getKind().split(";;");
        String[] obaaResourceCatalog = oai_obaa.getResourcecatalog().split(";;");
        String[] obaaResourceEntry = oai_obaa.getResourceentry().split(";;");
        String[] obaaResourceDescription = oai_obaa.getResourcedescription().split(";;");
        String[] obaaPurpose = oai_obaa.getPurpose().split(";;");
        String[] obaaSource = oai_obaa.getSource().split(";;");
        String[] obaaTaxonId = oai_obaa.getTaxonid().split(";;");
        String[] obaaTaxonEntry = oai_obaa.getTaxonentry().split(";;");
        String[] obaaTaxonPathDescription = oai_obaa.getTaxonpathdescription().split(";;");
        String[] obaaTaxonPathKeyword = oai_obaa.getTaxonpathkeyword().split(";;");
        String[] obaaHasVisual = oai_obaa.getHasvisual().split(";;");
        String[] obaaHasAuditory = oai_obaa.getHasauditory().split(";;");
        String[] obaaHasText = oai_obaa.getHastext().split(";;");
        String[] obaaHasTactile = oai_obaa.getHastactile().split(";;");
        String[] obaaDisplayTransformability = oai_obaa.getDisplaytransformability().split(";;");
        String[] obaaControlFlexibility = oai_obaa.getControlflexibility().split(";;");
        String[] obaaEquivalentResource = oai_obaa.getEquivalentresource().split(";;");
        String[] obaaIsSuplementary = oai_obaa.getIssuplementary().split(";;");
        String[] obaaAudioDescription = oai_obaa.getAudiodescription().split(";;");
        String[] obaaAudioDescriptionLanguage = oai_obaa.getAudiodescriptionlanguage().split(";;");
        String[] obaaAltTextLang = oai_obaa.getAlttextlang().split(";;");
        String[] obaaLongDescriptionLang = oai_obaa.getLongDescriptionLang().split(";;");
        String[] obaaColorAvoidance = oai_obaa.getColoravoidance().split(";;");
        String[] obaaGraphicAlternative = oai_obaa.getGraphicalternative().split(";;");
        String[] obaaSignLanguage = oai_obaa.getSignlanguage().split(";;");
        String[] obaaCaptionTypeLanguage = oai_obaa.getCaptiontypelanguage().split(";;");
        String[] obaaCaptionRate = oai_obaa.getCaptionrate().split(";;");
        String[] obaaSegmentInformation = oai_obaa.getSegmentinformation().split(";;");
        String[] obaaSegmentInformationIdentifier = oai_obaa.getSegmentinformationidentifier().split(";;");
        String[] obaaSegmentInformationTitle = oai_obaa.getSegmentinformationtitle().split(";;");
        String[] obaaSegmentInformationDescription = oai_obaa.getSegmentinformationdescription().split(";;");
        String[] obaaSegmentInformationKeyword = oai_obaa.getSegmentinformationkeyword().split(";;");
        String[] obaaSegmentMediaType = oai_obaa.getSegmentmediatype().split(";;");
        String[] obaaStart = oai_obaa.getStart().split(";;");
        String[] obaaEnd = oai_obaa.getEnd().split(";;");
        String[] obaaSegmentGroupInformationIdentifier = oai_obaa.getSegmentgroupinformationidentifier().split(";;");
        String[] obaaGroupType = oai_obaa.getGrouptype().split(";;");
        String[] obaaSegmentGroupInformationTitle = oai_obaa.getSegmentgroupinformationtitle().split(";;");
        String[] obaaSegmentGroupInformationDescription = oai_obaa.getSegmentgroupinformationdescription().split(";;");
        String[] obaaSegmentGroupInformationKeyword = oai_obaa.getSegmentgroupinformationkeyword().split(";;");
        String[] obaaSegmentsIdentifier = oai_obaa.getSegmentsidentifier().split(";;");

        StopWordTAD stWd = new StopWordTAD();
        Conectar conectar = new Conectar(); //instancia uma variavel da classe mysql.conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar
        stWd.load(con);
       
       Documento doc = new Documento(stWd);



        String containerName = dnRecebido.trim();

        LDAPAttribute attribute = null;
        LDAPAttributeSet attributeSet = new LDAPAttributeSet();


        System.out.println(" =============================");
        System.out.println("  Inserindo objeto: " + header.getIdentifier());
        System.out.println(" =============================\n");

        attributeSet.add(new LDAPAttribute(
                "objectclass", new String[]{"top", "obaa"}));



        //System.out.println(numerador);

        doc.setObaaEntry(header.getIdentifier()); //envia o obaaIdentifier para o indice
        doc.setServidor(idRep); //adiciona no indice o id do repositorio

        if (!obaaIdentifier[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaIdentifier", obaaIdentifier));
        }
        if (!obaaCatalog[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaCatalog", obaaCatalog));
        }
        if (!obaaTitle[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaTitle", obaaTitle));
            doc.setTitulo(oai_obaa.getTitle()); //envia o titulo para o indice
        }
        if (!obaaLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLanguage", obaaLanguage));
        }
        if (!obaaDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDescription", obaaDescription));
            doc.setDescricao(oai_obaa.getDescription()); //envia descricao para o indice
        }
        if (!obaaKeyword[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaKeyword", obaaKeyword));
            doc.setPalavrasChave(oai_obaa.getKeyword()); //envia palavras-chaves para o indice
        }
        if (!obaaCoverage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaCoverage", obaaCoverage));
        }
        if (!obaaStructure[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaStructure", obaaStructure));
        }
        if (!obaaAggregationLevel[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaAggregationLevel", obaaAggregationLevel));
        }
        if (!obaaVersion[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaVersion", obaaVersion));
        }
        if (!obaaStatus[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaStatus", obaaStatus));
        }
        if (!obaaRole[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaRole", obaaRole));
        }
        if (!obaaEntity[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEntity", obaaEntity));
            doc.setEntidade(oai_obaa.getEntity());
        }
        if (!obaaDate[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDate", obaaDate));
            doc.setData(oai_obaa.getDate());
        }
        if (!obaaMetaMetadataCatalog[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaMetaMetadataCatalog", obaaMetaMetadataCatalog));
        }
        if (!obaaMetaMetadataEntry[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaMetaMetadataEntry", obaaMetaMetadataEntry));
        }
        if (!obaaMetaMetadataRole[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaMetaMetadataRole", obaaMetaMetadataRole));
        }
        if (!obaaMetaMetadataEntity[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaMetaMetadataEntity", obaaMetaMetadataEntity));
        }
        if (!obaaMetaMetadataDate[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaMetaMetadataDate", obaaMetaMetadataDate));
        }
        if (!obaaMetadataSchema[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaMetadataSchema", obaaMetadataSchema));
        }
        if (!obaaMetaMetadataLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaMetaMetadataLanguage", obaaMetaMetadataLanguage));
        }
        if (!obaaFormat[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaFormat", obaaFormat));
        }
        if (!obaaSize[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSize", obaaSize));
        }
        if (!obaaLocation[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLocation", obaaLocation));
            doc.setLocalizacao(oai_obaa.getLocation());
        }
        if (!obaaType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaType", obaaType));
        }
        if (!obaaName[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaName", obaaName));
        }
        if (!obaaMinimumVersion[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaMinimumVersion", obaaMinimumVersion));
        }
        if (!obaaMaximumVersion[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaMaximumVersion ", obaaMaximumVersion));
        }
        if (!obaaInstallationRemarks[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaInstallationRemarks", obaaInstallationRemarks));
        }
        if (!obaaOtherPlatformRequirements[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaOtherPlatformRequirements", obaaOtherPlatformRequirements));
        }
        if (!obaaDuration[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDuration", obaaDuration));
        }
        if (!obaaSupportedPlatforms[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSupportedPlatforms", obaaSupportedPlatforms));
        }
        if (!obaaPlatformType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaPlatformType", obaaPlatformType));
        }
        if (!obaaSpecificFormat[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSpecificFormat", obaaSpecificFormat));
        }
        if (!obaaSpecificSize[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSpecificSize", obaaSpecificSize));
        }
        if (!obaaSpecificLocation[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSpecificLocation", obaaSpecificLocation));
        }
        if (!obaaSpecificType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSpecificType", obaaSpecificType));
        }
        if (!obaaSpecificName[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSpecificName", obaaSpecificName));
        }
        if (!obaaSpecificMinimumVersion[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSpecificMinimumVersion", obaaSpecificMinimumVersion));
        }
        if (!obaaSpecificMaximumVersion[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSpecificMaximumVersion", obaaSpecificMaximumVersion));
        }
        if (!obaaSpecificInstallationRemarks[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSpecificInstallationRemarks", obaaSpecificInstallationRemarks));
        }
        if (!obaaSpecificOtherPlatformRequirements[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSpecificOtherPlatformRequirements", obaaSpecificOtherPlatformRequirements));
        }
        if (!obaaServiceName[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaServiceName", obaaServiceName));
        }
        if (!obaaServiceType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaServiceType", obaaServiceType));
        }
        if (!obaaProvides[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaProvides", obaaProvides));
        }
        if (!obaaEssential[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEssential", obaaEssential));
        }
        if (!obaaProtocol[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaProtocol", obaaProtocol));
        }
        if (!obaaOntologyLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaOntologyLanguage", obaaOntologyLanguage));
        }
        if (!obaaOntologyLocation[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaOntologyLocation", obaaOntologyLocation));
        }
        if (!obaaServiceLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaServiceLanguage", obaaServiceLanguage));
        }
        if (!obaaServiceLocation[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaServiceLocation", obaaServiceLocation));
        }
        if (!obaaInteractivityType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaInteractivityType", obaaInteractivityType));
        }
        if (!obaaLearningResourceType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLearningResourceType", obaaLearningResourceType));
        }
        if (!obaaInteractivityLevel[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaInteractivityLevel", obaaInteractivityLevel));
        }
        if (!obaaSemanticDensity[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSemanticDensity", obaaSemanticDensity));
        }
        if (!obaaIntendedEndUserRole[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaIntendedEndUserRole", obaaIntendedEndUserRole));
        }
        if (!obaaContext[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaContext", obaaContext));
        }
        if (!obaaTypicalAgeRange[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaTypicalAgeRange", obaaTypicalAgeRange));
        }
        if (!obaaDifficulty[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDifficulty", obaaDifficulty));
        }
        if (!obaaTypicalLearningTime[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaTypicalLearningTime", obaaTypicalLearningTime));
        }
        if (!obaaEducationalDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEducationalDescription", obaaEducationalDescription));
        }
        if (!obaaEducationalLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEducationalLanguage", obaaEducationalLanguage));
        }
        if (!obaaLearningContentType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLearningContentType", obaaLearningContentType));
        }
        if (!obaaPerception[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaPerception", obaaPerception));
        }
        if (!obaaSynchronism[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSynchronism", obaaSynchronism));
        }
        if (!obaaCoPresence[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaCoPresence", obaaCoPresence));
        }
        if (!obaaReciprocity[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaReciprocity", obaaReciprocity));
        }
        if (!obaaDidacticStrategy[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDidacticStrategy", obaaDidacticStrategy));
        }
        if (!obaaCost[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaCost", obaaCost));
        }
        if (!obaaCopyrightAndOtherRestrictions[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaCopyrightAndOtherRestrictions", obaaCopyrightAndOtherRestrictions));
        }
        if (!obaaRightsDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaRightsDescription", obaaRightsDescription));
        }
        if (!obaaKind[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaKind", obaaKind));
        }
        if (!obaaResourceCatalog[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaResourceCatalog", obaaResourceCatalog));
        }
        if (!obaaResourceEntry[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaResourceEntry", obaaResourceEntry));
        }
        if (!obaaResourceDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaResourceDescription", obaaResourceDescription));
        }
        if (!obaaPurpose[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaPurpose", obaaPurpose));
        }
        if (!obaaSource[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSource", obaaSource));
        }
        if (!obaaTaxonId[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaTaxonId", obaaTaxonId));
        }
        if (!obaaTaxonEntry[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaTaxonEntry", obaaTaxonEntry));
        }
        if (!obaaTaxonPathDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaTaxonPathDescription", obaaTaxonPathDescription));
        }
        if (!obaaTaxonPathKeyword[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaTaxonPathKeyword", obaaTaxonPathKeyword));
        }
        if (!obaaHasVisual[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaHasVisual", obaaHasVisual));
        }
        if (!obaaHasAuditory[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaHasAuditory", obaaHasAuditory));
        }
        if (!obaaHasText[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaHasText", obaaHasText));
        }
        if (!obaaHasTactile[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaHasTactile", obaaHasTactile));
        }
        if (!obaaDisplayTransformability[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDisplayTransformability", obaaDisplayTransformability));
        }
        if (!obaaControlFlexibility[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaControlFlexibility", obaaControlFlexibility));
        }
        if (!obaaEquivalentResource[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEquivalentResource", obaaEquivalentResource));
        }
        if (!obaaIsSuplementary[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaIsSuplementary", obaaIsSuplementary));
        }
        if (!obaaAudioDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaAudioDescription", obaaAudioDescription));
        }
        if (!obaaAudioDescriptionLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaAudioDescriptionLanguage", obaaAudioDescriptionLanguage));
        }
        if (!obaaAltTextLang[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaAltTextLang", obaaAltTextLang));
        }
        if (!obaaLongDescriptionLang[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLongDescriptionLang", obaaLongDescriptionLang));
        }
        if (!obaaColorAvoidance[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaColorAvoidance", obaaColorAvoidance));
        }
        if (!obaaGraphicAlternative[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaGraphicAlternative", obaaGraphicAlternative));
        }
        if (!obaaSignLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSignLanguage", obaaSignLanguage));
        }
        if (!obaaCaptionTypeLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaCaptionTypeLanguage", obaaCaptionTypeLanguage));
        }
        if (!obaaCaptionRate[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaCaptionRate", obaaCaptionRate));
        }
        if (!obaaSegmentInformation[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentInformation", obaaSegmentInformation));
        }
        if (!obaaSegmentInformationIdentifier[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentInformationIdentifier", obaaSegmentInformationIdentifier));
        }
        if (!obaaSegmentInformationTitle[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentInformationTitle", obaaSegmentInformationTitle));
        }
        if (!obaaSegmentInformationDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentInformationDescription", obaaSegmentInformationDescription));
        }
        if (!obaaSegmentInformationKeyword[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentInformationKeyword", obaaSegmentInformationKeyword));
        }
        if (!obaaSegmentMediaType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentMediaType", obaaSegmentMediaType));
        }
        if (!obaaStart[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaStart", obaaStart));
        }
        if (!obaaEnd[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEnd", obaaEnd));
        }
        if (!obaaSegmentGroupInformationIdentifier[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentGroupInformationIdentifier", obaaSegmentGroupInformationIdentifier));
        }
        if (!obaaGroupType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaGroupType", obaaGroupType));
        }
        if (!obaaSegmentGroupInformationTitle[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentGroupInformationTitle", obaaSegmentGroupInformationTitle));
        }
        if (!obaaSegmentGroupInformationDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentGroupInformationDescription", obaaSegmentGroupInformationDescription));
        }
        if (!obaaSegmentGroupInformationKeyword[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentGroupInformationKeyword", obaaSegmentGroupInformationKeyword));
        }
        if (!obaaSegmentsIdentifier[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaSegmentsIdentifier", obaaSegmentsIdentifier));
        }


        String dn = "obaaEntry=" + header.getIdentifier() + "," + containerName;

        LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);

        //Insert LDAP

        try {

            // connect to the server
//            lc.connect(ldapHost, ldapPort);

            // authenticate to the server
//            lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));

            lc.add(newEntry);


            // disconnect with the server
//            lc.disconnect();
        } catch (LDAPException e) {
            System.out.println("Error:  " + e.toString());
        }

        
        try {
            System.out.println("adicionando documento ao indice");
            indexar.addDoc(doc, con); //adciona o documento no indice mysql
            } catch (SQLException e) {
            System.err.println("Erro ao inserir o documento no indice: " + e.getMessage());
        }
            try {
                con.close(); //fechar conexao mysql
                } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        
    }
}
