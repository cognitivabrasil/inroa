<%--
    Document   : editarRepositorio
    Created on : 18/08/2009, 14:59:26
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@include file="conexaoBD.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css"/>
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoes.js"></script>
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoesMapeamento.js"> //funcoes javascript que chamam o ajax</script>
        <script type="text/javascript" src="../scripts/validatejs.js"></script>
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>

    <body>
        <div id="page">
            <%
                Statement stm = con.createStatement();

                String id = request.getParameter("id");


                    String sql = "SELECT p.id as id_padrao, i.tipo_mapeamento_id FROM padraometadados p, info_repositorios i WHERE i.padrao_metadados=p.id AND i.id_repositorio=" + id;
                    
                    ResultSet res = stm.executeQuery(sql);

                    res.next();
                    int idPadrao = res.getInt("id_padrao");
                    int idTipoMap = res.getInt("tipo_mapeamento_id");


            %>
            <script type="text/javascript">
                var myForm = new Validate();
                myForm.addRules({id:'nome',option:'required',error:'* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
                myForm.addRules({id:'descricao',option:'required',error:'* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
                myForm.addRules({id:'padraoMet',option:'required',error:'* Deve ser informado o padr&atilde;o dos metadados do repositorio!'});
                myForm.addRules({id:'rdMap',option:'isNotEmpty',error:'* Deve ser selecionado o tipo de mapeamento!'});
                myForm.addRules({id:'metadataPrefix',option:'required',error:'* Deve ser informado o MetadataPrefix!'});
                myForm.addRules({id:'namespace',option:'required',error:'* Deve ser informado o NameSpace!'});
                myForm.addRules({id:'url',option:'urlcomip',error:'* Deve ser informada uma url <b>v&aacute;lida</b> que responda com protocolo OAI-PMH! Come&ccedil;ando por http://'});
                myForm.addRules({id:'periodicidadeAtualizacao',option:'required',error:'* Deve ser informado a periodicidade de atualiza&ccedil;&atilde;o. Em dias!'});
            </script>

            <div class="subTitulo-center">&nbsp;Editanto reposit&oacute;rio ${repModel.nome}</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
            <div class="EspacoAntes">&nbsp;</div>
            <form:form method="post" modelAttribute="repModel" action="salvarRepositorio" acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">
                <form:errors path="*" cssClass="error" />
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Nome:
                    </div>
                    <div class="Value">
                        <form:errors path="nome" cssClass="error" />
                        <form:input path="nome" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />                        
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Descri&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <form:errors path="descricao" cssClass="error" />
                        <form:input path="descricao" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Label">
                        Padr&atilde;o de metadados utilizado:
                    </div>
                    <div class="Value">
                        <select name="idPadraoMetadados" id="padraoMet" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" onChange="selecionaMapeamento('resultado', this.value, 'edita');">
                            <c:forEach var="padraoMet" items="${padraoMetadadosDAO.all}">  
                                <!--TESTAR SE PADRAO EH O UTILIZADO
                                <option value="${padraoMet.id}" selected> ${fn:toUpperCase(padraoMet.nome)}
                                -->
                                <option value="${padraoMet.id}"> ${fn:toUpperCase(padraoMet.nome)}
                                    
                                </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Label">
                        Tipo de mapeamento:
                    </div>
                    <div id="resultado">
                        <%
                            String sqlTM = "SELECT t.nome as tipo_map, t.descricao, t.id as id_map"
                                    + "  FROM mapeamentos m, tipomapeamento t"
                                    + "  WHERE m.tipo_mapeamento_id=t.id AND m.padraometadados_id=" + idPadrao
                                    + "  GROUP BY t.id, t.nome, t.descricao;";
                            ResultSet rsTM = stm.executeQuery(sqlTM);
                            while (rsTM.next()) {
                                int idtMap = rsTM.getInt("id_map");
                                if (idtMap == idTipoMap) {//se for o tipo que esta sendo usado deixa marcado o radio
                                    out.println("<div class=\"ValueIndex\"><input type=\"radio\" checked=true id=\"rdMap\" name=\"tipo_map\" value=" + rsTM.getString("id_map") + ">" + rsTM.getString("tipo_map") + " (" + rsTM.getString("descricao") + ")</div>");
                                } else {
                                    out.println("<div class=\"ValueIndex\"><input type=\"radio\" id=\"rdMap\" name=\"tipo_map\" value=" + rsTM.getString("id_map") + ">" + rsTM.getString("tipo_map") + " (" + rsTM.getString("descricao") + ")</div>");
                                }
                            }

                        %>

                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div> &nbsp;</div>
                    <div class="Label">MetadataPrefix:</div>
                    <div class="Value">
                        <form:errors path="metadataPrefix" cssClass="error" />
                        <form:input path="metadataPrefix" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                    <div class="Label">NameSpace:</div>
                    <div class="Value">
                        <form:errors path="namespace" cssClass="error" />
                        <form:input path="namespace" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                    
                <div class="subtitulo">Sincroniza&ccedil;&atilde;o dos metadados</div>
                <div class="EspacoAntes">&nbsp;</div>


                <div class="LinhaEntrada">
                    <div class="Label">
                        URL que responde OAI-PMH:
                    </div>
                    <div class="Value">
                        <form:errors path="url" cssClass="error" />
                        <form:input path="url" maxlength="200" onFocus="this.className='inputSelecionado'" onBlur="this.className='';verificaLinkOAI(this.value, this, document.getElementById('resultadoTesteOAI'), document.getElementById('confereLinkOAI'))" />
                        &nbsp;<div id="resultadoTesteOAI" class="linkCantoDireito"></div>
                    </div>
                    <input type="hidden" id="confereLinkOAI" value="">
                </div>

                <div class="LinhaEntrada">
                    <div class="Label">
                        Cole&ccedil;&otilde;es ou Comunidades:
                    </div>
                    <div class="Comentario">
                        Se for mais de uma separar por ponto e v&iacute;rgula.
                    </div>
                    <div class="Comentario">
                        Ex: com1;com2;com3
                    </div>
                    <div class="Value">
                        <form:errors path="colecoes" cssClass="error" />
                        <form:input path="colecoes" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />                        
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Periodicidade de atualiza&ccedil;&atilde;o (em dias):
                    </div>
                    <div class="Value">
                        <form:errors path="periodicidadeAtualizacao" cssClass="error" />
                        <form:input path="periodicidadeAtualizacao" maxlength="3" onkeypress ="return ( isNumber(event) );" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <input type="hidden" name="id" value="${param.id}"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="&lArr; Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar &rArr;" name="submit" />

                    </div>
                </div>

            </form:form>

        </div>
        <%                    
            con.close();
        %>
        <%@include file="../googleAnalytics"%>
    </body>
</html>
