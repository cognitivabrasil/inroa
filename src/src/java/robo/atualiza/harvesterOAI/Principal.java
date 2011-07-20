package robo.atualiza.harvesterOAI;


import java.io.*;
import java.util.ArrayList;
//import util.*;

// http://alcme.oclc.org/oaicat/
// http://www.oclc.org/research/software/oai/harvester2.htm
public class Principal {

    /**
     * Metodo que efetua o Harvesting OAI-PHM e imprime arquivos xml's com os resultados
     * @param endereco URL do repositório que responde com o padrao OAI-PMH
     * @param dataInicial data inicial para o filtro da busca. From
     * @param dataFinal data final para o filtro da busca. Until
     * @param nomeRepositorio nome do repositório. Utilizado apenas para salvar o arquivo xml. Ex.: /temp/nomeRepositorio.xml
     * @param dirXML Diretório onde serão salvos os arquivos XML's. Ex.: "c:/pasta" ou "/home/fulano/pasta"
     * @return Retorna um ArrayList de Strings contendo o(s) caminho(s) para o(s) xml(s) salvo(s).
     */
    public ArrayList<String> buscaXmlRepositorio(String endereco, String dataInicial, String nomeRepositorio, String dirXML, String metadataPrefix) throws Exception{

        String barra = System.getProperty("file.separator");
        int numeroXML =1;
        String caminhoAbsoluto = dirXML+barra+"temp"+nomeRepositorio+numeroXML+".xml";
        ArrayList<String> caminhosXML = new ArrayList<String>();
        caminhosXML.add(caminhoAbsoluto);
		
        //efetua o ver lisRecord com a url, a data inicial e datafinal recebida como parametro
        
        ListRecords listRecords = new ListRecords(endereco,dataInicial,null,null,metadataPrefix);
        //cria uma variavel do tipo ListRecords para efetuar o ResumptionToken se necessario
        ListRecords listRecordsResume;

        //escreve um arquivo xml em UTF8 no caminho setado no configuracao.java
        Writer out = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(caminhoAbsoluto), "UTF8"));
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            
            //solicita a tag ResumptionToken recebida no xml
            String resumption = listRecords.getResumptionToken();

            //lista o registros retornados
            listRecords.getDocument();
            //imprime no arquivo os registros transformados para string
            out.write(listRecords.toString());
            //fecha o arquivo xml que estava sendo escrito
            out.close();

//            String registros = listRecords.toString();
//            if(!resumption.isEmpty()){
//                System.out.println("\n\n-----"+registros.replaceAll("</ListRecords></OAI-PMH>", "")+"\n -----------");
//                out.write(registros);
                //System.out.println(registros.replaceAll("", resumption));
//            }
//            else{
//
//            }

            
        //imprime na tela uma mensagem com o ResumptionToken
        
        //enquanto existir resumptionToken segue efetuando harvester
        while(!resumption.isEmpty()){
            numeroXML++;
            //cria um novo caminho com o numeroXML incrementado
            caminhoAbsoluto = dirXML+barra+"temp"+nomeRepositorio+numeroXML+".xml";
            caminhosXML.add(caminhoAbsoluto);
            Writer outResume = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(caminhoAbsoluto), "UTF8"));
            outResume.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            System.out.println("ResumptionToken: "+resumption);
            listRecordsResume = new ListRecords(endereco,resumption);
            //armazena na variavel resumption o proximo ResumptionToken
            resumption = listRecordsResume.getResumptionToken();
            listRecordsResume.getDocument();
            //imprime no arquivo o resultado
            outResume.write(listRecordsResume.toString());
            outResume.close();
//            System.out.println("\n\n-----"+listRecordsResume.toString().replaceFirst("<OAI-PMH.*<ListRecords>", "")+"\n -----------");
        }


  
//se o resultado nao conter acento vai dar erro no parser xml pois aqui o arquivo nao sera criado em UTF8
        
        
            System.out.println(" Arquivo XML com dados do repositorio foi gravado");

              


        /* while (listRecords != null)
        {
        //Verifica se existe erros no retorno

        NodeList errors = listRecords.getErrors();
        if (errors != null && errors.getLength() > 0)
        {
        System.out.println("Found errors");
        int length = errors.getLength();
        for (int i=0; i<length; ++i) {
        Node item = errors.item(i);
        System.out.println(item);
        }
        System.out.println("Error record: " + listRecords.toString());
        break;
        }


        saida.write(listRecords.toString().getBytes("UTF-8"));
        saida.write("\n".getBytes("UTF-8"));

        String resumptionToken;
        resumptionToken = listRecords.getResumptionToken();
        if (resumptionToken == null || resumptionToken.length() == 0) {
        System.out.println("teste");
        listRecords = null;
        }
        } */
        
        return caminhosXML;

    }

}

/*


while (listSets != null)
{
//Verifica se existe erros no retorno	
NodeList errors = listSets.getErrors();
if (errors != null && errors.getLength() > 0) 
{
System.out.println("Found errors");
int length = errors.getLength();
for (int i=0; i<length; ++i) {
Node item = errors.item(i);
System.out.println(item);
}
System.out.println("Error record: " + listSets.toString());
break;
}
saida.write(listSets.toString().getBytes("UTF-8"));
saida.write("\n".getBytes("UTF-8"));            

String resumptionToken;

resumptionToken = listSets.getResumptionToken();
System.out.println("resumptionToken: " + resumptionToken);
if (resumptionToken == null || resumptionToken.length() == 0) {
listSets = null;
} //else {
//	listSets = new ListSets("http://www.lume.ufrgs.br/dspace-oai/request", resumptionToken);
//}

}


 * */

