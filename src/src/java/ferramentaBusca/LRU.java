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

/**
 *
 * @author Marcos
 */
public class LRU {

    private String consulta;
    private Connection con;
    private String resultado;

    public LRU(String consulta, Connection con) {
        this.consulta = consulta;
        this.con = con;
        this.resultado = "";
    }

    /**
     * Verifica se a consulta j&aacute; existe na base de dados. Se existir armazena resultado os ids.
     * @return true caso exista a consulta na base e false caso contr&aacute;rio.
     * @throws SQLException
     */
    public boolean verificaConsulta() throws SQLException {
        String sql = "SELECT ids FROM consultas where consulta='" + consulta + "'";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        resultado = "";
        while (rs.next()) {
            resultado = rs.getString(1);
        }

        if (resultado.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Retorna um ArrayList de inteiros contendo os ids dos objetos que contemplam a consulta.
     * @return ArrayList<Integer> contendo os ids dos objetos que contemplam a consulta
     */
    public ArrayList<Integer> getResultado() {

        String resultadoS[] = resultado.split(",");
        Integer resultadoI[] = new Integer[resultadoS.length];
        for (int i = 0; i < resultadoS.length; i++) {
            resultadoI[i] = Integer.parseInt(resultadoS[i]);
        }

        return new ArrayList<Integer>(Arrays.asList(resultadoI));
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
    public boolean gravaResultado() throws SQLException {
        String sql = "INSERT INTO consultas (consulta, ids) VALUES (?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, this.consulta);
        stmt.setString(2, this.resultado);
        int res = stmt.executeUpdate();
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

}
