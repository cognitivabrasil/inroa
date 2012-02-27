/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramentaBusca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import robo.util.Operacoes;

/**
 *
 * @author Marcos
 */
public class LRU {

    private String consulta;
    private Connection con;
    private String resultado;

    public LRU(String consulta, Connection con) {
        this.consulta = Operacoes.removeAcentuacao(consulta);
        this.con = con;
        this.resultado = "";
    }

    public LRU(ArrayList<String> consulta, Connection con) {

        this.consulta = Operacoes.arrayListToString(consulta);
        this.con = con;
        this.resultado = "";
    }

    /**
     * Verifica se a consulta j&aacute; existe na base de dados. Se existir armazena resultado os ids.
     * @return true caso exista a consulta na base e false caso contr&aacute;rio.
     * @throws SQLException
     */
    public boolean verificaConsulta() throws SQLException {
        boolean cache = false;
        String sql = "SELECT ids FROM consultas where consulta='" + consulta + "'";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        resultado = "";
        while (rs.next()) {
            resultado = rs.getString(1);
            cache = true;
        }

        return cache;
    }

    /**
     * Retorna um ArrayList de inteiros contendo os ids dos objetos que contemplam a consulta.
     * @return ArrayList<Integer> contendo os ids dos objetos que contemplam a consulta
     */
    public ArrayList<Integer> getResultado() {
        if (!resultado.isEmpty()) {
            String resultadoS[] = resultado.split(",");
            Integer resultadoI[] = new Integer[resultadoS.length];
            for (int i = 0; i < resultadoS.length; i++) {
                resultadoI[i] = Integer.parseInt(resultadoS[i]);
            }

            return new ArrayList<Integer>(Arrays.asList(resultadoI));
        } else {
            return new ArrayList<Integer>();
        }
    }

    /**
     * Concatena o resultado recebido com os resultados existentes, separando por v&iacute;rgulas.
     * @param resultado id do objeto que ser&acute; armazenado no resultado
     */
    public void setResultado(String resultado) {
        if (this.resultado.isEmpty()) {
            this.resultado = resultado;
        } else {
            this.resultado += "," + resultado;
        }
    }

    /**
     * Armazena na base de dados a consulta e os seus resultados setados pelo m&eacute;todo setResultado().
     * @return true se armazenou e false caso contr&aacute;rio.
     * @throws SQLException
     */
    public void gravaResultado() throws SQLException {
        String sql = "INSERT INTO consultas (consulta, ids) VALUES (?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, this.consulta);
        stmt.setString(2, this.resultado);
        stmt.executeUpdate();        
    }
}