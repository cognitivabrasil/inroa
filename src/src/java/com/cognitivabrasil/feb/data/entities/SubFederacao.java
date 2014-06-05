/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.entities;

import com.cognitivabrasil.feb.data.interfaces.FebDomainObject;
import com.cognitivabrasil.feb.util.Operacoes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Entity
@Table(name = "dados_subfederacoes")
public class SubFederacao implements java.io.Serializable, FebDomainObject {

    private static final long serialVersionUID = 7452479917517752879L;
    private Integer id;
    private String name;
    private String descricao;
    private String url;
    @DateTimeFormat(style = "M-")
    private DateTime ultimaAtualizacao;
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
        this.repositorios = new HashSet<>();
        this.version = "";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "data_xml")
    public String getDataXML() {
        return dataXML;
    }

    public void setDataXML(String dataXML) {
        this.dataXML = dataXML;
        this.dataXMLTemp = null;
    }
    
    @Transient
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

    @Override
    @Column(name = "nome")
    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "data_ultima_atualizacao")
    public DateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Retorna a data da &uacute;ltima atualizaç&atilde;o formatada. Se a
     * federa&ccedil;&atilde;o n&atilde;o tiver uma url associada ele informa
     * que n&atilde;o foi informado um endere&ccedil;o para
     * sincroniza&ccedil;&atilde;o.
     *
     * @return String contendo a data neste formato: Dia "x" &agrave;s "y"
     */
    @Transient
    public String getUltimaAtualizacaoFormatada() {
        return Operacoes.ultimaAtualizacaoFrase(getUltimaAtualizacao(), getUrl());
    }

    public void setUltimaAtualizacao(DateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
        if (dataXMLTemp != null) {
            this.dataXML = this.dataXMLTemp;
        }
    }

    @Transient
    public String getUrlOAIPMH() {
        String oai = url;
        //se a url não terminar com barra concatena a barra
        if (!oai.endsWith("/")) {
            oai += "/";
        }
        if (version.equals("2.1")) {
            return oai + "OAIHandler";
        } else {
            return oai + "oai";
        }
    }

    @OneToMany(mappedBy = "subFederacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("name ASC")
    public Set<RepositorioSubFed> getRepositorios() {
        return repositorios;
    }

    public void setRepositorios(Set<RepositorioSubFed> repositorios) {
        this.repositorios = repositorios;
    }

    @Transient
    public DateTime getProximaAtualizacao() {
        if (this.ultimaAtualizacao == null) {
            return null;
        } else {
            return getUltimaAtualizacao().plusDays(1); 
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
     * Test if federation is outdated.
     *
     * @return true if it is outdated or false if it is updated.
     */
    @Transient
    public boolean getIsOutdated() {
        return getProximaAtualizacao() == null || getProximaAtualizacao().isBeforeNow();
    }

    /**
     * M&eacute;todo que atualiza a base de dados local com os
     * reposit&oacute;rios da subfedera&ccedil;&atilde;o
     *
     * @param subFed objeto federa&ccedil;&atilde;o
     * @param listXml ArrayList de Strings contendo o nome dos
     * reposit&oacute;rios da subfedera&ccedil;&atilde;o
     * @throws Exception
     */
    public void atualizaListaSubRepositorios(Set<String> listXml) {

        Set<RepositorioSubFed> listBase = this.getRepositorios();

        for (String nomeSubRep : listXml) {
            RepositorioSubFed repTest = new RepositorioSubFed();
            repTest.setSubFederacao(this);
            repTest.setName(nomeSubRep);

            //se nao tiver na base o repositorio, adiciona.
            if (!listBase.contains(repTest)) { 
                listBase.add(repTest);
            }
        }
        List<RepositorioSubFed> delete = new ArrayList<>();
        for(RepositorioSubFed rep : listBase){
            //se tiver na base algum repositorio que nao esteja na lista, remove.
            if(!listXml.contains(rep.getName())){
                delete.add(rep);
            }
        }
        for(RepositorioSubFed rep : delete){
            listBase.remove(rep);
        }
    }

    public RepositorioSubFed getRepositoryByName(String nome) {
        for (RepositorioSubFed repSub : getRepositorios()) {
            if (repSub.getName().equals(nome)) {
                return repSub;
            }
        }
        return null;
    }


    @Override
    public String toStringDetailed() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
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
}