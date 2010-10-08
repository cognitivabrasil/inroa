/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.importa_OAI;

import ferramentaBusca.indexador.Documento;

/**
 *
 * @author Marcos
 */
public class Indice {

    
    /**
     * Método que recebe o nome do atributo, o valor do atributo e adiciona-os no índice identificando o atributo. Se identificar como title efetua um setTitulo na classe Documento, etc.
     * @param atributo Nome do atributo fornecido.
     * @param valor Valor do atributo
     * @param doc Variavel do tipo Documento, para onde serão enviados os dados identificados.
     */
    public void setIndice(String atributo, String valor, Documento doc) {
        
        if(atributo.equalsIgnoreCase("title")){
            doc.setTitulo(valor);
        }
        else if(atributo.equalsIgnoreCase("description"))
        {
            doc.setDescricao(valor);
        }
        else if(atributo.equalsIgnoreCase("date"))
        {
            doc.setData(valor);
        }
        else if(atributo.equalsIgnoreCase("keyword"))
        {
            doc.setPalavrasChave(valor);
        }
        else if(atributo.equalsIgnoreCase("entity"))
        {
            doc.setEntidade(valor);
        }
        else if(atributo.equalsIgnoreCase("location"))
        {
            doc.setLocalizacao(valor);
        }
    }

}
