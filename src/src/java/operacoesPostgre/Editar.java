package operacoesPostgre;

import java.sql.*;
import postgres.Conectar;

/**
 * Classe que disponibiliza operações de edição na base de dados.
 * @author Marcos Nunes
 */
public class Editar {

    /**
     * Método responsável por trocar o repositório de base subfederação. Recebe como entrada o id do repositório para que seja possível coletar os dados atuais, e o id da subfederação que passará a receber seus metadados.
     * @param idRep Id do repositório. Identificador da tabela repositorios.
     * @param idNovaFed Id da Federação nova para qual se quer trocar os metadados do repositório
     * @return Retorna true ou false, se o processo foi executado com sucesso ou não.
     */
    public boolean trocarSubFederacao(int idRep, int idNovaFed) {

        Conectar conectar = new Conectar();

        //chama metodo que conecta ao postgres
        Connection con = conectar.conectaBD();

        try {
            Statement stm = con.createStatement();
            //alterar na base as informações
            String sql = "UPDATE info_repositorios SET id_federacao=" + idNovaFed + " WHERE id_repositorio=" + idRep;
            int result = 0;
            result = stm.executeUpdate(sql);
            return true;
            } catch (SQLException e) {
                System.out.println("ERRO ao modificar a base"+e);
                return false;
            } finally {
                try {
                    con.close(); //fechar conexao
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }


}
