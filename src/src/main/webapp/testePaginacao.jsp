<%@ page session="false" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<html>
    <head>
        <title>Teste paginação</title>

    </head>
    <body bgcolor="#ffffff">

        <%
                    String style = "altavista";
                    String position = "both";
                    String index = "center";
                    int maxPageItems = getParam(request, "maxPageItems", 10);
                    int maxIndexPages = 10;

                    String url = "&repositorio=1&atributo=Keyword&key=Educa%E7%E3o";
        %>
        <form action="<%= request.getRequestURI()%>" method="get">
            <center>
                <table border="0" width="90%" cellpadding="4">

                    <tr>
                        <td colspan="2" align="center" valign="top">
                            <table border="0" cellspacing="2" cellpadding="0">
                                <tr><td>M&aacute;ximo de resultados por p&aacute;gina&nbsp;</td>
                                    <td><input type="text" size="4" name="maxPageItems" value="<%= maxPageItems%>" onChange="this.form.submit();"></td></tr>
                                
                            </table>
                        </td>
                    </tr>
                </table>

                                    <pg:pager
                                        items="<%= tamanhoHash %>"
                                        index="<%= index %>"
                                        maxPageItems="<%= maxPageItems %>"
                                        maxIndexPages="<%= maxIndexPages %>"
                                        isOffset="<%= true %>"
                                        export="offset,currentPageNumber=pageNumber"
                                        scope="request"
                                        url="<%=url%>">

                    <%-- keep track of preference --%>
                    <pg:param name="style"/>
                    <pg:param name="position"/>
                    <pg:param name="index"/>
                    <pg:param name="maxPageItems"/>
                    <pg:param name="maxIndexPages"/>

                    <%-- salva pager offset durante as mudancas do form --%>
                    <input type="hidden" name="pager.offset" value="<%= offset%>">


                    <%-- the pg:pager items attribute must be set to the total number of
                         items for index before items to work properly --%>
                    <% if ("top".equals(position) || "both".equals(position)) {%>
                    <br>
                    <pg:index>
                        <jsp:include page="/WEB-INF/jsp/altavista.jsp" flush="true"/>
                    </pg:index>
                    <% }%>

                    <hr>
                    <table width="90%" cellspacing="4" cellpadding="4">

                        
                        <%
                        int tamanhoDoHash=50;
                        
                                  for (int i = offset.intValue(),
                                          l = Math.min(i + maxPageItems, tamanhoDoHash);
                                          i < l; i++) {
                                      //colocar aqui para imprimir as consultas. Ele vai entrar aqui uma vez para cada resultado do hashmap
%>
                        <pg:item>
                            <tr><th bgcolor="white"><font color="black">Marcos <%= i + 1%></font></th></tr>
                        </pg:item><%
                    }
                        %>
                    </table>
                    <hr>

                    <% if ("bottom".equals(position) || "both".equals(position)) {%>
                    <pg:index>
                        <jsp:include page="/WEB-INF/jsp/altavista.jsp" flush="true"/>
                    </pg:index>
                    <% }%>
                </pg:pager>
            </center>
        </form>
    </body>
</html>
<%!

private static final int tamanhoHash = 50;

private static final int getParam(ServletRequest request, String name,
    int defval)
{
    String param = request.getParameter(name);
    int value = defval;
    if (param != null) {
        try { value = Integer.parseInt(param); }
        catch (NumberFormatException ignore) { }
    }
    return value;
}


%>
