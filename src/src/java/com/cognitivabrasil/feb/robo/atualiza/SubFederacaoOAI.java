/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.robo.atualiza;

import com.cognitivabrasil.feb.data.entities.RepositorioSubFed;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.exceptions.FederationException;
import com.cognitivabrasil.feb.ferramentaBusca.indexador.Indexador;
import com.cognitivabrasil.feb.robo.atualiza.subfedOAI.Objetos;
import com.cognitivabrasil.feb.robo.atualiza.subfedOAI.SubRepositorios;
import com.cognitivabrasil.feb.spring.ApplicationContextProvider;
import com.cognitivabrasil.feb.util.Operacoes;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.xml.sax.SAXException;

/**
 *
 * @author Marcos
 */
public class SubFederacaoOAI {

    Logger log = Logger.getLogger(SubFederacaoOAI.class);

    /**
     * Atualiza todas as subfedera&ccedil;&otilde;es. Coleta da base os dados
     * das subfedera&ccedil;&otilde;es e chama o m&etodo que efetua a
     * atualia&ccedil;&atilde;o.
     *
     * @param con Conex&atilde;o com a base de dados local.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar
     * os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados.
     * @return true ou false indicando se alguma subfedera&ccedil;&atilde;o foi
     * atualizada ou n&atilde;o.
     */
    public boolean pre_AtualizaSubFedOAI(Indexador indexar) {
        boolean atualizou = false;

        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.fatal("Could not get AppContext bean!");
            throw new ApplicationContextException("Could not get AppContext bean!");
        } else {

            try {
                FederationService subDao = ctx.getBean(FederationService.class);
                List<SubFederacao> listaSubfed = subDao.getAll();

                for (SubFederacao subFed : listaSubfed) { //percorre todas as federacoes cadastradas
                    boolean atualizadoTemp = false;

                    if ((subFed.getUltimaAtualizacao() == null && subFed.getDataXML() == null)) {
                        log.info("Deletando toda a base de dados da Subfederação: " + subFed.getName().toUpperCase());
                        subDao.deleteAllDocs(subFed);
                        log.info("Base deletada!");
                    }

                    if (subFed.getUrl().isEmpty()) { //testa se a string url esta vazia.
                        log.error("FEB ERRO: Nao existe uma url associada ao repositorio " + subFed.getName());
                    } else {

                        Long inicio = System.currentTimeMillis();
                        try {
                            atualizaSubFedOAI(subFed, indexar);
                            atualizadoTemp = true;
                        } catch (Exception e) {
                            /*
                             * ATENÇÃO: esse catch está vazio porque já é feito
                             * o tratamento de exceção dentro do metodo
                             * atualizaSubFedOAI mas é preciso subir a exceção
                             * porque se atualizar uma federação só pela
                             * ferramenta administrativa tem que saber se deu
                             * erro.
                             */
                        }
                        Long fim = System.currentTimeMillis();
                        Long total = fim - inicio;
                        log.info("Levou: " + Operacoes.formatTimeMillis(total) + " para atualizar a subfederacao: " + subFed.getName()+"\n");
                    }

                    if (atualizadoTemp) { //se alguma subfederacao foi atualizada entao seta o atualizou como true
                        atualizou = true;
                    }
                }
            } catch (HibernateException h) {
                log.error("FEB ERRO: Erro no Hibernate na classe: " + h.getClass() + ".", h);
            }
        }
        return atualizou;
    }

    /**
     * Chama o m&eacute;todo respons&aacute;vel por atualizar os
     * reposit&oacute;rios da subfedera&ccedil;&atilde;o e o m&eacute;todo
     * respons&aacute;vel por atualizar os metadados dos objetos da
     * subfedera&ccedil;&atilde;o. Este m&eacute;todo efetua todo o tratamento
     * de exce&ccedil;&otilde;es do processo de harverter, parser e
     * inser&ccedil;&atilde;o na base.
     *
     * @param subFed classe da subfedera&ccedil;&atilde;o.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar
     * os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados.
     * @return true ou false indicando se alguma subfedera&ccedil;&atilde;o foi
     * atualizada ou n&atilde;o.
     */
    private void atualizaSubFedOAI(SubFederacao subFed, Indexador indexar) throws Exception {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.fatal("Could not get AppContext bean!");
            throw new ApplicationContextException("Could not get AppContext bean!");
        } else {
            log.info("Atualizando subfederacao: " + subFed.getName());//imprime o nome da subfederacao
            try {

                //atualizar repositorios da subfederacao
                SubRepositorios subRep = new SubRepositorios();
                subRep.atualizaSubRepositorios(subFed);

                //atualizar objetos da subfederacao
                Objetos obj = new Objetos();
                obj.atualizaObjetosSubFed(subFed, indexar);


                subFed.setUltimaAtualizacao(DateTime.now());
                FederationService subDao = ctx.getBean(FederationService.class);
                subDao.save(subFed);


            } catch (UnknownHostException u) {
                log.error("FEB ERRO - Metodo atualizaSubFedOAI: Nao foi possivel encontrar o servidor oai-pmh informado, erro: " + u);
                throw u;
            } catch (SQLException e) {
                log.error("FEB ERRO - Metodo atualizaSubFedOAI: SQL Exception... Erro na consulta sql na classe SubFederacaoOAI:" + e.getMessage());
                throw e;
            } catch (ParserConfigurationException e) {
                log.error("FEB ERRO - Metodo atualizaSubFedOAI: O parser nao foi configurado corretamente. " + e);
                throw e;
            } catch (SAXException e) {
                String msg = e.getMessage();
                String msgOAI = "\nFEB ERRO - Metodo atualizaSubFedOAI: erro no parser do OAI-PMH, mensagem: ";
                if (msg.equalsIgnoreCase("badArgument")) {
                    log.error(msgOAI + msg + " - The request includes illegal arguments, is missing required arguments, includes a repeated argument, or values for arguments have an illegal syntax.\n");
                } else if (msg.equalsIgnoreCase("badResumptionToken")) {
                    log.error(msgOAI + msg + " - The value of the resumptionToken argument is invalid or expired.\n");
                } else if (msg.equalsIgnoreCase("badVerb")) {
                    log.error(msgOAI + msg + " - Value of the verb argument is not a legal OAI-PMH verb, the verb argument is missing, or the verb argument is repeated. \n");
                } else if (msg.equalsIgnoreCase("cannotDisseminateFormat")) {
                    log.error(msgOAI + msg + " -  The metadata format identified by the value given for the metadataPrefix argument is not supported by the item or by the repository.\n");
                } else if (msg.equalsIgnoreCase("idDoesNotExist")) {
                    log.error(msgOAI + msg + " - The value of the identifier argument is unknown or illegal in this repository.\n");
                } else if (msg.equalsIgnoreCase("noRecordsMatch")) {
                    log.info(msg + " - The combination of the values of the from, until, set and metadataPrefix arguments results in an empty list.\n");
                    subFed.setUltimaAtualizacao(DateTime.now());
                } else if (msg.equalsIgnoreCase("noMetadataFormats")) {
                    log.error(msgOAI + msg + " - There are no metadata formats available for the specified item.\n");
                } else if (msg.equalsIgnoreCase("noSetHierarchy")) {
                    log.error(msgOAI + msg + " - The repository does not support sets.\n");
                } else {
                    log.error("\nFEB ERRO: Problema ao fazer o parse do arquivo. " + e);
                }
                throw e;
            } catch (FileNotFoundException e) {
                log.error("\nFEB ERRO - " + this.getClass() + ": nao foi possivel coletar os dados de: " + e + "\n");
                throw e;
            } catch (IOException e) {
                log.error("\nFEB ERRO - Nao foi possivel coletar ou ler o XML em " + this.getClass() + ": " + e + "\n");
                throw e;
            } catch (Exception e) {
                log.error("\nFEB ERRO - " + this.getClass() + ": erro ao efetuar o Harvester " + e + "\n");
                throw e;
            }
        }
    }

    /**
     * Atualiza a subfedera&ccedil;&atilde;o informada e recalcula o
     * &iacute;ndice.
     *
     * @param idSub Id da subfedera&ccedil;&atilde;o na ser atualizada.
     * @return true se atualizou e false caso contr&aacute;rio.
     * @throws Exception
     */
    public void atualizaSubfedAdm(SubFederacao subFed, Indexador indexar, boolean apagar) throws Exception {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.fatal("Could not get AppContext bean!");
            throw new ApplicationContextException("Could not get AppContext bean!");
        } else {

            // Don't really know why, but the following 2 lines solve FEB-219
            subFed.setUltimaAtualizacao(subFed.getUltimaAtualizacao());
            subFed.setDataXML(subFed.getDataXML());
            if (apagar) {
                log.debug("Setar como null a data da última atualização e dataXML para que apague toda a base antes de atualizar.");
                subFed.setUltimaAtualizacao(null);
                subFed.setDataXML(null);
            }

            if (subFed.getUltimaAtualizacao() == null && subFed.getDataXML() == null) {

                log.info("Deletando toda a base de dados da Subfederação: " + subFed.getName().toUpperCase());

                for (RepositorioSubFed r : subFed.getRepositorios()) {
                    r.dellAllDocs();
                }
                log.info("Base deletada!");
            }

            String url = subFed.getUrl();
            if (url.isEmpty()) { //testa se a string url esta vazia.
                log.error("FEB ERRO: Nao existe uma url associada ao repositorio " + subFed.getName());
            } else {

                Long inicio = System.currentTimeMillis();
                atualizaSubFedOAI(subFed, indexar);
                Long fim = System.currentTimeMillis();
                Long total = fim - inicio;
                log.info("Levou: " + Operacoes.formatTimeMillis(total) + " para atualizar a subfederacao: " + subFed.getName()+"\n");
            }
        }
    }

    public void atualizaSubfedAdm(List<SubFederacao> subFed, Indexador indexar, boolean apagar) throws FederationException {
        ArrayList<String> erros = new ArrayList<>();
        for (SubFederacao subFederacao : subFed) {
            try {
                atualizaSubfedAdm(subFederacao, indexar, apagar);
            } catch (Exception e) {
                erros.add(subFederacao.getName());
                log.error("FEB ERRO: Erro ao atualizar a federação: " + subFederacao.getName(), e);
            }
        }
        if (erros.size() > 0) {
            throw new FederationException(getMensagem(erros));
        }
    }

    /**
     * Recebe um arrayList de nomes e retorna uma mensagem de erro com o nome
     * das federa&ccedil;&otilde;es que n&atilde;o foram atualizadas.
     *
     * @param nome ArrayList<String> contendo o nome das federa&ccedil;&otilde;es
     * que n&atilde;o foram atualizadas.
     * @return String com a mensagem de erro gerada.
     */
    public static String getMensagem(ArrayList<String> nome) {
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