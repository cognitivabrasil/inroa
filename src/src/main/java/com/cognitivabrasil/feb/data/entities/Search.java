package com.cognitivabrasil.feb.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Entity
@Table(name = "searches")
public class Search {

    private Integer id;
    private String text;
    private Integer c;
    
    private DateTime created;

    public Search() {
    }

    
    public Search(String string, Integer times) {
        this.text = string;
        this.c = times;
    }
    
    public Search(String string, DateTime created) {
        this.text = string;
        this.created = created;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }    
    
    /**
     * getter e setter para o Jdbc mapper setar o count, o nome da coluna não pode ser 'count', então deixei 'c'.
     * 
     * Use {@link #getCount()}
     * @return quantidade de vezes que esta consulta foi executada
     */
    @Transient
    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }
    
    @Transient
    public Integer getCount() {
        return c;
    }

    public void setCount(Integer times) {
        this.c = times;
    }
}