

// Generated 20/07/2011 15:25:15 by Hibernate Tools 3.2.0.b9

package modelos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  
 *       Represents a single playable track in the music database.
 *       @author Jim Elliott (with help from Hibernate)
 *     
 */
public class Objeto  implements java.io.Serializable {


     private int id;
     private String atributo;
     private String valor;

    public Objeto() {
    }

	
    public int getId() {
        return this.id;
    }
    
    protected void setId(int id) {
        this.id = id;
    }

	/**
	 * @return the atributo
	 */
	public String getAtributo() {
		return atributo;
	}

	/**
	 * @param atributo the atributo to set
	 */
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
    


}


