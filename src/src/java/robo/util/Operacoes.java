/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe com m&eacute;todos que efetuam opera&ccedil;otilde;es diversas. Como testes e remo&ccedil;&otilde;es de acentua&ccedil;&atilde;o
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

    /**
     * Substitui letras acentudas por letras sem acentos (remove acentos das letras), e remove todo tipo de caracter que n&atilde;o seja letra e n&uacute;mero.
     * @param texto texto que deseja remover os acentos.
     * @return texto sem acentos e apenas com letras e n&uacute;meros.
     */
    public static String removeAcentuacao(String texto) {
        texto = texto.replaceAll("á|à|â|ã|ä", "a");
        texto = texto.replaceAll("é|è|ê|ë", "e");
        texto = texto.replaceAll("í|ì|î|ï", "i");
        texto = texto.replaceAll("ó|ò|ô|õ|ö", "o");
        texto = texto.replaceAll("ú|ù|û|ü", "u");
        texto = texto.replaceAll("ç", "c");
        texto = texto.replaceAll("ñ", "n");
        texto = texto.replaceAll("\\W", " ");
        texto = texto.trim();
        return texto;
    }
}
