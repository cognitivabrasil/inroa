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
            
            search = request.getParameter("search");
            String id = request.getParameter("idRep");
            
            boolean testaConsulta = false;
            try {
                if (id.isEmpty() || search.isEmpty()) {
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
                out.clear();    //limpa a página. Sem isso, ele geraria o rss com algumas quebras de linha antes do inicio, que o navegador não reconhece como um rss
                Rss rss = new Rss(search, id, request.getRequestURL().toString());
                out.print(rss.generateFeed() + "\n");
                //out.print(search + " " + id);
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