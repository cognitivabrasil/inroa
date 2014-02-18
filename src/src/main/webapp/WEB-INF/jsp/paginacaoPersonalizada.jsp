<span class="paginacao">P&aacute;ginas de Resultado:
<pg:prev export="pageUrl">&nbsp;<a href="<%= pageUrl%>&consulta=${BuscaModel.consulta}">[&lt;&lt; Anterior]</a></pg:prev>
</span>

<pg:pages><%
  if (pageNumber.intValue() < 10) {
    %>&nbsp;<%
  }
  if (pageNumber == currentPageNumber) {
    %><strong><%= pageNumber %></strong><%
  } else {
    %><a href="<%= pageUrl%>&consulta=${BuscaModel.consulta}"><%= pageNumber %></a><%
  }
%>
</pg:pages>


<div class="paginacao">
<pg:next export="pageUrl">&nbsp;<a href="<%= pageUrl%>&consulta=${BuscaModel.consulta}">[Pr&oacute;xima &gt;&gt;]</a></pg:next>
</div>