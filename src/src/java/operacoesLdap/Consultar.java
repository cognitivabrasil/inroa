package operacoesLdap;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import postgres.Conectar;
import postgres.Configuracao;

/**
 * Disponibiliza método para efetuar consultas
 * @author Marcos Nunes
 */
//não deve apagar, foi reaproveitado mesmo sem LDAP
public class Consultar {

    ArrayList<HashMap> resultado = new ArrayList<HashMap>();

    /**
     * Metodo para consultar na base de dados atributos determinados em @param atributos para um determinado objeto
     * @param obaa_entry Identificador único do objeto a ser detalhado
     * @param atributos Lista de atributos a serem listados
     * @param con Conexão com a base de dados
     */
    public Consultar(String obaa_entry, String[] atributos, Connection con) {
        try {
            String consulta = "SELECT atributo, valor FROM objetos, documentos WHERE documentos.obaa_entry = '" + obaa_entry + "' AND objetos.documento = documentos.id AND ";
            for (int i = 0; i < atributos.length; i++) {
                if (i == atributos.length - 1) {
                    consulta += "objetos.atributo= '" + atributos[i] + "')";
                } else {
                    consulta += "(objetos.atributo= '" + atributos[i] + "' OR ";
                }
            }
            //System.out.println("CONSULTA: "+consulta);
            PreparedStatement stmt = con.prepareStatement(consulta);
            ResultSet rs = stmt.executeQuery();
            HashMap resultInterno = new HashMap();
            while (rs.next()) {
                resultInterno.put(rs.getString("atributo"), rs.getString("valor"));
            }
            resultado.add(resultInterno);
        } catch (SQLException e) {
            System.out.println("ERRO AO CARREGAR ATRIBUTOS DO OBJETO! " + e);
        }
    }

    /**
     * Metodo para consultar na base de dados todos os atributos para um determinado objeto, essa conexao é feita em seguida copm a subfederacao a qual o objeto pertence
     * @param obaa_entry Identificador único do objeto a ser detalhado
     * @param con Conexão com a base de dados local
     */
    public Consultar(String obaa_entry, String repositorio, String idBase, Connection con) throws NullPointerException {
        try {

            //vai na base para descobrir a subfederacao do objeto
            String sql = "SELECT ip, login, senha, porta, base FROM dados_subfederacoes WHERE id="+idBase;
            
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs1 = stm.executeQuery();
            Configuracao configuracaoSubFed;
            if (rs1.next()) { // testa se retornou alguma sufederacao
                System.out.println("");
                configuracaoSubFed = new Configuracao(rs1.getString("base"), rs1.getString("login"), rs1.getString("senha"), rs1.getString("ip"), rs1.getInt("porta"));

            } else { //se não poe a conexão com a base local soh para tratar essa excecao
                configuracaoSubFed = new Configuracao();
                System.out.println("Base de dados não encontrada!");
            }

            //conecta na subfederacao
            Conectar conexaoSubfederacao = new Conectar(configuracaoSubFed);
            Connection conSub = conexaoSubfederacao.conectaBD(); //chama o metodo conectaBD da classe conectar
            //faz a consulta na subfederacao
            String consulta = "SELECT o.atributo, o.valor "
                    + "FROM objetos o, documentos d, repositorios r "
                    + "WHERE d.obaa_entry = '" + obaa_entry.trim() + "' "
                    + "AND o.documento = d.id "
                    + "AND d.id_repositorio=r.id "
                    + "AND r.nome='"+repositorio.trim()+"'";

            PreparedStatement stmt = conSub.prepareStatement(consulta);
            ResultSet rs = stmt.executeQuery();
            HashMap<String, String> resultInterno = new HashMap<String, String>();
            while (rs.next()) {
                //System.out.println("atrbuto: " + rs.getString("atributo") + "valor: " + rs.getString("valor"));
                resultInterno.put(rs.getString("atributo"), rs.getString("valor"));
            }
            resultado.add(resultInterno);
            conSub.close();
            
        } catch (SQLException e) {
            System.out.println("ERRO AO CARREGAR ATRIBUTOS DO OBJETO OU CONECTANDO COM A SUBFEDERAÇÃO! " + e);
         
        }

    }

    /**
     * Retorna os resultados da busca efetuada pelo contrutor.
     * @return Retorna um ArrayList de HashMap
     */
    public ArrayList<HashMap> getResultado() {
        return resultado;
    }


    public static void main(String[] args) {

        Conectar conectar = new Conectar(); //instancia uma variavel da classe conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
        
        //Consultar run = new Consultar("objetodementira171", arg, con);
        Consultar run = new Consultar("oai:www.lume.ufrgs.br:10183/15888", "LUME", "1", con);
        System.out.println(run.getResultado());
    }
}
