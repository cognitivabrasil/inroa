package feb.data.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;

import feb.data.interfaces.StopWordsDao;
import feb.spring.ApplicationContextProvider;
import feb.util.Operacoes;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


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
    static Logger log = Logger.getLogger(Consulta.class);

    public Consulta() {
        consulta = "";
        autor = "";
        repositorios = new HashSet<Integer>();
        federacoes = new HashSet<Integer>();
        repSubfed = new HashSet<Integer>();
        rss = false;
        limit = 5;
        offset = 0;
        sizeResult = 0;
        idioma = "pt-BR";
    }

    public List<String> getConsultaTokenizada() {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.fatal("Could not get AppContext bean!");
            throw new ApplicationContextException("Could not get AppContext bean!");
        } else {
            StopWordsDao stopWordDao = ctx.getBean(StopWordsDao.class);
            return Operacoes.tokeniza(this.consulta, stopWordDao.getStopWords(idioma));
        }
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

    public Set<Integer> getFederacoes() {
        return federacoes;
    }

    public void setFederacoes(Set<Integer> federacoes) {
        this.federacoes = federacoes;
    }

    public Set<Integer> getRepSubfed() {
        return repSubfed;
    }

    public void setRepSubfed(Set<Integer> repSubfed) {
        this.repSubfed = repSubfed;
    }

    public Set<Integer> getRepositorios() {
        return repositorios;
    }

    public void setRepositorios(Set<Integer> repositorios) {
        this.repositorios = repositorios;
    }

    public boolean hasAuthor() {
        if (autor.equals("")) {
            return false;
        } else {
            return true;
        }
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
    
    /**
     * @return the consulta params in an URL encoded form
     */
    public String getUrlEncoded() {
    	try {
			String encoded = "consulta=" + URLEncoder.encode(consulta,"UTF-8");
			if(isNotBlank(autor)) { encoded += "&autor=" + URLEncoder.encode(autor,"UTF-8"); }
			
			for(Integer i : repositorios) {
				encoded += "&repositorios=" + URLEncoder.encode(i.toString(),"UTF-8");
			}
			for(Integer i : federacoes) {
				encoded += "&federacoes=" + URLEncoder.encode(i.toString(),"UTF-8");
			}
			for(Integer i : repositorios) {
				encoded += "&repositorios=" + URLEncoder.encode(i.toString(),"UTF-8");
			}
			
			return encoded;
		} catch (UnsupportedEncodingException e) {
			// UTF 8 is always supported
			throw new RuntimeException("FATAL", e);
		}
    }
}
