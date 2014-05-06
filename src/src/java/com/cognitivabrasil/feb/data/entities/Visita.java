package com.cognitivabrasil.feb.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Luiz Henrique Longhi Rossi <lh.rossi@cognitivabrasil.com.br>
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Entity
@Table(name = "visitas")
public class Visita {

    private int id;
    @DateTimeFormat(style = "M-")
    private DateTime horario;

    public Visita() {
        horario = DateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getHorario() {
        return horario;
    }

    public void setHorario(DateTime horario) {
        this.horario = horario;
    }
}