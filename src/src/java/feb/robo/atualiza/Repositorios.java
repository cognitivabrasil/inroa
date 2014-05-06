/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.robo.atualiza;

import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import feb.exceptions.RepositoryException;
import feb.ferramentaBusca.indexador.Indexador;
import feb.robo.atualiza.harvesterOAI.Harvester;
import feb.robo.atualiza.importaOAI.XMLtoDB;
import feb.util.Informacoes;
import feb.util.Operacoes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

/**
 *
 * @author Marcos
 */
public class Repositorios {

    @Autowired
    private Indexador indexar;
    @Autowired
    private RepositoryService repService;
    private static Logger log = Logger.getLogger(Repositorios.class);

    /**
     * Testa se algum reposit&oacute;rios precisa ser atualizado, se sim chama o
     * m&etodo respons&aacute;vel por isso.
     *
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar
     * os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados
     * @return true ou false indicando se algum reposit&aacute;rio foi
     * atualizado ou n&atilde;
     */
    public boolean testa_atualizar_repositorio(Indexador indexar) {

        Long inicio = System.currentTimeMillis();
        boolean atualizou = false;

        List<Repositorio> listRep = repService.getOutDated();

        for (Repositorio rep : listRep) { // percorre todos os repositorios que
            // precisam ser atualizados
            try { // chama o metodo que atualiza o repositorio
                if (atualizaRepositorio(repService.get(rep.getId()), indexar) > 0) {
                    atualizou = true;
                }
            } catch (Exception e) {
                // TODO: Isso é um BUG! Tem que dar catch só nas excessões conhecidas
                
		/*
                 * ATENÇÃO: esse catch está vazio porque já é feito o tratamento
                 * de exceção dentro do metodo atualizaRepositorio mas é preciso
                 * subir a exceção porque se atualizar um repositorio só pela
                 * ferramenta administrativa tem que saber se deu erro.
                 */
            }
        }
        Long fim = System.currentTimeMillis();
        Long total = fim - inicio;
        log.info("Levou: " + Operacoes.formatTimeMillis(total) + " para atualizar os repositórios");
        return atualizou;
    }

    /**
     * M&eacute;todo utilizado pela ferramenta administrativa para atualizar o
     * reposit&oacute;rio em tempo real. Este m&eacute;todo recebe um id, se
     * esse if for zero ele atualiza todos os reposit&aacute;rios existentes. Se
     * for um valor maior que zero ele atualiza apenas o escolhido.
     *
     * @param idRep id do reposit&oacute;rio a ser atualizado. Se informar zero
     * atualizar&aacute; todos
     * @param apagar informar se deseja apagar toda a base. true apaga e false
     * apenas atualiza.
     * @author Marcos Nunes
     */
    public void atualizaFerramentaAdm(int idRep, boolean apagar)
            throws Exception {

        boolean recalcularIndice = false;

        ArrayList<String> erros = new ArrayList<>();

        if (idRep > 0) { // atualizar um repositorio especifico ou
            // todos. 0 = todos
            Repositorio rep = repService.get(idRep);
            if (apagar) { 
                // seta as duas datas como null porque quando atualiza
                // se as datas forem null todos os documentos do
                // repositorio sao excluidos
                DateTime dateNull = null;
                //TODO: depois de substituir tudo de date para Datetime pode setar null direto. Agora tem dois setUltimaAtualizacao ai da erro.
                rep.setUltimaAtualizacao(dateNull);
                rep.setDataOrigem(null);
            }
            if (atualizaRepositorio(rep, indexar) > 0) {
                recalcularIndice = true;
            }
            repService.save(rep);

        } else {
            List<Repositorio> repositorios = repService.getAll();
            for (Repositorio rep : repositorios) {
                try {
                    if (atualizaRepositorio(rep, indexar) > 0) {
                        recalcularIndice = true;
                    }
                    repService.save(rep);
                } catch (Exception e) {
                    erros.add(rep.getName());
                    log.error("FEB ERRO: Erro ao atualizar o repositorio "
                            + rep.getName(), e);
                }
            }
        }

        if (recalcularIndice) {
            indexar.populateR1();
        }
        if (erros.size() > 0) {
            throw new RepositoryException(getMensagem(erros));
            // gera uma exception informando o nome dos repositorios que nao
            // foram atualizados
        }

    }

    /**
     * Atualiza o reposit&oacute;rio solicitado.
     *
     * @param idRepositorio id do reposit&oacute;rio que deve ser atualizado.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar
     * os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados
     * @param con Conex&atilde;o com a base de dados.
     * @return number of updated documents, -1 in case of error
     */
    public int atualizaRepositorio(Repositorio rep, Indexador indexar)
            throws Exception {
        Long inicio = System.currentTimeMillis();
        Harvester importar = new Harvester();
        Informacoes conf = new Informacoes();
        XMLtoDB gravar = new XMLtoDB();
        String caminhoDiretorioTemporario = conf.getCaminho();

        int updated = 0;

        try {
            log.info("Atualizando repositorio: " + rep.getName());// imprime o nome do repositorio

            Set<String> set = rep.getColecoes();
            if (set == null || set.isEmpty()) {
                set = null;
            }
            if (rep.getUrl().isEmpty()) { // testa se a string url esta vazia.
                log.error("Nao existe uma url associada ao repositorio "
                        + rep.getName());
                throw new MalformedURLException(
                        "Nao existe uma url associada ao repositorio "
                        + rep.getName());

            } else {// repositorio possui url para atualizacao

                DateTime data_ultima_atualizacao = rep.getUltimaAtualizacao();

                log.info("Ultima Atualizacao: "
                        + data_ultima_atualizacao + " nome do rep: "
                        + rep.getName());

                // se a data da ultima atualização for inferior a 01/01/1000
                // apaga todos as informacoes do repositorio
                if ((data_ultima_atualizacao == null && rep.getDataOrigem() == null)
                        || (Operacoes.testarDataDifZero(data_ultima_atualizacao) && Operacoes.testarDataDifZero(DateTime.parse(rep.getDataOrigem())))) {
                    log.info("Deletando todos os documentos do repositório: "
                            + rep.getName().toUpperCase());
                    try {
                        int result = rep.dellAllDocs();
                        log.info("" + result + " documentos deletados.");

                    } catch (Exception e) {
                        log.error("Erro ao deletar os objetos do repositorio "
                                + rep.getName() + ".", e);
                    }
                }

                // se o diretorio nao existir, cria se já existir apaga todos os .xml
                File caminhoTeste = new File(caminhoDiretorioTemporario);
                // se o caminho informado nao for um diretorio
                if (!caminhoTeste.isDirectory()) {
                    // cria o diretorio
                    caminhoTeste.mkdirs();
                } else {
                    // APAGA TODOS ARQUIVOS XML do FEB DA PASTA
                    File[] arquivos = caminhoTeste.listFiles();
                    for (File toDelete : arquivos) {
                        if (toDelete.getName().startsWith("FEB-")
                                && toDelete.getName().endsWith(".xml")) {
                            toDelete.delete();
                        }
                    }
                }

                if (caminhoTeste.isDirectory()) {
                    // efetua o Harvester e grava os xmls na pasta temporaria

                    // coletando xmls
                    ArrayList<String> caminhoXML = importar.coletaXML_ListRecords(rep.getUrl(), rep.getDataOrigem(), rep.getName(), caminhoDiretorioTemporario, rep.getMetadataPrefix(), set); // chama o
                    // metodo que efetua o HarvesterVerb grava um xml em disco e
                    // retorna um arrayList com os caminhos para os XML

                    // leXMLgravaBase: le do xml traduz para o padrao OBAA e armazena na base de dados
                    updated = gravar.saveXML(caminhoXML, rep, indexar);

                } else {
                    log.error("O caminho informado nao eh um diretorio. E nao pode ser criado em: '"
                            + caminhoDiretorioTemporario + "'");
                }
            }

            rep.setUltimaAtualizacao(DateTime.now()); // atualiza a hora da ultima atualizacao
            Long fim = System.currentTimeMillis();
            Long total = fim - inicio;

            log.info("Atualizou " + updated + " objetos em:" + Operacoes.formatTimeMillis(total));
            return updated;

        } catch (MalformedURLException m) {
            log.error("Erro na url", m);
        } catch (UnknownHostException u) {
            log.error(
                    "FEB ERRO: Nao foi possivel encontrar o servidor oai-pmh informado.",
                    u);
            throw u;
        } catch (SQLException e) {
            log.error(
                    "FEB ERRO: SQL Exception... Erro na consulta sql na classe Repositorios.",
                    e);
            throw e;
        } catch (ParserConfigurationException e) {
            log.error("FEB ERRO: O parser nao foi configurado corretamente.", e);
            throw e;
        } catch (SAXException e) {
            String msg = e.getMessage();
            String msgOAI = "\nERRO no parser do OAI-PMH, mensagem: ";
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
                log.warn(msg
                        + " - The combination of the values of the from, until, set and metadataPrefix arguments results in an empty list.\n");
                // atualiza a hora da ultima atualizacao
                rep.setUltimaAtualizacao(DateTime.now()); 
            } else if (msg.equalsIgnoreCase("noMetadataFormats")) {
                log.error(msgOAI
                        + msg
                        + " - There are no metadata formats available for the specified item.\n");
            } else if (msg.equalsIgnoreCase("noSetHierarchy")) {
                log.error(msgOAI + msg
                        + " - The repository does not support sets.\n");
            } else {
                log.error("Problema ao fazer o parse do arquivo. ", e);
            }
            throw e;
        } catch (FileNotFoundException e) {
            log.error("Nao foi possivel coletar os dados: ", e);
            throw e;
        } catch (IOException e) {
            log.error("Nao foi possivel coletar (Possível erro de timeout) ou ler o XML em: ", e);
            throw e;
        } catch (Exception e) {
            log.error("\nFEB ERRO ao efetuar o Harvester.", e);
            throw e;
        }
        return -1;
    }

    /**
     * Recebe um arrayList de nomes e retorna uma mensagem de erro com o nome
     * dos reposit&oacute;rios que n&atilde;o foram atualizados.
     *
     * @param nome ArrayList<String> contendo o nome dos reposit&oacute;rios que
     * n&atilde;o foram atualizados.
     * @return String com a mensagem de erro gerada.
     */
    public static String getMensagem(ArrayList<String> nome) {
        String msg;
        if (nome.size() > 0) {
            msg = "Erro atualizar os repositorios: ";
            for (String n : nome) {
                msg += " " + n;
            }
        } else {
            msg = "Erro ao atualizar o repositorio " + nome.get(0);
        }
        return msg;
    }
}