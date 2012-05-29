package robo.main;

import ferramentaBusca.indexador.Indexador;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import postgres.Conectar;
import robo.atualiza.Repositorios;
import robo.atualiza.SubFederacaoOAI;

/**
 * Ferramenta de Sincronismo (Robô)
 * @author Marcos
 */
public class Robo {    
    /**
     * Principal m&eacute;todo do rob&ocirc;. Este m&eacute;todo efetua uma consulta na base de dados, procurando por reposit&oacute;rios que est&atilde;o desatualizados, quando encontra algum, chama o m&eacute;todo que atualiza o repositório.
     * @author Marcos Nunes
     */
	@Transactional
    public void testaUltimaImportacao() {
        Logger log = Logger.getLogger(Robo.class.getName());
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        log.info(">>>");
        log.info(">>> FEB: iniciando o Robo. "+dataFormat.format(new Date()));
        log.info(">>>");
        
        Long inicioRobo = System.currentTimeMillis();
        Indexador indexar = new Indexador();
        Conectar conectar = new Conectar(); //instancia uma variavel da classe Conectar

        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
        boolean repAtualizado = false;
        boolean subFedAtualizada = false;
        
//TESTA/ATUALIZA SUBFEDERACAO
            SubFederacaoOAI subFed = new SubFederacaoOAI();
            subFedAtualizada = subFed.pre_AtualizaSubFedOAI(indexar);
            indexar.setDataXML(null);

//TESTA REPOSITORIO
            Repositorios repositorio = new Repositorios();
            repAtualizado = repositorio.testa_atualizar_repositorio(indexar);

            if (con != null) {

//TESTA SE PRECISA RECALCULA O INDICE
            try {
//           if ((subFedAtualizada || repAtualizado) && nDocumentos!=selectNumeroDocumentos(con)) {
                if (subFedAtualizada || repAtualizado) {
                    log.info("FEB: recalculando o indice " + dataFormat.format(new Date()));
                    Long inicioIndice = System.currentTimeMillis();
                    indexar.populateR1(con);
                    Long fimIndice = System.currentTimeMillis();
                    log.info("FEB: indice recalculado em: " + (fimIndice - inicioIndice) / 1000 + " segundos! ");
                } else {
                    log.info("FEB: NAO existe atualizaçoes para os repositorios! " + dataFormat.format(new Date()));
                }
                Long finalRobo = System.currentTimeMillis();
                Long tempoTotal = (finalRobo-inicioRobo)/1000;
                log.debug("Levou "+tempoTotal+" segundos todo o processo do Robo");
                
            } catch (SQLException e) {
                log.error("FEB: Erro com sql no robo", e);
            } finally {
//            Long fim = System.currentTimeMillis();
//            System.out.println("#TIMER: Tempo total: " + (fim - inicio) + " milisegundos! " );
                try {
                    con.close(); //fechar conexao
                } catch (SQLException e) {
                    log.error("Erro ao fechar a conexão no robo", e);
                }
            }
        }
    }
}
