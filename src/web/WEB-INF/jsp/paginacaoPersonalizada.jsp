<font face=Helvetica size=-1>P&aacute;ginas de Resultado:
<pg:prev export="pageUrl">&nbsp;<a href="<%= pageUrl+url %>">[&lt;&lt; Anterior]</a></pg:prev>
</font>

<pg:pages><%
  if (pageNumber.intValue() < 10) {
    %>&nbsp;<%
  }
  if (pageNumber == currentPageNumber) {
    %><b><%= pageNumber %></b><%
  } else {
    %><a href="<%= pageUrl+url %>"><%= pageNumber %></a><%
  }
%>
</pg:pages>

<font face=Helvetica size=-1>
<pg:next export="pageUrl">&nbsp;<a href="<%= pageUrl+url %>">[Pr&oacute;xima &gt;&gt;]</a></pg:next>
<br></font>