/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Classe com m&eacute;todos que efetuam opera&ccedil;otilde;es diversas. Como
 * testes e remo&ccedil;&otilde;es de acentua&ccedil;&atilde;o
 *
 * @author Marcos
 */
public class Operacoes {

    /**
     * Testa se a data recebida é anterior a 01/01/1970. Utilizado para testar
     * se a base de dados deve ser sincronizada do zero ou não.
     *
     * @param horaBase hora que será testada
     * @return true ou false. Se a data informada como parâmetro for menor
     * retorna true se não false
     */
    public static boolean testarDataAnterior1970(Date horaBase) {

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date dataTeste = null;
            try {
                dataTeste = format.parse("01/01/1970");
            } catch (ParseException p) {
                System.err.println("FEB: ERRO ao efetuar o parse da hora. Mensagem: " + p.getMessage());
            }

            if (dataTeste.after(horaBase)) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException n) {
            return true;
        }
    }

    /**
     * Substitui letras acentudas por letras sem acentos (remove acentos das
     * letras), e remove todo tipo de caracter que n&atilde;o seja letra e
     * n&uacute;mero.
     *
     * @param texto texto que deseja remover os acentos.
     * @return texto sem acentos e apenas com letras e n&uacute;meros.
     */
    public static String removeAcentuacao(String texto) {
        texto = texto.toLowerCase();
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

    /**
     * Recebe um ArrayList<String> percorre esse array e armazena em uma string
     * todas as posi&ccedil;%otilde;es do array separados por um espa&ccedil;o.
     *
     * @param array ArrayList<String> contendo as string que ser&atildeo
     * concatenadas.
     * @return um String contendo todo o conte&uacute;do do array separados por
     * um espa&ccedil;o.
     */
    public static String arrayListToString(ArrayList<String> array) {
        String resultado = "";
        for (int i = 0; i < array.size(); i++) {
            resultado += " " + array.get(i);
        }

        return resultado.trim();
    }

    /**
     * Testa se o diret&oacute;rio informado existe e apaga todos os XMLs
     * criados pelo FEB. Se n&atilde;o existir cria o diret&oacute;rio.
     *
     * @return File contendo o diret&oacute;rio informado.
     */
    public static File testaDiretorioTemp(String diretorio) {
        File caminhoTeste = new File(diretorio);
        if (!caminhoTeste.isDirectory()) {//se o caminho informado nao for um diretorio
            caminhoTeste.mkdirs();//cria o diretorio
        } else { //APAGA TODOS ARQUIVOS XML do FEB DA PASTA
            File[] arquivos = caminhoTeste.listFiles();
            for (File toDelete : arquivos) {
                if (toDelete.getName().startsWith("FEB-") && toDelete.getName().endsWith(".xml")) {
                    toDelete.delete();
                }
            }
        }
        return caminhoTeste;
    }

    /**
     * Informa como uma frase a data e hora da &uacute;ltima
     * atualiza&ccdil;&atilde;o, se a data for inferior a 01/01/1000 retorna a
     * mensagem: "Ainda n&atilde;o foi atualizado!"
     *
     * @return Strign contendo data e hora da ultima atualiza&ccdil;&atilde;o,
     * formatada como: "Dia dd/mm/aaa às hh:mm:ss"
     */
    public static String ultimaAtualizacaoFrase(Date data) {
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formathora = new SimpleDateFormat("HH:mm:ss");
        if (!Operacoes.testarDataAnterior1970(data)) {
            return "Dia " + formatdata.format(data) + " &agrave;s " + formathora.format(data);
        } else {
            return "Ainda n&atilde;o foi atualizado!";
        }
    }

    /**
     * Informa como uma frase a data e hora da &uacute;ltima
     * atualiza&ccdil;&atilde;o, se a data for inferior a 01/01/1000, um teste
     * para verificar se foi informado um endereço para atualização é realizado.
     * Se não existir endereço retorna um aviso, se existir retorna a mensagem:
     * "Ainda n&atilde;o foi atualizado!"
     *
     * @return Strign contendo data e hora da ultima atualiza&ccdil;&atilde;o,
     * formatada como: "Dia dd/mm/aaa às hh:mm:ss"
     */
    public static String ultimaAtualizacaoFrase(Date data, String caminho) {
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formathora = new SimpleDateFormat("HH:mm:ss");
        if (!Operacoes.testarDataAnterior1970(data)) {
            return "Dia " + formatdata.format(data) + " &agrave;s " + formathora.format(data);
        } else if (caminho.isEmpty()) {
            return ("N&atilde;o foi informado um endere&ccedil;o para sincroniza&ccedil;&atilde;o");
        } else {
            return "Ainda n&atilde;o foi atualizado!";
        }
    }

    /**
     * Informa a data e hora da &uacute;ltima atualiza&ccdil;&atilde;o, se a
     * data for inferior a 01/01/1000 retorna a mensagem: "Ainda n&atilde;o foi
     * atualizado!"
     *
     * @return Strign contendo data e hora da ultima atualiza&ccdil;&atilde;o,
     * formatada como: "dd/mm/aaa hh:mm:ss"
     */
    public static String ultimaAtualizacaoSimples(Date data) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (!Operacoes.testarDataAnterior1970(data)) {
            return format.format(data);
        } else {
            return "Ainda n&atilde;o foi atualizado!";
        }

    }

    /**
     * Testa se a data informada &eacute; anterior a data atual.
     *
     * @param data Vari&aacute;vel do tipo Date que ser&aacute; testada.
     * @return true se a data for anterior e falso caso contr&aacute;rio.
     */
    public static boolean dataAnteriorAtual(Date data) {
        if (data.before(new Date())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * formata a data recebida para o padr&atilde;o do OAI-PMH. Ex:
     * 2012-04-20T19:09:32Z
     *
     * @param date Objeto Date para ser formatado
     * @return String contendo a data formatada.
     */
    public static String formatDateOAIPMH(Date date) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            return format.format(date);
        }
    }
}
