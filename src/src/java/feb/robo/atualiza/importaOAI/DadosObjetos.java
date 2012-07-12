/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package feb.robo.atualiza.importaOAI;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Marcos
 */
public class DadosObjetos {

    HashMap<String, ArrayList<String>> atributos = new HashMap<String, ArrayList<String>>();

    public void setAtributos(String atributo, String valor) {
        if(this.atributos.containsKey(atributo)){
            ArrayList<String> lista = this.atributos.get(atributo) ;
            lista.add(valor);
        }else{
            ArrayList<String> lista = new ArrayList<String>();
            lista.add(valor);
            this.atributos.put(atributo, lista);
        }
    }

    public HashMap<String, ArrayList<String>> getAtributos() {
        return this.atributos;
    }


}
