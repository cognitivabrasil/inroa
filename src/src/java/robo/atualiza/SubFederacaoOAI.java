/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza;

import ferramentaBusca.indexador.Indexador;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import modelos.RepositorioSubFed;
import modelos.SubFederacao;
import modelos.SubFederacaoDAO;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.context.ApplicationContext;
import org.xml.sax.SAXException;
import postgres.Conectar;
import robo.atualiza.subfedOAI.Objetos;
import robo.atualiza.subfedOAI.SubRepositorios;
import robo.util.Informacoes;
import robo.util.Operacoes;
import spring.ApplicationContextProvider;

/**
 *
 * @author Marcos
 */
public class SubFederacaoOAI {
    
    Logger log = Logger.getLogger(this.getClass().getName());

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
            log.error("FEB ERRO: Could not get AppContext bean!");
        } else {
            
            try {
                SubFederacaoDAO subDao = ctx.getBean(SubFederacaoDAO.class);
                List<SubFederacao> listaSubfed = subDao.getAll();
                
                for (SubFederacao subFed : listaSubfed) { //percorre todas as federacoes cadastradas
                    boolean atualizadoTemp = false;
                    
                    if (subFed.getUltimaAtualizacao() == null || Operacoes.testarDataAnterior1970(subFed.getUltimaAtualizacao())) {
                        
                        log.info("FEB: Deletando toda a base de dados da Subfederação: " + subFed.getNome().toUpperCase());
                        
                        for (RepositorioSubFed r : subFed.getRepositorios()) {
                            r.dellAllDocs();
                        }
                        log.info("FEB: Base deletada!");
                    }
                    
                    if (subFed.getUrl().isEmpty()) { //testa se a string url esta vazia.
                        log.error("FEB ERRO: Nao existe uma url associada ao repositorio " + subFed.getNome());
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
                        log.info("FEB: Levou: " + (fim - inicio) / 1000 + " segundos para atualizar a subfederacao: " + subFed.getNome());
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
     * @param idSubfed Id da subfedera&ccedil;&atilde;o.
     * @param url Endere&ccedil;o http da subfedera&ccedil;&atilde;o que
     * responde por OAI-PMH. Ex: http://feb.ufrgs.br/feb/OAIHander
     * @param ultimaAtualizacao Data em que a subfedera&ccedil;&atilde;o sofreu
     * a &uacute;ltima atualiza&ccedil;&atilde;o.
     * @param nome Nome da subfedera&ccedil;&atilde;o.
     * @param con Conex&atilde;o com a base de dados local.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar
     * os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados.
     * @return true ou false indicando se alguma subfedera&ccedil;&atilde;o foi
     * atualizada ou n&atilde;o.
     */
    private void atualizaSubFedOAI(SubFederacao subFed, Indexador indexar) throws Exception {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        if (ctx == null) {
            log.error("FEB ERRO: Could not get AppContext bean!");
        } else {
            log.info("FEB: Atualizando subfederacao: " + subFed.getNome());//imprime o nome do repositorio

            try {
                Informacoes info = new Informacoes();
                String url = subFed.getUrl();
                if (url.endsWith("/")) { //se a url terminar com / concatena o endereço do oai-pmh sem a barra
                    url += info.getOaiPMH();
                } else {
                    url += "/" + info.getOaiPMH();
                }
                //atualizar repositorios da subfederacao
                SubRepositorios subRep = new SubRepositorios();
                subRep.atualizaSubRepositorios(url, subFed);

                //atualizar objetos da subfederacao
                Objetos obj = new Objetos();
                obj.atualizaObjetosSubFed(url, subFed, indexar);
                
                
                subFed.setUltimaAtualizacao(new Date());
                SubFederacaoDAO subDao = ctx.getBean(SubFederacaoDAO.class);
                subDao.save(subFed);
                
                
            } catch (UnknownHostException u) {
                System.err.println("FEB ERRO - Metodo atualizaSubFedOAI: Nao foi possivel encontrar o servidor oai-pmh informado, erro: " + u);
                throw u;
            } catch (SQLException e) {
                System.err.println("FEB ERRO - Metodo atualizaSubFedOAI: SQL Exception... Erro na consulta sql na classe SubFederacaoOAI:" + e.getMessage());
                throw e;
            } catch (ParserConfigurationException e) {
                System.err.println("FEB ERRO - Metodo atualizaSubFedOAI: O parser nao foi configurado corretamente. " + e);
                throw e;
            } catch (SAXException e) {
                String msg = e.getMessage();
                String msgOAI = "\nFEB ERRO - Metodo atualizaSubFedOAI: erro no parser do OAI-PMH, mensagem: ";
                if (msg.equalsIgnoreCase("badArgument")) {
                    System.err.println(msgOAI + msg + " - The request includes illegal arguments, is missing required arguments, includes a repeated argument, or values for arguments have an illegal syntax.\n");
                } else if (msg.equalsIgnoreCase("badResumptionToken")) {
                    System.err.println(msgOAI + msg + " - The value of the resumptionToken argument is invalid or expired.\n");
                } else if (msg.equalsIgnoreCase("badVerb")) {
                    System.err.println(msgOAI + msg + " - Value of the verb argument is not a legal OAI-PMH verb, the verb argument is missing, or the verb argument is repeated. \n");
                } else if (msg.equalsIgnoreCase("cannotDisseminateFormat")) {
                    System.err.println(msgOAI + msg + " -  The metadata format identified by the value given for the metadataPrefix argument is not supported by the item or by the repository.\n");
                } else if (msg.equalsIgnoreCase("idDoesNotExist")) {
                    System.err.println(msgOAI + msg + " - The value of the identifier argument is unknown or illegal in this repository.\n");
                } else if (msg.equalsIgnoreCase("noRecordsMatch")) {
                    System.out.println("FEB: " + msg + " - The combination of the values of the from, until, set and metadataPrefix arguments results in an empty list.\n");
                    subFed.setUltimaAtualizacao(new Date());
                    subFed.setDataXML(indexar.getDataXML());
                } else if (msg.equalsIgnoreCase("noMetadataFormats")) {
                    System.err.println(msgOAI + msg + " - There are no metadata formats available for the specified item.\n");
                } else if (msg.equalsIgnoreCase("noSetHierarchy")) {
                    System.err.println(msgOAI + msg + " - The repository does not support sets.\n");
                } else {
                    System.err.println("\nFEB ERRO: Problema ao fazer o parse do arquivo. " + e);
                }
                throw e;
            } catch (FileNotFoundException e) {
                System.err.println("\nFEB ERRO - " + this.getClass() + ": nao foi possivel coletar os dados de: " + e + "\n");
                throw e;
            } catch (IOException e) {
                System.err.println("\nFEB ERRO - Nao foi possivel coletar ou ler o XML em " + this.getClass() + ": " + e + "\n");
                throw e;
            } catch (Exception e) {
                System.err.println("\nFEB ERRO - " + this.getClass() + ": erro ao efetuar o Harvester " + e + "\n");
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
     */
    public void atualizaSubfedAdm(SubFederacao subFed, boolean apagar) throws Exception {
        
        Conectar conectar = new Conectar(); //instancia uma variavel da classe Conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
        Indexador indexar = new Indexador();
        
        if (apagar) {
            log.debug("Setar como null a data da última atualização e dataXML para que apague toda a base antes de atualizar.");
            subFed.setUltimaAtualizacao(null);
            subFed.setDataXML(null);
        }
        
        if (subFed.getUltimaAtualizacao() == null || Operacoes.testarDataAnterior1970(subFed.getUltimaAtualizacao())) {
            
            log.info("FEB: Deletando toda a base de dados da Subfederação: " + subFed.getNome().toUpperCase());
            
            for (RepositorioSubFed r : subFed.getRepositorios()) {
                r.dellAllDocs();
            }
            log.info("FEB: Base deletada!");
        }
        
        String url = subFed.getUrl();
        if (url.isEmpty()) { //testa se a string url esta vazia.
            log.error("FEB ERRO: Nao existe uma url associada ao repositorio " + subFed.getNome());
        } else {
            
            Long inicio = System.currentTimeMillis();
            atualizaSubFedOAI(subFed, indexar);
            Long fim = System.currentTimeMillis();
            log.info("FEB: Levou: " + (fim - inicio) / 1000 + " segundos para atualizar a subfederacao: " + subFed.getNome());
        }
        
        log.info("FEB: recalculando o indice.");
        Long inicio = System.currentTimeMillis();
        indexar.populateR1(con);
        Long fim = System.currentTimeMillis();
        log.info("FEB: indice recalculado em " + (fim - inicio) / 1000 + " segundos.");
        
        try {
            con.close(); //fechar conexao
        } catch (SQLException e) {
            log.error("FEB ERRO - Metodo atualizaSubFedOAI: Erro ao fechar a conexão no metodo atualizaSubfedAdm: " + e.getMessage());
        }
    }
}
