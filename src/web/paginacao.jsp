<%-- 
    Document   : paginacao
    Created on : 08/10/2009, 10:41:47
    Author     : Marcos
--%>

<%@include file="conexaoBD.jsp" %>
<html>
<head><title>Aprendendo JSP</title>
</head>
<body>

<%
  // Este exemplo mostra como paginar os resultados de uma
  // tabela o portgres
  // o nome da base de dados é "test"



  // conn é a conexão com o banco de dados
  int limit = 50; // quantidade de resultados por página

  // obtém a quantidade de registros
  PreparedStatement pstmt = con.prepareStatement(
		"SELECT COUNT(*) AS c FROM documentos");
  ResultSet rs = pstmt.executeQuery();
  rs.next();
  int total_rows = Integer.parseInt(rs.getString("c"));


  String pagina = request.getParameter("pagina"); // página atual
  if(pagina == null){
    pagina = "1";
  }

  int limitValue = (Integer.parseInt(pagina) * limit) - limit;

  PreparedStatement pstmt2 = con.prepareStatement(
		"SELECT * FROM documentos LIMIT " + limitValue + ", " + limit);
  ResultSet rs2 = pstmt2.executeQuery();

  while(rs2.next()) {
    int id = rs2.getInt("id");
    out.println("ID: " + id + "<br>");
    String nome = rs2.getString("obaa_entry");
    out.println("NOME: " + nome + "<br>");
  }

  int anterior;
  if(Integer.parseInt(pagina) != 1){
    anterior = Integer.parseInt(pagina) - 1;
    out.println("<a href=?pagina=" + anterior + ">" + limit + " Anteriores</a>");
  }
  else
    out.println(limit + " Anteriores ");

  int numOfPages = total_rows / limit;
  int i;

  for(i = 1; i <= numOfPages; i++){
    if(i == Integer.parseInt(pagina)){
      out.println("<b>" + i + "</b> ");
    }
    else{
      out.println("<a href=?pagina=" + i + ">" + i + "</a> ");
    }
  }

  if((total_rows % limit) != 0){
    if(i == Integer.parseInt(pagina)){
      out.println(i + " ");
    }
    else{
      out.println("<a href=?pagina=" + i + ">" + i + "</a> ");
    }
  }

  int proxima;
  if((total_rows - (limit * Integer.parseInt(pagina))) > 0){
    proxima = Integer.parseInt(pagina) + 1;

    out.println("<a href=?pagina=" + proxima + ">Próximos " + limit + "</a>");
  }
  else
    out.println("Próximos " + limit);

  con.close(); // fechar conexao o portgres
%>

</body>
</html>
