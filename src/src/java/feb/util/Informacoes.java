package feb.util;

/*
 * Informacoes.java
 *
 * Criado em 23 de Junho de 2009
 *
 * Marcos Nunes
 */
/**
 * Informações úteis para o robô
 *
 * @author Marcos nunes
 */
public class Informacoes {

    private String barra = System.getProperty("file.separator");
//    private String so = System.getProperty("os.name").toUpperCase();
//    private String caminhoWin = "C:" + barra + "temp";  
//    
//    private String CaminhoLinux = barra + "tmp";


    /**
     * Informa o caminho para armazenar os arquivos xml temporários. Se for
     * linux o sistema operacional responde um caminho, se for windows responde
     * outro.
     *
     * @return Retorna uma String contendo caminho para o diretorio temporario
     * onde serão armazenados os arquivos xml temporários
     */
    public String getCaminho() {
        return System.getProperty("java.io.tmpdir");
//        if (so.contains("LINUX")) {
//            return this.CaminhoLinux;
//        } else {
//            return this.caminhoWin;
//        }
    }

    public String getBarra() {
        return barra;
    }

}
