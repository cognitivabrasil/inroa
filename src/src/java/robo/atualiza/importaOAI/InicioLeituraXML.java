package robo.atualiza.importaOAI;

import OBAA.OBAA;
import OBAA.OaiOBAA;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import ferramentaBusca.indexador.Indexador;
import java.sql.Connection;
import modelos.DocumentosService;
import modelos.Repositorio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import postgres.Conectar;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Marcos
 */
public class InicioLeituraXML {

    /**
     * Método que chama o parser xml o qual insere na base de dados os registros
     * contidos nos arquivos xml recebidos como parâmetro. Recebe um ou mais
     * arquivos xml para realizar o parser e inserir objetos.
     *
     * @param caminhoXML ArrayList de Strings contendo caminhos para os arquivos
     * xml
     * @param id id do repositório na base de dados
     * @param indexar variavel do tipo Indexador
     * @param con Conex&atilde;o com a base de dados relacional.
     *
     */
    public boolean leXMLgravaBase(
            ArrayList<String> caminhoXML,
            int id,
            Indexador indexar,
            Connection con) throws ParserConfigurationException, SAXException, IOException {
        boolean atualizou = false;

        try {

            Conectar c = new Conectar();

            Connection conn = c.conectaBD();
            SessionFactory sessionFactory = new Configuration().configure().setProperty("hibernate.show_sql", "true").buildSessionFactory();
            Session session = sessionFactory.openSession(conn);
            Repositorio r = (Repositorio) session.get(Repositorio.class, id);
            DocumentosService service = new DocumentosService(r);

            XmlSaxReader reader = new XmlSaxReader();
            for (int i = 0; i < caminhoXML.size(); i++) {
                File arquivoXML = new File(caminhoXML.get(i));
                if (arquivoXML.isFile() || arquivoXML.canRead()) {

                    //reader.parser(caminhoXML.get(i), indexar, con, id);//efetua a leitura do xml e insere os objetos na base de dados
                    OaiOBAA oai = OaiOBAA.fromFilename(caminhoXML.get(i));
                    for (int j = 0; j < oai.getSize() - 1; j++) {
                        System.out.println("j: " + new Integer(j) + "/" + new Integer(oai.getSize()));
                        metadata.Header header = oai.getHeader(j);
                        OBAA obaa = null;
                        if (!header.isDeleted()) {
                            obaa = oai.getMetadata(j);
                            System.out.println(obaa.getTitles().get(0));

                        }
                        service.save(obaa, oai.getHeader(j));
                        System.out.println("Saved");

                        atualizou = true;

                    }
                    System.out.println("Out of for...");

                    //apaga arquivo XML
                    arquivoXML.delete();

                } else {
                    System.err.println("FEB ERRO: O arquivo informado nao eh um arquivo ou nao pode ser lido. Caminho: " + caminhoXML.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
        return atualizou;
    }
}
