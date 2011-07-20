package robo.atualiza.importa_OAI;

/**
 * Classe auxiliar que armazena informações dos metadados do padrão dublin core.
 * @author Marcos
 */
public class OAI_Interface {

    private String identifier = "";
    private String title = "";
    private String language = "";
    private String description = "";
    private String subject = "";
    private String coverage = "";
    private String type = "";
    private String date = "";
    private String creator = "";
    private String OtherContributor = "";
    private String publisher = "";
    private String format = "";
    private String rigths = "";
    private String relation = "";
    private String source = "";
    //adicionais para o obaa e lom
    private String catalog = "";
    private String keyword = "";
    private String structure = "";
    private String aggregationlevel = "";
    private String version = "";
    private String status = "";
    private String role = "";
    private String entity = "";
    private String metametadatacatalog = "";
    private String metametadataentry = "";
    private String metametadatarole = "";
    private String metametadataentity = "";
    private String metametadatadate = "";
    private String metadataschema = "";
    private String metametadatalanguage = "";
    private String size = "";
    private String location = "";
    private String name = "";
    private String minimumversion = "";
    private String maximumversion = "";
    private String installationRemarks = "";
    private String otherplatformrequirements = "";
    private String duration = "";
    private String supportedplatforms = "";
    private String platformtype = "";
    private String specificformat = "";
    private String specificsize = "";
    private String specificlocation = "";
    private String specifictype = "";
    private String specificname = "";
    private String specificminimumversion = "";
    private String specificmaximumversion = "";
    private String specificinstallationremarks = "";
    private String specificotherplatformrequirements = "";
    private String servicename = "";
    private String servicetype = "";
    private String provides = "";
    private String essential = "";
    private String protocol = "";
    private String ontologylanguage = "";
    private String ontologylocation = "";
    private String servicelanguage = "";
    private String servicelocation = "";
    private String interactivitytype = "";
    private String learningresourcetype = "";
    private String interactivitylevel = "";
    private String semanticdensity = "";
    private String intendedenduserrole = "";
    private String context = "";
    private String typicalagerange = "";
    private String difficulty = "";
    private String typicallearningtime = "";
    private String educationaldescription = "";
    private String educationallanguage = "";
    private String learningcontenttype = "";
    private String perception = "";
    private String synchronism = "";
    private String copresence = "";
    private String reciprocity = "";
    private String didacticstrategy = "";
    private String cost = "";
    private String copyrightandotherrestrictions = "";
    private String rightsdescription = "";
    private String kind = "";
    private String resourcecatalog = "";
    private String resourceentry = "";
    private String resourcedescription = "";
    private String purpose = "";
    private String taxonid = "";
    private String taxonentry = "";
    private String taxonpathdescription = "";
    private String taxonpathkeyword = "";
    private String hasvisual = "";
    private String hasauditory = "";
    private String hastext = "";
    private String hastactile = "";
    private String displaytransformability = "";
    private String controlflexibility = "";
    private String equivalentresource = "";
    private String issuplementary = "";
    private String audiodescription = "";
    private String audiodescriptionlanguage = "";
    private String alttextlang = "";
    private String longdescriptionlang = "";
    private String coloravoidance = "";
    private String graphicalternative = "";
    private String signlanguage = "";
    private String captiontypelanguage = "";
    private String captionrate = "";
    private String segmentinformation = "";
    private String segmentinformationidentifier = "";
    private String segmentinformationtitle = "";
    private String segmentinformationdescription = "";
    private String segmentinformationkeyword = "";
    private String segmentmediatype = "";
    private String start = "";
    private String end = "";
    private String segmentgroupinformationidentifier = "";
    private String grouptype = "";
    private String segmentgroupinformationtitle = "";
    private String segmentgroupinformationdescription = "";
    private String segmentgroupinformationkeyword = "";
    private String segmentsidentifier = "";

    /**
     * Armazena na variável identifier o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param id id que será armazenado na variavel identifier.
     */
    public void setIdentifier(String id) {
        if (this.identifier.isEmpty()) {
            this.identifier = id;
        } else {
            this.identifier += ";;" + id;
        }
    }

    /**
     * Responde com o conteúdo da String identifier. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Armazena na variável title o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param title valor que será armazenado na variavel title.
     */
    public void setTitle(String title) {
        if (this.title.isEmpty()) {
            this.title = title;
        } else {
            this.title += ";;" + title;
        }
    }

    /**
     * Responde com o conteúdo da String title. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Armazena na variável language o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param language Valor que será armazenado na variavel language.
     */
    public void setLanguage(String language) {
        if (this.language.isEmpty()) {
            this.language = language;
        } else {
            this.language += ";;" + language;
        }
    }

    /**
     * Responde com o conteúdo da String language. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Responde com o conteúdo da String description. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Responde com o conteúdo da String subject. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Responde com o conteúdo da String coverage. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string coverage.
     */
    public String getCoverage() {
        return coverage;
    }

    /**
     * Responde com o conteúdo da String type. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string type.
     */
    public String getType() {
        return type;
    }

    /**
     * Responde com o conteúdo da String date. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Responde com o conteúdo da String creator. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string creator.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Responde com o conteúdo da String OtherContributor. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string OtherContributor.
     */
    public String getOtherContributor() {
        return OtherContributor;
    }

    /**
     * Responde com o conteúdo da String publisher. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string publisher.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Responde com o conteúdo da String format. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string format.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Responde com o conteúdo da String rigths. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string rigths.
     */
    public String getRigths() {
        return rigths;
    }

    /**
     * Responde com o conteúdo da String relation. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string relation.
     */
    public String getRelation() {
        return relation;
    }

    /**
     * Responde com o conteúdo da String source. Tal String contém todos os valores correspondentes a este campo encontrados no xml
     * @return retorna o valor da string source.
     */
    public String getSource() {
        return source;
    }

    /**
     * Armazena na variável description o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param description Valor que será armazenado na variavel description.
     */
    public void setDescription(String description) {
        if (this.description.isEmpty()) {
            this.description = description;
        } else {
            this.description += ";;" + description;
        }
    }

    /**
     * Armazena na variável subject o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param subject Valor que será armazenado na variavel subject.
     */
    public void setSubject(String subject) {
        if (this.subject.isEmpty()) {
            this.subject = subject;
        } else {
            this.subject += ";;" + subject;
        }
    }

    /**
     * Armazena na variável coverage o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param coverage Valor que será armazenado na variavel coverage.
     */
    public void setCoverage(String coverage) {
        if (this.coverage.isEmpty()) {
            this.coverage = coverage;
        } else {
            this.coverage += ";;" + coverage;
        }
    }

    /**
     * Armazena na variável type o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param type Valor que será armazenado na variavel type.
     */
    public void setType(String type) {
        if (this.type.isEmpty()) {
            this.type = type;
        } else {
            this.type += ";;" + type;
        }
    }

    /**
     * Armazena na variável date o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param date Valor que será armazenado na variavel date.
     */
    public void setDate(String date) {
        if (this.date.isEmpty()) {
            this.date = date;
        } else {
            this.date += ";;" + date;
        }
    }

    /**
     * Armazena na variável creator o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param creator Valor que será armazenado na variavel creator.
     */
    public void setCreator(String creator) {
        if (this.creator.isEmpty()) {
            this.creator = creator;
        } else {
            this.creator += ";;" + creator;
        }
    }

    /**
     * Armazena na variável OtherContributor o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param OtherContributor Valor que será armazenado na variavel OtherContributor.
     */
    public void setOtherContributor(String OtherContributor) {
        if (this.OtherContributor.isEmpty()) {
            this.OtherContributor = OtherContributor;
        } else {
            this.OtherContributor += ";;" + OtherContributor;
        }
    }

    /**
     * Armazena na variável publisher o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param publisher Valor que será armazenado na variavel publisher.
     */
    public void setPublisher(String publisher) {
        if (this.publisher.isEmpty()) {
            this.publisher = publisher;
        } else {
            this.publisher += ";;" + publisher;
        }
    }

    /**
     * Armazena na variável format o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param format Valor que será armazenado na variavel format.
     */
    public void setFormat(String format) {
        if (this.format.isEmpty()) {
            this.format = format;
        } else {
            this.format += ";;" + format;
        }
    }

    /**
     * Armazena na variável rigths o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param rigths Valor que será armazenado na variavel rigths.
     */
    public void setRigths(String rigths) {
        if (this.rigths.isEmpty()) {
            this.rigths = rigths;
        } else {
            this.rigths += ";;" + rigths;
        }
    }

    /**
     * Armazena na variável relation o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param relation Valor que será armazenado na variavel relation.
     */
    public void setRelation(String relation) {
        if (this.relation.isEmpty()) {
            this.relation = relation;
        } else {
            this.relation += ";;" + relation;
        }
    }

    /**
     * Armazena na variável source o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param source Valor que será armazenado na variavel source.
     */
    public void setSource(String source) {
        if (this.source.isEmpty()) {
            this.source = source;
        } else {
            this.source += ";;" + source;
        }
    }

    //OBAA e LOM
    public String getAggregationlevel() {
        return aggregationlevel;
    }

    public String getAlttextlang() {
        return alttextlang;
    }

    public String getAudiodescription() {
        return audiodescription;
    }

    public String getAudiodescriptionlanguage() {
        return audiodescriptionlanguage;
    }

    public String getCaptionrate() {
        return captionrate;
    }

    public String getCaptiontypelanguage() {
        return captiontypelanguage;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getColoravoidance() {
        return coloravoidance;
    }

    public String getContext() {
        return context;
    }

    public String getControlflexibility() {
        return controlflexibility;
    }

    public String getCopresence() {
        return copresence;
    }

    public String getCopyrightandotherrestrictions() {
        return copyrightandotherrestrictions;
    }

    public String getCost() {
        return cost;
    }

    public String getDidacticstrategy() {
        return didacticstrategy;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getDisplaytransformability() {
        return displaytransformability;
    }

    public String getDuration() {
        return duration;
    }

    public String getEducationaldescription() {
        return educationaldescription;
    }

    public String getEducationallanguage() {
        return educationallanguage;
    }

    public String getEnd() {
        return end;
    }

    public String getEntity() {
        return entity;
    }

    public String getEquivalentresource() {
        return equivalentresource;
    }

    public String getEssential() {
        return essential;
    }

    public String getGraphicalternative() {
        return graphicalternative;
    }

    public String getGrouptype() {
        return grouptype;
    }

    public String getHasauditory() {
        return hasauditory;
    }

    public String getHastactile() {
        return hastactile;
    }

    public String getHastext() {
        return hastext;
    }

    public String getHasvisual() {
        return hasvisual;
    }

    public String getInstallationremarks() {
        return installationRemarks;
    }

    public String getIntendedenduserrole() {
        return intendedenduserrole;
    }

    public String getInteractivitylevel() {
        return interactivitylevel;
    }

    public String getInteractivitytype() {
        return interactivitytype;
    }

    public String getIssuplementary() {
        return issuplementary;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getKind() {
        return kind;
    }

    public String getLearningContentType() {
        return learningcontenttype;
    }

    public String getLearningResourceType() {
        return learningresourcetype;
    }

    public String getLocation() {
        return location;
    }

    public String getLongDescriptionLang() {
        return longdescriptionlang;
    }

    public String getMaximumVersion() {
        return maximumversion;
    }

    public String getMetadataschema() {
        return metadataschema;
    }

    public String getMetametadatacatalog() {
        return metametadatacatalog;
    }

    public String getMetametadatadate() {
        return metametadatadate;
    }

    public String getMetametadataentity() {
        return metametadataentity;
    }

    public String getMetametadataentry() {
        return metametadataentry;
    }

    public String getMetametadatalanguage() {
        return metametadatalanguage;
    }

    public String getMetametadatarole() {
        return metametadatarole;
    }

    public String getMinimumversion() {
        return minimumversion;
    }

    public String getName() {
        return name;
    }

    public String getOntologylanguage() {
        return ontologylanguage;
    }

    public String getOntologylocation() {
        return ontologylocation;
    }

    public String getOtherplatformrequirements() {
        return otherplatformrequirements;
    }

    public String getPerception() {
        return perception;
    }

    public String getPlatformtype() {
        return platformtype;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getProvides() {
        return provides;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getReciprocity() {
        return reciprocity;
    }

    public String getResourcecatalog() {
        return resourcecatalog;
    }

    public String getResourcedescription() {
        return resourcedescription;
    }

    public String getResourceentry() {
        return resourceentry;
    }

    public String getRightsdescription() {
        return rightsdescription;
    }

    public String getRole() {
        return role;
    }

    public String getSegmentgroupinformationdescription() {
        return segmentgroupinformationdescription;
    }

    public String getSegmentgroupinformationidentifier() {
        return segmentgroupinformationidentifier;
    }

    public String getSegmentgroupinformationkeyword() {
        return segmentgroupinformationkeyword;
    }

    public String getSegmentgroupinformationtitle() {
        return segmentgroupinformationtitle;
    }

    public String getSegmentinformation() {
        return segmentinformation;
    }

    public String getSegmentinformationdescription() {
        return segmentinformationdescription;
    }

    public String getSegmentinformationidentifier() {
        return segmentinformationidentifier;
    }

    public String getSegmentinformationkeyword() {
        return segmentinformationkeyword;
    }

    public String getSegmentinformationtitle() {
        return segmentinformationtitle;
    }

    public String getSegmentmediatype() {
        return segmentmediatype;
    }

    public String getSegmentsidentifier() {
        return segmentsidentifier;
    }

    public String getSemanticdensity() {
        return semanticdensity;
    }

    public String getServicelanguage() {
        return servicelanguage;
    }

    public String getServicelocation() {
        return servicelocation;
    }

    public String getServicename() {
        return servicename;
    }

    public String getServicetype() {
        return servicetype;
    }

    public String getSignlanguage() {
        return signlanguage;
    }

    public String getSize() {
        return size;
    }

    public String getSpecificformat() {
        return specificformat;
    }

    public String getSpecificinstallationremarks() {
        return specificinstallationremarks;
    }

    public String getSpecificlocation() {
        return specificlocation;
    }

    public String getSpecificmaximumversion() {
        return specificmaximumversion;
    }

    public String getSpecificminimumversion() {
        return specificminimumversion;
    }

    public String getSpecificname() {
        return specificname;
    }

    public String getSpecificotherplatformrequirements() {
        return specificotherplatformrequirements;
    }

    public String getSpecificsize() {
        return specificsize;
    }

    public String getSpecifictype() {
        return specifictype;
    }

    public String getStart() {
        return start;
    }

    public String getStatus() {
        return status;
    }

    public String getStructure() {
        return structure;
    }

    public String getSupportedplatforms() {
        return supportedplatforms;
    }

    public String getSynchronism() {
        return synchronism;
    }

    public String getTaxonentry() {
        return taxonentry;
    }

    public String getTaxonid() {
        return taxonid;
    }

    public String getTaxonpathdescription() {
        return taxonpathdescription;
    }

    public String getTaxonpathkeyword() {
        return taxonpathkeyword;
    }

    public String getTypicalagerange() {
        return typicalagerange;
    }

    public String getTypicallearningtime() {
        return typicallearningtime;
    }

    public String getVersion() {
        return version;
    }

// set OBAA E LOM
    public void setAggregationlevel(String entrada) {
        if (this.aggregationlevel.isEmpty()) {
            this.aggregationlevel = entrada;
        } else {
            this.aggregationlevel += ";;" + entrada;
        }
    }

    public void setAlttextlang(String entrada) {
        if (this.alttextlang.isEmpty()) {
            this.alttextlang = entrada;
        } else {
            this.alttextlang += ";;" + entrada;
        }
    }

    public void setAudiodescription(String entrada) {
        if (this.audiodescription.isEmpty()) {
            this.audiodescription = entrada;
        } else {
            this.audiodescription += ";;" + entrada;
        }
    }

    public void setAudiodescriptionlanguage(String entrada) {
        if (this.audiodescriptionlanguage.isEmpty()) {
            this.audiodescriptionlanguage = entrada;
        } else {
            this.audiodescriptionlanguage += ";;" + entrada;
        }
    }

    public void setCaptionrate(String entrada) {
        if (this.captionrate.isEmpty()) {
            this.captionrate = entrada;
        } else {
            this.captionrate += ";;" + entrada;
        }
    }

    public void setCaptiontypelanguage(String entrada) {
        if (this.captiontypelanguage.isEmpty()) {
            this.captiontypelanguage = entrada;
        } else {
            this.captiontypelanguage += ";;" + entrada;
        }
    }

    public void setCatalog(String entrada) {
        if (this.catalog.isEmpty()) {
            this.catalog = entrada;
        } else {
            this.catalog += ";;" + entrada;
        }
    }

    public void setColoravoidance(String entrada) {
        if (this.coloravoidance.isEmpty()) {
            this.coloravoidance = entrada;
        } else {
            this.coloravoidance += ";;" + entrada;
        }
    }

    public void setContext(String entrada) {
        if (this.context.isEmpty()) {
            this.context = entrada;
        } else {
            this.context += ";;" + entrada;
        }
    }

    public void setControlflexibility(String entrada) {
        if (this.controlflexibility.isEmpty()) {
            this.controlflexibility = entrada;
        } else {
            this.controlflexibility += ";;" + entrada;
        }
    }

    public void setCopresence(String entrada) {
        if (this.copresence.isEmpty()) {
            this.copresence = entrada;
        } else {
            this.copresence += ";;" + entrada;
        }
    }

    public void setCopyrightandotherrestrictions(String entrada) {
        if (this.copyrightandotherrestrictions.isEmpty()) {
            this.copyrightandotherrestrictions = entrada;
        } else {
            this.copyrightandotherrestrictions += ";;" + entrada;
        }
    }

    public void setCost(String entrada) {
        if (this.cost.isEmpty()) {
            this.cost = entrada;
        } else {
            this.cost += ";;" + entrada;
        }
    }

    public void setDidacticstrategy(String entrada) {
        if (this.didacticstrategy.isEmpty()) {
            this.didacticstrategy = entrada;
        } else {
            this.didacticstrategy += ";;" + entrada;
        }
    }

    public void setDifficulty(String entrada) {
        if (this.difficulty.isEmpty()) {
            this.difficulty = entrada;
        } else {
            this.difficulty += ";;" + entrada;
        }
    }

    public void setDisplaytransformability(String entrada) {
        if (this.displaytransformability.isEmpty()) {
            this.displaytransformability = entrada;
        } else {
            this.displaytransformability += ";;" + entrada;
        }
    }

    public void setDuration(String entrada) {
        if (this.duration.isEmpty()) {
            this.duration = entrada;
        } else {
            this.duration += ";;" + entrada;
        }
    }

    public void setEducationaldescription(String entrada) {
        if (this.educationaldescription.isEmpty()) {
            this.educationaldescription = entrada;
        } else {
            this.educationaldescription += ";;" + entrada;
        }
    }

    public void setEducationallanguage(String entrada) {
        if (this.educationallanguage.isEmpty()) {
            this.educationallanguage = entrada;
        } else {
            this.educationallanguage += ";;" + entrada;
        }
    }

public void setEnd(String entrada) {
        if (this.end.isEmpty()) {
            this.end = entrada;
        } else {
            this.end += ";;" + entrada;
        }
    }

    public void setEntity(String entrada) {
        if (this.entity.isEmpty()) {
            this.entity = entrada;
        } else {
            this.entity += ";;" + entrada;
        }
    }

    public void setEquivalentresource(String entrada) {
        if (this.equivalentresource.isEmpty()) {
            this.equivalentresource = entrada;
        } else {
            this.equivalentresource += ";;" + entrada;
        }
    }

    public void setEssential(String entrada) {
        if (this.essential.isEmpty()) {
            this.essential = entrada;
        } else {
            this.essential += ";;" + entrada;
        }
    }

    public void setGraphicalternative(String entrada) {
        if (this.graphicalternative.isEmpty()) {
            this.graphicalternative = entrada;
        } else {
            this.graphicalternative += ";;" + entrada;
        }
    }

    public void setGrouptype(String entrada) {
        if (this.grouptype.isEmpty()) {
            this.grouptype = entrada;
        } else {
            this.grouptype += ";;" + entrada;
        }
    }

    public void setHasauditory(String entrada) {
        if (this.hasauditory.isEmpty()) {
            this.hasauditory = entrada;
        } else {
            this.hasauditory += ";;" + entrada;
        }
    }

    public void setHastactile(String entrada) {
        if (this.hastactile.isEmpty()) {
            this.hastactile = entrada;
        } else {
            this.hastactile += ";;" + entrada;
        }
    }

    public void setHastext(String entrada) {
        if (this.hastext.isEmpty()) {
            this.hastext = entrada;
        } else {
            this.hastext += ";;" + entrada;
        }
    }

    public void setHasvisual(String entrada) {
        if (this.hasvisual.isEmpty()) {
            this.hasvisual = entrada;
        } else {
            this.hasvisual += ";;" + entrada;
        }
    }

    public void setInstallationremarks(String entrada) {
        if (this.installationRemarks.isEmpty()) {
            this.installationRemarks = entrada;
        } else {
            this.installationRemarks += ";;" + entrada;
        }
    }

    public void setIntendedenduserrole(String entrada) {
        if (this.intendedenduserrole.isEmpty()) {
            this.intendedenduserrole = entrada;
        } else {
            this.intendedenduserrole += ";;" + entrada;
        }
    }

    public void setInteractivitylevel(String entrada) {
        if (this.interactivitylevel.isEmpty()) {
            this.interactivitylevel = entrada;
        } else {
            this.interactivitylevel += ";;" + entrada;
        }
    }

    public void setInteractivitytype(String entrada) {
        if (this.interactivitytype.isEmpty()) {
            this.interactivitytype = entrada;
        } else {
            this.interactivitytype += ";;" + entrada;
        }
    }

    public void setIssuplementary(String entrada) {
        if (this.issuplementary.isEmpty()) {
            this.issuplementary = entrada;
        } else {
            this.issuplementary += ";;" + entrada;
        }
    }

    public void setKeyword(String entrada) {
        if (this.keyword.isEmpty()) {
            this.keyword = entrada;
        } else {
            this.keyword += ";;" + entrada;
        }
    }

    public void setKind(String entrada) {
        if (this.kind.isEmpty()) {
            this.kind = entrada;
        } else {
            this.kind += ";;" + entrada;
        }
    }

    public void setLearningContentType(String entrada) {
        if (this.learningcontenttype.isEmpty()) {
            this.learningcontenttype = entrada;
        } else {
            this.learningcontenttype += ";;" + entrada;
        }
    }

    public void setLearningResourceType(String entrada) {
        if (this.learningresourcetype.isEmpty()) {
            this.learningresourcetype = entrada;
        } else {
            this.learningresourcetype += ";;" + entrada;
        }
    }

    public void setLocation(String entrada) {
        if (this.location.isEmpty()) {
            this.location = entrada;
        } else {
            this.location += ";;" + entrada;
        }
    }

    public void setLongDescriptionLang(String entrada) {
        if (this.longdescriptionlang.isEmpty()) {
            this.longdescriptionlang = entrada;
        } else {
            this.longdescriptionlang += ";;" + entrada;
        }
    }

    public void setMaximumVersion(String entrada) {
        if (this.maximumversion.isEmpty()) {
            this.maximumversion = entrada;
        } else {
            this.maximumversion += ";;" + entrada;
        }
    }

    public void setMetadataschema(String entrada) {
        if (this.metadataschema.isEmpty()) {
            this.metadataschema = entrada;
        } else {
            this.metadataschema += ";;" + entrada;
        }
    }

    public void setMetaMetadataCatalog(String entrada) {
        if (this.metametadatacatalog.isEmpty()) {
            this.metametadatacatalog = entrada;
        } else {
            this.metametadatacatalog += ";;" + entrada;
        }
    }

    public void setMetaMetadataDate(String entrada) {
        if (this.metametadatadate.isEmpty()) {
            this.metametadatadate = entrada;
        } else {
            this.metametadatadate += ";;" + entrada;
        }
    }

    public void setMetaMetadataEntity(String entrada) {
        if (this.metametadataentity.isEmpty()) {
            this.metametadataentity = entrada;
        } else {
            this.metametadataentity += ";;" + entrada;
        }
    }

    public void setMetaMetadataEntry(String entrada) {
        if (this.metametadataentry.isEmpty()) {
            this.metametadataentry = entrada;
        } else {
            this.metametadataentry += ";;" + entrada;
        }
    }

    public void setMetametadatalanguage(String entrada) {
        if (this.metametadatalanguage.isEmpty()) {
            this.metametadatalanguage = entrada;
        } else {
            this.metametadatalanguage += ";;" + entrada;
        }
    }

    public void setMetametadatarole(String entrada) {
        if (this.metametadatarole.isEmpty()) {
            this.metametadatarole = entrada;
        } else {
            this.metametadatarole += ";;" + entrada;
        }
    }

    public void setMinimumversion(String entrada) {
        if (this.minimumversion.isEmpty()) {
            this.minimumversion = entrada;
        } else {
            this.minimumversion += ";;" + entrada;
        }
    }

    public void setName(String entrada) {
        if (this.name.isEmpty()) {
            this.name = entrada;
        } else {
            this.name += ";;" + entrada;
        }
    }

    public void setOntologylanguage(String entrada) {
        if (this.ontologylanguage.isEmpty()) {
            this.ontologylanguage = entrada;
        } else {
            this.ontologylanguage += ";;" + entrada;
        }
    }

    public void setOntologylocation(String entrada) {
        if (this.ontologylocation.isEmpty()) {
            this.ontologylocation = entrada;
        } else {
            this.ontologylocation += ";;" + entrada;
        }
    }

    public void setOtherplatformrequirements(String entrada) {
        if (this.otherplatformrequirements.isEmpty()) {
            this.otherplatformrequirements = entrada;
        } else {
            this.otherplatformrequirements += ";;" + entrada;
        }
    }

    public void setPerception(String entrada) {
        if (this.perception.isEmpty()) {
            this.perception = entrada;
        } else {
            this.perception += ";;" + entrada;
        }
    }

    public void setPlatformtype(String entrada) {
        if (this.platformtype.isEmpty()) {
            this.platformtype = entrada;
        } else {
            this.platformtype += ";;" + entrada;
        }
    }

    public void setProtocol(String entrada) {
        if (this.protocol.isEmpty()) {
            this.protocol = entrada;
        } else {
            this.protocol += ";;" + entrada;
        }
    }

    public void setProvides(String entrada) {
        if (this.provides.isEmpty()) {
            this.provides = entrada;
        } else {
            this.provides += ";;" + entrada;
        }
    }

    public void setPurpose(String entrada) {
        if (this.purpose.isEmpty()) {
            this.purpose = entrada;
        } else {
            this.purpose += ";;" + entrada;
        }
    }

    public void setReciprocity(String entrada) {
        if (this.reciprocity.isEmpty()) {
            this.reciprocity = entrada;
        } else {
            this.reciprocity += ";;" + entrada;
        }
    }

    public void setResourcecatalog(String entrada) {
        if (this.resourcecatalog.isEmpty()) {
            this.resourcecatalog = entrada;
        } else {
            this.resourcecatalog += ";;" + entrada;
        }
    }

    public void setResourcedescription(String entrada) {
        if (this.resourcedescription.isEmpty()) {
            this.resourcedescription = entrada;
        } else {
            this.resourcedescription += ";;" + entrada;
        }
    }

    public void setResourceentry(String entrada) {
        if (this.resourceentry.isEmpty()) {
            this.resourceentry = entrada;
        } else {
            this.resourceentry += ";;" + entrada;
        }
    }

    public void setRightsdescription(String entrada) {
        if (this.rightsdescription.isEmpty()) {
            this.rightsdescription = entrada;
        } else {
            this.rightsdescription += ";;" + entrada;
        }
    }

    public void setRole(String entrada) {
        if (this.role.isEmpty()) {
            this.role = entrada;
        } else {
            this.role += ";;" + entrada;
        }
    }

    public void setSegmentgroupinformationdescription(String entrada) {
        if (this.segmentgroupinformationdescription.isEmpty()) {
            this.segmentgroupinformationdescription = entrada;
        } else {
            this.segmentgroupinformationdescription += ";;" + entrada;
        }
    }

    public void setSegmentgroupinformationidentifier(String entrada) {
        if (this.segmentgroupinformationidentifier.isEmpty()) {
            this.segmentgroupinformationidentifier = entrada;
        } else {
            this.segmentgroupinformationidentifier += ";;" + entrada;
        }
    }

    public void setSegmentgroupinformationkeyword(String entrada) {
        if (this.segmentgroupinformationkeyword.isEmpty()) {
            this.segmentgroupinformationkeyword = entrada;
        } else {
            this.segmentgroupinformationkeyword += ";;" + entrada;
        }
    }

    public void setSegmentgroupinformationtitle(String entrada) {
        if (this.segmentgroupinformationtitle.isEmpty()) {
            this.segmentgroupinformationtitle = entrada;
        } else {
            this.segmentgroupinformationtitle += ";;" + entrada;
        }
    }

    public void setSegmentinformation(String entrada) {
        if (this.segmentinformation.isEmpty()) {
            this.segmentinformation = entrada;
        } else {
            this.segmentinformation += ";;" + entrada;
        }
    }

    public void setSegmentinformationdescription(String entrada) {
        if (this.segmentinformationdescription.isEmpty()) {
            this.segmentinformationdescription = entrada;
        } else {
            this.segmentinformationdescription += ";;" + entrada;
        }
    }

    public void setSegmentinformationidentifier(String entrada) {
        if (this.segmentinformationidentifier.isEmpty()) {
            this.segmentinformationidentifier = entrada;
        } else {
            this.segmentinformationidentifier += ";;" + entrada;
        }
    }

    public void setSegmentinformationkeyword(String entrada) {
        if (this.segmentinformationkeyword.isEmpty()) {
            this.segmentinformationkeyword = entrada;
        } else {
            this.segmentinformationkeyword += ";;" + entrada;
        }
    }

    public void setSegmentinformationtitle(String entrada) {
        if (this.segmentinformationtitle.isEmpty()) {
            this.segmentinformationtitle = entrada;
        } else {
            this.segmentinformationtitle += ";;" + entrada;
        }
    }

    public void setSegmentmediatype(String entrada) {
        if (this.segmentmediatype.isEmpty()) {
            this.segmentmediatype = entrada;
        } else {
            this.segmentmediatype += ";;" + entrada;
        }
    }

    public void setSegmentsidentifier(String entrada) {
        if (this.segmentsidentifier.isEmpty()) {
            this.segmentsidentifier = entrada;
        } else {
            this.segmentsidentifier += ";;" + entrada;
        }
    }

    public void setSemanticdensity(String entrada) {
        if (this.semanticdensity.isEmpty()) {
            this.semanticdensity = entrada;
        } else {
            this.semanticdensity += ";;" + entrada;
        }
    }

    public void setServicelanguage(String entrada) {
        if (this.servicelanguage.isEmpty()) {
            this.servicelanguage = entrada;
        } else {
            this.servicelanguage += ";;" + entrada;
        }
    }

    public void setServicelocation(String entrada) {
        if (this.servicelocation.isEmpty()) {
            this.servicelocation = entrada;
        } else {
            this.servicelocation += ";;" + entrada;
        }
    }

    public void setServicename(String entrada) {
        if (this.servicename.isEmpty()) {
            this.servicename = entrada;
        } else {
            this.servicename += ";;" + entrada;
        }
    }

    public void setServicetype(String entrada) {
        if (this.servicetype.isEmpty()) {
            this.servicetype = entrada;
        } else {
            this.servicetype += ";;" + entrada;
        }
    }

    public void setSignlanguage(String entrada) {
        if (this.signlanguage.isEmpty()) {
            this.signlanguage = entrada;
        } else {
            this.signlanguage += ";;" + entrada;
        }
    }

    public void setSize(String entrada) {
        if (this.size.isEmpty()) {
            this.size = entrada;
        } else {
            this.size += ";;" + entrada;
        }
    }

    public void setSpecificformat(String entrada) {
        if (this.specificformat.isEmpty()) {
            this.specificformat = entrada;
        } else {
            this.specificformat += ";;" + entrada;
        }
    }

    public void setSpecificinstallationremarks(String entrada) {
        if (this.specificinstallationremarks.isEmpty()) {
            this.specificinstallationremarks = entrada;
        } else {
            this.specificinstallationremarks += ";;" + entrada;
        }
    }

    public void setSpecificlocation(String entrada) {
        if (this.specificlocation.isEmpty()) {
            this.specificlocation = entrada;
        } else {
            this.specificlocation += ";;" + entrada;
        }
    }

    public void setSpecificmaximumversion(String entrada) {
        if (this.specificmaximumversion.isEmpty()) {
            this.specificmaximumversion = entrada;
        } else {
            this.specificmaximumversion += ";;" + entrada;
        }
    }

    public void setSpecificminimumversion(String entrada) {
        if (this.specificminimumversion.isEmpty()) {
            this.specificminimumversion = entrada;
        } else {
            this.specificminimumversion += ";;" + entrada;
        }
    }

    public void setSpecificname(String entrada) {
        if (this.specificname.isEmpty()) {
            this.specificname = entrada;
        } else {
            this.specificname += ";;" + entrada;
        }
    }

    public void setSpecificotherplatformrequirements(String entrada) {
        if (this.specificotherplatformrequirements.isEmpty()) {
            this.specificotherplatformrequirements = entrada;
        } else {
            this.specificotherplatformrequirements += ";;" + entrada;
        }
    }

    public void setSpecificsize(String entrada) {
        if (this.specificsize.isEmpty()) {
            this.specificsize = entrada;
        } else {
            this.specificsize += ";;" + entrada;
        }
    }

    public void setSpecifictype(String entrada) {
        if (this.specifictype.isEmpty()) {
            this.specifictype = entrada;
        } else {
            this.specifictype += ";;" + entrada;
        }
    }

    public void setStart(String entrada) {
        if (this.start.isEmpty()) {
            this.start = entrada;
        } else {
            this.start += ";;" + entrada;
        }
    }

    public void setStatus(String entrada) {
        if (this.status.isEmpty()) {
            this.status = entrada;
        } else {
            this.status += ";;" + entrada;
        }
    }

    public void setStructure(String entrada) {
        if (this.structure.isEmpty()) {
            this.structure = entrada;
        } else {
            this.structure += ";;" + entrada;
        }
    }

    public void setSupportedplatforms(String entrada) {
        if (this.supportedplatforms.isEmpty()) {
            this.supportedplatforms = entrada;
        } else {
            this.supportedplatforms += ";;" + entrada;
        }
    }

    public void setSynchronism(String entrada) {
        if (this.synchronism.isEmpty()) {
            this.synchronism = entrada;
        } else {
            this.synchronism += ";;" + entrada;
        }
    }

    public void setTaxonentry(String entrada) {
        if (this.taxonentry.isEmpty()) {
            this.taxonentry = entrada;
        } else {
            this.taxonentry += ";;" + entrada;
        }
    }

    public void setTaxonid(String entrada) {
        if (this.taxonid.isEmpty()) {
            this.taxonid = entrada;
        } else {
            this.taxonid += ";;" + entrada;
        }
    }

    public void setTaxonpathdescription(String entrada) {
        if (this.taxonpathdescription.isEmpty()) {
            this.taxonpathdescription = entrada;
        } else {
            this.taxonpathdescription += ";;" + entrada;
        }
    }

    public void setTaxonpathkeyword(String entrada) {
        if (this.taxonpathkeyword.isEmpty()) {
            this.taxonpathkeyword = entrada;
        } else {
            this.taxonpathkeyword += ";;" + entrada;
        }
    }

    public void setTypicalagerange(String entrada) {
        if (this.typicalagerange.isEmpty()) {
            this.typicalagerange = entrada;
        } else {
            this.typicalagerange += ";;" + entrada;
        }
    }

    public void setTypicallearningtime(String entrada) {
        if (this.typicallearningtime.isEmpty()) {
            this.typicallearningtime = entrada;
        } else {
            this.typicallearningtime += ";;" + entrada;
        }
    }

    public void setVersion(String entrada) {
        if (this.version.isEmpty()) {
            this.version = entrada;
        } else {
            this.version += ";;" + entrada;
        }
    }

}
