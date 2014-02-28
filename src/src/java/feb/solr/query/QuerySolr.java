package feb.solr.query;

import feb.data.entities.DocumentoReal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class QuerySolr {

    private HttpSolrServer serverSolr;
    private SolrQuery query;
    private List<Integer> idsDocumentos;
    private QueryResponse queryResponse;

    public QuerySolr() {
        serverSolr = new HttpSolrServer("http://localhost:8983/solr");
        query = new SolrQuery();
        queryResponse = new QueryResponse();
        idsDocumentos = new ArrayList<Integer>();

    }

    public QuerySolr(String url) {
        serverSolr = new HttpSolrServer(url);
        query = new SolrQuery();
        queryResponse = new QueryResponse();
        idsDocumentos = new ArrayList<Integer>();

    }

    public List<Integer> getIDs() {
        return idsDocumentos;
    }

    public void setIDs(List<Integer> ids) {
        idsDocumentos = ids;
    }

    public List<Integer> maisResultados(int... indice) {
        List<Integer> ids = new ArrayList<Integer>();

        //Pega os resultados de X a Y, por exemplo
        return ids;
    }

    public String printIds() {
        String ids = "";
        for (int i = 0; i < idsDocumentos.size(); i++) {
            ids += idsDocumentos.get(i) + "\n";
        }

        return ids;
    }

    public boolean pesquisaSimples(String pesquisa, int offset, int limit) {

        query = new SolrQuery(pesquisa);

        query.setStart(offset);
        query.setRows(limit);
        System.out.println("Consulta Limit: " + limit);
        System.out.println("Consulta Offset: " + offset);
        try {

            queryResponse = serverSolr.query(query);
            System.out.println("QueryResponde da busca: " + queryResponse.getResults().size());
            return true;

        } catch (SolrServerException ex) {
            System.out.println("Ocorreu um erro durante a pesquisa");
            Logger.getLogger(QuerySolr.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public int getNumDocs() {
        return (int) queryResponse.getResults().getNumFound();
    }

    public boolean pesquisaCompleta(String pesquisa, int... rows) {

        String busca = "";
        String[] searchFields = pesquisa.split(" ");
        for (String s : searchFields) {
            busca = busca.concat(" OR " + s + ":" + pesquisa);
        }

        //Remove o " OR " inicial
        busca = busca.substring(4);
        query.setQuery(busca);

        int startRow = rows.length > 0 ? rows[0] : 0;
        if (rows.length > 1) {
            query.setRows(rows[1]);
        }

        query.setStart(startRow);

        try {
            queryResponse = serverSolr.query(query);
            SolrDocumentList list = queryResponse.getResults();

            for (int i = 0; i < list.size(); i++) {
                idsDocumentos.add((Integer) list.get(i).getFieldValue("obaa.idBaseDados"));
            }

            return true;

        } catch (SolrServerException ex) {
            System.out.println("Ocorreu um erro durante a pesquisa");
            Logger.getLogger(QuerySolr.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public List<DocumentoReal> getDocumentosReais(int start, int offset) {
        List<DocumentoReal> retorno = new ArrayList<DocumentoReal>();

        SolrDocumentList list = queryResponse.getResults();
        System.out.println("QueryResponde da do Real : " + queryResponse.getResults().size());
        System.out.println("List do DocReais: " + list.size());

        for (int i = 0; i < offset && i < list.size(); i++) {
            System.out.println("i: " + i);
            //O valor de numDoc eh o documento a ser apresentado
            int numDoc = i;
            DocumentoReal doc = new DocumentoReal();

            if (list.get(numDoc).getFieldValues("obaa.general.title") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.general.title")) {
                    //       System.out.println("title " +(String)o);
                    doc.addTitle((String) o);
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.description") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.general.description")) {
                    //  System.out.println("Descricao " +(String)o);
                    doc.addDescription((String) o);
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.keyword") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.general.keyword")) {
                    //   System.out.println("key "+(String)o);
                    doc.addKeyword((String) o);
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.technical.location") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.technical.location")) {
                    //     System.out.println("Local "+(String)o);
                    doc.addLocation((String) o);
                }
            }

            doc.setId((Integer) list.get(numDoc).getFieldValue("obaa.idBaseDados"));

            retorno.add(doc);
        }

        return retorno;
    }

}
