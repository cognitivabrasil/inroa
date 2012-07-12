/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.entities;

/**
 *
 * @author marcos
 */
public class ResultadoConsulta {
    
    private Consulta consulta;
    private DocumentoReal documento;

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public DocumentoReal getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoReal documento) {
        this.documento = documento;
    }
    
    
    
}
