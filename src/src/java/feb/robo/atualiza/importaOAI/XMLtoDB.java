package feb.robo.atualiza.importaOAI;

import feb.data.entities.Repositorio;
import feb.data.interfaces.DocumentosDAO;
import feb.data.interfaces.RepositoryDAO;
import feb.ferramentaBusca.indexador.Indexador;
import feb.spring.ApplicationContextProvider;

import java.io.File;
import java.util.ArrayList;
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

            RepositoryDAO repDao = ctx.getBean(RepositoryDAO.class);
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
            ArrayList<String> caminhoXML,
            Repositorio r,
            Indexador indexar) throws Exception {

        int updated = 0;

        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.fatal("Could not get AppContext bean!");
            throw new ApplicationContextException("Could not get AppContext bean!");
        } else {

            DocumentosDAO docDao = ctx.getBean(DocumentosDAO.class);
            RepositoryDAO repDao = ctx.getBean(RepositoryDAO.class);

            for (int i = 0; i < caminhoXML.size(); i++) {
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

    public void testeLume(DocumentosDAO docDao, RepositoryDAO repDao) {
        String inputXmlFile = "/home/marcos/NetBeansProjects/feb/src/test/java/metadata/lume_erro_null_documento.xml";


        Repositorio r = repDao.get("LUME");

        Importer imp = new Importer();
        imp.setInputFile(new File(inputXmlFile));
        imp.setRepositorio(r);
        imp.setDocDao(docDao);
        imp.setRepDao(repDao);
        imp.update();
    }
}