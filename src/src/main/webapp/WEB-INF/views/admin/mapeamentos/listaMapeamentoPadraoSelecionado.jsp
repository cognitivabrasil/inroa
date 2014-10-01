<%-- 
    Document   : listaMapeamentoPadraoSelecionado
    Created on : 17/02/2011, 18:11:06
    Author     : Marcos
--%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<c:choose>
    <c:when test="${idZero}">
        <div class="Value">Selecione um padr&atilde;o</div>
        <input type='hidden' id='mapeamento.id' name=\"idTipoMapeamento\" value=''>
        <input type="hidden" id="metadataPrefix"  name="metadataPrefix" value=''>
        <input type="hidden" id="namespace" name="namespace" value=''>
    </c:when>
    <c:otherwise>
        <c:if test="${empty padraoMet.mapeamentos}"> <div class="ValueIndex textoErro">N&atilde;o existe nenhum mapeamento cadastrado!</div></c:if>
        <c:forEach var="map" items="${padraoMet.mapeamentos}">
            <div class="ValueIndex">
                <input type="radio" ${map.id == mapSelecionado ? 'checked=true':''} id="mapeamento.id" name="mapeamento.id" value="${map.id}">${map.name} (${map.description})</div>
        </c:forEach>
    </c:otherwise>
</c:choose>
                    
<c:if test="${novoRep}">
    <div> &nbsp;</div>
    <form:errors path="metadataPrefix" cssClass="ValueErro" />
    <div class="Label">MetadataPrefix:</div>
    <div class="Value">  
        <input type="text" value="${padraoMet.metadataPrefix}" id="metadataPrefix" name="metadataPrefix" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
    </div>
    <form:errors path="namespace" cssClass="ValueErro" />
    <div class="Label">NameSpace:</div>
    <div class="Value">
        <input type="text" value="${padraoMet.namespace}" id="namespace" name="namespace" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" /> 
    </div>
</c:if>
