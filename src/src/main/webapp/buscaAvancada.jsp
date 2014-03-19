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
            
            <form:form method="post" modelAttribute="buscaModel" action="consultaAvancada" acceptCharset="utf-8">    
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
                                <input type="text" name="consulta" id="consulta" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                            </div>
                        </div>  
                        
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar objetos <b>de autoria</b> de
                            </div>
                            <div class="Value">
                                <input type="text" name="autor" id="autor" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                            </div>
                        </div>
                            
                        <div class="LinhaEntrada">
                            <form:errors path="idioma" cssClass="ValueErro" />
                            <div class="LabelLeft">
                                <b>Idioma</b>
                            </div>
                            <div class="Value">
                                <input type="text" name="idioma" id="consulta" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                            </div>
                        </div>  
                            
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar <b>formato</b> de objetos
                            </div>
                            <div class="Value">
                                <input type="text" name="format" id="format" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                            </div>
                        </div>
                            
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar <b>tamanho</b> de objetos
                            </div>
                            <div class="Value">
                                <input type="text" name="size" id="size" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                            </div>
                        </div>
                            
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar objetos por <b>idade</b>
                            </div>
                            <div class="Value">
                                fazer lista
                                <input type="range" name="age_range" id="age_range" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                            </div>
                        </div> 
                            
<!--                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar objetos por <b>dificuldade</b>
                            </div>
                            <div class="Value">
                                <input type="text" name="difficult" id="difficult" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                            </div>
                        </div> -->
                            
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                <b>Custo</b>
                            </div>
                            <div class="Value">
                               <input type="radio" name="cost" value="false" onBlur="this.className=''"/> Grátis <input type="radio" name="cost" value="true" onBlur="this.className=''"/> Pagos <input type="radio" name="cost" value="" onBlur="this.className=''"/> Ambos
                            </div>
                        </div> 
                            
                        <div class="LinhaEntrada">
                            <div class="LabelLeft"> 
                                Objetos com <b>visual</b>
                            </div>
                            <div class="Value">
                                <input type="radio" name="hasVisual" value="sim" onBlur="this.className=''"/> Sim <input type="radio" name="hasVisual" value="nao" onBlur="this.className=''"/> Não <input type="radio" name="hasVisual" value="" checked="true" onBlur="this.className=''"/> Ambos
                            </div>
                        </div> 
                         
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Objetos <b>auditivos</b>
                            </div>
                            <div class="Value">
                                <input type="radio" name="hasAditory" value="sim" onBlur="this.className=''"/> Sim <input type="radio" name="hasAditory" value="nao" onBlur="this.className=''"/> Não <input type="radio" name="hasAditory" value="" onBlur="this.className=''"/> Ambos
                            </div>
                        </div>
                            
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Objetos <b>textuais</b>
                            </div>
                            <div class="Value">
                                <input type="radio" name="hasText" value="sim" onBlur="this.className=''"/> Sim <input type="radio" name="hasText" value="nao" onBlur="this.className=''"/> Não <input type="radio" name="hasText" value=" " onBlur="this.className=''"/> Ambos
                            </div>
                        </div>    
                            
                        <div class="LinhaEntrada">
                            <div class="Buttons">
                                <input class="BOTAO" type="submit" value="Consultar"/>
                            </div>
                        </div>
                    </div>
                    
                    <div>
                        <a href="${index}">Retornar a busca padr&atilde;o</a>
                    </div>
                </div>
            </form:form>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
