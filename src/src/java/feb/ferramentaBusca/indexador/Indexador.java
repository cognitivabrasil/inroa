/**
 * o pacote indexador correspoonde as classes que fazem a preparaç&atilde;o dos
 * dados
 */
package feb.ferramentaBusca.indexador;

import feb.data.entities.DocumentoReal;
import feb.data.entities.Repositorio;
import feb.data.entities.RepositorioPaginavel;
import feb.data.entities.RepositorioSubFed;
import feb.data.entities.SubFederacao;
import feb.data.entities.SubRepPaginavel;
import feb.data.interfaces.DocumentosDAO;
import feb.data.interfaces.Paginavel;
import feb.data.interfaces.RepositoryDAO;
import feb.data.interfaces.SubFederacaoDAO;
import feb.data.interfaces.TokensDao;
import feb.util.Operacoes;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

/**
 * Indexador é a classe que faz os processos de contruç&atilde;o da base de
 * dados para preparaç&atilde;o da posterior recuperaç&atilde;o de informações
 *
 * @author Luiz Rossi <lh.rossi@gmail.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class Indexador {

    static Logger log = Logger.getLogger(Indexador.class);
    @Autowired
    private DocumentosDAO docDao;
    @Autowired
    private RepositoryDAO repDao;
    @Autowired
    private TokensDao tokenDao;
    @Autowired
    private SubFederacaoDAO subFedDao;
    @Autowired
    private SessionFactory sessionFactory;

    public Indexador() {
    }

    /**
     * Gets the session.
     *
     * @return the session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Indexa todos os reposit&oacute;rios e recalcula o &iacutendice
     *
     */
    public void indexarTodosRepositorios() {
    	Session session = getSession();

        for (Repositorio rep : repDao.getAll()) {
            log.info("Indexando repositorio " + rep.getName());
            rep = (Repositorio) session.load(Repositorio.class, rep.getId());
            indexaDocumentos(new RepositorioPaginavel(rep));
        }

        for (SubFederacao subFed : subFedDao.getAll()) {
            log.info("Indexando a federação " + subFed.getName());
            for (RepositorioSubFed repS : subFed.getRepositorios()) {
            	repS = (RepositorioSubFed) session.load(RepositorioSubFed.class, repS.getId());
                log.info("Indexando Federação: " + subFed.getName() + " Repositório: " + repS.getName());
                indexaDocumentos(new SubRepPaginavel(repS));
            }
        }

        populateR1(); //recalcular o indice. Calcula/preeche as tabelas auxiliares
    }

    /**
     * M&eacute;todo respons&aacute;vel por indexar documentos. Efetua a
     * tokeniza&ccedil&atilde;o, armazena na base e recalcula o indice
     *
     * @param docs Conjunto de documentos que devem ser indexados.
     */
    public void indexaDocumentos(Paginavel<DocumentoReal> docs) {
        Long inicio = System.currentTimeMillis();
        double size = docs.size();
        log.info("Inicio da indexacao... (" + size +")");
        int i = 0;
      
        for(Collection<DocumentoReal> d : docs) {
        	log.debug("indexando " + d.size());
            StopWatch t = new StopWatch();
        	t.start("Indexando ");

        	indexaDocumentos100(d);
        	
        	t.stop();
        	i++;
        	log.debug("Demorou " + t.getTotalTimeSeconds() + ", " + i/size + "%");
        }

        Long fim = System.currentTimeMillis();
        Long total = fim - inicio;
        log.debug("\n Tempo total para inserir objetos: " + Operacoes.formatTimeMillis(total));

    }
    
    public void indexaDocumentos100(Collection<DocumentoReal> docs) {

        Session session = getSession();

        //ateh aqui pegou todos os objetos que fazem parte do indice
        for (DocumentoReal doc : docs) {

            doc.generateTokens();
//            tokenDao.save(doc.getTokens());          
        }    	

        session.flush();
        session.clear();

    }

    /**
     * Esse metodo deverá ser executado sempre depois da adiç&atilde;o de todos
     * os documentos na base. Este que preenche as tabelas auxiliares.
     *
     * @param con a conex&atilde;o como banco de dados
     *
     */
    public void populateR1() {
        log.info("Recalculando o indice...");
        Long inicio = System.currentTimeMillis();


        //apaga as tabelas antes de inserir
        apagarCalculosIndice();

        preencheR1Tokens(); //procura por documentos que nao foram tokenizados e tokeniza.
        log.debug("Calculando e inserindo o r1size");
        String r1size = "INSERT INTO r1size(size) SELECT COUNT(d.id) FROM documentos d;";

        getSession().createSQLQuery(r1size).executeUpdate();

        log.debug("Calculando e inserindo o IDF");
        String r1idf = "INSERT INTO r1idf(token, idf) SELECT t.token, LOG(s.size)-LOG(COUNT(DISTINCT(t.documento_id))) FROM r1tokens t, r1size s group by t.token, s.size;";
        getSession().createSQLQuery(r1idf).executeUpdate();

        log.debug("Calculando e inserindo o TF");
        String r1tf = "INSERT INTO r1tf(documento_id, token, tf) SELECT t.documento_id , t.token, SUM(CASE t.field WHEN 1 THEN 3 ELSE 1 END) FROM r1tokens t GROUP BY t.documento_id, t.token;";
        getSession().createSQLQuery(r1tf).executeUpdate();
        //TODO: O r1idf e o  r1tf podem ser executados juntos para melhorar a performance. Pois são inserts demorados

        log.debug("Calculando e inserindo o r1length");
        String r1Lenght = "INSERT INTO r1length(documento_id, len) SELECT T.documento_id, SQRT(SUM(I.idf*I.idf*T.tf*T.tf)) FROM r1idf I, r1tf T WHERE I.token = T.token GROUP BY T.documento_id;";
        getSession().createSQLQuery(r1Lenght).executeUpdate();

        log.debug("Deletando os pesos existentes");
        String sqlDeletar1weights = "DELETE FROM r1weights";
        getSession().createSQLQuery(sqlDeletar1weights).executeUpdate();

        log.debug("Calculando e inserindo os novos pesos para os tokens de cada documento");
        String r1Weights = "INSERT INTO r1weights(documento_id, token, weight)  SELECT  T.documento_id, T.token, (CASE L.len WHEN 0 THEN 0 ELSE I.idf*T.tf/L.len END) as weight FROM r1idf I, r1tf T, r1length L  WHERE I.token = T.token AND T.documento_id = L.documento_id;";
        getSession().createSQLQuery(r1Weights).executeUpdate();

//        apagarCalculosIndice(con);

        Long fim = System.currentTimeMillis();
        Long total = fim - inicio;
        log.info("Tempo total para o recalculo do indice: " + Operacoes.formatTimeMillis(total));
    }

    /**
     * Apaga as tabelas r1idf, r1size, r1tf e r1length da base de dados. Estas
     * tabelas armazenam os calculos do indice.
     *
     */
    private void apagarCalculosIndice() {
        String sql1 = "DELETE FROM r1idf;";
        String sql2 = "DELETE FROM r1size;";
        String sql3 = "DELETE FROM r1tf;";
        String sql4 = "DELETE FROM r1length;";
        String sql5 = "DELETE FROM consultas;";

        getSession().createSQLQuery(sql1).executeUpdate();
        getSession().createSQLQuery(sql2).executeUpdate();
        getSession().createSQLQuery(sql3).executeUpdate();
        getSession().createSQLQuery(sql4).executeUpdate();
        getSession().createSQLQuery(sql5).executeUpdate();

        //TODO: ver como executar todos juntos.
    }

    /**
     * Procura na base de dados por documentos que não foram tokenizados e
     * tokeniza-os, excluindo os que não tiverem pelo menos um atributo
     * utilizado para calcular o &iacite;ndice.
     */
    private void preencheR1Tokens() {

        for (DocumentoReal doc : docDao.getwithoutToken()) {
            if (doc.isIndexEmpty()) {
                log.warn("Foi encontrado um documento sem nenhum atributo. \n obaaEntry: " + doc.getObaaEntry());
                docDao.delete(doc);
                log.warn("Documento deletado");
            } else {
                doc.generateTokens();

            }
        }
    }
}
