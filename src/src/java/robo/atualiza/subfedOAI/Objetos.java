/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza.subfedOAI;

import ferramentaBusca.indexador.Indexador;
import java.io.File;
import java.util.ArrayList;
import modelos.DocumentosDAO;
import modelos.SubFederacao;
import modelos.SubFederacaoDAO;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import robo.atualiza.harvesterOAI.Harvester;
import robo.util.Informacoes;
import robo.util.Operacoes;
import spring.ApplicationContextProvider;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class Objetos {

    /**
     * Chama o m&eacute;todo respons&aacute;vel por efetuar o harverter na subfedera&ccedil;&atilde;o
     * e o m&eacute;todo respons&aacute;vel por efetuar o parser nos xmls e inserir na
     * base de dados.
     *
     * @param endereco endere&ccedil;o http da subfedera&ccedil;&atilde;o que
     * responde por OAI-PMH. Ex: http://feb.ufrgs.br/feb/OAIHander
     * @param dataInicial data em que a subfedera&ccedil;&atilde;o sofreu a
     * &uacute;ltima atualiza&ccedil;&atilde;o
     * @param nomeSubfed nome da subfedera&ccedil;&atilde;o
     * @param metadataPrefix metadataPrefix da subfedera&ccedil;&atilde;o
     * @param set nome do conjunto que restringir&aacute; a consulta. Nome de um
     * reposit&oacute;rios da subfedera&ccedil;&atilde;o.
     * @param con Conex&atilde;o com a base de dados local
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar
     * os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados
     * @throws Exception
     */
    public void atualizaObjetosSubFed(String endereco, SubFederacao subFed, Indexador indexar) throws Exception {
        Logger log = Logger.getLogger(this.getClass().getName());

        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.error("Could not get AppContext bean! Class: " + this.getClass().getName());
        } else {


            Informacoes conf = new Informacoes();
            String caminhoDiretorioTemporario = conf.getCaminho();

            File caminhoTeste = Operacoes.testaDiretorioTemp(caminhoDiretorioTemporario);
            if (caminhoTeste.isDirectory()) {

                //efetua o Harvester e grava os xmls na pasta temporaria
                Harvester harvesterOAI = new Harvester();
                ArrayList<String> caminhosXML = harvesterOAI.coletaXML_ListRecords(endereco, subFed.getDataXML(), subFed.getNome(), caminhoDiretorioTemporario, "obaa", null);

                //efetua o parser do xml e insere os documentos na base            
                if (!caminhosXML.isEmpty()) {
                    log.info("FEB: Lendo os XMLs e inserindo os objetos na base");
                }
                
                DocumentosDAO docDao = ctx.getBean(DocumentosDAO.class);

                
                for (String caminho : caminhosXML) {

                    File arquivoXML = new File(caminho);
                    if (arquivoXML.isFile() || arquivoXML.canRead()) {
                        log.info("FEB: Lendo XML " + caminho.substring(caminho.lastIndexOf("/") + 1));

                        Importer imp = new Importer();
                        imp.setInputFile(arquivoXML);
                        imp.setDocDao(docDao);
                        imp.setSubFed(subFed);
                        imp.update();

                        //apaga arquivo XML
                        arquivoXML.delete();

                    } else {
                        log.error("FEB: O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminho);
                    }
                }

                log.info("Objetos da subfederacao " + subFed.getNome() + " atualizados!");

            } else {
                log.error("O caminho informado não é um diretório. E não pode ser criado em: '" + caminhoDiretorioTemporario + "'");
            }

        }
    }
}
