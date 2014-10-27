package com.cognitivabrasil.feb.solr.query;

import cognitivabrasil.obaa.General.General;
import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.Technical.Technical;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.spring.FebConfig;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.search.QueryParsing;

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
     * Realiza a query no SOLR nos campos padroes definidos no schema.xml (titulo, keywords e description). O resultado
     * da pesquisa eh armazenado na variavel queryResponse e pode ser utilizado posteriormente
     *
     * @param pesquisa Os termos que serao pesquisados
     * @param offset Posição do primeiro resultado a aparecer
     * @param limit Número de resultados desejados
     * @throws SolrServerException - Não foi possível fazer a pesquisa (server offline?)
     */
    public void pesquisaSimples(String pesquisa, int offset, int limit) throws SolrServerException {

        query = new SolrQuery();

        query.setRequestHandler("/feb");
        query.setStart(offset);
        query.setRows(limit);

        query.setQuery(pesquisa);

        queryResponse = serverSolr.query(query);

    }

    /**
     * Realiza a busca avancada. A funcao verifica quais campos foram escolhidos e realiza a busca neles PERGUNTA:
     * REALIZA A BUSCA APENAS NELES OU NOS PRINCIPAIS TAMBEM? E A BUSCA PODE SER DIFERENTE PARA CADA CAMPO?
     *
     * @param pesquisa Um Objeto consulta com todas as informacoes da busca (String, campos, etc)
     * @param offset Posicao do primeiro resultado a aparecer
     * @param limit Numero de resultados desejados
     * @throws SolrServerException - Não foi possível fazer a pesquisa (server offline?)
     */
    public void pesquisaCompleta(Consulta pesquisa, int offset, int limit) throws SolrServerException {

        String campos = CriaQuery.criaQueryCompleta(pesquisa);

        query = new SolrQuery();

        query.setQuery(campos);

        query.setStart(offset);
        query.setRows(limit);

        query.setRequestHandler("/feb_avancado");

        //Para definir que espaco em branco sera considerado OR e nao +
        query.set(QueryParsing.OP, "OR");

        queryResponse = serverSolr.query(query);

    }

    /**
     * @return Numero de documentos retornados da busca
     */
    public int getNumDocs() {
        return (int) queryResponse.getResults().getNumFound();
    }

    /**
     * Transoforma a resposta fornecida pelo SOLR em Documentos Reais
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
                for (Object o : list.get(numDoc).getFieldValues("obaa.general.title")) {
                    obaa.getGeneral().addTitle((String) o);
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.description") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.general.description")) {
                    obaa.getGeneral().addDescription((String) o);
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.keyword") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.general.keyword")) {
                    obaa.getGeneral().addKeyword((String) o);
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
