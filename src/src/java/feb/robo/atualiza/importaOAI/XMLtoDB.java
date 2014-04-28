package feb.robo.atualiza.importaOAI;

import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import feb.ferramentaBusca.indexador.Indexador;
import feb.spring.ApplicationContextProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Marcos
 */
public class XMLtoDB {

    static Logger log = Logger.getLogger(XMLtoDB.class);

    /**
     * Método que chama o parser xml o qual insere na base de dados os registros
     * contidos nos arquivos xml recebidos como parâmetro. Recebe um ou mais
     * arquivos xml para realizar o parser e inserir objetos.
     *
     * @param caminhoXML ArrayList de Strings contendo caminhos para os arquivos
     * xml
     * @param id id do repositório na base de dados
     * @param indexar variavel do tipo Indexador
     *
     */
    public void saveXML(
            ArrayList<String> caminhoXML,
            int id,
            Indexador indexar) throws Exception {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.fatal("Could not get AppContext bean!");
            throw new ApplicationContextException("Could not get AppContext bean!");
        } else {

            RepositoryService repDao = ctx.getBean(RepositoryService.class);
            Repositorio r = repDao.get(id);
            saveXML(caminhoXML, r, indexar);
        }
    }

    /**
     * M&eacute;todo que chama o parser xml o qual insere na base de dados os
     * registros contidos nos arquivos xml recebidos como par&acirc;metro.
     * Recebe um ou mais arquivos xml para realizar o parser e inserir objetos.
     *
     * @param caminhoXML ArrayList de Strings contendo caminhos para os arquivos
     * xml
     * @param r reposit&oacute;rio a ser atualizado
     * @param indexar variavel do tipo Indexador
     * @return number of update documents
     *
     */
    public int saveXML(
            List<String> caminhoXML,
            Repositorio r,
            Indexador indexar) throws Exception {

        int updated = 0;

        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.fatal("Could not get AppContext bean!");
            throw new ApplicationContextException("Could not get AppContext bean!");
        } else {

            DocumentService docDao = ctx.getBean(DocumentService.class);
            RepositoryService repDao = ctx.getBean(RepositoryService.class);

            for (int i = 0; i < caminhoXML.size(); i++) {
                log.info("Lendo XML " + caminhoXML.get(i).substring(caminhoXML.get(i).lastIndexOf("/") + 1));
                File arquivoXML = new File(caminhoXML.get(i));
                if (arquivoXML.isFile() || arquivoXML.canRead()) {
                    Importer imp = new Importer();
                    imp.setInputFile(arquivoXML);
                    imp.setRepositorio(r);
                    imp.setDocDao(docDao);
                    imp.setRepDao(repDao);
                    updated += imp.update();

                    //apaga arquivo XML
//                    arquivoXML.delete();

                } else {
                    log.error("FEB ERRO: O arquivo informado nao eh um arquivo ou nao pode ser lido. Caminho: " + caminhoXML.get(i));
                }
            }

            return updated;
        }
    }
}
