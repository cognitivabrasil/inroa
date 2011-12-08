<%@page import="java.sql.*"%>
<%@page import="postgres.Conectar"%>
<%
            postgres.SingletonConfig.initConfig(application);

            Conectar conect = new Conectar();
            //chama metodo que conecta no banco de dados
            Connection con = conect.conectaBD();
            
    %>


