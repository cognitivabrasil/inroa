/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ferramentaAdministrativa;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import robo.atualiza.harvesterOAI.Identify;
import robo.util.Informacoes;
import robo.util.Operacoes;

/**
 *
 * @author Marcos
 */
public class VerificaLinkOAI {


    /**
     * Coleta o XML com a identificação do repositório efetua o parser e retorna uma lista de atributos e valores.
     * @param enderecoOAI endere&ccedil;o que responde OAI-PMH da subfedera&ccedil;&atilde;o
     * @throws Exception
     */
    public static void verificaLink(String enderecoOAI) {
        try{
        Informacoes conf = new Informacoes();
        String caminhoDiretorioTemporario = conf.getCaminho();
        File caminhoTeste = Operacoes.testaDiretorioTemp(caminhoDiretorioTemporario);
        if (caminhoTeste.isDirectory()) {//efetua o Harvester e grava os xmls na pasta temporaria

            String caminhoArquivoXML = coletaXML_Identify(enderecoOAI, caminhoDiretorioTemporario); //coleta o xml por OAI-PMH
            System.out.println(caminhoArquivoXML);

            ParserIdentify parserIdentify = new ParserIdentify();
            File arquivoXML = new File(caminhoArquivoXML);
            if (arquivoXML.isFile() || arquivoXML.canRead()) {

                HashMap<String,String> listaSubrep = parserIdentify.parser(arquivoXML);//efetua a leitura do xml e insere os objetos na base de dados

                arquivoXML.delete(); //apaga arquivo XML

            } else {
                System.err.println("FEB ERRO: O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoArquivoXML);
            }
        } else {
            System.out.println("FEB ERRO: O caminho informado não é um diretório. E não pode ser criado em: '" + caminhoDiretorioTemporario + "'");
        }
        }catch (Exception e){

            //colocar aqui os tratamentos para o oai-pmh
        }
    }

    /**
     * Efetua a coleta do XML utilizando o verbo oai-pmh ListSets.
     * @param endereco URL que responde com o padrao OAI-PMH.
     * @param nomeSubfed nome da subfederacao.
     * @param dirXML diret&oacute;rio onde ser&aacute; salvo o xml.
     * @return String contendo o endere&ccedil;o para o xml salvo.
     * @throws Exception toda e qualquer exce&ccedil;&atilde;o gerada.
     */
    private static String coletaXML_Identify(String endereco, String dirXML) throws Exception {


        String barra = System.getProperty("file.separator"); //barra do sistema operacional muda de win pra linux
        String caminhoAbsoluto = dirXML + barra + "FEB-identify.xml"; //endereco + nome do arquivo. Utilizado em mais de um local no codigo.

        //efetua por OAI-PMH o verbo lisRecord com a url, a data inicial, o set e o metadataPrefix recebidos como parametro
        Identify listIndentifiers = new Identify(endereco);

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(caminhoAbsoluto), "UTF8")); //escreve um arquivo xml em UTF8 no caminho setado no configuracao.java


        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        listIndentifiers.getDocument(); //lista o registros retornados
        out.write(listIndentifiers.toString()); //imprime no arquivo os registros transformados para string
        out.close();//fecha o arquivo xml que estava sendo escrito


        return caminhoAbsoluto;
    }

    public static void main(String[] args) {
        VerificaLinkOAI.verificaLink("http://objetoseducacionais2.mec.gov.br/oai/request");
    }


}
