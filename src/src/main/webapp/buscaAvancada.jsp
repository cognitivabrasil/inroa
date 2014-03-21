<%-- 
    Document   : index
    Created on : 25/06/2009, 18:26:51
    Author     : Marcos Nunes e Alan Santos
    Updated on : 19/03/2014, 14:05
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="images" value="/imagens" />
<c:url var="logoReduzido" value="/imagens/Logo FEB_reduzido.png" />
<c:url var="index" value="/" />
<c:url var="scripts" value="/scripts" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="javascript" type="text/javascript" src='${scripts}/vendor/jquery-1.7.2.js'></script>
        <script language="JavaScript" type="text/javascript" src="${scripts}/buscaAvancadaSolr.js"></script>
    </head>

    <body>
        <jsp:include page="barraSuperior.jsp" />

        <div id="page-index">
            <c:if test="${!empty erro}">
                <div class="DivErro" id="MensagemErro">${erro}</div>
            </c:if>

            <form:form method="GET" modelAttribute="buscaModel" action="consultaAvancada" acceptCharset="utf-8">   
                <div id="index">
                    <a href="${index}">
                        <img src="${logoReduzido}" alt="Logo FEB_reduzido" class="logo"/>
                    </a>

                    <div class="clear"> </div>
                    <div class="EspacoAntes">&nbsp;</div>


                    <div id="buscaAvancada">
                        <div class="LinhaEntrada">
                            <form:errors path="consulta" cssClass="ValueErro" />
                            <div class="LabelLeft">
                                <b>Texto</b> para a busca
                            </div>
                            <div class="Value">
                                <form:input path="consulta"/>
                            </div>
                        </div>  

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar objetos <b>de autoria</b> de
                            </div>
                            <div class="Value">
                                <form:input path="autor"/>
                            </div>
                        </div> 

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar <b>formato</b> de objetos
                            </div>
                            <div class="Value">
                                <form:input path="format"/>
                            </div>
                        </div>

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar <b>tamanho</b> de objetos
                            </div>
                            <div class="Value">
                                <form:input path="size"/>
                            </div>
                        </div>

                        <div class="LinhaEntrada">
                            <form:errors path="idioma" cssClass="ValueErro" />
                            <div class="LabelLeft">
                                <b>Idioma</b>
                            </div>
                            <div class="Value">
                                <form:select path="idioma" items="${buscaModel.languages}"/>
                            </div>
                        </div> 

                        <%--                        <div class="LinhaEntrada">
                                                    <div class="LabelLeft">
                                                        Pesquisar objetos por <b>idade</b>
                                                    </div>
                                                    <div class="Value">
                                                        fazer lista
                                                        <input type="range" name="age_range" id="age_range" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                                                    </div>
                                                </div>
                                                    
                                                <div class="LinhaEntrada">
                                                    <div class="LabelLeft">
                                                        Pesquisar objetos por <b>dificuldade</b>
                                                    </div>
                                                    <div class="Value">
                                                    <form:input path="difficult"/>
                                                        <input type="text" name="difficult" id="difficult" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                                                    </div>
                                                </div> --%>

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                <b>Custo</b>
                            </div>
                            <div class="Value">
                                <form:radiobutton path="cost" value="trrue"/> Grátis
                                <form:radiobutton path="cost" value="false"/> Pagos
                                <form:radiobutton path="cost" value=""/> Ambos
                            </div>
                        </div> 

                        <div class="LinhaEntrada">
                            <div class="LabelLeft"> 
                                Objetos com <b>visual</b>
                            </div>
                            <div class="Value">
                                <form:radiobutton path="hasVisual" value="true"/> Sim
                                <form:radiobutton path="hasVisual" value="false"/> Não
                                <form:radiobutton path="hasVisual" value=""/> Ambos
                            </div>
                        </div> 

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Objetos <b>auditivos</b>
                            </div>
                            <div class="Value">
                                <form:radiobutton path="hasAditory" value="true"/> Sim
                                <form:radiobutton path="hasAditory" value="false"/> Não
                                <form:radiobutton path="hasAditory" value=""/> Ambos
                            </div>
                        </div>

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Objetos <b>textuais</b>
                            </div>
                            <div class="Value">
                                <form:radiobutton path="hasText" value="true"/> Sim
                                <form:radiobutton path="hasText" value="false"/> Não
                                <form:radiobutton path="hasText" value=""/> Ambos
                            </div>
                        </div>    

                        <div class="LinhaEntrada">
                            <div class="Buttons">
                                <input class="BOTAO" type="submit" value="Consultar"/>
                            </div>
                        </div>
                    </div>
                    <div class="EspacoAntes">&nbsp;</div>
                    <div>
                        <a href="${index}">Retornar a busca padr&atilde;o</a>
                    </div>
                </div>
            </form:form>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
