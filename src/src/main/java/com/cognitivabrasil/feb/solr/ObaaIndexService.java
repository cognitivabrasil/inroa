package com.cognitivabrasil.feb.solr;

import java.util.List;

import com.cognitivabrasil.feb.solr.camposObaa.ObaaDocument;


public interface ObaaIndexService {

    /**
     * Apaga todo o indice do Solr e todos os documentos contindo nele
     *
     * @return True se deu certo. False se houve alguma falha.
     */
    boolean apagarIndice();

    /**
     * Recebe uma lista de documentos reais, converte eles para DocumentSolr e envia eles para o sistema indexar
     * Converte um a um os documentos reais ate atingir maxDocs documentos. Apos, envia eles para o Sorl
     *
     * @param docs Lista de documentos reais a serem indexados
     */
    void indexarBancoDeDados(List<? extends ObaaDocument> docs);



}
