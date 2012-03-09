/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author Marcos Nunes
 */
public class PadraoMetadados implements java.io.Serializable {
    
    private int id;
    private String nome;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
}
