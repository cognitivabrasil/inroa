/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Marcos Nunes
 */
public class SubFederacao implements java.io.Serializable {
    
    private int id;
    private String nome;
    private String descricao;
    private String url;
    private Date ultimaAtualizacao;
    private Date dataXML;
    private List<RepositorioSubFed> repositorios;

    public Date getDataXML() {
        return dataXML;
    }

    public void setDataXML(Date dataXML) {
        this.dataXML = dataXML;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return getNome();
    }

    public List<RepositorioSubFed> getRepositorios() {
        return repositorios;
    }

    public void setRepositorios(List<RepositorioSubFed> repositorios) {
        this.repositorios = repositorios;
    }
    
    
}
