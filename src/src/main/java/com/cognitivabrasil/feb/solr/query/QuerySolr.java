package com.cognitivabrasil.feb.solr.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.General.General;
import cognitivabrasil.obaa.Technical.Technical;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.entities.Repositorio;

/**
 * @author Daniel Epstein
 * @author Paulo Schreiner
 */
@Service
public class QuerySolr {
    private static final Logger log = LoggerFactory.getLogger(QuerySolr.class);


    @Autowired
    private HttpSolrServer serverSolr;
    

    /**
     * Realiza a busca avancada. A funcao verifica quais campos foram escolhidos e realiza a busca neles PERGUNTA:
     * REALIZA A BUSCA APENAS NELES OU NOS PRINCIPAIS TAMBEM? E A BUSCA PODE SER DIFERENTE PARA CADA CAMPO?
     *
     * @param pesquisa Um Objeto consulta com todas as informacoes da busca (String, campos, etc)
     * @param offset Posicao do primeiro resultado a aparecer
     * @param limit Numero de resultados desejados
     * @return 
     * @throws SolrServerException - Não foi possível fazer a pesquisa (server offline?)
     */
    public QueryResponse pesquisaCompleta(Consulta pesquisa, int offset, int limit) throws SolrServerException {
        SolrQuery query = CriaQuery.criaQueryCompleta(pesquisa);

        query.setStart(offset);
        query.setRows(limit);

        query.setRequestHandler("/feb");

        return serverSolr.query(query);
    }
    
    /**
     * Faz uma busca no handler de auto-suggest do Solr.
     * @param auto string de busca (o que o usuário digitou no field)
     * @return resultado da consulta, com as sugestões do autosuggest.
     */
    public QueryResponse autosuggest(String auto) {
        SolrQuery query = new SolrQuery(); 
        
        query.setRequestHandler("/suggest");
        query.setQuery(auto);
        
        try {
            return serverSolr.query(query);
        }
        catch (SolrServerException e) {
            log.error("Ocorreu um erro durante o autosuggest", e);
            return null;
        }
        
    }

    /**
     * Transforma a resposta fornecida pelo SOLR em Documentos Reais, com hit higlighting e snippets.
     *
     * @param queryResponse resultado da busca a ser transformado em documentos reais
     * @param numberOfDocs Numero de resultados da busca a ser transformado em Document
     *
     * @return Lista de DocumentosReais construida a partir da busca.
     */
    public static List<Document> getDocumentosReais(QueryResponse queryResponse, int numberOfDocs) {

        List<Document> retorno = new ArrayList<>();
        SolrDocumentList list = queryResponse.getResults();

        for (int i = 0; i < numberOfDocs && i < list.size(); i++) {

            //O valor de numDoc eh o documento a ser apresentado
            int numDoc = i;

            Document doc = new Document();
            OBAA obaa = new OBAA();
            doc.setMetadata(obaa);

            obaa.setGeneral(new General());

            obaa.setTechnical(new Technical());

            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            if (list.get(numDoc).getFieldValues("obaa.general.title") != null) {
                // id is the uniqueKey field
                String id = (String) list.get(numDoc).getFieldValue("obaa.general.identifier.entry");

                if (highlighting.get(id) != null && highlighting.get(id).get("obaa.general.title") != null) {
                    for (String snippet : highlighting.get(id).get("obaa.general.title")) {
                        obaa.getGeneral().addTitle(snippet);
                    }
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.description") != null) {
                String id = (String) list.get(numDoc).getFieldValue("obaa.general.identifier.entry"); 

                if (highlighting.get(id) != null && highlighting.get(id).get("obaa.general.description") != null) {
                    for (String snippet : highlighting.get(id).get("obaa.general.description")) {
                        obaa.getGeneral().addDescription(snippet.replaceFirst("^\\.", ""));
                    }
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.keyword") != null) {
                String id = (String) list.get(numDoc).getFieldValue("obaa.general.identifier.entry"); // id is the
                // uniqueKey field

                if (highlighting.get(id) != null && highlighting.get(id).get("obaa.general.keyword") != null) {
                    for (String snippet : highlighting.get(id).get("obaa.general.keyword")) {
                        obaa.getGeneral().addKeyword(snippet);
                    }
                }

            }

            if (list.get(numDoc).getFieldValues("obaa.technical.location") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.technical.location")) {
                    log.trace((String) o);
                    obaa.getTechnical().addLocation((String) o);
                }
            }

            /**
             * FEDERACAO, SUBFEDERACAO E REPOSITORIO*
             */
            Repositorio rep = new Repositorio();
            rep.setName((String) list.get(numDoc).getFieldValue("obaa.repName"));
            doc.setRepositorio(rep);

            doc.setId((Integer) list.get(numDoc).getFieldValue("obaa.idBaseDados"));

            retorno.add(doc);
        }

        return retorno;
    }
    
    /**
         * @return Numero de documentos retornados da busca
         * @deprecated não há por que usar
         */
        @Deprecated
        public static int getNumDocs(QueryResponse queryResponse) {
            return (int) queryResponse.getResults().getNumFound();
        }

}
