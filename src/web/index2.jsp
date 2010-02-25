<%--
    Document   : index
    Created on : 25/06/2009, 18:26:51
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.*"%>
<%@include file="conexaoBD.jsp"%>
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <script type="text/javascript">
            function exibeFormatos(valor, div_destino )
            {
                var selectFormat = "<div class='LinhaEntrada'>"+
                    "<div class='Label'>"+
                    "Formato:"+
                    "</div>"+
                    "<div class='Value'>"+
                    "<select name=\"key\">"+
                    "<option value='video'>V&iacute;deo Indexado</option>"+
                    "<option value='audio/x-pn-realaudio'>Audio RealAudio</option>"+
                    "<option value='audio/mpeg'>Audio MP3</option>"+
                    "<option value='audio/x-midi'>Audio MIDI</option>"+
                    "<option value='image/jpeg'>Imagem JPEG</option>"+
                    "<option value='audio/gif'>Imagem GIF</option>"+
                    "<option value='video/mpeg'>Video MPEG</option>"+
                    "<option value='video/x-ms-wmv'>Video WMV</option>"+
                    "<option value='video/vnd.rn-realvideo'>Video RealVideo</option>"+
                    "<option value='video/quicktime'>Video Quicktime</option>"+
                    "<option value='application/msword'>Aplica&ccedil;&atilde;o MS Word</option>"+
                    "<option value='application/pdf'>Aplica&ccedil;&atilde;o PDF</option>"+
                    "<option value='application/vnd.ms-powerpoint'>Aplica&ccedil;&atilde;o MS PowerPoint</option>"+
                    "<option value='application/x-shockwave-flash'>Aplica&ccedil;&atilde;o Macromedia Shockwave/Flash</option>"+
                    "<option value='application/other'>Outro Aplicativo</option>"+
                    "<option value='text/html'>Texto HTML</option>"+
                    "<option value='application/rtf'>Texto RTF</option>"+
                    "<option value='text/xml'>Texto XML</option>"+
                    "<option value='text/plain'>Texto ASCII</option>";
                "</select>"+
                    "</div>"+
                    "</div>";
                if(valor=="Format") {
                    document.getElementById(div_destino).innerHTML = selectFormat;
                } else {
                    document.getElementById(div_destino).innerHTML = "<div class='LinhaEntrada'>"+
                        "   	<div class='Label'>"+
                        "       	Texto para a busca:"+
                        "       </div>"+
                        "		<div class='Value'>"+
                        "   		<input type='text' name='key' value='' />"+
                        "   	</div>"+
                        "	</div>";
                }
            }
        </script>

    </head>

    <body id="bodyMenor">
        <!-- incluir um arquivo %@ include file="top.html" %> -->
        <div id="page">
            <div class="linkCantoDireito"><a href="./adm.jsp">Ferramenta Administrativa</a></div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="subTitulo-center">&nbsp;CONSULTA DE OBJETOS EDUCACIONAIS</div>

            <form name="consulta" action="consulta_backup.jsp" method="post">

                <div class="LinhaEntrada">
                    <div class="EspacoAntes">&nbsp;</div>
                    <div class="Label">
                        Servidor:
                    </div>
                    <div class="Value">
                        <select name="repositorio" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            <%
        //Carrega do banco de dados os repositorios cadastrados
        ResultSet res = stm.executeQuery("SELECT nome, id FROM repositorios ORDER BY nome ASC");
        while (res.next()) {
            if (res.getString("nome").equalsIgnoreCase("todos")) {
                out.println("<option selected value=" + res.getInt("id") + ">" + res.getString("nome").toUpperCase());
            } else {
                out.println("<option value=" + res.getInt("id") + ">" + res.getString("nome").toUpperCase());
            }

        }
                            %>

                        </select>
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Campo onde ser&aacute; efetuada a busca:
                    </div>
                    <div class="Value">
                        <select name="atributo" onchange="exibeFormatos(this.value,'modificavel')" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            
                            <option value="Keyword" selected>Palavra-chave
                            <option value="Title">T&iacute;tulo
                            <option value="Format">Formato
                        </select>
                    </div>
                </div>
                <div id="modificavel">
                    <div class="LinhaEntrada">
                        <div class="Label">
                            Texto para a busca:
                        </div>
                        <div class="Value">
                            <input type="text" name="key" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                        </div>
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input class="BOTAO" type="submit" value="Consultar"/>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>