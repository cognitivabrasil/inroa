package feb.data.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class Consulta {

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
    private String format;
    private String age_range;
    private String difficult;
    public Boolean cost;
    public Boolean hasVisual;
    public Boolean hasAditory;
    public Boolean hasText;
    public String size;
    private final Map<String, String> languages;
    private static final Logger log = Logger.getLogger(Consulta.class);        

    public Consulta() {
        rss = false;
        limit = 5;
        offset = 0;
        sizeResult = 0;
        
        languages = new HashMap<String, String>();
        languages.put("pt", "Português");
        languages.put("en", "Inglês");
        languages.put("es", "Espanhol");
        languages.put("fr", "Francês");
    }

    public boolean isEmpty() {
        return isBlank(consulta) && isBlank(autor) && isBlank(idioma)
                && isBlank(format) && isBlank(age_range) && isBlank(difficult)
                && isBlank(size) && cost == null && hasVisual == null
                && hasAditory == null && hasText == null;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        try {
            byte[] bytes = consulta.getBytes();
            this.consulta = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
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
            federacoes = new HashSet<Integer>();
        }
        return federacoes;
    }

    public void setFederacoes(Set<Integer> federacoes) {
        this.federacoes = federacoes;
    }

    public Set<Integer> getRepSubfed() {
        if (repSubfed == null) {
            repSubfed = new HashSet<Integer>();
        }
        return repSubfed;
    }

    public void setRepSubfed(Set<Integer> repSubfed) {
        this.repSubfed = repSubfed;
    }

    public Set<Integer> getRepositorios() {
        if (repositorios == null) {
            repositorios = new HashSet<Integer>();
        }
        return repositorios;
    }

    public void setRepositorios(Set<Integer> repositorios) {
        this.repositorios = repositorios;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAge_range() {
        return age_range;
    }

    public void setAge_range(String age_range) {
        this.age_range = age_range;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
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

    public Boolean getHasAditory() {
        return hasAditory;
    }

    public void setHasAditory(Boolean hasAditory) {
        this.hasAditory = hasAditory;
    }

    public Boolean getHasText() {
        return hasText;
    }

    public void setHasText(Boolean hasText) {
        this.hasText = hasText;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean hasAuthor() {
        return !autor.equals("");
    }

    public boolean isRss() {
        return rss;
    }

    public void setRss(boolean rss) {
        this.rss = rss;
    }

    /**
     * Retorna o valor de inicio para busca. Utilizado para
     * pagina&ccedil;&atilde;o dos resultados
     *
     * @return inteiro com o valor inicial
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Valor inicial para busca. Utilizado para pagina&ccedil;&atilde;o dos
     * resultados
     *
     * @param limit Valor inicial para busca, inicio = 5 informa que necessita
     * dos resultados da consulta apartir do resultado 5.
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

    public int getSizeResult() {
        return sizeResult;
    }

    public void setSizeResult(int sizeResult) {
        this.sizeResult = sizeResult;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    /**
     * @return the consulta params in an URL encoded form
     */
    @Deprecated
    public String getUrlEncoded() {
        try {
            String encoded = "consulta=" + URLEncoder.encode(consulta, "UTF-8");
            if (isNotBlank(autor)) {
                encoded += "&autor=" + URLEncoder.encode(autor, "UTF-8");
            }

            for (Integer i : getRepositorios()) {
                encoded += "&repositorios=" + URLEncoder.encode(i.toString(), "UTF-8");
            }
            for (Integer i : getFederacoes()) {
                encoded += "&federacoes=" + URLEncoder.encode(i.toString(), "UTF-8");
            }
            for (Integer i : getRepositorios()) {
                encoded += "&repositorios=" + URLEncoder.encode(i.toString(), "UTF-8");
            }

            return encoded;
        } catch (UnsupportedEncodingException e) {
            // UTF 8 is always supported
            throw new RuntimeException("FATAL", e);
        }
    }
}
