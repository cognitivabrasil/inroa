/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.style.ToStringCreator;

import feb.util.Operacoes;

/**
 *
 * @author Marcos Nunes
 */
public class SubFederacao implements java.io.Serializable, SubNodo {

    private static final long serialVersionUID = 7452479917517752879L;
    private Integer id;
    private String name;
    private String descricao;
    private String url;
    private Date ultimaAtualizacao;
    private String dataXML;
    private String version;
    private String dataXMLTemp;
    private Set<RepositorioSubFed> repositorios;

    public SubFederacao() {
        this.id = null;
        this.name = "";
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
        this.dataXMLTemp = null;
    }

    public String getDataXMLTemp() {
        return dataXMLTemp;
    }

    public void setDataXMLTemp(String dataXMLTemp) {
        this.dataXMLTemp = dataXMLTemp;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param nome
     * @deprecated use {@link getName()}
     */
    @Deprecated
    public String getNome() {
        return name;
    }

    /**
     * @param nome
     * @deprecated use {@link setName()}
     */
    @Deprecated
    public void setNome(String nome) {
        this.name = nome;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    /**
     * Retorna a data da &uacute;ltima atualizaç&atilde;o formatada. Se a
     * federa&ccedil;&atilde;o n&atilde;o tiver uma url associada ele informa
     * que n&atilde;o foi informado um endere&ccedil;o para
     * sincroniza&ccedil;&atilde;o.
     *
     * @return String contendo a data neste formato: Dia "x" &agrave;s "y"
     */
    public String getUltimaAtualizacaoFormatada() {
        return Operacoes.ultimaAtualizacaoFrase(getUltimaAtualizacao(), getUrl());
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
        if (dataXMLTemp != null) {
            this.dataXML = this.dataXMLTemp;
        }
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
        if (this.ultimaAtualizacao == null) {
            return null;
        } else {
            return new Date(this.ultimaAtualizacao.getTime() + 24 * 60 * 60 * 1000); // soma a periodicidade em dias
        }
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
        if (notBlank(r2.getName())) {
            setNome(r2.getName());
        }
        if (notBlank(r2.getUrl())) {
            setUrl(r2.getUrl());
        }


    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", this.getId()).append("name", this.getName()).append("descrição", this.getDescricao()).append("url", this.getUrl()).append("última atualização", this.getUltimaAtualizacao()).toString();
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

    @Override
    public Integer getSize() {
        return ((Integer) getSizeDoc());
    }

    /**
     * Test if federation is outdated.
     *
     * @return true if it is outdated or false if it is updated.
     */
    public boolean getIsOutdated() {
        if (getProximaAtualizacao() == null || getProximaAtualizacao().before(new Date())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * M&eacute;todo que atualiza a base de dados local com os
     * reposit&oacute;rios da subfedera&ccedil;&atilde;o
     *
     * @param subFed objeto federa&ccedil;&atilde;o
     * @param listaSubRep ArrayList de Strings contento o name dos
     * reposit&oacute;rios da subfedera&ccedil;&atilde;o
     * @throws Exception
     */
    public void atualizaListaSubRepositorios(Set<String> listaSubRep) {

        Set<RepositorioSubFed> repSubFed = this.getRepositorios();


        for (String nomeSubRep : listaSubRep) {
            RepositorioSubFed repTest = new RepositorioSubFed();
            repTest.setSubFederacao(this);
            repTest.setName(nomeSubRep);

            if (!repSubFed.contains(repTest)) { //se nao tiver na base o repositorio, adiciona.
                repSubFed.add(repTest);
            }
        }

        Set<RepositorioSubFed> newListRepositories = new HashSet<RepositorioSubFed>();
        for (RepositorioSubFed repTest : repSubFed) {
            if (listaSubRep.contains(repTest.getName())) { //se tiver na base algum repositorio que nao esteja na lista, remove.
                newListRepositories.add(repTest);
            }
        }
        this.setRepositorios(newListRepositories); //armazena o Set modificado
    }

    public RepositorioSubFed getRepositoryByName(String nome) {
        for (RepositorioSubFed repSub : getRepositorios()) {
            if (repSub.getName().equals(nome)) {
                return repSub;
            }
        }
        return null;
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}