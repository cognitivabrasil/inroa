package robo.atualiza.importaOAI;

import OBAA.OBAA;
import OBAA.OaiOBAA;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import ferramentaBusca.indexador.Indexador;
import java.sql.Connection;
import modelos.DocumentosDAO;
import modelos.Repositorio;
import modelos.RepositoryDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import spring.ApplicationContextProvider;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Marcos
 */
public class InicioLeituraXML {

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
    public void leXMLgravaBase(
            ArrayList<String> caminhoXML,
            int id,
            Indexador indexar) throws Exception {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
            if (ctx == null) {
                System.out.println("Could not get AppContext bean!");
            } else {
                System.out.println("AppContext bean retrieved!");
            }
            RepositoryDAO repDao = ctx.getBean(RepositoryDAO.class);
            Repositorio r = repDao.get(id);
            leXMLgravaBase(caminhoXML, r, indexar);
    }
    
    /**
     * Método que chama o parser xml o qual insere na base de dados os registros
     * contidos nos arquivos xml recebidos como parâmetro. Recebe um ou mais
     * arquivos xml para realizar o parser e inserir objetos.
     *
     * @param caminhoXML ArrayList de Strings contendo caminhos para os arquivos
     * xml
     * @param r  reposit&oacute;rio a ser atualizado
     * @param indexar variavel do tipo Indexador
     *
     */
    public void leXMLgravaBase(
            ArrayList<String> caminhoXML,
            Repositorio r,
            Indexador indexar) throws Exception {

            ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
            if (ctx == null) {
                System.out.println("Could not get AppContext bean!");
            } else {
                System.out.println("AppContext bean retrieved!");
            } //TODO: isso aqui ta mal. Se conseguiu nao tem que imprimir nada e todo o codigo deve estar dentro do else.

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
                	imp.update();
                	
                    //apaga arquivo XML
                    arquivoXML.delete();

                } else {
                    System.err.println("FEB ERRO: O arquivo informado nao eh um arquivo ou nao pode ser lido. Caminho: " + caminhoXML.get(i));
                }
                
            }
            
            /* gravar realmente as modificações */
            docDao.flush();
    }
}
