/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postgres;

/**
 *
 * @author Marcos
 */
public class teste {

    public static void main(String[] args) {

        //Date hoje = new Date();
        String resumoLimitado = "marcos nunes";
        if (resumoLimitado.length() >= 5) {
            System.out.println(resumoLimitado.substring(0, 5) + "(...)");
        } else {
            System.out.println(resumoLimitado);
        }

    }
}
