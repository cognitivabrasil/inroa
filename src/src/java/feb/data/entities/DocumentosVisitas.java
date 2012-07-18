package feb.data.entities;

/**
 *
 * @author Luiz H. L. Rossi <lh.rossi@cognitivabrasil.com>
 */
public class DocumentosVisitas {
    private int id;
    private DocumentoReal documento;
    private Visita visita;

    public DocumentosVisitas() {
    }

    public DocumentoReal getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoReal documento) {
        this.documento = documento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }    
    
}
