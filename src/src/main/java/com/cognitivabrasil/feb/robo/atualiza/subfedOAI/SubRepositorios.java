package com.cognitivabrasil.feb.robo.atualiza.subfedOAI;

import ORG.oclc.oai.harvester2.verb.ListSets;

import java.io.*;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.util.Informacoes;
import com.cognitivabrasil.feb.util.Operacoes;

/**
 *
 * @author Marcos
 */
@Component
@Scope("prototype")
public class SubRepositorios {
    private static final Logger log = Logger.getLogger(SubRepositorios.class);
    
    @Autowired
    private FederationService subFedDao;

    /**
     * Coleta o XML com os reposit&oacute;rios da subfedera&ccedil;&atilde;o
     * efetua o parser a atualiza a base de dados local.
     *
     * @param subFed {@link SubFederacao} que terá seus repositórios atualizados.
     * @throws Exception
     */
    public void atualizaSubRepositorios(SubFederacao subFed) throws Exception {
        log.debug("Atualizando os subrepositorios");
     
            Informacoes conf = new Informacoes();
            String caminhoDiretorioTemporario = conf.getCaminho();
            File caminhoTeste = Operacoes.testaDiretorioTemp(caminhoDiretorioTemporario);
            if (caminhoTeste.isDirectory()) {//efetua o Harvester e grava os xmls na pasta temporaria

                //coleta o xml por OAI-PMH
                String caminhoArquivoXML = coletaXML_ListSets(subFed.getUrlOAIPMH(), subFed.getName(), caminhoDiretorioTemporario); 

                ParserListSets parserListSets = new ParserListSets();
                File arquivoXML = new File(caminhoArquivoXML);
                if (arquivoXML.isFile() || arquivoXML.canRead()) {

                    Set<String> listaSubrep = parserListSets.parser(arquivoXML);//efetua a leitura do xml e insere os objetos na base de dados
                    log.debug("Repositorios coletados: " + listaSubrep);
                    subFed.atualizaListaSubRepositorios(listaSubrep);
                    
                    subFedDao.save(subFed);
                    
                    arquivoXML.delete(); //apaga arquivo XML
                    log.debug("Subrepositorios atualizados");
                } else {
                    log.error("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoArquivoXML);
                }
            } else {
                log.error("O caminho informado não é um diretório. E não pode ser criado em: '" + caminhoDiretorioTemporario + "'");
            }
      

    }

    /**
     * Efetua a coleta do XML utilizando o verbo oai-pmh ListSets.
     *
     * @param endereco URL que responde com o padrao OAI-PMH.
     * @param nomeSubfed nome da subfederacao.
     * @param dirXML diret&oacute;rio onde ser&aacute; salvo o xml.
     * @return String contendo o endere&ccedil;o para o xml salvo.
     * @throws Exception toda e qualquer exce&ccedil;&atilde;o gerada.
     */
    private String coletaXML_ListSets(String endereco, String nomeSubfed, String dirXML) throws Exception {


        String barra = System.getProperty("file.separator"); //barra do sistema operacional muda de win pra linux
        String caminhoAbsoluto = dirXML + barra + "FEB-" + nomeSubfed + ".xml"; //endereco + nome do arquivo. Utilizado em mais de um local no codigo.

        //efetua por OAI-PMH o verbo lisRecord com a url, a data inicial, o set e o metadataPrefix recebidos como parametro
        ListSets listSets = new ListSets(endereco);

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(caminhoAbsoluto), "UTF-8")); //escreve um arquivo xml em UTF-8 no caminho setado no configuracao.java


        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        listSets.getDocument(); //lista o registros retornados
        out.write(listSets.toString()); //imprime no arquivo os registros transformados para string
        out.close();//fecha o arquivo xml que estava sendo escrito


        return caminhoAbsoluto;
    }
}
