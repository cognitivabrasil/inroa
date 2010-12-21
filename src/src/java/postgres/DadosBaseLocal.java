/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package postgres;

import java.util.jar.Attributes;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author LuizRossi
 */
public class DadosBaseLocal {
    private static boolean DEBUG = true;
    private String usuarioPostgre;
    private String senhaPostgre;
    private int portaPostgres;
    private String usuarioAdmin;
    private String senhaAdmin;
    private static StringBuffer valorAtual = new StringBuffer(50);

    public void setPortaPostgres(int portaPostgres) {
        this.portaPostgres = portaPostgres;
    }

    public void setSenhaAdmin(String senhaAdmin) {
        this.senhaAdmin = senhaAdmin;
    }

    public void setSenhaPostgre(String senhaPostgre) {
        this.senhaPostgre = senhaPostgre;
    }

    public void setUsuarioAdmin(String usuarioAdmin) {
        this.usuarioAdmin = usuarioAdmin;
    }

    public void setUsuarioPostgre(String usuarioPostgre) {
        this.usuarioPostgre = usuarioPostgre;
    }

    public int getPortaPostgres() {
        return portaPostgres;
    }

    public String getSenhaAdmin() {
        return senhaAdmin;
    }

    public String getSenhaPostgre() {
        return senhaPostgre;
    }

    public String getUsuarioAdmin() {
        return usuarioAdmin;
    }

    public String getUsuarioPostgre() {
        return usuarioPostgre;
    }

    public void ler(){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser parser = factory.newSAXParser();

            SaxHandler handler = new SaxHandler();

            InputSource input = new InputSource("src\\java\\postgres\\febPropriedades.xml");
            parser.parse(input, handler);

        } catch (Exception ex) {
            System.out.println("Erro ao ler o arquivo do XML: "+ex);
        }
  }

    /**
     * Our own implementation of SAX handler reading
     * a purchase-order data.
     */

    private static final class SaxHandler extends DefaultHandler {

    @Override
        public void startDocument() throws SAXException {
            if (DEBUG) {
                System.out.println("Document processing started");
            }
        }

        // notifies about finish of parsing:
        @Override
        public void endDocument() throws SAXException {
            if (DEBUG) {
                System.out.println("Document processing finished");
            }
        }

        
    public void startElement(String uri, String localName, String tag, Attributes atributos) {

            if (DEBUG) {
                System.out.println("start element");
            }

            if (tag.equals("dados")) {
            } else if (tag.equals("usuario")) {
            } /* if (...)
            } */ else {
                throw new IllegalArgumentException("Element '"
                        + tag + "' is not allowed here");
            }
        }

        // we leave element 'qName' without any actions:
        public void endElement(String uri, String localName, String tag)
        throws SAXException {
            if (DEBUG) System.out.println("end element "+ tag);
            // do nothing;
        }

            /**
     * Indica que o parser achou algum Texto (Informação).
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        //System.out.print(String.copyValueOf(ch,start,length).trim());

        valorAtual.append(ch, start, length);

    }
    }
    public static void main(String[] args) {
        DadosBaseLocal d = new DadosBaseLocal();
        d.ler();
    }
}