package robo.main;

import java.sql.*;
import ferramentaBusca.indexador.Indexador;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void testaUltimaImportacao() {
        Long inicioRobo = System.currentTimeMillis();
        Indexador indexar = new Indexador();
        Conectar conectar = new Conectar(); //instancia uma variavel da classe Conectar

        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
        boolean repAtualizado = false;
        boolean subFedAtualizada = false;
        if (con != null) {

            SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

//TESTA/ATUALIZA SUBFEDERACAO
            SubFederacaoOAI subFed = new SubFederacaoOAI();
            subFedAtualizada = subFed.pre_AtualizaSubFedOAI(con, indexar);
            indexar.setDataXML(null);

//TESTA REPOSITORIO
            Repositorios repositorio = new Repositorios();
            repAtualizado = repositorio.testa_atualizar_repositorio(con, indexar);


//TESTA SE PRECISA RECALCULA O INDICE
            try {
//           if ((subFedAtualizada || repAtualizado) && nDocumentos!=selectNumeroDocumentos(con)) {
                if (subFedAtualizada || repAtualizado) {
                    System.out.println("FEB: recalculando o indice " + dataFormat.format(new Date()));
                    Long inicioIndice = System.currentTimeMillis();
                    indexar.populateR1(con);
                    Long fimIndice = System.currentTimeMillis();
                    System.out.println("FEB: indice recalculado em: " + (fimIndice - inicioIndice) / 1000 + " segundos! ");
                } else {
                    System.err.println("FEB: NAO existe atualizaçoes para os repositorios! " + dataFormat.format(new Date()));
                }
                Long finalRobo = System.currentTimeMillis();
                Long tempoTotal = ((finalRobo-inicioRobo)/1000)/24;
                
            } catch (SQLException e) {
                System.err.println("FEB: Erro com sql no robo: " + e.getMessage());
            } finally {
//            Long fim = System.currentTimeMillis();
//            System.out.println("#TIMER: Tempo total: " + (fim - inicio) + " milisegundos! " );
                try {
                    con.close(); //fechar conexao
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar a conexão no robo: " + e.getMessage());
                }
            }
        }
    }
}
