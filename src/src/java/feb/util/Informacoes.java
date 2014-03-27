package feb.util;

import java.util.Map;
import java.util.TreeMap;

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
    
    public static Map<String,String> getMimeType(){
        Map<String,String> types = new TreeMap<String, String>();
        types.put("application", "Aplicação");
        types.put("audio", "Áudio");
        types.put("image", "Imagem");
        types.put("message", "Mensagem");
        types.put("model", "Modelo 3D");
        types.put("text", "Texto");
        types.put("video", "Vídeo");
        return types;
    }

}
