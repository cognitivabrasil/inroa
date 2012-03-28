/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ferramentaAdministrativa.validarOAI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import robo.atualiza.harvesterOAI.Identify;
import robo.util.Informacoes;
import robo.util.Operacoes;

/**
 *
 * @author Marcos
 */
public class VerificaLinkOAI extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //TODO: tem mudar isso aqui para o Controller do spring. Mas nao sei como fazer o spring retornar um texto e nao uma pagina
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String query = request.getQueryString();
        try {
            out.print(verificaLinkOAIPMH(query));

        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

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
                System.err.println("FEB ERRO: O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoArquivoXML);
            }
        } else {
            System.out.println("FEB ERRO: O caminho informado não é um diretório. E não pode ser criado em: '" + caminhoDiretorioTemporario + "'");
        }
        }catch (Exception e){
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
                new FileOutputStream(caminhoAbsoluto), "UTF8")); //escreve um arquivo xml em UTF8 no caminho setado no configuracao.java


        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        listIndentifiers.getDocument(); //lista o registros retornados
        out.write(listIndentifiers.toString()); //imprime no arquivo os registros transformados para string
        out.close();//fecha o arquivo xml que estava sendo escrito


        return caminhoAbsoluto;
    }

}
