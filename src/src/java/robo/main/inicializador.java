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

public class inicializador implements ServletContextListener
{
   public void contextInitialized(ServletContextEvent e)
   {
   }

    public void contextDestroyed(ServletContextEvent sce) {
        Temporizador temporizador = new Temporizador();

        temporizador.start();//chama o metodo que inicia o temporizador para chamar o robo
    }

}