<%-- 
    Document   : editaMapeamentos
    Created on : 19/08/2010, 16:22:05
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../conexaoBD.jsp"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css">
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoes.js">
            //necessario para usar o ajax
        </script>
        <script type="text/javascript">

            function cancelar(idResultado, idMap, idPadraoDestino)
            {
                processo(idResultado, idMap, idPadraoDestino, "cancelar","")
            }

            function exibeSelect(idResultado, idMap, idPadraoDestino)
            {
                processo(idResultado, idMap, idPadraoDestino, "comboBox", "")
            }

            function salvarBase(idResultado, idMap)
            {
                var novo = document.getElementById("atributos").value;
                processo(idResultado, idMap, 0, "salvar", novo)
            }
            /**
             * Função utilizada pelo mapeamento dinamico com ajax.
             * Quando chamada, ela repassa os dados, utilizando ajax, para o arquivo jsp que rodará sem que a pagina principal seja recarregada.
             */
            function processo(idResultado, idMap, idPadraoDestino, acao, novoValor)
            {
                //var nome = document.getElementById('nome').value; //Note que as variáveis são resgatadas pela função getElementById.
                

                var exibeResultado = document.getElementById(idResultado);
                
                var ajax = openAjax(); // Inicia o Ajax.
                //ajax.open("GET", "respostaAjax.jsp?nome=" + nome, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.
                ajax.open("GET", "respostaAjax.jsp?tipo="+acao+"&idPadraoDestino="+idPadraoDestino+"&idMap="+idMap+"&idResultado="+idResultado+"&novo="+novoValor, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.
                ajax.onreadystatechange = function()
                {
                    if(ajax.readyState == 1) // Quando estiver carregando, exibe: carregando...
                    {
                        exibeResultado.innerHTML = "Aguarde...";
                    }
                    if(ajax.readyState == 4) // Quando estiver tudo pronto.
                    {
                        if(ajax.status == 200)
                        {
                            var resultado = ajax.responseText;
                            exibeResultado.innerHTML = resultado;
                        }
                        else
                        {
                            exibeResultado.innerHTML = "Erro nas funções do Ajax";
                        }
                    }
                }
                ajax.send(null); // submete
                //document.getElementById("nome").value= "";//limpa os campos
                //document.getElementById("nome").setFocus=true;

            }

        </script>
    </head>
    <body>


        <div id="page">
            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de mapeamentos cadastrados</div>
            <table class='mapeamentos-table' cellpadding=5%>
            
            <tr style="background-color: #AEC9E3">
                <th width="30%">Origem</th>

                <th width="50%">Destino (OBAA)</th>
                <th width="10%">Complementar</th>
                <th width="10%">&nbsp;</th>

            </tr>
            
            <%
                int tipoMapeamento = 0;
                int idPadrao = 0;
                int linha = 1;
                String yesnocolor = "";
                String atributoComplementar="";
                String valorComplementar="";
                try {
                    String tipoMap = request.getParameter("tipmap");
                    String padrao = request.getParameter("padrao");
                    if (tipoMap.isEmpty() || padrao.isEmpty()) { //se não foi informado nenhum valor.
                        new Exception();
                    }
                    tipoMapeamento = Integer.parseInt(tipoMap);
                    idPadrao = Integer.parseInt(padrao);
                    if (tipoMapeamento < 1 || idPadrao < 1) {//se for informado um valor invalido
                        new Exception();
                    }
                } catch (Exception e) {
                    out.println("Informe o tipo do mapeamento e o padrão de metadados.");
                    //out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>" +
                    //                   "<script type='text/javascript'>history.back(-1);</script>");
                    }

                String sqlPadrao = "SELECT nome FROM padraometadados p WHERE p.id=" + idPadrao + ";";
                String sqlMap = "SELECT m.id as idMap, a1.atributo as origem, a2.atributo as destino, a2.idPadrao as idPadraoDestino, m.mapeamentoComposto_id" +
                        " FROM atributos a1, mapeamentos m, atributos a2" +
                        " WHERE a1.id=m.origem_id and a2.id=m.destino_id and m.tipoMapeamento_id=" + tipoMapeamento + " AND m.padraometadados_id=" + idPadrao + ";";

                ResultSet rs1 = stm.executeQuery(sqlPadrao);
                //pega o proximo resultado retornado pela consulta sql
                rs1.next();
                String nomePadrao = rs1.getString("nome");
                //rs1.close();
                out.print("<div class=\"subtitulo\">" + nomePadrao + " / <b>OBAA - COLOCAR NOME DO MAPEAMENTO</b></div>");
                
                    ResultSet rs2 = stm.executeQuery(sqlMap);
                    //pega o proximo resultado retornado pela consulta sql
                    while (rs2.next()) {
                    String origem = rs2.getString("origem");
                    String destino = rs2.getString("destino");
                    int idComplementar = rs2.getInt("mapeamentoComposto_id");
                    int idMapeamento = rs2.getInt("idMap");
                    int idPadraoDestino = rs2.getInt("idPadraoDestino");

                    if (linha % 2 == 0) {
                        yesnocolor = "price-yes";
                    } else {
                        yesnocolor = "price-no";
                    }
            %>
            
            <tr class='center'>
                <td class="<%=yesnocolor%>">&nbsp;<%=origem%></td>
                <td class="<%=yesnocolor%>">
                    <div id='<%="result"+linha%>'>&nbsp;<%=destino%></div>
                </td>
                
            
            
                <%

                    atributoComplementar="";
                    valorComplementar="";
                    if (idComplementar > 0) { //se tiver mapeamento complementar

                        String sql = "SELECT a.atributo as destino, m.valor " + "FROM mapeamentoComposto m, atributos a " + "WHERE m.id_origem=a.id " + "AND m.id=" + idComplementar + ";";
                        ResultSet rs;
                        Statement stm2 = con.createStatement();
                        rs = stm2.executeQuery(sql);
                        rs.next();//procura a próxima ocorrencia
                        valorComplementar = rs.getString("valor");
                        atributoComplementar = rs.getString("destino");
                        
                        stm2.close();
                        rs.close();
                        out.println("<td class=\""+yesnocolor+"\">&nbsp;Sim</td>");

                    }
                    else
                        out.println("<td class=\""+yesnocolor+"\">&nbsp;-</td>");
                %>
                <td class="<%=yesnocolor%>">
                    <input type="button" size="30" name="editar" id="editar" value="Editar" onclick="exibeSelect('<%="result"+linha%>', '<%=idMapeamento%>', '<%=idPadraoDestino%>')"/>
                    <a title="Excluir" onclick="">
                        <img src="../imagens/ico24_deletar.gif" border="0" width="24" height="24" alt="Laudado!" align="middle">
                    </a>
                </td>
            </tr>
            <%
            if(!atributoComplementar.isEmpty() && !valorComplementar.isEmpty()){
                %>
                <tr class='center'>
                    <td class="<%=yesnocolor%>"><%=atributoComplementar%></td>
                    <td class="<%=yesnocolor%>"><%=valorComplementar%></td>
                    <td class="<%=yesnocolor%>"></td>
                    <td class="<%=yesnocolor%>"></td>
                </tr>
            <%
                
            }
             linha++;
                      }


            %>
            <tr class='center'>
                <td>

                    <a title="Adicionar" onclick="">
                        <img src="../imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </a>

                
                    &nbsp;&nbsp;
                    <a onclick="NewWindow('cadastraRepositorio.jsp','Cadastro','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        Adicionar
                    </a>

                </td>

                <% linha++;%>

            </tr>
            </table>
        </div>
    </body>
</html>
<%
            con.close();

%>
