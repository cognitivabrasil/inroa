/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.robo.atualiza.subfedOAI;

import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.ferramentaBusca.indexador.Indexador;
import com.cognitivabrasil.feb.robo.atualiza.harvesterOAI.Harvester;
import com.cognitivabrasil.feb.util.Informacoes;
import com.cognitivabrasil.feb.util.Operacoes;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class Objetos {

    @Autowired
    private ImporterSubfed imp;

    /**
     * Chama o m&eacute;todo respons&aacute;vel por efetuar o harverter na subfedera&ccedil;&atilde;o e o m&eacute;todo
     * respons&aacute;vel por efetuar o parser nos xmls e inserir na base de dados.
     *
     * @param subfed classe da subfedera&ccedil;&atilde;o
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar os dados para o indice durante a
     * atualiza&ccidil;&atilde;o dos metadados
     * @throws Exception
     */
    public void atualizaObjetosSubFed(SubFederacao subFed, Indexador indexar)
            throws Exception {
        Logger log = Logger.getLogger(this.getClass().getName());

        Informacoes conf = new Informacoes();
        String caminhoDiretorioTemporario = conf.getCaminho();

        File caminhoTeste = Operacoes
                .testaDiretorioTemp(caminhoDiretorioTemporario);
        if (caminhoTeste.isDirectory()) {

            // efetua o Harvester e grava os xmls na pasta temporaria
            Harvester harvesterOAI = new Harvester();
            ArrayList<String> caminhosXML = harvesterOAI.coletaXML_ListRecords(
                    subFed.getUrlOAIPMH(), subFed.getDataXML(),
                    subFed.getName(), caminhoDiretorioTemporario, "obaa", null);

            // efetua o parser do xml e insere os documentos na base
            if (!caminhosXML.isEmpty()) {
                log.info("Lendo os XMLs e inserindo os objetos na base");
            }

            for (String caminho : caminhosXML) {

                File arquivoXML = new File(caminho);
                if (arquivoXML.isFile() || arquivoXML.canRead()) {
                    log.info("Lendo XML "
                            + caminho.substring(caminho.lastIndexOf("/") + 1));

                    imp.setInputFile(arquivoXML);
                    imp.setSubFed(subFed);
                    imp.update();

                    // apaga arquivo XML
                    arquivoXML.delete();

                } else {
                    log.error("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: "
                            + caminho);
                }
            }

            log.info("Objetos da subfederacao " + subFed.getName()
                    + " atualizados!");

        } else {
            log.error("O caminho informado não é um diretório. E não pode ser criado em: '"
                    + caminhoDiretorioTemporario + "'");
        }

    }
}
