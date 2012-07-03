/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.util;

import ferramentaBusca.indexador.StopWordTAD;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import metadata.TextElement;
import org.apache.log4j.Logger;
import ptstemmer.Stemmer;
import ptstemmer.exceptions.PTStemmerException;

/**
 * Classe com m&eacute;todos que efetuam opera&ccedil;otilde;es diversas. Como
 * testes e remo&ccedil;&otilde;es de acentua&ccedil;&atilde;o
 *
 * @author Marcos
 */
public class Operacoes {
    static Logger log = Logger.getLogger(Operacoes.class);

    /**
     * Testa se a data recebida é newDate(0) ou se j&aacute; foi alterada.
     * Utilizado para testar se a base de dados deve ser sincronizada do zero ou
     * não.
     *
     * @param horaBase hora que será testada
     * @return true ou false. Se a data informada como parâmetro for menor
     * retorna true se não false
     */
    public static boolean testarDataDifZero(Date horaBase) {
        try {
            Date dataTeste = new Date(1);

            return dataTeste.after(horaBase);

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
     * atualiza&ccdil;&atilde;o, se a data for inferior a 01/01/1000, um teste
     * para verificar se foi informado um endereço para atualização é realizado.
     * Se não existir endereço retorna um aviso, se existir retorna a mensagem:
     * "Ainda n&atilde;o foi atualizado!"
     *
     * @return Strign contendo data e hora da ultima atualiza&ccdil;&atilde;o,
     * formatada como: "Dia dd/mm/aaa às hh:mm:ss"
     */
    public static String ultimaAtualizacaoFrase(Date data, String url) {
        SimpleDateFormat f = new SimpleDateFormat("'Dia' dd/MM/yyyy '&agrave;s' HH:mm:ss");
        if (data == null || testarDataDifZero(data)) {
            return "Ainda n&atilde;o foi atualizado!";
        } else if (url == null || url.isEmpty()) {
            return ("N&atilde;o foi informado um endere&ccedil;o para sincroniza&ccedil;&atilde;o");
        } else {
            return f.format(data);
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
        if (!Operacoes.testarDataDifZero(data)) {
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
        if (data == null || data.before(new Date())) {
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

    public static List<String> toStringList(List<? extends TextElement> elements) {
        List<String> s = new ArrayList<String>();
        for (TextElement e : elements) {
            s.add(e.getText());
        }
        return s;
    }

    /**
     * Esse m&eacute;todo retorna uma lista de palavras stemizada, sem stopwords
     *
     * @param S String que será tokenizada
     * @param stopWords lista de stopwords
     * @return lista de palavras stemizada, sem stopwords
     */
    public static List<String> tokeniza(String S, List<String> stopWords) {

        ArrayList<String> Words = new ArrayList<String>();

        try {
            Stemmer st;
            st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);

            st.enableCaching(1000);

            S = S.toLowerCase();

            st.remove(stopWords);

            S = Operacoes.removeAcentuacao(S); //chama o metodo que substitui as letras acentuadas e todo tipo de caracter alem de [a-zA-Z_0-9]

            String tokens[];
            tokens = st.getPhraseStems(S);

            for (int i = 0; i < tokens.length; i++) {

                if (!tokens[i].isEmpty()) {
                    Words.add(tokens[i]);
                }

            }

            if (Words.size() < 1) {
                log.debug("Nenhuma palavra capturada! String: " + S + ". Provavelmente todos os tokens foram considerados StopWords.");
            }
            return Words;
        } catch (PTStemmerException e) {
            log.error("Erro ao stemmizar a frase: " + S, e);
            return Words;
        }
    }
}
