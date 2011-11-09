package robo.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.TimeZone;

/**
 * Classe Temporizador responsável por executar o robô de hora em hora
 * @author Marcos
 */
public class Temporizador {

//    public static final long TEMPO = (1000 * 7200); // chama o robo a cada 2 hora
    public static final long TEMPO = (1000 * 86400); // informa que eh p/ chamar o robo a cada 24 horas
//    public static final long PRIMEIROTEMPO = (1000 * 30); //informa que a primeira vez vai rodar em 30 segundos
//    public static final long TEMPO = (1000 * 60); // informa que eh p/ chamar o robo a cada 1min
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    /**
     * Temporizador que executa o robô de hora em hora.
     */
    public void start() {
        
        System.out.println(">>>>");
        System.out.println(">>>>Inicio do FEB "+formato.format(new Date()));
        System.out.println(">>>>");

        Timer timer = null;
        if (timer == null) {
            timer = new Timer();

            TimerTask tarefa = new TimerTask() {

                public void run() {
                    System.out.println("FEB: iniciando o Robo "+formato.format(new Date()));
                    Robo iniciaRobo = new Robo();
                    try {

                        //chamar metodo que testa na base se precisa atualizar o repositório
                        iniciaRobo.testaUltimaImportacao();
                    } catch (Exception e) {
                        System.err.println("FEB ERRO: Erro no Temporizador.java: "+e.getMessage());
                    }
                }
            };

////            timer.scheduleAtFixedRate(tarefa, PRIMEIROTEMPO, TEMPO);

            //Get the Date corresponding to 11:01:00 pm today.
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 2);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
            Date time = calendar.getTime();


            timer.schedule(tarefa, time, TEMPO);


        }
    }
}
