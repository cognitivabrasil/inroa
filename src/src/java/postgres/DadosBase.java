/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package postgres;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;

/**
 *
 * @author LuizRossi
 */
public class DadosBase extends HttpServlet {




    public void teste() {


        try {
            DadosBase d = new DadosBase();
            d.getServletConfig().getServletContext().getResourceAsStream("/WEB-INF/myfile");
            URL url = d.getServletContext().getResource("/WEB-INF/pass");
            System.out.println("url: "+url);

        } catch (Exception e){
            System.out.println("Caminho do arquivo propreties mal formatado: "+e);
        }
    }
}
