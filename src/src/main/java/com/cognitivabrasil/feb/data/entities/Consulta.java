package com.cognitivabrasil.feb.data.entities;

import com.cognitivabrasil.feb.util.Informacoes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Paulo Schreiner
 */
public class Consulta {

    // TODO: deve ter transferida para spring.dtos
    private String consulta;
    private Set<Integer> repositorios;
    private Set<Integer> federacoes;
    private Set<Integer> repSubfed;
    private String autor;
    private boolean rss;
    private int limit;
    private int offset;
    private int sizeResult;
    private String idioma;
    private List<String> format;
    private Boolean adultAge;
    private List<String> difficulty;
    public List<Boolean> cost;
    public List<Boolean> hasVisual;
    public List<Boolean> hasAuditory;
    public List<Boolean> hasText;
    public List<Boolean> hasTactile;
    public String size;
    private final Map<String, String> languages;
    private List<Integer> ageRangeInt;
    private static final Logger log = LoggerFactory.getLogger(Consulta.class);

    public Consulta() {
        rss = false;
        limit = 10;
        offset = 0;
        sizeResult = 0;

        languages = new HashMap<>();
        languages.put("", "Todos");
        languages.put("pt", "Português");
        languages.put("en", "Inglês");
        languages.put("es", "Espanhol");
        languages.put("fr", "Francês");
    }

    /**
     * Cria nova consulta com base em anterior.
     * 
     * @param consulta Consulta a ser duplicada
     */
    public Consulta(Consulta consulta) {
        rss = false;
        limit = 10;
        offset = 0;
        sizeResult = 0;

        languages = new HashMap<>();
        languages.put("", "Todos");
        languages.put("pt", "Português");
        languages.put("en", "Inglês");
        languages.put("es", "Espanhol");
        languages.put("fr", "Francês");

        setConsulta(consulta.getConsulta());
        setAutor(consulta.getAutor());
        setFederacoes(new HashSet<>(consulta.getFederacoes()));
        setRepSubfed(new HashSet<>(consulta.getRepSubfed()));
        setRepositorios(new HashSet<>(consulta.getRepositorios()));
        setIdioma(consulta.getIdioma());

        if (consulta.getFormat() != null) {
            setFormat(new ArrayList<>(consulta.getFormat()));
        }
        if (consulta.getDifficulty() != null) {
            setDifficulty(new ArrayList<>(consulta.getDifficulty()));
        }
        if (consulta.getCost() != null) {
            setCost(new ArrayList<>(consulta.getCost()));
        }
        if (consulta.getHasVisual() != null) {
            setHasVisual(new ArrayList<>(consulta.getHasVisual()));
        }
        if (consulta.getHasAuditory() != null) {
            setHasAuditory(new ArrayList<>(consulta.getHasAuditory()));
        }
        if (consulta.getHasText() != null) {
            setHasText(new ArrayList<>(consulta.getHasText()));
        }
        if (consulta.getHasTactile() != null) {
            setHasTactile(new ArrayList<>(consulta.getHasTactile()));
        }
        
        if (consulta.getAgeRangeInt() != null) {
            setAgeRangeInt(new ArrayList<>(consulta.getAgeRangeInt()));
        }
        

    }

    public boolean isEmpty() {
        return isBlank(consulta) && isBlank(autor) && isBlank(idioma) && isBlankList(format) && isBlankList(ageRangeInt)
                && adultAge == null && isBlankList(difficulty) && isBlank(size) && cost == null && hasVisual == null
                && hasAuditory == null && hasText == null && hasTactile == null;
    }

    private <T extends Object> boolean isBlankList(List<T> format2) {
        return format2 == null || format2.isEmpty();
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        try {
            byte[] bytes = consulta.getBytes();
            this.consulta = new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            log.error("Não foi possível codificar em utf-8 a string: " + consulta, e);
        }
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Set<Integer> getFederacoes() {
        if (federacoes == null) {
            federacoes = new HashSet<>();
        }
        return federacoes;
    }

    public void setFederacoes(Set<Integer> federacoes) {
        this.federacoes = federacoes;
    }

    public Set<Integer> getRepSubfed() {
        if (repSubfed == null) {
            repSubfed = new HashSet<>();
        }
        return repSubfed;
    }

    public void setRepSubfed(Set<Integer> repSubfed) {
        // o form envia valoes em branco e o spring seta como null na lista, ai tem que remover
        repSubfed.removeAll(Collections.singleton(null));
        this.repSubfed = repSubfed;
    }

    public Set<Integer> getRepositorios() {
        if (repositorios == null) {
            repositorios = new HashSet<>();
        }
        return repositorios;
    }

    public void setRepositorios(Set<Integer> repositorios) {
        repositorios.removeAll(Collections.singleton(null));
        this.repositorios = repositorios;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<String> getFormat() {
        return format;
    }

    public void setFormat(List<String> format) {
        this.format = format;
    }


    public Boolean isAdultAge() {
        return adultAge;
    }

    public void setAdultAge(Boolean adultAge) {
        this.adultAge = adultAge;
    }

    public List<String> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(List<String> difficult) {
        this.difficulty = difficult;
    }

    public List<Boolean> getCost() {
        return cost;
    }

    public void setCost(List<Boolean> cost) {
        this.cost = cost;
    }
    public void addCost(boolean b) {
    	if(cost == null) {
    		cost = new ArrayList<>();
    	}
    	cost.add(b);
    }

    public List<Boolean> getHasVisual() {
        return hasVisual;
    }

    public void setHasVisual(List<Boolean> hasVisual) {
        this.hasVisual = hasVisual;
    }
    public void addHasVisual(boolean b) {
    	if(hasVisual == null) {
    		hasVisual = new ArrayList<>();
    	}
    	hasVisual.add(b);
    }

    public List<Boolean> getHasAuditory() {
        return hasAuditory;
    }

    public void setHasAuditory(List<Boolean> hasAuditory) {
        this.hasAuditory = hasAuditory;
    }
    public void addHasAuditory(boolean b) {
    	if(hasAuditory == null) {
    		hasAuditory = new ArrayList<>();
    	}
    	hasAuditory.add(b);
    }

    public List<Boolean> getHasText() {
        return hasText;
    }

    public void setHasText(List<Boolean> hasText) {
        this.hasText = hasText;
    }
    
    public void addHasText(boolean b) {
    	if(hasText == null) {
    		hasText = new ArrayList<>();
    	}
    	hasText.add(b);
    }

    public List<Boolean> getHasTactile() {
        return hasTactile;
    }
    
    public void addHasTactile(boolean b) {
    	if(hasTactile == null) {
    		hasTactile = new ArrayList<>();
    	}
    	hasTactile.add(b);
    }

    public void setHasTactile(List<Boolean> hasTactile) {
        this.hasTactile = hasTactile;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean hasAuthor() {
        return !isBlank(autor);
    }

    public boolean isRss() {
        return rss;
    }

    public void setRss(boolean rss) {
        this.rss = rss;
    }

    /**
     * Retorna o valor de inicio para busca. Utilizado para pagina&ccedil;&atilde;o dos resultados
     *
     * @return inteiro com o valor inicial
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Valor inicial para busca. Utilizado para pagina&ccedil;&atilde;o dos resultados
     *
     * @param limit Valor inicial para busca, inicio = 5 informa que necessita dos resultados da consulta apartir do
     *            resultado 5.
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    /**
     * Seta a quantidade de resultados para a busca.
     *
     * @param offset Quantidade de resultados que necessita como resposta.
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Define o offset de acordo com a página exibida. O offset é a quantidade de resultados para a busca, então o seu
     * resultado é a multiplicação da página atual pelo limite de resultados exibidos na página.
     *
     * @param page
     */
    public void setOffsetByPage(int page) {
        if (page > 0) {
            this.offset = page * getLimit();
        }
    }

    public int getSizeResult() {
        return sizeResult;
    }

    public void setSizeResult(int sizeResult) {
        this.sizeResult = sizeResult;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public Map<String, String> getMimeTypes() {
        return Informacoes.getMimeType();
    }

    /**
     * @return the consulta params in an URL encoded form
     */
    public String getUrlEncoded() {
        log.debug("getUrlEncoded {}", this);
        
        try {
            String encoded = "consulta=" + URLEncoder.encode(consulta, "UTF-8");
            if (isNotBlank(autor)) {
                encoded += "&autor=" + URLEncoder.encode(autor, "UTF-8");
            }
            if (isNotBlank(idioma)) {
                encoded += "&idioma=" + URLEncoder.encode(idioma, "UTF-8");
            }
            if (!isBlankList(format)) {
                for (String f : format) {
                    encoded += "&format=" + URLEncoder.encode(f, "UTF-8");
                }
            }
            if (!isBlankList(difficulty)) {
                for (String d : difficulty) {
                    encoded += "&difficulty=" + URLEncoder.encode(d, "UTF-8");
                }
            }
            if (isNotBlank(size)) {
                encoded += "&size=" + URLEncoder.encode(size, "UTF-8");
            }
 
            if (!isBlankList(cost)) {
            	for(Boolean v : cost) {
            		encoded += "&cost=" + URLEncoder.encode(v.toString(), "UTF-8");
            	}
            }
            if (!isBlankList(hasVisual)) {
            	for(Boolean v : hasVisual) {
            		encoded += "&hasVisual=" + URLEncoder.encode(v.toString(), "UTF-8");
            	}
            }
            if (!isBlankList(hasAuditory)) {
            	for(Boolean v : hasAuditory) {
            		encoded += "&hasAuditory=" + URLEncoder.encode(v.toString(), "UTF-8");
            	}
            }
            if (!isBlankList(hasText)) {
            	for(Boolean v : hasText) {
            		encoded += "&hasText=" + URLEncoder.encode(v.toString(), "UTF-8");
            	}
            }
            if (!isBlankList(hasTactile)) {
            	for(Boolean v : hasTactile) {
            		encoded += "&hasTactile=" + URLEncoder.encode(v.toString(), "UTF-8");
            	}
            }
            if (!isBlankList(ageRangeInt)) {
                for(Integer v : ageRangeInt) {
                    encoded += "&ageRangeInt=" + URLEncoder.encode(v.toString(), "UTF-8");
                }
            }
            

            for (Integer i : getRepositorios()) {
                encoded += "&repositorios=" + URLEncoder.encode(i.toString(), "UTF-8");
            }
            for (Integer i : getFederacoes()) {
                encoded += "&federacoes=" + URLEncoder.encode(i.toString(), "UTF-8");
            }
            for (Integer i : getRepSubfed()) {
                encoded += "&repSubfed=" + URLEncoder.encode(i.toString(), "UTF-8");
            }

            return encoded;
        }
        catch (UnsupportedEncodingException e) {
            // UTF 8 is always supported
            throw new RuntimeException("FATAL", e);
        }
    }

    public void addFacetFilter(String fieldName, String value) {
        switch (fieldName) {
        case "format":
            addFormat((String) value);
            break;
        case "difficulty":
            if(difficulty == null) {
                difficulty = new ArrayList<>();
            }
            difficulty.add((String)value);
            break;
        case "hasvisual":
        	addHasVisual(Boolean.valueOf((String)value));

            break;
        case "hasauditory":
        	addHasAuditory(Boolean.valueOf((String)value));

            break;
        case "hastactile":
        	addHasTactile(Boolean.valueOf((String)value));
            break;
        case "hastext":
        	addHasText(Boolean.valueOf((String)value));

            break;
        case "cost":
        	addCost(Boolean.valueOf((String)value));

            break;
        case "typicalagerangeint":
            addAgeRangeInt(Integer.valueOf((String)value));

            break;
        }
        
        
    }

    public void removeFacetFilter(String fieldName, String value) {
        switch (fieldName) {
        case "format":
            if (getFormat() != null) {
                getFormat().remove(value);
            }
            break;
        case "difficulty":
            if (getDifficulty() != null) {
                getDifficulty().remove(value);
            }
            break;
        case "hasvisual":
            if (getHasVisual() != null) {
                getHasVisual().remove(Boolean.valueOf(value));
            }
        case "hasauditory":
            if (getHasAuditory() != null) {
            	getHasAuditory().remove(Boolean.valueOf(value));
            }
        case "hastactile":
            if (getHasTactile() != null) {
            	getHasTactile().remove(Boolean.valueOf(value));
            }
        case "hastext":
            if (getHasText() != null) {
            	getHasText().remove(Boolean.valueOf(value));
            }
        case "cost":
            if (getCost() != null) {
            	getCost().remove(Boolean.valueOf(value));
            }
            break;
        case "typicalagerangeint":
            if (getAgeRangeInt() != null) {
                getAgeRangeInt().remove(Integer.valueOf(value));
            }
            break;
        }
        
    }

    public boolean isActive(String fieldName, String value) {
        switch (fieldName) {
        case "format":
            if (getFormat() == null || (!getFormat().contains(value))) {
                return false;
            }
            else {
                return true;
            }
        case "difficulty":
            if (getDifficulty() == null || (!getDifficulty().contains(value))) {
                return false;
            }
            else {
                return true;
            }
        case "hasvisual":
        	return getHasVisual() != null && getHasVisual().contains(Boolean.valueOf(value));
        case "hasauditory":
        	return getHasAuditory() != null && getHasAuditory().contains(Boolean.valueOf(value));
        case "hastactile":
        	return getHasTactile() != null && getHasTactile().contains(Boolean.valueOf(value));
        case "hastext":
        	return getHasText() != null && getHasText().contains(Boolean.valueOf(value));
        case "cost":
        	return getCost() != null && getCost().contains(Boolean.valueOf(value));
        case "typicalagerangeint":
            return getAgeRangeInt() != null && getAgeRangeInt().contains(Integer.valueOf(value));
          
        }
        log.error("Não deveria chegar aqui, fieldName: {}, value: {}", fieldName, value);
        return false;
    }

    public void addFormat(String f) {
        if (format == null) {
            format = new ArrayList<String>();
        }
        format.add(f);
    }

    public List<Integer> getAgeRangeInt() {
        return ageRangeInt;
    }
    
    public void setAgeRangeInt(List<Integer> ar) {
        ageRangeInt = ar;
    }

    public void addAgeRangeInt(Integer i) {
        if (ageRangeInt == null) {
            ageRangeInt = new ArrayList<Integer>();
        }
        ageRangeInt.add(i);
    }
}
