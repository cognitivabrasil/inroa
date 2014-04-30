package feb.ferramentaAdministrativa.validarOAI;

import ORG.oclc.oai.harvester2.verb.Identify;
import feb.util.Informacoes;
import feb.util.Operacoes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.log4j.Logger;


/**
 *
 * @author Marcos
 */
public class VerificaLinkOAI {
	static Logger log = Logger.getLogger(VerificaLinkOAI.class);

    /**
     * Verifica se o link OAI-PMH é v&atilde;lido ou n&atilde;o. Coleta o XML com a identificação do repositório efetua o parser e verifica se tem algum erro.
     * @param enderecoOAI endere&ccedil;o que responde OAI-PMH da subfedera&ccedil;&atilde;o
     * @return true se o link oai-pmh for v&atilde;lido ou n&atilde;o.
     */
    public static boolean verificaLinkOAIPMH(String enderecoOAI) {
        boolean resultado = false;
        try{
        Informacoes conf = new Informacoes();
        String caminhoDiretorioTemporario = conf.getCaminho();
        File caminhoTeste = Operacoes.testaDiretorioTemp(caminhoDiretorioTemporario);
        if (caminhoTeste.isDirectory()) {//efetua o Harvester e grava os xmls na pasta temporaria

            String caminhoArquivoXML = coletaXML_Identify(enderecoOAI, caminhoDiretorioTemporario); //coleta o xml por OAI-PMH
          
            ParserIdentify parserIdentify = new ParserIdentify();
            File arquivoXML = new File(caminhoArquivoXML);
            if (arquivoXML.isFile() || arquivoXML.canRead()) {

                parserIdentify.parser(arquivoXML);//efetua a leitura do xml e insere os objetos na base de dados

                arquivoXML.delete(); //apaga arquivo XML
                resultado=true;
            } else {
                log.error("FEB ERRO: O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoArquivoXML);
            }
        } else {
            log.error("FEB ERRO: O caminho informado não é um diretório. E não pode ser criado em: '" + caminhoDiretorioTemporario + "'");
        }
        }catch (Exception e){
        	log.error(e);
            resultado = false;
            //colocar aqui os tratamentos para o oai-pmh
        }finally{
            return resultado;
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
                new FileOutputStream(caminhoAbsoluto), "UTF-8")); //escreve um arquivo xml em UTF-8 no caminho setado no configuracao.java


        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        listIndentifiers.getDocument(); //lista o registros retornados
        out.write(listIndentifiers.toString()); //imprime no arquivo os registros transformados para string
        out.close();//fecha o arquivo xml que estava sendo escrito


        return caminhoAbsoluto;
    }

}
