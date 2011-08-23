package OBAA.General;

import java.util.AbstractCollection;
import java.util.Collection;
import sun.text.resources.CollationData;


/**
 * <div class="en">
 * This category groups the general information
 * that describes this learning object as a whole.
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 *</div>
 *
 * <div class="br">
 * Conjunto de metadados gerais.
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 *
 * @author LuizRossi
 */
public class General {
    private Collection <Identifier> identifiers;
    private Collection <Title> titles;
    private Collection <Language> languages;
    private Collection <Description> descriptions;
    private Collection <Keyword> keywords;
    private Collection <Coverage> coverages;
    private Structure structure;
    private AggregationLevel aggregationLevel;

    public General() {

        identifiers.clear();
        structure = new Structure();
        aggregationLevel = new AggregationLevel();
    }


    //Identifiers
    public void setIdentifiers(Collection<Identifier> identifiers) {
        this.identifiers = identifiers;
    }
    public Collection<Identifier> getIdentifiers() {
        return identifiers;
    }
    public void addIdentifier (String catalog, String entry){
        Identifier newIdentifier = new Identifier(catalog,entry);
        this.identifiers.add(newIdentifier);

    }
    
    //Titles
    public void setTitles(Collection<Title> titles) {
        this.titles = titles;
    }
    public Collection<Title> getTitles() {
        return titles;
    }
    public void addTitle(String title) {
        titles.add(new Title(title));
    }

    //Language
    public void setLanguages(Collection<Language> languages) {
        this.languages = languages;
    }
    public Collection<Language> getLanguages() {
        return languages;
    }
    public void addLanguage (String language) {
        this.languages.add(new Language(language));
    }

    //Descriptions
    public void setDescriptions(Collection<Description> descriptions) {
        this.descriptions = descriptions;
    }
    public Collection<Description> getDescriptions() {
        return descriptions;
    }
    public void addDescription (String description) {
        this.descriptions.add(new Description(description));
    }

    //Keywords
    public void setKeywords(Collection<Keyword> keywords) {
        this.keywords = keywords;
    }
    public Collection<Keyword> getKeywords() {
        return keywords;
    }
    public void addKeyword (String keyword){
        this.keywords.add(new Keyword(keyword));
    }

    //Coverage
    public void setCoverages(Collection<Coverage> coverage) {
        this.coverages = coverage;
    }
    public Collection<Coverage> getCoverages() {
        return coverages;
    }
    public void addCoverage (String coverage){
        this.coverages.add(new Coverage(coverage));
    }

    //Structure
    public void setStructure(String structure) throws IllegalArgumentException {
        this.structure.setStructure(structure);
    }
    public Structure getStructure() {
        return structure;
    }

    //Aggragation Level
    public void setAggregationLevel(int aggregationLevel) {
        this.aggregationLevel.setAggregationLevel(aggregationLevel);
    }

    public AggregationLevel getAggregationLevel() {
        return aggregationLevel;
    }

}
