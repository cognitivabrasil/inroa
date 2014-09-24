package com.cognitivabrasil.feb.data.entities;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.core.style.ToStringCreator;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "repositorios_subfed")
public class RepositorioSubFed{

    private int id;
    private String name;
    private SubFederacao subFederacao;
    private Set<Document> documentos;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "nome")
    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    @ManyToOne
    @JoinColumn(name = "id_subfed")
    public SubFederacao getSubFederacao() {
        return subFederacao;
    }

    public void setSubFederacao(SubFederacao subFederacao) {
        this.subFederacao = subFederacao;
    }

    /**
     * @return the documentos
     */
    
    @OneToMany(mappedBy = "repositorioSubFed", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Document> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(Set<Document> documentos) {
        this.documentos = documentos;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RepositorioSubFed other = (RepositorioSubFed) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
        @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", this.getId()).append("nome", this.getName()).toString();
    }    
}