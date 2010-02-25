package robo.importa_OAI_DC;

/**
 * Classe auxiliar que armazena informações dos metadados do padrão dublin core.
 * @author Marcos
 */
public class OAI_DC {

    private String identifier = "";
    private String title = "";
    private String language = "";
    private String description = "";
    private String subject="";
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

    
    /**
     * Armazena na variável identifier o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param id id que será armazenado na variavel identifier.
     */
    public void setIdentifier(String id) {
        if(this.identifier.isEmpty())
              this.identifier=id;
          else
              this.identifier+=";;"+id;
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
        if(this.title.isEmpty())
              this.title=title;
          else
              this.title+=";;"+title;
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
        if(this.language.isEmpty())
              this.language=language;
          else
              this.language+=";;"+language;
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
        if(this.description.isEmpty())
            this.description=description;
        else
            this.description+=";;"+description;
    }

    /**
     * Armazena na variável subject o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param subject Valor que será armazenado na variavel subject.
     */
    public void setSubject(String subject) {
          if(this.subject.isEmpty())
              this.subject=subject;
          else
              this.subject+=";;"+subject;
    }

    /**
     * Armazena na variável coverage o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param coverage Valor que será armazenado na variavel coverage.
     */
    public void setCoverage(String coverage) {
        if(this.coverage.isEmpty())
              this.coverage=coverage;
          else
              this.coverage+=";;"+coverage;
    }

    /**
     * Armazena na variável type o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param type Valor que será armazenado na variavel type.
     */
    public void setType(String type) {
        if(this.type.isEmpty())
              this.type=type;
          else
              this.type+=";;"+type;
    }

    /**
     * Armazena na variável date o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param date Valor que será armazenado na variavel date.
     */
    public void setDate(String date) {
        if(this.date.isEmpty())
              this.date=date;
          else
              this.date+=";;"+date;
    }

    /**
     * Armazena na variável creator o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param creator Valor que será armazenado na variavel creator.
     */
    public void setCreator(String creator) {
        if(this.creator.isEmpty())
              this.creator=creator;
          else
              this.creator+=";;"+creator;
    }

    /**
     * Armazena na variável OtherContributor o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param OtherContributor Valor que será armazenado na variavel OtherContributor.
     */
    public void setOtherContributor(String OtherContributor) {
        if(this.OtherContributor.isEmpty())
              this.OtherContributor=OtherContributor;
          else
              this.OtherContributor+=";;"+OtherContributor;
    }

    /**
     * Armazena na variável publisher o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param publisher Valor que será armazenado na variavel publisher.
     */
    public void setPublisher(String publisher) {
        if(this.publisher.isEmpty())
              this.publisher=publisher;
          else
              this.publisher+=";;"+publisher;
    }

    /**
     * Armazena na variável format o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param format Valor que será armazenado na variavel format.
     */
    public void setFormat(String format) {
        if(this.format.isEmpty())
              this.format=format;
          else
              this.format+=";;"+format;
    }

    /**
     * Armazena na variável rigths o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param rigths Valor que será armazenado na variavel rigths.
     */
    public void setRigths(String rigths) {
        if(this.rigths.isEmpty())
              this.rigths=rigths;
          else
              this.rigths+=";;"+rigths;
    }

    /**
     * Armazena na variável relation o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param relation Valor que será armazenado na variavel relation.
     */
    public void setRelation(String relation) {
        if(this.relation.isEmpty())
              this.relation=relation;
          else
              this.relation+=";;"+relation;
    }

    /**
     * Armazena na variável source o valor recebido como parametro, se já existe algum valor armazenado, o novo é concatenado a ele utilizando ";;" como separador.
     * @param source Valor que será armazenado na variavel source.
     */
    public void setSource(String source) {
        if(this.source.isEmpty())
              this.source=source;
          else
              this.source+=";;"+source;
    }
    

}
