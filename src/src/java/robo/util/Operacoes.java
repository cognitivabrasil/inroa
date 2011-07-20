/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Marcos
 */
public class Operacoes {

    /**
     *  Testa se a data recebida é anterior a 01/01/1000. Utilizado para testar se a base de dados deve ser sincronizada do zero ou não.
     * @param horaBase hora que será testada
     * @return true ou false. Se a data informada como parâmetro for menor retorna true se não false
     */
    public static boolean testarDataAnteriorMil(Date horaBase) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date dataTeste = null;
        try {
            dataTeste = format.parse("01/01/1000");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dataTeste.after(horaBase)) {
            return true;
        } else {
            return false;
        }

    }
}
