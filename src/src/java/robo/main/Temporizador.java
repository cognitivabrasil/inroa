
package robo.main;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

/**
 * Classe Temporizador responsável por executar o robô de hora em hora
 * @author Marcos
 */
public class Temporizador {

//    public static final long TEMPO = (1000 * 7200); // chama o robo a cada 2 hora
    public static final long TEMPO = (1000 * 86400); // informa que eh p/ chamar o robo a cada 24 horas
    public static final long PRIMEIROTEMPO = (1000 * 30); //informa que a primeira vez vai rodar em 30 segundos
//    public static final long TEMPO = (1000 * 60); // informa que eh p/ chamar o robo a cada 1min
    
    /**
     * Temporizador que executa o robô de hora em hora.
     */
    public void start() {
        System.out.println(">>>>");
        System.out.println(">>>>Inicio do processo temporizador");

        Timer timer = null;
        if (timer == null) {
            timer = new Timer();
            
            TimerTask tarefa = new TimerTask() {

                public void run() {
                                        
                    Robo iniciaRobo = new Robo();
                    try {
                        
                        //chamar metodo que testa na base se precisa atualizar o repositório
                        iniciaRobo.testaUltimaIportacao();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
//            timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);
            timer.scheduleAtFixedRate(tarefa, PRIMEIROTEMPO, TEMPO);
//            timer.scheduleAtFixedRate(tarefa, new Date(), TEMPO);
//            Date midnight_jan1_2000 = new Date(30*365L*24L*60L*60L*1000L);

        }
    }
}
