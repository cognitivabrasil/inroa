/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.ferramentaAdministrativa.validarOAI;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Classe que faz o parser/leitura de um arquivo XML
 *
 * @author Marcos Nunes
 *
 */
public class ParserIdentify extends DefaultHandler {

    /** Buffer que guarda as informacoes quando um texto e encontrado */
    private StringBuffer valorAtual = new StringBuffer(50);
    private boolean resultado = false;


    /**
     * Constutor que inicializa os objetos necessarios para fazer o parser
     * do arquivo contato.xml
     *
     * @param caminhoXML caminho completo para o arquivo xml que será lido.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void parser(File caminhoXML) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();

        parser.parse(caminhoXML, this);
        if(!resultado){
            throw new SAXException("FEB: Is not an OAI-PMH link");
        }

    }

    /**
     * Indica que o parser achou o início do documento XML. Este evento não
     * lhe passa qualquer informação, apenas indica que o parser vai começar
     * a escanear o arquivo XML.
     */
    @Override
    public void startDocument() {
    }

    /**
     * Indica que o parser achou e fim do documento XML.
     */
    @Override
    public void endDocument() {
    }

    /**
     * Indica que o parser achou o início de uma tag (tag de abertura/início).
     * Este evento fornece o nome do elemento, o nome e valor dos atributos
     * deste elemento, e também pode fornecer as informações sobre o namespace.
     */
    @Override
    public void startElement(String uri, String localName, String tag, Attributes atributos) throws SAXException {

        if (tag.equalsIgnoreCase("error")) {

            String erro = "";
            for (int i = 0; i < atributos.getLength(); i++) {
                erro = atributos.getValue(i);
            }
            throw new SAXException(erro);

        } else if(tag.equalsIgnoreCase("Identify")){
//            informacoes = new HashMap<String, String>();
              resultado = true;
        }


    }

    /**
     * Indica que o parser achou o fim de uma tag/elemento.
     * A cada tag encontrada armazena a informação, e ao final do xml chama o método insereObaa para inserir os dados encontrados na base de dados no padrão OBAA
     */
    @Override
    public void endElement(String uri, String localName, String tag) {


        //limpa o valor Atual
        valorAtual.delete(0, valorAtual.length());
    }

    /**
     * Indica que o parser achou algum Texto (Informação).
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        
        valorAtual.append(ch, start, length);

    }
}