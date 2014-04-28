package com.cognitivabrasil.feb.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Luiz H. L. Rossi <lh.rossi@cognitivabrasil.com.br>
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Entity
@Table(name = "documentos_visitas")
public class DocumentosVisitas {

    private int id;
    private DocumentoReal documento;
    private Visita visita;    
       
    public DocumentosVisitas() {
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "documento")
    public DocumentoReal getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoReal documento) {
        this.documento = documento;
    }

    @ManyToOne
    @JoinColumn(name = "visita")
    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }
}
