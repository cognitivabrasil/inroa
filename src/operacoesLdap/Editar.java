package operacoesLdap;

import java.sql.*;
import mysql.Conectar;

/**
 * Classe que disponibiliza operações de edição no LDAP.
 * @author Marcos Nunes
 */
public class Editar {

    /**
     * Método responsável por trocar o repositório de base Ldap. Recebe como entrada o id do repositório para que seja possível coletar os dados atuais, e o id do Ldap que passará a receber seus metadados.
     * @param idRep Id do repositório. Identificador da tabela repositorios do mysql.
     * @param idLdapNovo Id do Ldap para qual se quer trocar os metadados do repositório
     * @return Retorna true ou false, se o processo foi executado com sucesso ou não.
     */
    public boolean trocarBaseLdap(int idRep, int idLdapNovo) {


        boolean resultado = false;
        boolean nodoinserido = false;
        Conectar conectar = new Conectar();
        Inserir insere = new Inserir();


        //chama metodo que conecta ao mysql
        Connection con = conectar.conectaBD();
        String consultaSql = "SELECT l.id, l.ip, l.dn, l.login, l.senha, l.porta, i.nome_na_federacao as nomeNodo from ldaps l, info_repositorios i where i.ldapDestino=l.id AND i.id_repositorio=" + idRep;
        String sqlLdapNovo = "SELECT l.ip, l.dn, l.login, l.senha, l.porta FROM ldaps l where id=" + idLdapNovo;

        try {

            Statement stm = con.createStatement();
            ResultSet infoLdapAntigo = stm.executeQuery(consultaSql); //executa o sql na base
            infoLdapAntigo.next();
            String nomeNodo = infoLdapAntigo.getString("nomeNodo");
            String ipAntigo = infoLdapAntigo.getString("ip");
            String dnAntigo = infoLdapAntigo.getString("dn");
            String loginAntigo = infoLdapAntigo.getString("login");
            String SenhaAntiga = infoLdapAntigo.getString("senha");
            int idAntigo = infoLdapAntigo.getInt("id");
            int portaAntiga = infoLdapAntigo.getInt("porta");

            //se nao tiver o que mudar apenas retorna true;
            if (idAntigo == idLdapNovo) {
                return true;
            }

            ResultSet infoLdapNovo = stm.executeQuery(sqlLdapNovo); //executa o sql na base
            infoLdapNovo.next();
            System.out.println("nomeNodo: " + nomeNodo + " ip: " + infoLdapNovo.getString("ip") + " dn: " + infoLdapNovo.getString("dn") + " login: " + infoLdapNovo.getString("login") + " senha: " + infoLdapNovo.getString("senha") + " porta: " + infoLdapNovo.getString("porta"));

            //insere o nodo no novo repositorio

            nodoinserido = insere.insereNodo(nomeNodo, infoLdapNovo.getString("ip"), infoLdapNovo.getString("dn"), infoLdapNovo.getString("login"), infoLdapNovo.getString("senha"), infoLdapNovo.getInt("porta"));
            System.out.println(nodoinserido);
            if (nodoinserido) {//se inseriu entra no if
                //alterar no mysql as informações
                String sql = "UPDATE info_repositorios SET ldapDestino=" + idLdapNovo + " WHERE id_repositorio=" + idRep;
                int result = 0;
                result = stm.executeUpdate(sql);
                if (result > 0) {//se alterou no mysql segue
                    String sqlUpdtData = "UPDATE info_repositorios SET dataUltimaAtualizacao='0001-01-01 00:00:00' where id_repositorio=" + idRep;
                    int result2 = 0;
                    result2 = stm.executeUpdate(sqlUpdtData);
                    if (result2 > 0) {
////colocar aqui metodo que copie dados de um ldap para outro

                        Remover exclui = new Remover();
                        boolean nodoExcluido = false;
                        System.out.println("dnAntigo:" + dnAntigo);
                        nodoExcluido = exclui.removeNodo(nomeNodo, ipAntigo, dnAntigo, loginAntigo, SenhaAntiga, portaAntiga);
                        if (nodoExcluido) {
                            resultado = true;
                        }
                    }
                }
            }

        } catch (Exception e) {
            resultado = false;
        }
        return resultado;
    }

    /**
     * Edita informações referente ao ldap no arquivo de configuração do metadiretorio (slapd dot conf). Se o endereço ip ou dn da base ldap foi alterada, é necessário alterar o arquivo de configuração do metadiretório.
     * @param ipAntigo Endereço ip que estava cadastrado para o ldap
     * @param dnAntigo DN que estava cadastrado para o ldap
     * @param ipNovo Novo endereço ip do ldap
     * @param dnNovo Novo dn do ldap
     * @return retorna true se foi possível remover as informações antigas e inserir as novas, e false se uma das duas deu erro.
     */
    public boolean editaLdapSlapd(String ipAntigo, String dnAntigo, String ipNovo, String dnNovo){
        boolean resultado = false;

        Remover removeLdap = new Remover();
        boolean removeuLdap=false;
        removeuLdap = removeLdap.excluiLdapSlapd(ipAntigo, dnAntigo); //remove as informacoes antigas
        if(removeuLdap){
            Inserir insereLdap = new Inserir();
            boolean inseriuLdap = false;
            inseriuLdap = insereLdap.insereRepositorioSldap(ipNovo, dnNovo); //insere as novas informacoes
            if(inseriuLdap){ //se as info foram inseridas
            ReiniciarServico restartLdap = new ReiniciarServico(); //reinicia o servico ldap no linux
            resultado = restartLdap.getResultado(); //retorna o resultado da operacao de reiniciar o servico
            }
        }
        return resultado;
    }

}
