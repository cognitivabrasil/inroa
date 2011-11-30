/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robo.main;

/**
 *
 * @author Marcos
 */
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

public class Inicializador implements ServletContextListener
{
   public void contextInitialized(ServletContextEvent e)
   {


       Temporizador temporizador = new Temporizador();

        temporizador.start();//chama o metodo que inicia o tempoorizador para chamar o robo

   }

    public void contextDestroyed(ServletContextEvent sce) {

    }

}