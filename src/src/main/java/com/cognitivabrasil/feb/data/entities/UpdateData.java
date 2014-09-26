/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.entities;

import com.cognitivabrasil.feb.data.interfaces.FebDomainObject;
import com.cognitivabrasil.feb.util.Operacoes;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Método que possui as caracteriscas em comum para atualização do OAI-PMH utilizados em {@link Repositorio} e {@link SubFederacao}.
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@MappedSuperclass
public class UpdateData implements java.io.Serializable, FebDomainObject {

    
    private String name;
    private String descricao;
    private String url;
    @DateTimeFormat(style = "M-")
    private DateTime ultimaAtualizacao;
    private String dataXml;
    private String dataXmlTemp;

    public UpdateData() {
        name = "";
        descricao = "";
        url = "";
    }


    /**
     * @return the name
     */
    @Override
    @Column(name = "nome")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the url
     */
    @Column
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Define a data que foi realizada a última atualização. Neste método é copiada a data de {@link #dataXmlTemp}
     * para {@link #dataXml}.
     *
     * @param ultimaAtualizacao
     */
    public void setUltimaAtualizacao(DateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
        if (getDataXmlTemp() != null) {
            setDataXml(getDataXmlTemp());
        }
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "data_ultima_atualizacao")
    public DateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    /**
     * Retorna a data da &uacute;ltima atualizaç&atilde;o formatada. Se o reposit&oacute;rio n&atilde;o tiver uma url
     * associada ele informa que n&atilde;o foi informado um endere&ccedil;o para sincroniza&ccedil;&atilde;o.
     *
     * @return String contendo a data neste formato: Dia "x" &agrave;s "y"
     */
    @Transient
    public String getUltimaAtualizacaoFormatada() {
        return Operacoes.ultimaAtualizacaoFrase(getUltimaAtualizacao(),
                getUrl());
    }

    @Column(name = "data_xml")
    public String getDataXml() {
        return dataXml;
    }

    public void setDataXml(String dataOrigem) {
        this.dataXml = dataOrigem;
        this.dataXmlTemp = null;
    }

    @Transient
    public String getDataXmlTemp() {
        return dataXmlTemp;
    }

    public void setDataXmlTemp(String dataOrigemTemp) {
        this.dataXmlTemp = dataOrigemTemp;
    }

    @Transient
    public DateTime getProximaAtualizacao() {
        if (this.ultimaAtualizacao == null) {
            return null;
        } else {
            return getUltimaAtualizacao().plusDays(1);
        }
    }

    /**
     * Test if federation is outdated. Utilizado na interface gráfica para exibir alerta se a federação está
     * desatualizada.
     *
     * @return true if it is outdated or false if it is updated.
     */
    @Transient
    public boolean getIsOutdated() {
        return getProximaAtualizacao() == null || getProximaAtualizacao().isBeforeNow();
    }

    @Transient
    public String getProximaAtualizacaoFormatada() {
        return Operacoes.ultimaAtualizacaoFrase(getProximaAtualizacao(), getUrl());
    }


    @Override
    public String toStringDetailed() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
    }
    
    protected boolean notBlank(String s) {
        return s != null && !(s.equals(""));
    }
    
    protected boolean notBlank(Set s) {
        return s != null && !(s.isEmpty());
    }
}