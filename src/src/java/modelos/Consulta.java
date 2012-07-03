/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import ferramentaBusca.indexador.StopWordTAD;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import robo.util.Operacoes;
import spring.ApplicationContextProvider;

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
    StopWordTAD stWd;
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
}
