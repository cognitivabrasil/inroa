/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

import java.util.ArrayList;

/**
 *
 * @author Marcos Nunes
 */
public class RepositoriosException extends Exception {

    private ArrayList<String> nome;

    /**
     * Creates a new instance of
     * <code>RepositoriosException</code> without detail message.
     */
    public RepositoriosException() {
        nome = new ArrayList<String>();        
    }

    /**
     * Constructs an instance of
     * <code>RepositoriosException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RepositoriosException(String msg) {
        super(msg);
    }

    public ArrayList<String> getNome() {
        return nome;
    }

    public void setNome(ArrayList<String> nome) {
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome.add(nome);
    }

    public String getMensagem() {
        String msg;
        if (nome.size() > 0) {
            msg = "Erro atualizar os repositorios: ";
            for (String n : nome) {
                msg += " " + n;
            }
        } else {
            msg = "Erro ao atualizar o repositorio " + nome.get(0);
        }
        return msg;
    }
}
