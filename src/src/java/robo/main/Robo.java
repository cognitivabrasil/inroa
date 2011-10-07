package robo.main;

import java.sql.*;
import ferramentaBusca.indexador.Indexador;
import java.util.Date;
import postgres.Conectar;
import robo.atualiza.SubFederacao;
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
        Indexador indexar = new Indexador();
        Conectar conectar = new Conectar(); //instancia uma variavel da classe Conectar

        Connection con = con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
        boolean repAtualizado = false;
        boolean subFedAtualizada = false;
//CONTA O NUMERO DE DOCUMENTOS
//        int nDocumentos = selectNumeroDocumentos(con);

//TESTA/ATUALIZA SUBFEDERACAO
        SubFederacaoOAI subFed = new SubFederacaoOAI();
        subFedAtualizada = subFed.testaAtualizaSubFedOAI(con, indexar);


//TESTA REPOSITORIO
        Repositorios repositorio = new Repositorios();
        repAtualizado = repositorio.testa_atualizar_repositorio(con, indexar);


//TESTA SE PRECISA RECALCULA O INDICE
        try {
//           if ((subFedAtualizada || repAtualizado) && nDocumentos!=selectNumeroDocumentos(con)) {
              if (subFedAtualizada || repAtualizado) {
                System.out.println("FEB: recalculando o indice " + new Date());
                Long inicioIndice = System.currentTimeMillis();
                indexar.populateR1(con);
                Long fimIndice = System.currentTimeMillis();
                System.out.println("FEB: indice recalculado em: "+ (fimIndice - inicioIndice)/1000 + " segundos! ");
            } else {
                System.err.println("FEB: NAO existe atualizaçoes para os repositorios! " + new Date());
            }

            
        } catch (SQLException e) {
            System.err.println("FEB: Erro com sql no robo: " + e.getMessage());
        } finally{
//            Long fim = System.currentTimeMillis();
//            System.out.println("#TIMER: Tempo total: " + (fim - inicio) + " milisegundos! " );
            try {
                con.close(); //fechar conexao
                } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão no robo: " + e.getMessage());
            }
        }
    }

    private static int selectNumeroDocumentos(Connection con) {
        int nDocumentos = 0;
        String sql = "SELECT count(*) from documentos;";
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            rs.next();
            nDocumentos = rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("FEB: Erro no SQL ao contar o numero de documentos. Classe Robo metodo selectNumeroDocumentos: " + e.getMessage());
        } finally{
            return nDocumentos;
        }
        
    }
}
