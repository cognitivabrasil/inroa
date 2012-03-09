/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author Marcos
 */
public class RepositorioSubFed {
    
    private int id;
    private String nome;
    private SubFederacao subFederacao;

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

    public SubFederacao getSubFederacao() {
        return subFederacao;
    }

    public void setSubFederacao(SubFederacao subFederacao) {
        this.subFederacao = subFederacao;
    }
    
    //TODO: implementar
    public int size(){
        
        return 40;
    }
}
