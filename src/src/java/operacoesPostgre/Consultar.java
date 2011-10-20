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
     * @param idRep id do reposit&oacute;rio o qual o objeto pertence
     * @param con Conexão com a base de dados local
     */
    public Consultar(String obaa_entry, int idRep, int idSubrep, Connection con) throws NullPointerException {
        try {


            //consulta sql
            String consulta = "SELECT o.atributo, o.valor "
                    + "FROM objetos o, documentos d "
                    + "WHERE d.obaa_entry = '" + obaa_entry.trim() + "' "
                    + "AND o.documento = d.id ";
            if (idRep > 0) { //se for informado id do repositorio consulta no repositorio
                consulta += "AND d.id_repositorio=" + idRep;
            } else if (idSubrep > 0) { //se não consulta no repositorio da subfederacao
                consulta += "AND d.id_rep_subfed=" + idSubrep;
            }


            PreparedStatement stmt = con.prepareStatement(consulta);
            ResultSet rs = stmt.executeQuery();
            HashMap<String, String> resultInterno = new HashMap<String, String>();
            while (rs.next()) {

                String atributo = rs.getString("atributo");
                if (resultInterno.containsKey(atributo)) {
                    resultInterno.put(atributo, resultInterno.get(atributo) + ";; " + rs.getString("valor"));
                } else {
                    resultInterno.put(rs.getString("atributo"), rs.getString("valor"));
                }
            }
            resultado.add(resultInterno);

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

    /**
     * Consulta na base de dados a descri&ccedil;&atilde;o do tipo de mapeamento.
     * @param id id do tipo de mapeamento que deseja saber a descri&ccedil;&atilde;o
     * @return String contendo a descri&ccedil;&atilde;o
     */
    public static String consultaDescricaoMapeamento(int id) {//utilizado pelo ajax que apresenta na tela a descricao do repositorio selecionado
        String descricao = "";
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        try {
            Statement stm = con.createStatement();

            String sql = "SELECT t.descricao FROM tipomapeamento t WHERE t.id=" + id + ";";
            ResultSet rsTipo = stm.executeQuery(sql);
            while (rsTipo.next()) {
                descricao = rsTipo.getString(1);
            }
        } catch (SQLException s) {
            System.err.println("FEB: Erro ao consultar a descricao do mapeamento. Classe Consultar metodo consultaDescricaoMapeamento. Mensagem: " + s.getMessage());
        } finally {
            try {
                con.close(); //fechar conexao
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
            return descricao;
        }
    }

    public static int selectNumeroDocumentosRep(Connection con, int idRep) {
        int nDocumentos = 0;
        String sql = "SELECT count(*) from documentos WHERE id_repositorio=" + idRep;
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            rs.next();
            nDocumentos = rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("FEB: Erro no SQL ao contar o numero de documentos. Classe Consultar metodo selectNumeroDocumentosRep: " + e.getMessage());
        } finally {
            return nDocumentos;
        }

    }
}
