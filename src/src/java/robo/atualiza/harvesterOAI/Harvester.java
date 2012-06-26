package robo.atualiza.harvesterOAI;

import ORG.oclc.oai.harvester2.verb.ListRecords;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Set;
import modelos.Repositorio;
import org.apache.log4j.Logger;
import robo.util.Operacoes;

//import util.*;

// http://alcme.oclc.org/oaicat/
// http://www.oclc.org/research/software/oai/harvester2.htm
public class Harvester {

    private String endereco;
    private String dataInicial;
    private String metadataPrefix;
    static Logger log = Logger.getLogger(Repositorio.class);

    /**
     * Metodo que efetua o Harvesting OAI-PHM e salva arquivos xml's com os resultados
     * @param endereco URL do repositório que responde com o padrao OAI-PMH
     * @param dataInicial data inicial para o filtro da busca. From
     * @param dataFinal data final para o filtro da busca. Until
     * @param nomeRepositorio nome do repositório. Utilizado apenas para salvar o arquivo xml. Ex.: /temp/nomeRepositorio.xml
     * @param dirXML Diretório onde serão salvos os arquivos XML's. Ex.: "c:/pasta" ou "/home/fulano/pasta"
     * @param metadataPrefix metadataPrefix do repositorio.
     * @param set nome do conjunto que restringir&aacute; a consulta. Nome da coleção ou da comunidade.
     * @return Retorna um ArrayList de Strings contendo o(s) caminho(s) para o(s) xml(s) salvo(s).
     */
    public ArrayList<String> coletaXML_ListRecords(String endereco, String dataInicial, String nomeRepositorio, String dirXML, String metadataPrefix, Set<String> set) throws Exception {
        this.endereco = endereco;
        this.dataInicial = dataInicial;
        this.metadataPrefix = metadataPrefix;
        int numeroXML = 0;


        ArrayList<String> caminhosXML = new ArrayList<String>();
        //substitui os espacos por underline
        nomeRepositorio = Operacoes.removeAcentuacao(nomeRepositorio);
        nomeRepositorio = nomeRepositorio.replaceAll(" ", "_");
        //barra do sistema operacional muda de win pra linux
        String barra = System.getProperty("file.separator");
        String nomeArquivo = dirXML + barra + "FEB-" + nomeRepositorio; //endereco + nome do arquivo. Utilizado em mais de um local no codigo.

        if (set == null) {
            coletaXMLSet(caminhosXML, nomeArquivo, numeroXML, null);
        } else {
            
            for (String setInterno : set) { //percorre todos os sets que tem que atualizar.
                numeroXML = coletaXMLSet(caminhosXML, nomeArquivo, numeroXML, setInterno); //coleta todos os dados do set especificado
            }
        }

        log.info("FEB: Arquivos XML foram gravados");


        return caminhosXML;

    }

    private int coletaXMLSet(ArrayList<String> caminhosXML, String nomeArquivo, int numeroXML, String set) throws Exception {

        numeroXML++;
        String caminhoAbsoluto = nomeArquivo + numeroXML + ".xml";
        caminhosXML.add(caminhoAbsoluto);

        //efetua por OAI-PMH o verbo lisRecord com a url, a data inicial, o set e o metadataPrefix recebidos como parametro
        ListRecords listRecords = new ListRecords(this.endereco, this.dataInicial, null, set, this.metadataPrefix);

        ListRecords listRecordsResume; //cria uma variavel do tipo ListRecords para efetuar o ResumptionToken se necessario

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(caminhoAbsoluto), "UTF8")); //escreve um arquivo xml em UTF8 no caminho setado no configuracao.java

        String resumption = listRecords.getResumptionToken(); //solicita a tag ResumptionToken recebida no xml

        //out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        listRecords.getDocument(); //lista o registros retornados
                
        out.write(listRecords.toString()); //imprime no arquivo os registros transformados para string
        out.close();//fecha o arquivo xml que estava sendo escrito

        while (!resumption.isEmpty()) { //enquanto existir resumptionToken segue efetuando harvester
            numeroXML++;

            caminhoAbsoluto = nomeArquivo + numeroXML + ".xml"; //cria um novo nome de arquivo com o numeroXML incrementado
            caminhosXML.add(caminhoAbsoluto); //adiciona o endereço do xml no arrayList
            Writer outResume = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(caminhoAbsoluto), "UTF8")); //cria o arquivo xml
            //outResume.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //escreve a primeira linha do xml

            listRecordsResume = new ListRecords(this.endereco, resumption);

            resumption = listRecordsResume.getResumptionToken(); //armazena na variavel resumption o proximo ResumptionToken
            listRecordsResume.getDocument();

            outResume.write(listRecordsResume.toString()); //imprime no arquivo o resultado
            outResume.close(); //fecha o arquivo xml

        }

        return numeroXML;
    }
}


