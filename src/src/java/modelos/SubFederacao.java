/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.style.ToStringCreator;

/**
 *
 * @author Marcos Nunes
 */
public class SubFederacao implements java.io.Serializable {

    private Integer id;
    private String nome;
    private String descricao;
    private String url;
    private Date ultimaAtualizacao;
    private String dataXML;
    private Set<RepositorioSubFed> repositorios;

    public SubFederacao() {
        this.nome = "";
        this.descricao = "";
        this.url = "";
        this.ultimaAtualizacao = null;
        this.dataXML = null;
        this.repositorios = new HashSet<RepositorioSubFed>();
    }

    public String getDataXML() {
        return dataXML;
    }

    public void setDataXML(String dataXML) {
        this.dataXML = dataXML;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
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

    public Set<RepositorioSubFed> getRepositorios() {
        return repositorios;
    }

    public void setRepositorios(Set<RepositorioSubFed> repositorios) {
        this.repositorios = repositorios;
    }

    public Date getProximaAtualizacao() {
        return new Date(this.ultimaAtualizacao.getTime() + 24 * 60 * 60 * 1000); // soma a periodicidade em horas
    }
    
    
    private boolean notBlank(String s) {
        return s != null && !(s.equals(""));
    }

    private boolean notBlank(Set s) {
        return s != null && !(s.isEmpty());
    }

    
    /**
     * Updates the repository with the same with the data in r2 safely, ignoring
     * null and blank values
     *
     * @param r2 A repository that we want to update.
     * @throws IllegalArgumentException If the ids dont match.
     */
    public void merge(SubFederacao r2) {

        if (r2.getId() != null && !(r2.getId().equals(getId()))) {
            throw new IllegalArgumentException("Merge must not be used on SubFederation with different Ids");
        }

        if (notBlank(r2.getDescricao())) {
            setDescricao(r2.getDescricao());
        }
        if (notBlank(r2.getNome())) {
            setNome(r2.getNome());
        }
        if (notBlank(r2.getUrl())) {
            setUrl(r2.getUrl());
        }


    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", this.getId()).append("nome", this.getNome()).append("descrição", this.getDescricao()).append("url", this.getUrl()).append("última atualização", this.getUltimaAtualizacao()).toString();
    }

    /**
     * Retorna o n&uacute;mero de documentos que a subfedera&ccedil;&atilde;o
     * possui.
     *
     * @return int com o n&uacute;mero de documentos
     */
    public int getSizeDoc() {
        int size = 0;

        for (RepositorioSubFed repSub : getRepositorios()) {
            size += repSub.getSize();
        }

        return size;
    }
}
