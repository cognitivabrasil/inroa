package postgres;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class DadosTeste {
    
    public DadosTeste() {
    }

    public boolean load() {
        DOMParser parser = new DOMParser();
        String document = "<dados>"
                + "<postgre>"
                + "   <usuario_p>feb</usuario_p>"
                + "  <senha_p>12345</senha_p>"
                + " <porta>5432</porta>"
                + "<base_de_dados>federacao</base_de_dados>"
                + " </postgre>"
                + " <admin>"
                + "    <usuario>admin</usuario>"
                + "       <senha>teste</senha>"
                + "  </admin>"
                + "</dados>";

        try {
            parser.parse(document);
            System.out.println(document + " is well-formed.");
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao ler XML " + e);
            return false;
        }
    }

    public static void main(String[] args) {
        DadosTeste d = new DadosTeste();
    }
}
