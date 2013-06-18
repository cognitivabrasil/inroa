package feb.util;

/**
 * Informações úteis para o robô
 *
 * @author Marcos nunes
 */
public class Informacoes {

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
    }

}
