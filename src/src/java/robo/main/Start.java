/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robo.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

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
     public void init(ServletConfig config) throws ServletException {
         // TODO Auto-generated method stub
         super.init();
         
         
         Temporizador temporizador = new Temporizador();
        
        temporizador.start();//chama o metodo que inicia o temporizador para chamar o robo
     }

}