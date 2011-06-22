package operacoesPostgre;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import postgres.Conectar;
import postgres.Configuracao;

/**
 * Disponibiliza método para efetuar consultas
 * @author Marcos Nunes
 */

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
     * Metodo para consultar na base de dados todos os atributos para um determinado objeto, essa conexao &eacute; feita em seguida com a subfederacao a qual o objeto pertence
     * @param obaa_entry Identificador único do objeto a ser detalhado
     * @param repositorio id do reposit&oacute;rio o qual o objeto pertence
     * @param con Conexão com a base de dados local
     */
    public Consultar(String obaa_entry, int repositorio, String idBase, Connection con) throws NullPointerException {
        try {
            Configuracao configuracaoSubFed;

            if (obaa_entry.contains(";FEB;")) { //se for subfederacao
                String idEntry[] = obaa_entry.split(";FEB;");
                repositorio = Integer.parseInt(idEntry[0]);
                obaa_entry = idEntry[1];

                //vai na base para descobrir a subfederacao do objeto
                String sql = "SELECT ip, login, senha, porta, base FROM dados_subfederacoes WHERE id=" + idBase;

                PreparedStatement stm = con.prepareStatement(sql);
                ResultSet rs1 = stm.executeQuery();

                if (rs1.next()) { // testa se retornou alguma sufederacao
                    configuracaoSubFed = new Configuracao(rs1.getString("base"), rs1.getString("login"), rs1.getString("senha"), rs1.getString("ip"), rs1.getInt("porta"));

                } else { //se não poe a conexão com a base local soh para tratar essa excecao
                    configuracaoSubFed = new Configuracao();
                    System.out.println("FEB: Base de dados não encontrada!");
                }
            } else { //se nao for subfederacao
                configuracaoSubFed = new Configuracao();
            }
            //conecta na base de dados (federacao ou nao)
            Conectar conexao = new Conectar(configuracaoSubFed);
            Connection conSub = conexao.conectaBD(); //chama o metodo conectaBD da classe conectar
            //faz a consulta na subfederacao
            String consulta = "SELECT o.atributo, o.valor "
                    + "FROM objetos o, documentos d, repositorios r "
                    + "WHERE d.obaa_entry = '" + obaa_entry.trim() + "' "
                    + "AND o.documento = d.id "
                    + "AND d.id_repositorio=r.id "
                    + "AND r.id=" + repositorio;

            PreparedStatement stmt = conSub.prepareStatement(consulta);
            ResultSet rs = stmt.executeQuery();
            HashMap<String, String> resultInterno = new HashMap<String, String>();
            while (rs.next()) {
                //System.out.println("atrbuto: " + rs.getString("atributo") + "valor: " + rs.getString("valor"));
                
                String atributo = rs.getString("atributo");
                if(resultInterno.containsKey(atributo)){
                    resultInterno.put(atributo, resultInterno.get(atributo)+";; "+rs.getString("valor"));
                }else
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
        System.out.println("conectou");
        //Consultar run = new Consultar("objetodementira171", arg, con);
        Consultar run = new Consultar("562;FEB;oai:cesta2.cinted.ufrgs.br:123456789/222", 1, "null", con);
        System.out.println(run.getResultado());
    }
}
