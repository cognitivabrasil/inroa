<%-- 
    Document   : editaPadrao
    Created on : 01/10/2010, 10:52:12
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../testaSessaoNovaJanela.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>FEB - Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>

        <link rel="StyleSheet" href="../../css/padrao.css" type="text/css">
        <link href="../../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />

        <script language="JavaScript" type="text/javascript" src="../../scripts/funcoes.js"></script>
        <script language="JavaScript" type="text/javascript" src="/feb/scripts/funcoesPadrao.js"></script>


    </head>
    <body onUnLoad="recarrega()">
        <div id="page">

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de padr&otilde;es cadastrados</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>

            <div class="LinhaForm">
                <div class="Legenda">
                    Nome do Padr&atilde;o:
                </div>
                <div class="Editar">&nbsp;
                    <input type="button" class="botaoEditar" size="30" name="editarNome" id="editarNome" onclick="exibeText('nome', '${padrao.id}', 'nome', this)"/>
                </div>
                <div class="Valor" id="nome">${padrao.nome}</div>


                <div class="Legenda">
                    metadataPrefix:
                </div>
                <div class="Editar">&nbsp;
                    <input type="button" class="botaoEditar" size="30" name="editarMetadataPrefix" id="editarMetadataPrefix"  onclick="exibeText('metadataprefix', '${padrao.id}', 'metadata_prefix', this)"/>
                </div>
                <div class="Valor" id="metadataprefix">${padrao.metadataPrefix}</div>

                <div class="Legenda">
                    nameSpace:
                </div>
                <div class="Editar">&nbsp;
                    <input type="button" class="botaoEditar" size="30" name="editarNameSpace" id="editarNameSpace"  onclick="exibeText('namespace','${padrao.id}', 'name_space', this)"/>
                </div>
                <div class="Valor" id="namespace">${padrao.namespace}</div>

            </div>

            <div class="subtitulo">Atributos do padr&atilde;o</div>
            <div id='msgerro' class='textoErro center'></div>
       <!--Tabela que apresenta os atributos-->
            <table class='mapeamentos-table' id="tabela" cellpadding=5%>

                <tr style="background-color: #AEC9E3">
                    <th width="30%">Atributo</th>
                    <th width="10%">Opera&ccedil;&otilde;es</th>
                </tr>
                
                <c:forEach var="atributos" items="${padrao.atributosList}" varStatus="linha">
                    <tr class="${linha.index % 2 == 0? 'price-yes' : 'price-no'} center" > 
                        <td>
                             <div id="atributo${linha.index}">${atributos}</div>
                         </td>
                         <td>
                        <input type="button" class="botaoEditar" size="30" name="editar" id="editar"  onclick="exibeTextAtributo('atributo${linha.index}', '1', this)"/>
                        <a title="Excluir" onclick="confirmaExclusao('1', 'msgerro', this.parentNode.parentNode.rowIndex)">
                            <img src="../../imagens/ico24_deletar.gif" border="0" width="24" height="24" alt="Excluir" align="middle">
                        </a>
                        
                    </td>
                </tr>
                </c:forEach>
            </table>
<!--funcao que seta a linha-->
<!--script type="text/javascript">
                setLinha('1');
            </script-->

 <table class='mapeamentos-table-add' id="tblAdicionar" cellpadding=5%>
                <tr class='center'>
                    
                    <td width="10%">&nbsp;</td>
                    <td width="80%">

                        <a title="Adicionar Atributo" onclick="">
                            <img src="../../imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                        </a>


                        &nbsp;&nbsp;
                        <a onclick="adicionaAtributo('1', this)">
                            Adicionar Atributo
                        </a>

                    </td>


                </tr>
            </table>
        </div>
    </body>
</html>