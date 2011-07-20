package robo.main;

import java.sql.*;
import ferramentaBusca.indexador.Indexador;
import java.util.Date;
import postgres.Conectar;
import robo.atualiza.SubFederacao;
import robo.atualiza.Repositorios;

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
        Indexador indexar = new Indexador();
        Conectar conectar = new Conectar(); //instancia uma variavel da classe Conectar

        Connection con = con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
        boolean repAtualizado = false;
        boolean subFedAtualizada = false;

//TESTA/ATUALIZA SUBFEDERACAO
        SubFederacao subFed = new SubFederacao();
        subFedAtualizada = subFed.atualiza_subFederacao(con);

//TESTA REPOSITORIO
        Repositorios repositorio = new Repositorios();
        repAtualizado = repositorio.testa_atualizar_repositorio(con, indexar);

//SE PRECISAR RECALCULA O INDICE
        try {
            if (subFedAtualizada || repAtualizado) {
                System.out.println("recalculando o indice " + new Date());
                indexar.populateR1(con);
                System.out.println("indice recalculado! " + new Date());
            } else {
                System.err.println("NAO existe atualizaçoes para os repositorios!");
            }

            con.close(); //fechar conexao
        } catch (SQLException e) {
            System.out.println("Erro com sql no robo: " + e.getMessage());
        }
    }

}
