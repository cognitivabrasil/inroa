/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.robo.atualiza;

import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.exceptions.FederationException;
import com.cognitivabrasil.feb.robo.atualiza.harvesterOAI.Harvester;
import com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ImporterSubfed;
import com.cognitivabrasil.feb.robo.atualiza.subfedOAI.SubRepositorios;
import com.cognitivabrasil.feb.util.Informacoes;
import com.cognitivabrasil.feb.util.Operacoes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateSystemException;
import org.xml.sax.SAXException;

/**
 *
 * @author Marcos
 */
public class SubFederacaoOAI {

    Logger log = Logger.getLogger(SubFederacaoOAI.class);

    @Autowired
    private FederationService subDao;

    @Autowired
    private ImporterSubfed imp;

    @Autowired
    private SubRepositorios subRep;

    /**
     * Atualiza todas as subfedera&ccedil;&otilde;es. Coleta da base os dados das subfedera&ccedil;&otilde;es e chama o
     * m&etodo que efetua a atualia&ccedil;&atilde;o.
     *
     * @param con Conex&atilde;o com a base de dados local.
     * @return true ou false indicando se alguma subfedera&ccedil;&atilde;o foi atualizada ou n&atilde;o.
     */
    public boolean pre_AtualizaSubFedOAI() {
        boolean atualizou = false;

        try {
            List<SubFederacao> listaSubfed = subDao.getAll();

            // percorre todas as federacoes cadastradas
            for (SubFederacao subFed : listaSubfed) {
                boolean atualizadoTemp = false;

                if ((subFed.getUltimaAtualizacao() == null && subFed
                        .getDataXML() == null)) {
                    log.info("Deletando toda a base de dados da Subfederação: " + subFed.getName().toUpperCase());
                    subDao.deleteAllDocs(subFed);
                    log.info("Base deletada!");
                }

                if (subFed.getUrl().isEmpty()) { // testa se a string url esta
                    // vazia.
                    log.error("FEB ERRO: Nao existe uma url associada ao repositorio "
                            + subFed.getName());
                } else {

                    Long inicio = System.currentTimeMillis();
                    try {
                        atualizaSubFedOAI(subFed);
                        atualizadoTemp = true;
                    } catch (Exception e) {
                        /*
                         * ATENÇÃO: esse catch está vazio porque já é feito o
                         * tratamento de exceção dentro do metodo
                         * atualizaSubFedOAI mas é preciso subir a exceção
                         * porque se atualizar uma federação só pela ferramenta
                         * administrativa tem que saber se deu erro.
                         */
                    }
                    Long fim = System.currentTimeMillis();
                    Long total = fim - inicio;
                    log.info("Levou: " + Operacoes.formatTimeMillis(total)
                            + " para atualizar a subfederacao: "
                            + subFed.getName() + "\n");
                }

                // se alguma subfederacao foi atualizada entao seta o atualizou como true
                if (atualizadoTemp) {
                    atualizou = true;
                }
            }
        } catch (HibernateException h) {
            log.error("Erro no Hibernate.", h);
        }
        return atualizou;
    }

    /**
     * Chama o m&eacute;todo respons&aacute;vel por atualizar os reposit&oacute;rios da subfedera&ccedil;&atilde;o e o
     * m&eacute;todo respons&aacute;vel por atualizar os metadados dos objetos da subfedera&ccedil;&atilde;o. Este
     * m&eacute;todo efetua todo o tratamento de exce&ccedil;&otilde;es do processo de harverter, parser e
     * inser&ccedil;&atilde;o na base.
     *
     * @param subFed classe da subfedera&ccedil;&atilde;o.
     * @return true ou false indicando se alguma subfedera&ccedil;&atilde;o foi atualizada ou n&atilde;o.
     */
    private void atualizaSubFedOAI(SubFederacao subFed)
            throws Exception {
        String fedName = subFed.getName();
        log.info("Atualizando subfederacao: " + fedName);
        try {

            // atualizar repositorios da subfederacao
            subRep.atualizaSubRepositorios(subFed);

            // atualizar objetos da subfederacao
            atualizaObjetosSubFed(subFed);
            
            //este get é necessário para atualizar as informações que foram alteradas na base nos processos anteriores.
            subFed = subDao.get(fedName);
            
            subFed.setUltimaAtualizacao(DateTime.now());
            subDao.save(subFed);

        } catch (UnknownHostException u) {
            log.error("Nao foi possivel encontrar o servidor oai-pmh informado.", u);
            throw u;
        } catch (HibernateException | HibernateSystemException e) {
            log.error("Erro com o hibernate.", e);
            throw e;
        } catch (ParserConfigurationException e) {
            log.error("O parser nao foi configurado corretamente. ", e);
            throw e;
        } catch (SAXException e) {
            String msg = e.getMessage();
            String msgOAI = "Erro no parser do OAI-PMH, mensagem: ";
            if (msg.equalsIgnoreCase("badArgument")) {
                log.error(msgOAI
                        + msg
                        + " - The request includes illegal arguments, is missing required arguments, includes a repeated argument, or values for arguments have an illegal syntax.\n");
            } else if (msg.equalsIgnoreCase("badResumptionToken")) {
                log.error(msgOAI
                        + msg
                        + " - The value of the resumptionToken argument is invalid or expired.\n");
            } else if (msg.equalsIgnoreCase("badVerb")) {
                log.error(msgOAI
                        + msg
                        + " - Value of the verb argument is not a legal OAI-PMH verb, the verb argument is missing, or the verb argument is repeated. \n");
            } else if (msg.equalsIgnoreCase("cannotDisseminateFormat")) {
                log.error(msgOAI
                        + msg
                        + " -  The metadata format identified by the value given for the metadataPrefix argument is not supported by the item or by the repository.\n");
            } else if (msg.equalsIgnoreCase("idDoesNotExist")) {
                log.error(msgOAI
                        + msg
                        + " - The value of the identifier argument is unknown or illegal in this repository.\n");
            } else if (msg.equalsIgnoreCase("noRecordsMatch")) {
                log.info(msg
                        + " - The combination of the values of the from, until, set and metadataPrefix arguments results in an empty list.\n");
                subFed.setUltimaAtualizacao(DateTime.now());
            } else if (msg.equalsIgnoreCase("noMetadataFormats")) {
                log.error(msgOAI
                        + msg
                        + " - There are no metadata formats available for the specified item.\n");
            } else if (msg.equalsIgnoreCase("noSetHierarchy")) {
                log.error(msgOAI + msg
                        + " - The repository does not support sets.\n");
            } else {
                log.error("Problema ao fazer o parse do arquivo. "
                        + e);
            }
            throw e;
        } catch (FileNotFoundException e) {
            log.error("\nNao foi possivel coletar os dados do arquivo.", e);
            throw e;
        } catch (IOException e) {
            log.error("\nNao foi possivel coletar ou ler o XML.", e);
            throw e;
        } catch (Exception e) {
            log.error("\nErro ao efetuar o Harvester.", e);
            throw e;
        }
    }

    /**
     * Chama o m&eacute;todo respons&aacute;vel por efetuar o harverter na subfedera&ccedil;&atilde;o e o m&eacute;todo
     * respons&aacute;vel por efetuar o parser nos xmls e inserir na base de dados.
     *
     * @param subfed classe da subfedera&ccedil;&atilde;o
     * @throws Exception
     */
    public void atualizaObjetosSubFed(SubFederacao subFed)
            throws Exception {

        Informacoes conf = new Informacoes();
        String caminhoDiretorioTemporario = conf.getCaminho();

        File caminhoTeste = Operacoes
                .testaDiretorioTemp(caminhoDiretorioTemporario);
        if (caminhoTeste.isDirectory()) {

            // efetua o Harvester e grava os xmls na pasta temporaria
            Harvester harvesterOAI = new Harvester();
            List<String> caminhosXML = harvesterOAI.coletaXML_ListRecords(
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

    /**
     * Atualiza a subfedera&ccedil;&atilde;o informada e recalcula o &iacute;ndice.
     *
     * @param idSub Id da subfedera&ccedil;&atilde;o na ser atualizada.
     * @return true se atualizou e false caso contr&aacute;rio.
     * @throws Exception
     */
    public void atualizaSubfedAdm(SubFederacao subFed, boolean apagar) throws Exception {
        // Don't really know why, but the following 2 lines solve FEB-219
        subFed.setUltimaAtualizacao(subFed.getUltimaAtualizacao());
        subFed.setDataXML(subFed.getDataXML());
        if (apagar) {
            log.debug("Setar como null a data da última atualização e dataXML para que apague toda a base antes de atualizar.");
            subFed.setUltimaAtualizacao(null);
            subFed.setDataXML(null);
        }

        if (subFed.getUltimaAtualizacao() == null
                && subFed.getDataXML() == null) {

            log.info("Deletando toda a base de dados da Subfederação: "
                    + subFed.getName().toUpperCase());
            subDao.deleteAllDocs(subFed);
            log.info("Base deletada!");
        }

        String url = subFed.getUrl();
        // testa se a string url esta vazia.
        if (url.isEmpty()) {
            log.error("FEB ERRO: Nao existe uma url associada ao repositorio "
                    + subFed.getName());
        } else {

            Long inicio = System.currentTimeMillis();
            atualizaSubFedOAI(subFed);
            Long fim = System.currentTimeMillis();
            Long total = fim - inicio;
            log.info("Levou: " + Operacoes.formatTimeMillis(total)
                    + " para atualizar a subfederacao: " + subFed.getName()
                    + "\n");
        }
    }

    public void atualizaSubfedAdm(List<SubFederacao> subFed, boolean apagar) throws FederationException {
        List<String> erros = new ArrayList<>();
        for (SubFederacao subFederacao : subFed) {
            try {
                atualizaSubfedAdm(subFederacao, apagar);
            } catch (Exception e) {
                erros.add(subFederacao.getName());
                log.error("FEB ERRO: Erro ao atualizar a federação: "
                        + subFederacao.getName(), e);
            }
        }
        if (erros.size() > 0) {
            throw new FederationException(getMensagem(erros));
        }
    }

    /**
     * Recebe um arrayList de nomes e retorna uma mensagem de erro com o nome das federa&ccedil;&otilde;es que
     * n&atilde;o foram atualizadas.
     *
     * @param nome List<String> contendo o nome das federa&ccedil;&otilde;es que n&atilde;o foram atualizadas.
     * @return String com a mensagem de erro gerada.
     */
    private static String getMensagem(List<String> nome) {
        String msg;
        if (nome.size() > 0) {
            msg = "Erro atualizar as federacoes: ";
            for (String n : nome) {
                msg += " " + n;
            }
        } else {
            msg = "Erro ao atualizar a federacao " + nome.get(0);
        }
        return msg;
    }
}
