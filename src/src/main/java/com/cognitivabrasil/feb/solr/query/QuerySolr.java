package com.cognitivabrasil.feb.solr.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.General.General;
import cognitivabrasil.obaa.Technical.Technical;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.spring.FebConfig;

public class QuerySolr {

    private final HttpSolrServer serverSolr;
    private SolrQuery query;
    private QueryResponse queryResponse;
    private static final Logger log = LoggerFactory.getLogger(QuerySolr.class);

    public QuerySolr(FebConfig c) {
        serverSolr = new HttpSolrServer(c.getSolrUrl());
        query = new SolrQuery();
        queryResponse = new QueryResponse();

    }

    /**
     * Realiza a busca em uma url determinada do Solr (se for utilizado a url padrao a funcao nao precisa de parametros)
     *
     * @param url URL do SOLR
     */
    public QuerySolr(String url) {
        serverSolr = new HttpSolrServer(url);
        query = new SolrQuery();
        queryResponse = new QueryResponse();

    }

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
        query = CriaQuery.criaQueryCompleta(pesquisa);

        query.setStart(offset);
        query.setRows(limit);

        query.setRequestHandler("/feb");

        queryResponse = serverSolr.query(query);
        
        return queryResponse;
    }
    public QueryResponse autosuggest(String auto) {
        query.setRequestHandler("/suggest");
        query.setQuery(auto);
        
        try {
            queryResponse = serverSolr.query(query);
        }
        catch (SolrServerException e) {
            log.error("Ocorreu um erro durante o autosuggest", e);
            return null;
        }
        
        return queryResponse;
    }

    /**
     * @return Numero de documentos retornados da busca
     */
    public int getNumDocs() {
        return (int) queryResponse.getResults().getNumFound();
    }

    /**
     * Transoforma a resposta fornecida pelo SOLR em Documentos Reais, com hit higlighting e snippets.
     * 
     * Vai retornar somente as palavras-chave que 
     *
     * @param start Posicao do primeiro resultado da busca a ser transformado em Document
     * @param offset Numero de resultados da busca a ser transformado em Document
     * @return Lista de DocumentosReais construida a partir da busca.
     */
    public List<Document> getDocumentosReais(int start, int offset) {

        List<Document> retorno = new ArrayList<>();
        SolrDocumentList list = queryResponse.getResults();

        for (int i = 0; i < offset && i < list.size(); i++) {

            //O valor de numDoc eh o documento a ser apresentado
            int numDoc = i;

            Document doc = new Document();
            OBAA obaa;
            try {
                obaa = doc.getMetadata();
            } catch (IllegalStateException il) {
                obaa = new OBAA();
                doc.setMetadata(obaa);
            }

            obaa.setGeneral(new General());

            obaa.setTechnical(new Technical());

            if (list.get(numDoc).getFieldValues("obaa.general.title") != null) {
                String id = (String) list.get(numDoc).getFieldValue("obaa.general.identifier.entry"); // id is the
                // uniqueKey field

                if (queryResponse.getHighlighting().get(id) != null) {
                    if (queryResponse.getHighlighting().get(id).get("obaa.general.title") != null) {
                        for (String snippet : queryResponse.getHighlighting().get(id).get("obaa.general.title")) {
                            obaa.getGeneral().addTitle(snippet);
                        }
                    }
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.description") != null) {

                /*
                 * for (Object o : list.get(numDoc).getFieldValues("obaa.general.description")) {
                 * obaa.getGeneral().addDescription((String) o); }
                 */

                String id = (String) list.get(numDoc).getFieldValue("obaa.general.identifier.entry"); // id is the
                                                                                                      // uniqueKey field

                if (queryResponse.getHighlighting().get(id) != null) {
                    if (queryResponse.getHighlighting().get(id).get("obaa.general.description") != null) {
                        for (String snippet : queryResponse.getHighlighting().get(id).get("obaa.general.description")) {
                            obaa.getGeneral().addDescription(snippet.replaceFirst("^\\.", ""));
                        }
                    }
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.keyword") != null) {
                String id = (String) list.get(numDoc).getFieldValue("obaa.general.identifier.entry"); // id is the
                // uniqueKey field

                if (queryResponse.getHighlighting().get(id) != null) {
                    if (queryResponse.getHighlighting().get(id).get("obaa.general.keyword") != null) {
                        for (String snippet : queryResponse.getHighlighting().get(id).get("obaa.general.keyword")) {
                            obaa.getGeneral().addKeyword(snippet);
                        }
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

}
