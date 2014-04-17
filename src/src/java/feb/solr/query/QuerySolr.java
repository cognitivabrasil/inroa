package feb.solr.query;

import feb.data.entities.Consulta;
import feb.data.entities.DocumentoReal;
import feb.data.entities.Repositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class QuerySolr {

    private final HttpSolrServer serverSolr;
    private SolrQuery query;
    private QueryResponse queryResponse;

    public QuerySolr() {
        serverSolr = new HttpSolrServer("http://localhost:8983/solr");
        query = new SolrQuery();
        queryResponse = new QueryResponse();

    }

    /**
     * Realiza a busca em uma url determinada do Solr (se for utilizado a url
     * padrao a funcao nao precisa de parametros)
     *
     * @param url URL do SOLR
     */
    public QuerySolr(String url) {
        serverSolr = new HttpSolrServer(url);
        query = new SolrQuery();
        queryResponse = new QueryResponse();

    }

    /**
     * Realiza a query no SOLR nos campos padroes definidos no schema.xml
     * (titulo, keywords e description). O resultado da pesquisa eh armazenado
     * na variavel queryResponse e pode ser utilizado posteriormente
     *
     * @param pesquisa Os termos que serao pesquisados
     * @param offset Posicao do primeiro resultado a aparecer
     * @param limit Numero de resultados desejados
     * @return True se tudo correu bem e false se nao foi possivel fazer a
     * pesquisa (server offline?)
     */
    public boolean pesquisaSimples(String pesquisa, int offset, int limit) {

        // Parametros da pesquisa
        
    // Isso talvez seja necessario para os pesos dos campos
        query.set("qt", "\browse");
//query.setQueryType("\browser");

        query = new SolrQuery(pesquisa);
        query.setStart(offset);
        query.setRows(limit);

        try {
            queryResponse = serverSolr.query(query);
            return true;
        } catch (SolrServerException ex) {
            //TODO   Implementar Logger aqui!
            //("Ocorreu um erro durante a pesquisa");
            Logger.getLogger(QuerySolr.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /**
     * Realiza a busca avancada. A funcao verifica quais campos foram escolhidos
     * e realiza a busca neles PERGUNTA: REALIZA A BUSCA APENAS NELES OU NOS
     * PRINCIPAIS TAMBEM? E A BUSCA PODE SER DIFERENTE PARA CADA CAMPO?
     *
     * @param pesquisa Um Objeto consulta com todas as informacoes da busca
     * (String, campos, etc)
     * @param offset Posicao do primeiro resultado a aparecer
     * @param limit Numero de resultados desejados
     * @return True se tudo correu bem e false se nao foi possivel fazer a
     * pesquisa (server offline?)
     */
    public boolean pesquisaCompleta(Consulta pesquisa, int offset, int limit) {

        String campos = CriaQuery.criaQueryCompleta(pesquisa);

        query.setQuery(campos);

        query.setStart(offset);
        query.setRows(limit);

        try {
            queryResponse = serverSolr.query(query);
            return true;

        } catch (SolrServerException ex) {
            System.out.println("Ocorreu um erro durante a pesquisa");
            Logger.getLogger(QuerySolr.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

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
     * @param start Posicao do primeiro resultado da busca a ser transformado em
     * DocumentoReal
     * @param offset Numero de resultados da busca a ser transformado em
     * DocumentoReal
     * @return Lista de DocumentosReais construida a partir da busca.
     */
    public List<DocumentoReal> getDocumentosReais(int start, int offset) {

        List<DocumentoReal> retorno = new ArrayList<DocumentoReal>();
        SolrDocumentList list = queryResponse.getResults();

        for (int i = 0; i < offset && i < list.size(); i++) {

            //O valor de numDoc eh o documento a ser apresentado
            int numDoc = i;

            //Apenas para ver a diferenca dos searchs.
         //   System.out.println(list.get(numDoc).toString());

            DocumentoReal doc = new DocumentoReal();

            if (list.get(numDoc).getFieldValues("obaa.general.title") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.general.title")) {
                    doc.addTitle((String) o);
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.description") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.general.description")) {
                    doc.addDescription((String) o);
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.keyword") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.general.keyword")) {
                    doc.addKeyword((String) o);
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.technical.location") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.technical.location")) {
                    doc.addLocation((String) o);
                }
            }

            /**
             * FEDERACAO, SUBFEDERACAO E REPOSITORIO*
             */
            Repositorio rep = new Repositorio();
            rep.setName((String) list.get(numDoc).getFieldValue("obaa.nomeRep"));
            doc.setRepositorio(rep);

            doc.setId((Integer) list.get(numDoc).getFieldValue("obaa.idBaseDados"));

            retorno.add(doc);
        }

        return retorno;
    }

}
