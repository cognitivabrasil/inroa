package ferramentaBusca.indexador;

import java.util.ArrayList;
import java.util.Collections;
import ptstemmer.Stemmer;
import uk.ac.shef.wit.simmetrics.wordhandlers.GenericStopTermHandler;
import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;

public class Documento {

    private ArrayList<String> titulo;
    private ArrayList<String> palCh;
    private ArrayList<String> ent;
    private ArrayList<String> desc;
    private String obaaEntry;
    private int id;
    private static final InterfaceTermHandler GSTH = new GenericStopTermHandler();

    public Documento() {
        titulo = new ArrayList<String>();
        palCh = new ArrayList<String>();
        ent = new ArrayList<String>();
        desc = new ArrayList<String>();
        id = 1;
        obaaEntry="";
    }

    public Documento(String query) {
        titulo = new ArrayList<String>();
        palCh = new ArrayList<String>();
        ent = new ArrayList<String>();
        desc = tokeniza(query);
        id = 1;
        obaaEntry="";
    }

    public String getObaaEntry() {
        return obaaEntry;
    }

    public void setObaaEntry(String obaaEntry) {
        this.obaaEntry = obaaEntry;
    }

    public ArrayList<String> getTitulo() {
        return titulo;
    }

    public ArrayList<String> getPalavrasChave() {
        return palCh;
    }

    public ArrayList<String> getEntidade() {
        return ent;
    }

    public ArrayList<String> getDescricao() {
        return desc;
    }

    public ArrayList<String> getTokens() {
        ArrayList<String> tokens = new ArrayList<String>();


        for (int i = 0; i < titulo.size(); i++) {
            tokens.add(titulo.get(i));
        }

        for (int i = 0; i < palCh.size(); i++) {
            tokens.add(palCh.get(i));
        }

        for (int i = 0; i < ent.size(); i++) {
            tokens.add(ent.get(i));
        }

        for (int i = 0; i < desc.size(); i++) {
            tokens.add(desc.get(i));
        }

        return (tokens);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = tokeniza(titulo);
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palCh = tokeniza(palavrasChave);
    }

    public void setEntidade(String entidade) {
        this.ent = tokeniza(entidade);
    }

    public void setDescricao(String descricao) {
        this.desc = tokeniza(descricao);
    }

    /**
     * Esse método retorna uma lista de palavras ordenada, sem stopwords
     * @param S String que será tokenizada
     * @return lista de palavras ordenada, sem stopwords
     */
    public static ArrayList<String> tokeniza(String S) {

        Stemmer st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);
        st.enableCaching(1000);
        ArrayList<String> Words = new ArrayList<String>();
        String aux = "";
        S = S.toLowerCase();

        S = S.replaceAll(":|!|'|\"|\\.|,|;|\\?|\\||\\(|\\)|\\{|\\}|\\[|\\]| - |\\+|\\=|\\#|\\&|_|\\\\", " ");
        S = S.replaceAll("á|à|â|ã|ä", "a");
        S = S.replaceAll("é|è|ê|ë", "e");
        S = S.replaceAll("í|ì|î|ï", "i");
        S = S.replaceAll("ó|ò|ô|õ|ö", "o");
        S = S.replaceAll("ú|ù|û|ü", "u");
        S = S.replaceAll("ç", "c");
        int j;

        while (S.length() > 0) {
            S = S.trim();
            if (S.indexOf(" ") != -1) {
                j = S.indexOf(" ");
                String S1 = S.substring(0, j);
                S = S.substring(j);
                if (!GSTH.isWord(S1)) {
                    aux = st.wordStemming(S1);
                    if (!aux.equals("")) {
                        Words.add(aux);
                    }
                }
            } else if (!GSTH.isWord(S)) {
                //aux = bs.stem(S);
                aux = st.wordStemming(S);
                if (!aux.equals("")) {
                    Words.add(aux);
                }
                S = "";
            }
        }

        if (Words.size() < 1) {
            System.out.println("Nenhuma palavra capturada!");
        } else {
            Collections.sort(Words);
        }
        return Words;
    }
}
