/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;


/**
 *
 * @author cei
 */
public class Autor {
    private int id;
    private String nome;
    private DocumentoReal doc;

    public DocumentoReal getDoc() {
        return doc;
    }

    public void setDoc(DocumentoReal doc) {
        this.doc = doc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    
}
