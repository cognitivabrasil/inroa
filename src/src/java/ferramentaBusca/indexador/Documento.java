    package ferramentaBusca.indexador;

import java.util.ArrayList;
import ptstemmer.Stemmer;
import ptstemmer.support.stopwords.*;


public class Documento {

    private ArrayList<String> titulo;
    private ArrayList<String> palCh;
    private ArrayList<String> ent;
    private ArrayList<String> desc;
    private String resumo;
    private String data;
    private String localizacao;
    private int servidor;
    private String obaaEntry;
    private String tituloOriginal;
    private int id;
    private StopWordTAD StopWd;
  

    
//    public Documento() {
//        titulo = new ArrayList<String>();
//        palCh = new ArrayList<String>();
//        ent = new ArrayList<String>();
//        desc = new ArrayList<String>();
//        id = 1;
//        obaaEntry="";
//        resumo = "";
//        data = "";
//        localizacao = "";
//        servidor = 0;
//        obaaEntry = "";
//        tituloOriginal = "";
//
//
//
//    }

        public Documento(StopWordTAD StopWd) {
        titulo = new ArrayList<String>();
        palCh = new ArrayList<String>();
        ent = new ArrayList<String>();
        desc = new ArrayList<String>();
        id = 1;
        obaaEntry="";
        resumo = "";
        data = "";
        localizacao = "";
        servidor = 0;
        obaaEntry = "";
        tituloOriginal = "";
        this.StopWd = StopWd;


    }

    public Documento(String query) {
        titulo = new ArrayList<String>();
        palCh = new ArrayList<String>();
        ent = new ArrayList<String>();
        desc = tokeniza(query);
        id = 1;
        obaaEntry="";
        resumo = "";
        data = "";
        localizacao = "";
        servidor = 0;
        tituloOriginal = "";
        this.StopWd = null;
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



    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.tituloOriginal = titulo;
        this.titulo.addAll(tokeniza(titulo, StopWd)); //adiciona o arraylist de tokens dentro do array list de titulos (igual o apend de listas)
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palCh.addAll(tokeniza(palavrasChave, StopWd)); //adiciona o arraylist de tokens dentro do array list de palavras chaves (igual o apend de listas)
    }

    public void setEntidade(String entidade) {
//        nao estamos utilizando temporariamente esta tag no indice
//        this.entaddAll(tokeniza(entidade, StopWd));
    }

    public void setDescricao(String descricao) {
//        nao estamos utilizando temporariamente esta tag no indice
//        this.descaddAll(tokeniza(descricao, StopWd));
    }

    public void setData(String data) {
        this.data += data;
    }

    public void setResumo(String resumo) {
        this.resumo += resumo;
    }

    public void setServidor(int servidor) {
        this.servidor = servidor;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao += localizacao;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal += tituloOriginal;
    }

    public String getData() {
        return data;
    }

    public String getResumo() {
        return resumo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public int getServidor() {
        return servidor;
    }



    /**
     * Esse método retorna uma lista de palavras stemizada
     * @param S String que será tokenizada
     * @return lista de palavras stemizada
     */
     public static ArrayList<String> tokeniza(String S) {

        Stemmer st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);
        st.enableCaching(1000);

        ArrayList<String> Words = new ArrayList<String>();

        S = S.toLowerCase();

        S = S.replaceAll(":|!|'|\"|\\.|,|;|\\?|\\||\\(|\\)|\\{|\\}|\\[|\\]| - |\\+|\\=|\\#|\\&|_|\\\\|/|-", " ");
        S = S.replaceAll("á|à|â|ã|ä", "a");
        S = S.replaceAll("é|è|ê|ë", "e");
        S = S.replaceAll("í|ì|î|ï", "i");
        S = S.replaceAll("ó|ò|ô|õ|ö", "o");
        S = S.replaceAll("ú|ù|û|ü", "u");
        S = S.replaceAll("ç", "c");

        S = S.trim();

            String tokens[];
            tokens = st.phraseStemming(S);

            for (int i = 0; i < tokens.length; i++) {


                if (!tokens[i].isEmpty()){
                    Words.add(tokens[i]);
                }
            }

          if (Words.size() < 1) {
                System.out.println("Nenhuma palavra capturada! String: "+S+"\n");
            }
        return Words;

    }
        

    /**
     * Esse método retorna uma lista de palavras stemizada, sem stopwords
     * @param S String que será tokenizada
     * @param StopWords a variável com as stopwords sem estarem carregas
     * @return lista de palavras stemizada, sem stopwords
     */
    public static ArrayList<String> tokeniza(String S, StopWordTAD StopWords) {

        Stemmer st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);
        st.enableCaching(1000);

        ArrayList<String> Words = new ArrayList<String>();

        S = S.toLowerCase();

        StopWordList StopwdPort = new StopWordsFromHashSet(StopWords.getRes());
        st.ignoreStopWords(StopwdPort);


        S = S.replaceAll(":|!|'|\"|\\.|,|;|\\?|\\||\\(|\\)|\\{|\\}|\\[|\\]| - |\\+|\\=|\\#|\\&|_|\\\\|/|-", " ");
        S = S.replaceAll("á|à|â|ã|ä", "a");
        S = S.replaceAll("é|è|ê|ë", "e");
        S = S.replaceAll("í|ì|î|ï", "i");
        S = S.replaceAll("ó|ò|ô|õ|ö", "o");
        S = S.replaceAll("ú|ù|û|ü", "u");
        S = S.replaceAll("ç", "c");

        S = S.trim();


            String tokens[];
            tokens = st.phraseStemming(S);

            

            for (int i = 0; i < tokens.length; i++) {
                

                if (!tokens[i].isEmpty() && !StopwdPort.isStopWord(tokens[i])){
                    Words.add(tokens[i]);
                }
                    
            }

          if (Words.size() < 1) {
                System.out.println("Nenhuma palavra capturada! String: "+S+"\n");
            }
        return Words;

    }
}
