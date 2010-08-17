package robo.util;

/*
 * Informacoes.java
 *
 *  Criado em 23 de Junho de 2009
 *  
 *  Marcos Nunes
 */
/**
 * Informações úteis para o robô
 * @author Marcos nunes
 */
public class Informacoes {

    private String barra = System.getProperty("file.separator");
    private String so = System.getProperty("os.name").toUpperCase();
    private String caminhoWin = "C:" + barra + "marcos" + barra + "temp";
    private String CaminhoLinux = barra + "tmp";


    /**
     * Informa o caminho para armazenar os arquivos xml temporários. Se for linus o sistema operacional responde um caminho, se for windows responde outro.
     * @return Retorna uma String contendo caminho para o diretorio temporario onde serão armazenados os arquivos xml temporários
     */
    public String getCaminho() {
        if (so.contains("LINUX")) {
            return this.CaminhoLinux;
        } else {
            return this.caminhoWin;
        }
    }

    /**
     * Altera o diretório temporário onde serão armazenados os arquivos temporários do robô.
     * @param caminho String contento o caminho para o diretório temporário
     */
    public void setCaminho(String caminho) {
        if (so.contains("LINUX")) {
            this.CaminhoLinux = caminho;
        } else {
            this.caminhoWin = caminho;
        }
    }

}
