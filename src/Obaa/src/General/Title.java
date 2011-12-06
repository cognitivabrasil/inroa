package General;

/**
 * <div class="en">
 *
 * Name given to this learning object.
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 *
 * There is no language treatment implemented in this metadata.
 * <div class="br">
 * Nome do objeto de aprendizagem.
 *
 * Não há tratamento de idiomas implementado para esse metadado.
 * Adaptado de http://www.portalobaa.org/
 * </div>
 * 
 * 
 * @author LuizRossi
 */
public class Title {

    private String title;

    public Title (){
        this.title = "";
    }

    public Title (String title){
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


}
