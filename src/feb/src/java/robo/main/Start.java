/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robo.main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


//teste


/**
 * Servlet que será executana na inicialização do tomcat, para iniciar o timer que chama o robô
 * @author Marcos Nunes
 */
public class Start extends HttpServlet {
   


    /**
     * Responsável por chamar o metodo start da classe robo.main que inicializa o robô.
     * @throws ServletException se ocorrer um erro sobre servlet.
     */
    @Override
     public void init() throws ServletException {
         // TODO Auto-generated method stub
         super.init();
         Temporizador run = new Temporizador();
        
        run.start();//chama o metodo que inicia o temporizador para chamar o robo
     }


}
