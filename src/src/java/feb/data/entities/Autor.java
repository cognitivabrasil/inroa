package feb.data.entities;

/**
 *
 * @author cei
 */
public class Autor {

    private int id;
    private String nome;
    private DocumentoReal doc;

    public DocumentoReal getDoc() {
        return doc;
    }

    public void setDoc(DocumentoReal doc) {
        this.doc = doc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {

        if (nome.length() >= 1024) {
            nome = nome.substring(0, 1000);
        }
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
