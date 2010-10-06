package operacoesLdap;

import java.sql.*;
import postgres.Conectar;

/**
 * Classe que disponibiliza operações de edição no LDAP.
 * @author Marcos Nunes
 */
public class Editar {

    /**
     * Método responsável por trocar o repositório de base subfederação. Recebe como entrada o id do repositório para que seja possível coletar os dados atuais, e o id da subfederação que passará a receber seus metadados.
     * @param idRep Id do repositório. Identificador da tabela repositorios.
     * @param idNovaFed Id da Federação nova para qual se quer trocar os metadados do repositório
     * @return Retorna true ou false, se o processo foi executado com sucesso ou não.
     */
    public boolean trocarBaseLdap(int idRep, int idNovaFed) {

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

    /**
     * Edita informações referente ao ldap no arquivo de configuração do metadiretorio (slapd dot conf). Se o endereço ip ou dn da base ldap foi alterada, é necessário alterar o arquivo de configuração do metadiretório.
     * @param ipAntigo Endereço ip que estava cadastrado para o ldap
     * @param dnAntigo DN que estava cadastrado para o ldap
     * @param ipNovo Novo endereço ip do ldap
     * @param dnNovo Novo dn do ldap
     * @return retorna true se foi possível remover as informações antigas e inserir as novas, e false se uma das duas deu erro.
     */
//    public boolean editaLdapSlapd(String ipAntigo, String dnAntigo, String ipNovo, String dnNovo) {
//        boolean resultado = false;
//
//        Remover removeLdap = new Remover();
//        boolean removeuLdap = false;
//        removeuLdap = removeLdap.excluiLdapSlapd(ipAntigo, dnAntigo); //remove as informacoes antigas
//        if (removeuLdap) {
//            Inserir insereLdap = new Inserir();
//            boolean inseriuLdap = false;
//            inseriuLdap = insereLdap.insereRepositorioSldap(ipNovo, dnNovo); //insere as novas informacoes
//            if (inseriuLdap) { //se as info foram inseridas
//                ReiniciarServico restartLdap = new ReiniciarServico(); //reinicia o servico ldap no linux
//                resultado = restartLdap.getResultado(); //retorna o resultado da operacao de reiniciar o servico
//            }
//        }
//        return resultado;
//    }
}
