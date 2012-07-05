package modelos;

import java.util.Date;

/**
 *
 * @author cei
 */
public class Visitas {
    private int id;
    private Date horario;

    public Visitas() {
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    
}
