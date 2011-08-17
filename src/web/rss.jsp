<%@page import="RSS.Rss"%>
<%@page import="java.util.ArrayList"%>
<%--
    Document   : rss
    Created on : 23/09/2010, 17:29:56
    Author     : antonio
--%>

<%

            String search = "";

            request.setCharacterEncoding("UTF-8");
            
            String idRepLocal[] = {""};
            String idSubfed[] = {""};
            String idSubRep[] = {""};


            String textoBusca = "";



            boolean testaConsulta = false;
            try {
                textoBusca = request.getParameter("key"); //recebe a consulta informada no formulario
                idRepLocal = request.getParameterValues("replocal");
                idSubfed = request.getParameterValues("subfed");
                idSubRep = request.getParameterValues("subrep");


                if (textoBusca.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>"
                            + "<script type='text/javascript'>history.back(-1);</script>");
                } else {
                    testaConsulta = true;
                }
            } catch (Exception e) {
                out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>"
                        + "<script type='text/javascript'>window.location=\"index.jsp\";</script>");
                e.printStackTrace();

            }

            if (testaConsulta) { //se nao foi informada a consulta nao entra no if
                out.clear();    //limpa a p�gina. Sem isso, ele geraria o rss com algumas quebras de linha antes do inicio, que o navegador n�o reconhece como um rss
                Rss rss = new Rss(textoBusca, idRepLocal, idSubfed, idSubRep, request.getRequestURL().toString());
                out.print(rss.generateFeed() + "\n");
                
            }
%>

<%/*
            //rss.jsp antigo, quando recebia apenas um identificador de repositorio para fazer a busca
            out.clear();

            String search = "";
            int idRep = 0;


            search = request.getParameter("search");
            String id = request.getParameter("idRep");

            try
            {
            idRep = Integer.parseInt(id);
            }
            catch (NumberFormatException e)
            {
            System.out.println("Id de repositorio invalido " + e);
            idRep = 0;
            }

            Rss rss = new Rss (search, idRep);
            out.print(rss.generateFeed() + "\n"); */
%>