<%@page import="java.sql.*"%>
<%@page import="postgres.Conectar"%>
<%

            Conectar conect = new Conectar();
            //chama metodo que conecta no banco de dados
            Connection con = conect.conectaBD();

            Statement stm = con.createStatement();
            
    %>


