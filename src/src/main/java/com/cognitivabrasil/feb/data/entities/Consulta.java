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
    private String ageRange;
    private Boolean adultAge;
    private List<String> difficulty;
    public Boolean cost;
    public Boolean hasVisual;
    public Boolean hasAuditory;
    public Boolean hasText;
    public Boolean hasTactile;
    public String size;
    private final Map<String, String> languages;
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

    }

    public boolean isEmpty() {
        return isBlank(consulta) && isBlank(autor) && isBlank(idioma) && isBlankList(format) && isBlank(ageRange)
                && adultAge == null && isBlankList(difficulty) && isBlank(size) && cost == null && hasVisual == null
                && hasAuditory == null && hasText == null && hasTactile == null;
    }

    private boolean isBlankList(List<String> format2) {
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

    public String getAgeRange() {
        if (isAdultAge() != null && isAdultAge()) {
            return "19 - 100";
        }
        if (ageRange != null) {
            return ageRange.replaceAll(" ", "").replaceFirst(":|,", " - ");
        }
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public int getStartAgeRange() {
        String[] start = getAgeRange().split(" - ");
        return Integer.parseInt(start[0]);
    }

    public int getEndAgeRange() {
        String[] start = getAgeRange().split(" - ");
        return Integer.parseInt(start[1]);
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

    public Boolean getCost() {
        return cost;
    }

    public void setCost(Boolean cost) {
        this.cost = cost;
    }

    public Boolean getHasVisual() {
        return hasVisual;
    }

    public void setHasVisual(Boolean hasVisual) {
        this.hasVisual = hasVisual;
    }

    public Boolean getHasAuditory() {
        return hasAuditory;
    }

    public void setHasAuditory(Boolean hasAuditory) {
        this.hasAuditory = hasAuditory;
    }

    public Boolean getHasText() {
        return hasText;
    }

    public void setHasText(Boolean hasText) {
        this.hasText = hasText;
    }

    public Boolean getHasTactile() {
        return hasTactile;
    }

    public void setHasTactile(Boolean hasTactile) {
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
            if (isNotBlank(ageRange)) {
                encoded += "&ageRange=" + URLEncoder.encode(ageRange, "UTF-8");
            }
            if (adultAge != null) {
                encoded += "&adultAge=" + URLEncoder.encode(adultAge.toString(), "UTF-8");
            }
            if (!isBlankList(difficulty)) {
                for (String d : difficulty) {
                    encoded += "&difficulty=" + URLEncoder.encode(d, "UTF-8");
                }
            }
            if (isNotBlank(size)) {
                encoded += "&size=" + URLEncoder.encode(size, "UTF-8");
            }
            if (cost != null) {
                encoded += "&cost=" + URLEncoder.encode(cost.toString(), "UTF-8");
            }
            if (hasVisual != null) {
                encoded += "&hasVisual=" + URLEncoder.encode(hasVisual.toString(), "UTF-8");
            }
            if (hasAuditory != null) {
                encoded += "&hasAuditory=" + URLEncoder.encode(hasAuditory.toString(), "UTF-8");
            }
            if (hasText != null) {
                encoded += "&hasText=" + URLEncoder.encode(hasText.toString(), "UTF-8");
            }
            if (hasTactile != null) {
                encoded += "&hasTactile=" + URLEncoder.encode(hasTactile.toString(), "UTF-8");
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

    public void addFacetFilter(String fieldName, Object value) {
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
        }
        
    }

    public void removeFacetFilter(String fieldName, Object value) {
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
        }
    }

    public boolean isActive(String fieldName, Object value) {
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

}
