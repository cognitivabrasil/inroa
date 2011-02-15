package postgres;

import java.io.FileNotFoundException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class DadosTeste {

    public DadosTeste() {
    }

    public boolean load() {
        try {
            String path = "./src/java/postgres/febPropriedades.xml";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(path);
            Element elem = doc.getDocumentElement();
            String userPost = elem.getAttribute("porta");

            System.out.println("user: "+userPost);
            return true;

        } catch (FileNotFoundException i){
            System.out.println("FEB: O arquivo febPropriedades.xml não foi localizado, coloque-o na pasta src/java/postgres/");
            return false;
            
        } catch (SAXException s){
            System.out.println("FEB: O arquivo febPropriedades.xml está mal formatado, verifique se ele está no seguinte formato:\n <dados>\n\t<postgre>\n\t\t<usuario_p>nome do usuario do postgre</usuario_p>\n\t\t<senha_p>senha do postgre</senha_p>\n\t\t<porta>porta do postgre, o padrao e 5432</porta>\n\t\t<base_de_dados>nome da base de dados para p FEB, o padrao e federacao</base_de_dados>\n\t</postgre>\n\t<admin>\n\t\t<usuario>nome de usuario para a ferramenta administrativa</usuario>\n\t\t<senha>senha para a ferramenta administrativa</senha>\n\t</admin>\n</dados>");
            System.out.println(s);
            return false;
        }
        catch (Exception e) {
            System.out.println("FEB: O arquivo febPropriedades.xml foi localizado mas não pôde ser lido, verifique-o e tente novamente " + e);
            return false;
        }
    }

    public static void main(String[] args) {
        DadosTeste d = new DadosTeste();
        d.load();
    }
}
