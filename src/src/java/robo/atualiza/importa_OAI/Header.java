package robo.atualiza.importa_OAI;

import java.util.ArrayList;


/**
 * Classe auxiliar que armazena informações contidas na tag Header do xml no padrão OAI-PMH
 * @author Marcos
 */
public class Header {

    private String identifier;
    private String datasatamp;
    private String setSpec;
    private ArrayList<String> status = new ArrayList<String>();
    private ArrayList<String> idDeletado = new ArrayList<String>();

    /**
     * Adiciona um valor ao ArrayList status
     * @param status String contendo o valor para adicionar ao Arraylist status
     */
    public void setStatus(String status) {
        this.status.add(status);
    }
    /**
     * Adiciona um valor ao ArrayList idDeletado
     * @param iddel String contendo o valor para adcionar ao ArrayList idDeletado
     */
    public void setidDeletado(String iddel) {
        this.idDeletado.add(iddel);
    }

    /**
     * Seta o valor para a variável identifier
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Retorna o valor contido na variável identifier
     * @return String contendo o valor da variável identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Retorna o ArrayList status
     * @return ArrayList de Strings status
     */
    public ArrayList<String> getStatus() {
        return status;
    }

    /**
     * Retorna o ArrayList idDeletado
     * @return ArrayList de Strings idDeletado
     */
    public ArrayList<String> getIdDeletado() {
        return idDeletado;
    }
    

    
}
