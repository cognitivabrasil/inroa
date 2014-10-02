/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.core.style.ToStringCreator;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Entity
@Table(name = "dados_subfederacoes")
public class SubFederacao extends UpdateData {

    private static final long serialVersionUID = 7452479917517752879L;
    private Integer id;
    private String version;
    private Set<RepositorioSubFed> repositorios;

    public SubFederacao() {
        super();
        this.repositorios = new HashSet<>();
        this.version = "";
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DADOS_SUBFEDERACOES_ID_SEQ")
    @SequenceGenerator(name="DADOS_SUBFEDERACOES_ID_SEQ", sequenceName="DADOS_SUBFEDERACOES_ID_SEQ")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Pega a url da federação através do método {@link #getUrl()} e dependendo da versão da federação concatena a
     * chamada para o protocolo OAI-PMH.
     *
     * @return Retorna a url que responde ao protocolo OAI-PMH.
     */
    @Transient
    public String getUrlOAIPMH() {
        String oai = getUrl();
        //se a url não terminar com barra concatena a barra
        if (oai!=null && !oai.endsWith("/")) {
            oai += "/";
        }
        if (version!=null && version.equals("2.1")) {
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

    /**
     * Updates the repository with the same with the data in r2 safely, ignoring null and blank values
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

    /**
     * Atualiza a lista de repositórios desta federação, atravéz do conjunto de repositórios recebido como parâmetro.
     *
     * @param listXml ArrayList de Strings contendo o nome dos reposit&oacute;rios da subfedera&ccedil;&atilde;o.
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
        for (RepositorioSubFed rep : listBase) {
            //se tiver na base algum repositorio que nao esteja na lista, remove.
            if (!listXml.contains(rep.getName())) {
                delete.add(rep);
            }
        }
        for (RepositorioSubFed rep : delete) {
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("name", this.getName())
                .append("descrição", this.getDescricao())
                .append("url", this.getUrl())
                .append("última atualização", this.getUltimaAtualizacao()).toString();
    }
}