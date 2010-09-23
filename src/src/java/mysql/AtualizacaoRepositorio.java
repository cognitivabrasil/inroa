/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postgres;

import java.text.SimpleDateFormat;
import java.util.Date;
import robo.main.Robo;

/**
 *
 * @author Marcos
 */
public class AtualizacaoRepositorio {

    /**
     * Informa como uma frase a data e hora da &uacute;ltima atualiza&ccdil;&atilde;o, , se a data for inferior a 01/01/1000 retorna a mensagem: "Ainda n&atilde;o foi atualizado!"
     * @return Strign contendo data e hora da ultima atualiza&ccdil;&atilde;o, formatada como: "Dia dd/mm/aaa às hh:mm:ss"
     */
    public static String ultimaAtualizacaoFrase(Date data) {
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formathora = new SimpleDateFormat("HH:mm:ss");
        if (!Robo.testarDataAnteriorMil(data)) {
            return "Dia " + formatdata.format(data) + " &agrave;s " + formathora.format(data);
        } else {
            return "Ainda n&atilde;o foi atualizado!";
        }
    }

    /**
     * Informa como uma frase a data e hora da &uacute;ltima atualiza&ccdil;&atilde;o, se a data for inferior a 01/01/1000, um teste para verificar se foi informado um endereço para atualização é realizado. Se não existir endereço retorna um aviso, se existir retorna a mensagem: "Ainda n&atilde;o foi atualizado!"
     * @return Strign contendo data e hora da ultima atualiza&ccdil;&atilde;o, formatada como: "Dia dd/mm/aaa às hh:mm:ss"
     */
    public static String ultimaAtualizacaoFrase(Date data, String caminho) {
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formathora = new SimpleDateFormat("HH:mm:ss");
        if (!Robo.testarDataAnteriorMil(data)) {
            return "Dia " + formatdata.format(data) + " &agrave;s " + formathora.format(data);
        } else if(caminho.isEmpty()){
            return("N&atilde;o foi informado um endere&ccedil;o para sincroniza&ccedil;&atilde;o");
        }else {
            return "Ainda n&atilde;o foi atualizado!";
        }
    }


    /**
     * Informa a data e hora da &uacute;ltima atualiza&ccdil;&atilde;o, se a data for inferior a 01/01/1000 retorna a mensagem: "Ainda n&atilde;o foi atualizado!"
     * @return Strign contendo data e hora da ultima atualiza&ccdil;&atilde;o, formatada como: "dd/mm/aaa hh:mm:ss"
     */
    public static String ultimaAtualizacaoSimples(Date data) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (!Robo.testarDataAnteriorMil(data)) {
            return format.format(data);
        } else {
            return "Ainda n&atilde;o foi atualizado!";
        }

    }
}
