package com.cognitivabrasil.feb.robo.atualiza.harvesterOAI;

import ORG.oclc.oai.harvester2.verb.ListRecords;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.util.Operacoes;

import java.util.List;


// http://alcme.oclc.org/oaicat/
// http://www.oclc.org/research/software/oai/harvester2.htm
public class Harvester {

    private String endereco;
    private String dataInicial;
    private String metadataPrefix;
    private static final Logger log = LoggerFactory.getLogger(Repositorio.class);

    /**
     * Metodo que efetua o Harvesting OAI-PHM e salva arquivos xml's com os resultados
     * @param endereco URL do repositório que responde com o padrao OAI-PMH
     * @param dataInicial data inicial para o filtro da busca. Atributo From do OAI-PMH.
     * @param nomeRepositorio nome do repositório. Utilizado apenas para salvar o arquivo xml. Ex.: /temp/nomeRepositorio.xml
     * @param dirXML Diretório onde serão salvos os arquivos XML's. Ex.: "c:/pasta" ou "/home/fulano/pasta"
     * @param metadataPrefix metadataPrefix do repositorio.
     * @param set nome do conjunto que restringir&aacute; a consulta. Nome da coleção ou da comunidade.
     * @return Retorna um ArrayList de Strings contendo o(s) caminho(s) para o(s) xml(s) salvo(s).
     */
    public List<String> coletaXML_ListRecords(String endereco, String dataInicial, String nomeRepositorio, 
            String dirXML, String metadataPrefix, Set<String> set) throws Exception {
        this.endereco = endereco;
        this.dataInicial = dataInicial;
        this.metadataPrefix = metadataPrefix;
        int numeroXML = 0;


        List<String> caminhosXML = new ArrayList<>();
        //substitui os espacos por underline
        nomeRepositorio = Operacoes.removeAcentuacao(nomeRepositorio);
        nomeRepositorio = nomeRepositorio.replaceAll(" ", "_");
        //barra do sistema operacional muda de win pra linux
        String barra = System.getProperty("file.separator");
        String nomeArquivo = dirXML + barra + "FEB-" + nomeRepositorio; //endereco + nome do arquivo. Utilizado em mais de um local no codigo.

        if (set == null) {
            coletaXMLSet(caminhosXML, nomeArquivo, numeroXML, null);
        } else {
            //percorre todos os sets que tem que atualizar.
            for (String setInterno : set) {
                numeroXML = coletaXMLSet(caminhosXML, nomeArquivo, numeroXML, setInterno.trim()); //coleta todos os dados do set especificado
            }
        }

        log.info("Arquivos XML foram gravados");


        return caminhosXML;

    }

    private int coletaXMLSet(List<String> caminhosXML, String nomeArquivo, int numeroXML, String set) throws Exception {

        numeroXML++;
        String caminhoAbsoluto = nomeArquivo+"-"+ numeroXML + ".xml";
        caminhosXML.add(caminhoAbsoluto);

        
        //efetua por OAI-PMH o verbo listRecord com a url, a data inicial, o set e o metadataPrefix recebidos como parametro
        ListRecords listRecords = new ListRecords(this.endereco, this.dataInicial, null, set, this.metadataPrefix);
        
        //cria uma variavel do tipo ListRecords para efetuar o ResumptionToken se necessario
        ListRecords listRecordsResume; 

        //escreve um arquivo xml em UTF-8 no caminho setado no configuracao.java
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminhoAbsoluto), "UTF-8")); 

        //solicita a tag ResumptionToken recebida no xml
        String resumption = listRecords.getResumptionToken();

        //lista o registros retornados
        listRecords.getDocument();
                
        //imprime no arquivo os registros transformados para string
        out.write(listRecords.toString());
        //fecha o arquivo xml que estava sendo escrito
        out.close();

        //enquanto existir resumptionToken segue efetuando harvester
        while (!resumption.isEmpty()) { 
            numeroXML++;

            //cria um novo nome de arquivo com o numeroXML incrementado
            caminhoAbsoluto = nomeArquivo + numeroXML + ".xml";
            //adiciona o endereço do xml no arrayList
            caminhosXML.add(caminhoAbsoluto);
            //cria o arquivo xml
            Writer outResume = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(caminhoAbsoluto), "UTF-8"));
            listRecordsResume = new ListRecords(this.endereco, resumption);

            //armazena na variavel resumption o proximo ResumptionToken
            resumption = listRecordsResume.getResumptionToken();
            listRecordsResume.getDocument();

            //imprime no arquivo o resultado
            outResume.write(listRecordsResume.toString());
            //fecha o arquivo xml
            outResume.close();

        }
        return numeroXML;
    }
}