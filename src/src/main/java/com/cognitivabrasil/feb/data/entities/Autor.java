package com.cognitivabrasil.feb.data.entities;

/**
 *
 * @author Luiz Rossi
 */
public class Autor {

    private int id;
    private String nome;
    private Document doc;

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {

        if (nome.length() >= 1024) {
            nome = nome.substring(0, 1000);
        }
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
