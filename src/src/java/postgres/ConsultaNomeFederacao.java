package postgres;

import java.sql.*;

/**
 * Disponibiliza metodos para recuperar informações sobre um ldap apartir do nome do seu nodo. Tais informações estão armazenadas na base de dados Postgres.
 * @author Marcos e Luiz Rossi
 */
public class ConsultaNomeFederacao {

    String nomeEncontrado = null;
    String ipEncontrado = null;
    String dnEncontrado = null;

    /**
     *  Recebe como entrada o nome do repositório no ldap e consulta
     * no mysql por esse nome para encontrar o nome real do
     * repositorio, o ip e o seu dn.
     * Exemplo: Existe um repositório chamado CESTA que é idenficado na federação(ldap) como pgie3.
     * @param nomeRepositorio nome do repositório no ldap. Ex.: pgie3
     */
    public ConsultaNomeFederacao(String nomeRepositorio, Connection con) {

        nomeRepositorio = nomeRepositorio.trim().toLowerCase().replaceAll(",", "");


        String sql = "SELECT r.nome, i.nome_na_federacao,concat('ou=',i.nome_na_federacao,',',l.dn)as dn, l.ip " +
                "FROM repositorios r, info_repositorios i, ldaps l " +
                "WHERE i.nome_na_federacao='" + nomeRepositorio + "' " +
                "AND r.id=i.id_repositorio " +
                "AND i.ldapDestino=l.id;";
        
        try {

            Statement stm = con.createStatement();
            //executa a consulta que esta na variavel sql
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {

                String nome = rs.getString("nome_na_federacao");
                if (nome.contains(nomeRepositorio)) {
                    nomeEncontrado = rs.getString("nome");
                }
                ipEncontrado = rs.getString("ip");
                dnEncontrado = rs.getString("dn");
            }


        } catch (SQLException e) {
            System.out.println("SQL Exception... Erro na consulta: ");
            e.printStackTrace();
        } 

    }

    /**
     * Método que retorna o nome do repositório encontrado pela classe ConsultaNomeFederacao
     * @return o nome do repositório encontrado
     */
    public String getNomeEncontrado() {
        return this.nomeEncontrado.replaceAll(" ", "_");
    }

    /**
     * Método que retorna o dn do repositório encontrado pela classe ConsultaNomeFederacao
     * @return dn do repositório encontrado
     */
    public String getDnEncontrado() {
        return this.dnEncontrado;
    }

    /**
     * Método que retorna o ip do repositório encontrado pela classe ConsultaNomeFederacao
     * @return endereço ip referente ao LDAP local que armazena os metadados do repositório encontrado
     */
    public String getIpEncontrado() {
        return this.ipEncontrado;
    }
}

