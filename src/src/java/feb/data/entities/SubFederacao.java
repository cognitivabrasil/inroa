/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.entities;

import feb.data.interfaces.FebDomainObject;
import feb.spring.ApplicationContextProvider;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.style.ToStringCreator;

import feb.util.Operacoes;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.support.DataAccessUtils;

/**
 *
 * @author Marcos Nunes
 */
public class SubFederacao implements java.io.Serializable, SubNodo, FebDomainObject {

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
    private transient SessionFactory sessionFactory;

    public SubFederacao() {
        this.id = null;
        this.name = "";
        this.descricao = "";
        this.url = "";
        this.ultimaAtualizacao = null;
        this.dataXML = null;
        this.repositorios = new HashSet<RepositorioSubFed>();
        this.version = "";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SubFederacao other = (SubFederacao) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
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

    public String getUrlOAIPMH() {
        String oai = url;
        if (!oai.endsWith("/")) { //se a url não terminar com barra concatena a barra
            oai += "/";
        }
        if (version.equals("2.1")) {
            return oai + "OAIHandler";
        } else {
            return oai + "oai";
        }
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
            setName(r2.getName());
        }
        if (notBlank(r2.getUrl())) {
            setUrl(r2.getUrl());
        }
        setVersion(r2.getVersion());

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

    @Override
    public Integer getVisits() {
        return DataAccessUtils.intResult(getSessionFactory().getCurrentSession().
                createQuery("SELECT COUNT(*) FROM DocumentosVisitas dv, DocumentoReal d WHERE d.id=dv.documento AND d.repositorioSubFed.subFederacao = :rep").setParameter("rep", this).list());
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
     * @param listaSubRep ArrayList de Strings contendo o nome dos
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

    private SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
            if (ctx != null) {
                //TODO:
                sessionFactory = ctx.getBean(SessionFactory.class);
            } else {
                throw new IllegalStateException("Could not get Application context");
            }
        }
        return sessionFactory;
    }

    @Override
    public String toStringDetailed() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
    }
}