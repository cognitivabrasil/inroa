/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OBAA.Accessibility;

/**
 *
 * <div class="en">
 *
 * according to IMS
 * </div>
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class Equivalent {
    
    private PrimaryResource prmairyResouce;
    private PrimaryFile primaryFile;
    private boolean supplementaty;
    private Content content;
    
    public Equivalent() {
        prmairyResouce = new PrimaryResource();
        primaryFile = new PrimaryFile();
        supplementaty = false;
        content = new Content();
        
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setPrimaryFile(PrimaryFile primaryFile) {
        this.primaryFile = primaryFile;
    }

    public void setPrmairyResouce(PrimaryResource prmairyResouce) {
        this.prmairyResouce = prmairyResouce;
    }

    public void setSupplementaty(boolean supplementaty) {
        this.supplementaty = supplementaty;
    }

    public Content getContent() {
        return content;
    }

    public PrimaryFile getPrimaryFile() {
        return primaryFile;
    }

    public PrimaryResource getPrmairyResouce() {
        return prmairyResouce;
    }
    
    public boolean isSupplementaty(){
        return supplementaty;
    }
}
