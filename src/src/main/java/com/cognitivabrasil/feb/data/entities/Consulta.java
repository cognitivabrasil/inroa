package com.cognitivabrasil.feb.data.entities;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognitivabrasil.feb.ferramentaBusca.ResultadoBusca;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Paulo Schreiner
 */
public class Consulta {

    // TODO: deve ter transferida para spring.dtos
    private String consulta;
    private String autor;
    private boolean rss;
    private int limit;
    private int offset;
    private int sizeResult;
    private String idioma;
    private Boolean adultAge;
    private String size;

    
    private Map<String, List<? extends Object>> params; 
    private static final Map<String, Class> VALID_PARAMS; 
    
    static {
        VALID_PARAMS = new HashMap<>(); 
        VALID_PARAMS.put("hasVisual", Boolean.class);
        VALID_PARAMS.put("hasAuditory", Boolean.class);
        VALID_PARAMS.put("hasText", Boolean.class);
        VALID_PARAMS.put("hasTactile", Boolean.class);
        VALID_PARAMS.put("cost", Boolean.class);
        VALID_PARAMS.put("format", String.class);
        VALID_PARAMS.put("difficulty", String.class);
        VALID_PARAMS.put("ageRangeInt", Integer.class);
        VALID_PARAMS.put("federacoes", Integer.class);
        VALID_PARAMS.put("repSubfed", Integer.class);
        VALID_PARAMS.put("repositorios", Integer.class);
    }



    private static final Logger log = LoggerFactory.getLogger(Consulta.class);

    /**
     * Constrói uma nova consulta.
     */
    public Consulta() {
        params = new HashMap<>();
        
        rss = false;
        limit = 10;
        offset = 0;
        sizeResult = 0;

    }

    /**
     * Cria nova consulta com base em anterior.
     * 
     * @param consulta Consulta a ser duplicada
     */
    public Consulta(Consulta consulta) {
        params = new HashMap<>();

        rss = false;
        limit = 10;
        offset = 0;
        sizeResult = 0;


        setAutor(consulta.getAutor());
        setConsulta(consulta.getConsulta());

        setIdioma(consulta.getIdioma());
        
        for(String key : consulta.getParams().keySet()) {
            params.put(key, (List<? extends Object>) ((ArrayList)consulta.getParams().get(key)).clone());
            
        }
    }

    protected Map<String, List<? extends Object>>  getParams() {
        return params;
    }

    /**
     * @return verdadeiro se a consulta não tiver nem consulta nem autor (mesmo 
     * se ela tiver filtros).
     */
    public boolean isEmpty() {
        return isBlank(consulta) && isBlank(autor);
    }

    private <T extends Object> boolean isBlankList(List<T> format2) {
        return format2 == null || format2.isEmpty();
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<Integer> getFederacoes() {
        if (params.get("federacoes") == null) {
            setFederacoes(new ArrayList<>());
        }
        return (List<Integer>) params.get("federacoes");
    }

    public void setFederacoes(List<Integer> federacoes) {
        params.put("federacoes", federacoes);
    }

    public List<Integer> getRepSubfed() {
        if (params.get("repSubfed") == null) {
            setRepSubfed(new ArrayList<>());
        }
        return (List<Integer>) params.get("repSubfed");
    }

    public void setRepSubfed(List<Integer> repSubfed) {
        // o form envia valoes em branco e o spring seta como null na lista, ai tem que remover
        repSubfed.removeAll(Collections.singleton(null));
        params.put("repSubfed",repSubfed);
    }

    public List<Integer> getRepositorios() {
        if (params.get("repositorios") == null) {
            setRepositorios(new ArrayList<>());
        }
        return (List<Integer>) params.get("repositorios");
    }

    public void setRepositorios(List<Integer> repositorios) {
        repositorios.removeAll(Collections.singleton(null));

        params.put("repositorios", repositorios);
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<String> getFormat() {
        return (List<String>)params.get("format");
    }

    public void setFormat(List<String> format) {
        params.put("format", format);
    }


    public Boolean isAdultAge() {
        return adultAge;
    }

    public void setAdultAge(Boolean adultAge) {
        this.adultAge = adultAge;
    }

    public List<String> getDifficulty() {
        return (List<String>)params.get("difficulty");
    }

    public void setDifficulty(List<String> difficulty) {
        params.put("difficulty", difficulty);
    }

    public List<Boolean> getCost() {
        return (List<Boolean>)params.get("cost");
    }

    public void setCost(List<Boolean> cost) {
        params.put("cost", cost);
    }
    public void addCost(boolean b) {
    	if(getCost() == null) {
    		setCost(new ArrayList<>());
    	}
    	getCost().add(b);
    }

    public List<Boolean> getHasVisual() {
        return (List<Boolean>)params.get("hasVisual");
    }

    public void setHasVisual(List<Boolean> hasVisual) {
        params.put("hasVisual", hasVisual);
    }
    public void addHasVisual(boolean b) {
    	if(getHasVisual() == null) {
    		setHasVisual(new ArrayList<>());
    	}
    	getHasVisual().add(b);
    }

    public List<Boolean> getHasAuditory() {
        return (List<Boolean>)params.get("hasAuditory");
    }

    public void setHasAuditory(List<Boolean> hasAuditory) {
        params.put("hasAuditory", hasAuditory);
    }
    public void addHasAuditory(boolean b) {
    	if(getHasAuditory() == null) {
    		setHasAuditory(new ArrayList<>());
    	}
    	getHasAuditory().add(b);
    }

    public List<Boolean> getHasText() {
        return (List<Boolean>)params.get("hasText");
    }

    public void setHasText(List<Boolean> hasText) {
        params.put("hasText", hasText);
    }
    
    public void addHasText(boolean b) {
    	if(getHasText() == null) {
    		setHasText(new ArrayList<>());
    	}
    	getHasText().add(b);
    }

    public List<Boolean> getHasTactile() {
        return (List<Boolean>)params.get("hasTactile");
    }
    
    public void addHasTactile(boolean b) {
    	if(getHasTactile() == null) {
    		setHasTactile(new ArrayList<>());
    	}
    	getHasTactile().add(b);
    }

    public void setHasTactile(List<Boolean> hasTactile) {
        params.put("hasTactile", hasTactile);
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

    /**
     * @deprecated use {@link ResultadoBusca#getResultSize()} 
     */
    @Deprecated
    public int getSizeResult() {
        return sizeResult;
    }

    /**
     * @deprecated use {@link ResultadoBusca#setResultSize(int)}
     */
    @Deprecated
    public void setSizeResult(int sizeResult) {
        this.sizeResult = sizeResult;
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
            if (isNotBlank(size)) {
                encoded += "&size=" + URLEncoder.encode(size, "UTF-8");
            }
            
            for(String key : params.keySet()) {
                if (!isBlankList(params.get(key))) {
                    for(Object v : params.get(key)) {
                        encoded += "&" + key + "=" + URLEncoder.encode(v.toString(), "UTF-8");
                    }
                }
            }

            return encoded;
        }
        catch (UnsupportedEncodingException e) {
            // UTF 8 is always supported
            throw new RuntimeException("FATAL", e);
        }
    }
    
    /**
     * Sinônimo de {@link #addFacetFilter(String, Object)}
     * @see #addFacetFilter(String, Object)
     */
    public void add(String fieldName, Object value) {
        addFacetFilter(fieldName, value);
    }

    /**
     * Adiciona um filtro à consulta.
     * 
     * Valida os campos antes de adicionar, e converte o tipo do value quando necessário
     * para Integer ou Boolean.
     * 
     * @param fieldName nome do campo a ser adicionado
     * @param value valor a ser adicionado
     * @throws IllegalArgumentException quando o campo adicionado não é válido
     */
    public void addFacetFilter(String fieldName, Object value) throws IllegalArgumentException {
        if(VALID_PARAMS.get(fieldName) == null) {
            throw new IllegalArgumentException("Field " + fieldName + " is not valid for Consulta");
        }
        
           
        if(params.get(fieldName) == null) {
            params.put(fieldName , new ArrayList<>());
        }
        List<Object> l = (List<Object>) params.get(fieldName);
        
        if(VALID_PARAMS.get(fieldName).equals(Boolean.class)) {
            l.add(Boolean.valueOf(value.toString()));
        }
        else if(VALID_PARAMS.get(fieldName).equals(Integer.class)) {
            l.add(Integer.valueOf(value.toString()));
        }
        else {
            l.add(value);
        }
    }

    /**
     * Remove filtro para este campo e valor.
     * @param fieldName nome do campo
     * @param value valor do filtro
     */
    public void removeFacetFilter(String fieldName, String value) {
        if(params.get(fieldName) !=null) {
            List<Object> l = new ArrayList<>();
            for(Object o : params.get(fieldName)) {
                if(!o.toString().equals(value)) {
                    l.add(o);
                }
            }
            params.put(fieldName, l);
        }   
    }

    /**
     * Testa se o campo possui um filtro ativo nesta consulta.
     * 
     * @param fieldName nome do campo
     * @param value nome do valor
     * @return verdadeiro se esta consulta está filtrando neste campo pelo valor especificado
     */
    public boolean isActive(String fieldName, String value) {
        if(params.get(fieldName) == null) {
            return false;
        }
        else {
            return params.get(fieldName).stream().anyMatch(o -> o.toString().equals(value));
        }
    }

    public void addFormat(String f) {
        if (getFormat() == null) {
            setFormat(new ArrayList<String>());
        }
        getFormat().add(f);
    }

    public List<Integer> getAgeRangeInt() {
        return (List<Integer>)params.get("ageRangeInt");
    }
    
    public void setAgeRangeInt(List<Integer> ar) {
        params.put("ageRangeInt",ar);
    }

    public void addAgeRangeInt(Integer i) {
        add("ageRangeInt", i);
    }
}
