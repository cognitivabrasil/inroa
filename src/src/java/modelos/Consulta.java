/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.HashSet;
import java.util.Set;

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
    

    public Consulta() {
        consulta = "";
        autor = "";
        repositorios = new HashSet<Integer>();
        federacoes = new HashSet<Integer>();
        repSubfed = new HashSet<Integer>();
        rss = false;
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
    
    public boolean hasAuthor(){
        if (autor.equals("")) return false;
        else
            return true;
    }

    public boolean isRss() {
        return rss;
    }

    public void setRss(boolean rss) {
        this.rss = rss;
    }
     
    
    
}