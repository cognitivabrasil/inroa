<%--
    Document   : exibeRepositorios
    Created on : 03/08/2009, 16:14:12
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="conexaoBD.jsp"%>
<%@page import="operacoesPostgre.Consultar"%>
<%@page import="java.util.Date" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css">
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoes.js"></script>
    </head>

    <body>
        <jsp:useBean id="operacoesBean" class="robo.util.Operacoes" scope="page" />
        
        <div id="page">

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de reposit&oacute;rios cadastrados</div>

            <%
                String id = request.getParameter("id");

                String sql = "SELECT p.nome as nome_padrao, t.nome as nometipomap, t.descricao as descricaotm "
                        + " FROM repositorios r, info_repositorios i, padraometadados p, tipomapeamento t "
                        + " WHERE r.id=" + id + " AND r.id=i.id_repositorio AND i.padrao_metadados=p.id AND i.tipo_mapeamento_id=t.id "
                        + " ORDER BY r.nome ASC";

                Statement stm = con.createStatement();
                ResultSet res = stm.executeQuery(sql);
                if (res.next()) {
            %>

            
            <!--Informações Gerais-->
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
            <div class="editar">
                <input type="button" class="botaoEditar" title="Editar" name="editar" id="editarRep" onclick="location.href='./editarRepositorio?id=${param.id}'" >
                <a href="./editarRepositorio?id=${param.id}">Editar</a>                
            </div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Nome do reposit&oacute;rio: 
                </div>
                <div class="Value">&nbsp;${repDAO.get(param.id).nome}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Descri&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;${repDAO.get(param.id).descricao}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Padr&atilde;o de metadados utilizado:
                </div>
                <div class="Value">&nbsp;<%=res.getString("nome_padrao").toUpperCase()%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Nome do mapeamento:
                </div>
                <div class="Value">&nbsp;<%=res.getString("nometipomap") + " - " + res.getString("descricaotm")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    MetadataPrefix:
                </div>
                <div class="Value">&nbsp;${repDAO.get(param.id).metadataPrefix}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    NameSpace:
                </div>
                <div class="Value">&nbsp;${repDAO.get(param.id).namespace}</div>
            </div>

            <div class="subtitulo">Sincroniza&ccedil;&atilde;o dos metadados</div>
            

            <div class="LinhaEntrada">
                <div class="Label">
                    URL que responde OAI-PMH:
                </div>
                <div class="Value">&nbsp;${repDAO.get(param.id).url}</div>
            </div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Cole&ccedil;&otilde;es ou Comunidades:
                </div>
                <div class="Value">&nbsp;
                    <c:choose>
                        <c:when test="${empty repDAO.get(param.id).colecoes}">
                            Todas
                        </c:when>
                        <c:otherwise>
                            ${repDAO.get(param.id).colecoes}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Periodicidade de atualiza&ccedil;&atilde;o :
                </div>
                <div class="Value">&nbsp;${repDAO.get(param.id).periodicidadeAtualizacao} dia(s)</div>
            </div>


            <%

                } else
                    out.println("<p class='textoErro'>Ocorreu um erro ao consultar a base de dados.</p>");
            %>
            <div class="subtitulo">Atualiza&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="LinhaEntrada">
                <div class="Label">
                    &Uacute;ltima Atualiza&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;${operacoesBean.ultimaAtualizacaoFrase(repDAO.get(param.id).ultimaAtualizacao)}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Pr&oacute;xima Atualiza&ccedil;&atilde;o:
                </div>
                
                <c:choose>
                        <c:when test="${operacoesBean.dataAnteriorAtual(repDAO.get(param.id).proximaAtualizacao)}">
                            <div id='textResult${param.id}' class="ValueErro">&nbsp; 
                                ${operacoesBean.ultimaAtualizacaoFrase(repDAO.get(param.id).proximaAtualizacao, repDAO.get(param.id).url)}
                                &nbsp;&nbsp;
                                <a title="Atualizar agora" onclick="javaScript:atualizaRepAjax(${param.id}, this.parentNode);">
                                    <img src='../imagens/erro_sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                           <div class="Value" id="textResult${param.id}">&nbsp;
                               ${operacoesBean.ultimaAtualizacaoFrase(repDAO.get(param.id).proximaAtualizacao, repDAO.get(param.id).url)}
                               &nbsp;&nbsp;
                               <a title='Atualizar agora' onclick="javaScript:atualizaRepAjax(${param.id}, this.parentNode);">
                                   <img src='../imagens/sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'> 
                               </a> 
                           </div>
                        </c:otherwise>
                    </c:choose>
                
            </div>
            
            <div class="LinhaEntrada">
                <div class="Label">
                    N&uacute;mero de objetos:
                </div>
                <div class="Value">
                    <div>&nbsp;<%=Consultar.selectNumeroDocumentosRep(con, Integer.parseInt(id))%></div>

                    <div id="removeAtualiza" class="ApagaObjetos">&nbsp;<input type="button" value="Formatar e restaurar" onclick="javascript:apagaAtualizaRepAjax(${param.id}, this.parentNode)"></div>

                </div>               
            </div>
        </div>

        <%@include file="../googleAnalytics"%>
    </body>

</html>
<%
    con.close(); //fechar conexao
%>