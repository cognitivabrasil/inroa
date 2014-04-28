package com.cognitivabrasil.feb.data.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Luiz Henrique Longhi Rossi <lh.rossi@cognitivabrasil.com.br>
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br> * 
 */
@Entity
@Table(name = "visitas")
public class Visita {

    private int id;
    private Date horario;

    public Visita() {
        horario = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }
}