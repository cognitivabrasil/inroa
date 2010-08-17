package login;

import java.sql.*;
import mysql.Conectar;


/**
 * Disponibiliza método para verificar usuários cadastrados no sistema.
 * @author Marcos Nunes
 */
public class Usuario {

    
    /**
     * Testa na base de dados se o usuario e a senha informados estão corretos
     * @param usuario usuário que sera testado na base de dados
     * @param senha senha que sera testada na base de dados
     * @return boolean informando se o usuario e senha confere ou não
     */
    public boolean verificaUsuario(String usuario, String senha){
        boolean result=false;
        String sql="";
        //Connection con = conectarBD();
        Conectar conectar = new Conectar();
        Connection con = conectar.conectaBD();

        sql+="select nome from usuarios ";
        sql+="where login='"+usuario+"' ";
        sql+="and senha=md5('"+senha+"');";
        try{
            Statement stm = con.createStatement();
            //executa a consulta que esta na variavel sql
            ResultSet rs = stm.executeQuery(sql);
            if(rs.next())//testa se retornou algum resultado
                result=true;
            
        }catch(Exception e){
            System.out.println(e);
        }finally {
            try {
                con.close(); //fechar conexao mysql
                } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
        return result; //retorna o resultado
    }

}
