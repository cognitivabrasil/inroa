<%-- 
    Document   : infoDetalhada
    Created on : 01/07/2009, 17:59:06
    Author     : Marcos


COLOCAR STILO NO CSS E TIRAR OS <FONTE> DO CODIGO

//codigo pronto para testar se existe a chave obaa... e se existir mais de uma percorrer todas testaldo se é igual a ""


--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="operacoesLdap.Consultar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            ArrayList<HashMap> resultHash = new ArrayList<HashMap>();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federação de Repositórios Educa Brasil</title>
        <link rel="StyleSheet" href="./css/padrao.css" type="text/css"/>
    </head>
    <body>

        <%
            String ipServidor = request.getParameter("ip");
            String id = request.getParameter("id");
            String dn = request.getParameter("dn");
            String consulta = "(obaaEntry=" + id + ")";

            Consultar busca = new Consultar(ipServidor, consulta, dn, "", "", 389);
            resultHash = busca.getResultado();

            HashMap dados = new HashMap();
            //coloca no HashMap dados o resultado recebido

            dados = (HashMap) resultHash.get(0);

            //se retornar mais de um objeto com o mesmo identificador imprime um erro na tela
            if (resultHash.size() > 1) {
                out.println("<font color=red>ERRO no repositorio: Retornou mais de um resultado com o mesmo identificador</font>");
            }

        %>

        <!--INICIO TRADUCAO CODIGO PHP-->

        <table width="90%" border="0" align="center">
            <tr>
                <td colspan="4" height="24">
                    <strong>
                        <font size="4" face="Verdana, Arial, Helvetica, sans-serif">
                            <%=dados.get("obaaTitle")%>
                        </font>
                    </strong>
                </td>
            </tr>
            <tr>
                <td colspan="4">

                    <font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        &nbsp;

                        <strong>
                            <font color="#32618F">
                                <% if (dados.containsKey("obaaIdentifier")) {
                                    String obaaIdentifier[] = dados.get("obaaIdentifier").toString().split(";; ");
                                %>
                                <em>Objeto <%=obaaIdentifier[0]%></em>
                                <%} else {
                                     if (dados.containsKey("obaaEntry")){
                                %>
                                    <em>Objeto <%=dados.get("obaaEntry")%></em>
                                    <%}}%>
                            </font>
                        </strong>

                        <br>
                    </font>
                </td>
            </tr>

            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>

            <tr>
                <td width="3%">&nbsp;</td>
                <td colspan="3">
                    <strong>
                        <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                            Informa&ccedil;&otilde;es Gerais do Objeto
                        </font>
                    </strong>
                </td>
            </tr>
            <%
            if (dados.containsKey("obaaLanguage")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td width="3%">&nbsp;</td>

                <td width="24%" valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Idioma:
                    </font>
                </td>

                <td width="70%" valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%            
                String lingua = dados.get("obaaLanguage").toString().trim();
                if (lingua.equalsIgnoreCase("PT-BR")) {
                    out.print("Portugu&ecirc;s do Brasil");
                } else if (lingua.equalsIgnoreCase("PT-PT")) {
                    out.print("Portugu&ecirc;s de Portugal");
                } else if (lingua.equalsIgnoreCase("EN-US")) {
                    out.print("Ingl&ecirc;s dos Estados Unidos");
                } else if (lingua.equalsIgnoreCase("EN-UK")) {
                    out.print("Ingl&ecirc;s da Inglaterra");
                } else if (lingua.equalsIgnoreCase("SP-SP")) {
                    out.print("Espanhol da Espanha");
                } else if (lingua.equalsIgnoreCase("SP-LA")) {
                    out.print("Espanhol Latino-americano");
                } else if (lingua.equalsIgnoreCase("FR-FR")) {
                    out.print("Franc&ecirc;s da Fran&ccedil;a");
                } else if (lingua.equalsIgnoreCase("FR-CA")) {
                    out.print("Franc&ecirc;s do Canad&aacute;");
                } else {
                    out.print(lingua);
                }
                        %>
                    </font>
                </td>
            </tr>
            <%}%>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Descri&ccedil;&atilde;o:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            int cont = 0;
            if (dados.containsKey("obaaDescription")) {
                String[] tempObaa = dados.get("obaaDescription").toString().split(";; ");
                for (int kk = 0; kk < tempObaa.length; kk++) {
                    //apos o primeiro elemento colocar um "<BR>"
                    if (kk > 0) {
                        out.print("<p>");
                    }

                    out.print("- " + tempObaa[kk].trim());
                }
            } else {
                out.print("N&#227;o documentado");
            }
                        %>



                    </font>
                </td>
            </tr>
            <%
            if (dados.containsKey("obaaKeyword")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Palavra chave:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%

                //imprime cada palavra chave em uma linha, desde que estejam separadas por ";; "
                String obaaTemp = dados.get("obaaKeyword").toString().trim();

            String temp[] = obaaTemp.split(";; ");
            for (int kk = 0; kk < temp.length; kk++) {
                //apos o primeiro elemento colocar um "<BR>"
                if (kk > 0) {
                    out.print("<BR>");
                }
                out.print(temp[kk].trim());
            }
            
                        %>

                    </font>
                </td>
            </tr>
            <%
            }

            if (dados.containsKey("obaaFormat") || dados.containsKey("obaaSize")) {
            %>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="3">
                    <strong>
                        <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                            Informa&ccedil;&otilde;es T&eacute;cnicas Sobre o Objeto
                        </font>
                    </strong>
                </td>
            </tr>
            <tr>
                <%
            if (dados.containsKey("obaaFormat")) {
                %>
                <td>&nbsp;</td>
                <td>&nbsp;</td>

                <td valign="top"><font size="2" face="Verdana, Arial, Helvetica, sans-serif">Formato:</font></td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%=dados.get("obaaFormat")%>

                    </font></td>
                    <%}%>
            </tr>
            <%
            if (dados.containsKey("obaaSize")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Tamanho:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">

                        <%=dados.get("obaaSize")%>
                        <!--Tem que ver se por padrao o tamanho eh salvo em bits-->
                        <%
                        //((Float.parseFloat(dados.get("obaaSize").toString()) * 1) / 1048576)
                        %>

                        
                    </font>
                </td>
            </tr>
            <% }
            }//fim teste se um dos dois existe
            if (dados.containsKey("obaaType") || dados.containsKey("obaaName")) {
            %>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="3">
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        <strong>Requisitos T&eacute;cnicos Para Funcionalidade do Objeto</strong>
                    </font>
                </td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Tipo/Nome da Tecnologia:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            //tem que ver se existe a possibilidade de ter dois obaaType ou dois obaaName
            //se existir tem que dar um split por ; e trabalhar com vetor

            String obaaType = null;
            String obaaName = null;


            //adiciona o atributo obaaType para uma variavel transformando em string e retirando os espaços em branco antes de depois
            if (dados.containsKey("obaaType")) {
                obaaType = dados.get("obaaType").toString().trim();
            }

                if (dados.containsKey("obaaName")) {
                obaaName = dados.get("obaaName").toString().trim();

            } else {
                obaaName = "N&#227;o documentado";
            }
            //testa se e sistema operacional ou navegador

            if (!obaaType.isEmpty()) {
                if (obaaType.equalsIgnoreCase("operational_system")) {
                    out.print("Sistema Operational/");
                } else if (obaaType.equalsIgnoreCase("browser")) {
                    out.print("Navegador/");
                } else {
                    out.print(obaaType + "/");
                }
            }


            //imprime qual eh o tipo do sistema operacional ou navegador
            if (obaaName.equalsIgnoreCase("pc-dos")) {
                out.print("PC-DOS");
            } else if (obaaName.equalsIgnoreCase("ms-windows")) {
                out.print("MS-Windows");
            } else if (obaaName.equalsIgnoreCase("macos")) {
                out.print("Mac-OS");
            } else if (obaaName.equalsIgnoreCase("unix")) {
                out.print("Unix");
            } else if (obaaName.equalsIgnoreCase("multi-os")) {
                out.print("MULTI-OS");
            } else if (obaaName.equalsIgnoreCase("none")) {
                out.print("Nenhum");
            } else if (obaaName.equalsIgnoreCase("any")) {
                out.print("Qualquer um");
            } else if (obaaName.equalsIgnoreCase("netscape navegator") || obaaName.equalsIgnoreCase("netscape")) {
                out.print("Nescape");
            } else if (obaaName.equalsIgnoreCase("ms-internet explorer") || obaaName.equalsIgnoreCase("ms-internet")) {
                out.print("MS-Internet Explorer");
            } else if (obaaName.equalsIgnoreCase("opera")) {
                out.print("Opera");
            } else if (obaaName.equalsIgnoreCase("amaya")) {
                out.print("Amaya");
            } else {
                out.print(obaaName);
            }

                        %>


                    </font></td>
            </tr>
            <%
            }
            %>
            
            <%
            if (dados.containsKey("obaaDuration")) {
            %>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="3">
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        <strong>Dura&ccedil;&atilde;o do Objeto</strong>
                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    Tempo de dura&ccedil;&atilde;o:
                </td>

                <td>                    
                <%=dados.get("obaaDuration")%>
                </td>
            </tr>
            <%
            }
            if(dados.containsKey("obaaVersion") || dados.containsKey("obaaStatus")){

%>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="3">
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        <strong>Ciclo de Vida do Objeto</strong>
                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Vers&atilde;o:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaaVersion")) {
                out.print(dados.get("obaaVersion"));
            } else {
                out.print("N&#227;o Documentado");
            }
                        %>
                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">Status:</font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaaStatus")) {
                if (dados.get("obaaStatus").toString().trim().equalsIgnoreCase("draft")) {
                    out.print("Rascunho");
                } else if (dados.get("obaaStatus").toString().trim().equalsIgnoreCase("final")) {
                    out.print("Final");
                } else if (dados.get("obaaStatus").toString().trim().equalsIgnoreCase("revised")) {
                    out.print("Revisado");
                } else if (dados.get("obaaStatus").toString().trim().equalsIgnoreCase("unavailable")) {
                    out.print("Indispon&iacute;vel");
                }
            } else {
                out.print("N&#227;o Documentado");
            }
                        %>
                    </font>
                </td>
            </tr>
            <%
            } //fim if que testa se um dos dois existe
            %>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="3">
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        <strong>Contribui&ccedil;&otilde;es Para o Objeto</strong>
                    </font>
                </td>
            </tr>

            <%
            if (dados.containsKey("obaaEntity")){
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Entidade que contribuiu:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        
                        <%
            String obaaTemp = dados.get("obaaEntity").toString().trim();

            String temp[] = obaaTemp.split(";; ");
            for (int kk = 0; kk < temp.length; kk++) {
                //apos o primeiro elemento colocar um "<BR>"
                if (kk > 0) {
                    out.print("; ");
                }
                out.print(temp[kk].trim());
            }

                        %>

                    </font>
                </td>
            </tr>
            <%
            }//fim id se obaaEntity existe
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Data da contribui&ccedil;&atilde;o:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.get("obaaDate") != null) {
                out.print(dados.get("obaaDate") + " (Ver aqui como vamos imprimir a data)");
            } else {
                out.print("N&#227;o documentada");
            }
                        %>
                        <!--?php
            if (IsSet($info[0]["obaadate"][0])){


            $date = explode( "-", $info[0]["obaadate"][0] );
            $meses = array(	"01" => "Janeiro", 	"02" => "Fevereiro", 	"03" => "Mar&ccedil;o",
                            "04" => "Abril",	"05" => "Maio",			"06" => "Junho",
                            "07" => "Julho",	"08" => "Agosto",		"09" => "Setembro",
                            "10" => "Outubro",	"11" => "Novembro",		"12" => "Dezembro" );
            $a = $date[0];
            $m = $meses[$date[1]];
            $d = $date[2];

            if(!$d | !$m | !$a ) echo "Não documentada";
            else echo "$d de $m de $a";
            }else{
                echo "Não documentada";
            }
                        ?-->
                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Tipo de contribui&ccedil;&atilde;o:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.get("obaaRole") == null) {
                out.print("N&#227;o documentado");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("creator")) {
                out.print("Cria&ccedil;&atilde;o");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("validator")) {
                out.print("Valida&ccedil;&atilde;o");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("unknown")) {
                out.print("Desconhecido");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("initiator")) {
                out.print("Inicializador");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("terminator")) {
                out.print("Finalizador");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("editor")) {
                out.print("Editor");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("graphical designer")) {
                out.print("Designer gr&aacute;fico");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("technical implementer")) {
                out.print("Implementador t&eacute;cnico");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("content provider")) {
                out.print("Provedor de conte&uacute;do");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("educational validator")) {
                out.print("Validador Educacional");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("script writer")) {
                out.print("Roteirista");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("instructional designer")) {
                out.print("Designer instrucional");
            } else if (dados.get("obaaRole").toString().trim().equalsIgnoreCase("subject matter expert")) {
                out.print("Especialista em conte&uacute;do");
            } else {
                out.print(dados.get("obaaRole").toString().trim());
            }
                        %>

                    </font></td>
            </tr>

            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td colspan="3">
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        <strong>Caracter&iacute;sticas Pedag&oacute;gicas e Educacionais do Objeto </strong>
                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Tipo de interatividade:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            //se o obaaInteractivityType N&#227;o existir a variavel contera "N&#227;o documentado"

            if (dados.containsKey("obaaInteractivityType")) {
                String obaaInteractivityType = dados.get("obaaInteractivityType").toString().trim();

                //imprime a traducao correspondente ou se N&#227;o imprime o que tiver na variavel
                if (obaaInteractivityType.equalsIgnoreCase("active")) {
                    out.print("Ativo");
                } else if (obaaInteractivityType.equalsIgnoreCase("expositive")) {
                    out.print("Expositivo");
                } else if (obaaInteractivityType.equalsIgnoreCase("mixed")) {
                    out.print("Misto");
                } else {
                    out.print(obaaInteractivityType);
                }
            } else {
                out.print("N&#227;o documentado");
            }



                        %>

                    </font></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Tipo de recurso educacional:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaaLearningResourceType")) {
                String obaaLearningresourceType = null;
                obaaLearningresourceType = dados.get("obaaLearningResourceType").toString().trim();
                String temp[] = obaaLearningresourceType.split(";; ");
                for (int kk = 0; kk < temp.length; kk++) {
                    //apos o primeiro elemento colocar um "; "
                    if (kk > 0) {
                        out.print("; ");
                    }

                    if (temp[kk].trim().equalsIgnoreCase("exercise")) {
                        out.print("Exerc&iacute;cio");
                    } else if (temp[kk].trim().equalsIgnoreCase("simulation")) {
                        out.print("Simula&ccedil;&atilde;o");
                    } else if (temp[kk].trim().equalsIgnoreCase("questionnaire")) {
                        out.print("Question&aacute;rio");
                    } else if (temp[kk].trim().equalsIgnoreCase("diagram")) {
                        out.print("Diagrama");
                    } else if (temp[kk].trim().equalsIgnoreCase("figure")) {
                        out.print("Figura");
                    } else if (temp[kk].trim().equalsIgnoreCase("graph")) {
                        out.print("Gr&aacute;fico");
                    } else if (temp[kk].trim().equalsIgnoreCase("index")) {
                        out.print("&Iacute;ndice");
                    } else if (temp[kk].trim().equalsIgnoreCase("slide")) {
                        out.print("Slide");
                    } else if (temp[kk].trim().equalsIgnoreCase("table")) {
                        out.print("Tabela");
                    } else if (temp[kk].trim().equalsIgnoreCase("narrative text")) {
                        out.print("Narrativa de texto");
                    } else if (temp[kk].trim().equalsIgnoreCase("exam")) {
                        out.print("Exame");
                    } else if (temp[kk].trim().equalsIgnoreCase("experiment")) {
                        out.print("Experimento");
                    } else if (temp[kk].trim().equalsIgnoreCase("problem statement")) {
                        out.print("Indica&ccedil;&atilde;o de problema");
                    } else if (temp[kk].trim().equalsIgnoreCase("self assessment")) {
                        out.print("Auto-avalia&ccedil;&atilde;");
                    } else if (temp[kk].trim().equalsIgnoreCase("lecture")) {
                        out.print("Leitura");
                    } else {
                        out.print(temp[kk].trim());
                    }
                }

            } else {
                out.print("N&#227;o documentado");
            }
                        %>

                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        N&iacute;vel de Interatividade:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaaInteractivityLevel")) {
                String obaaInteractivityLevel = dados.get("obaaInteractivityLevel").toString().trim();
                if (obaaInteractivityLevel.equalsIgnoreCase("very low")) {
                    out.print("Muito baixo");
                } else if (obaaInteractivityLevel.equalsIgnoreCase("low")) {
                    out.print("Baixo");
                } else if (obaaInteractivityLevel.equalsIgnoreCase("medium")) {
                    out.print("M&eacute;dio");
                } else if (obaaInteractivityLevel.equalsIgnoreCase("high")) {
                    out.print("Alto");
                } else if (obaaInteractivityLevel.equalsIgnoreCase("very high")) {
                    out.print("Muito Alto");
                } else {
                    out.print(obaaInteractivityLevel);
                }
            } else {
                out.print("N&#227;o documentado");
            }
                        %>

                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Usu&aacute;rio final esperado:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaaIntendedEndUserRole")) {

                String obaaTemp = dados.get("obaaIntendedEndUserRole").toString().trim();

                String temp[] = obaaTemp.split(";; ");
                for (int kk = 0; kk < temp.length; kk++) {
                    //apos o primeiro elemento colocar um "; "
                    if (kk > 0) {
                        out.print("; ");
                    }

                    if (temp[kk].trim().equalsIgnoreCase("techer") || temp[kk].trim().equalsIgnoreCase("teacher")) {
                        out.print("Professor");
                    } else if (temp[kk].trim().equalsIgnoreCase("author")) {
                        out.print("Autor");
                    } else if (temp[kk].trim().equalsIgnoreCase("learner")) {
                        out.print("Aluno");
                    } else if (temp[kk].trim().equalsIgnoreCase("manager")) {
                        out.print("Administrador");
                    } else {
                        out.print(temp[kk].trim());
                    }
                }

            } else {
                out.print("N&#227;o documentado");
            }
                        %>
                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Ambiente de utiliza&ccedil;&atilde;o:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaaContext")) {

                String obaaTemp = dados.get("obaaContext").toString().trim();

                String temp[] = obaaTemp.split(";; ");
                for (int kk = 0; kk < temp.length; kk++) {
                    //apos o primeiro elemento colocar um "; "
                    if (kk > 0) {
                        out.print("; ");
                    }

                    if (temp[kk].trim().equalsIgnoreCase("school")) {
                        out.print("Escola");
                    } else if (temp[kk].trim().equalsIgnoreCase("higher education")) {
                        out.print("Faculdade");
                    } else if (temp[kk].trim().equalsIgnoreCase("training")) {
                        out.print("Treinamento");
                    } else if (temp[kk].trim().equalsIgnoreCase("other")) {
                        out.print("Outro");
                    } else {
                        out.print(temp[kk].trim());
                    }
                }

            } else {
                out.print("N&#227;o documentado");
            }
                        %>
                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Faixa et&aacute;ria:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaatypicalagerange")) {
                out.print(dados.get("obaatypicalagerange"));
            } else {
                out.print("N&#227;o documentado");
            }
                        %>

                    </font>
                </td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>

                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Descri&ccedil;&atilde;o:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            //obaaCaracDesc

            if (dados.containsKey("obaaEducationalDescription")) {

                String obaaTemp = dados.get("obaaEducationalDescription").toString().trim();

                String temp[] = obaaTemp.split(";; ");
                for (int kk = 0; kk < temp.length; kk++) {
                    //apos o primeiro elemento colocar um "<BR>"
                    if (kk > 0) {
                        out.print("<BR>");
                    }
                    out.print(temp[kk].trim());
                }

            } else {
                out.print("N&#227;o documentado");
            }
                        %>


                    </font></td>

            </tr>

            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="3">
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        <strong>Propriedade Intelectual do Objeto</strong>
                    </font>
                </td>
            </tr>
            <tr>
                <td height="22">&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Requer pagamento:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaaCost")) {
                String obaaTemp = dados.get("obaaCost").toString().trim();
                if (obaaTemp.equalsIgnoreCase("yes")) {
                    out.print("Sim");
                } else if (obaaTemp.equalsIgnoreCase("no")) {
                    out.print("N&atilde;o");
                } else {
                    out.print(obaaTemp);
                }
            } else {
                out.print("N&#227;o documentado");
            }
                        %>
                    </font>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Possui direito autoral:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaaCopyrightAndOtherRestrictions")) {
                String obaaTemp = dados.get("obaaCopyrightAndOtherRestrictions").toString().trim();
                if (obaaTemp.equalsIgnoreCase("yes")) {
                    out.print("Sim");
                } else if (obaaTemp.equalsIgnoreCase("no")) {
                    out.print("N&atilde;o");
                } else {
                    out.print(obaaTemp);
                }
            } else {
                out.print("N&#227;o documentado");
            }
                        %>

                    </font></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Condi&ccedil;&otilde;es de uso:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            if (dados.containsKey("obaaRightsDescription")) {
                out.print(dados.get("obaaRightsDescription"));
            } else {
                out.print("N&#227;o documentado");
            }
                        %>
                    </font>
                </td>
            </tr>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="3">
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        <strong>Localiza&ccedil;&atilde;o do Objeto</strong>
                    </font>
                </td>
            </tr>
            <%
            if (dados.containsKey("obaaLocation") || dados.containsKey("obaaResourceEntry") || dados.containsKey("obaaIdentifier")) {
%>
<tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2">
        <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
            <%
    String obaaTemp = "N&#227;o documentado";
    if (dados.containsKey("obaaLocation")) {
        obaaTemp = dados.get("obaaLocation").toString().trim();
    } else if (dados.containsKey("obaaResourceEntry")) {
        obaaTemp = dados.get("obaaResourceEntry").toString().trim();
    } else if (dados.containsKey("obaaIdentifier")) {
        String temp[] = dados.get("obaaIdentifier").toString().split(";; ");
        obaaTemp = "";
        
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].contains("http")) {
                if (i == 0) {
                    obaaTemp = temp[i];
                } else {
                    obaaTemp = obaaTemp + ";; " + obaaTemp;
                }
            }
        }
    }
    
    String temp[] = obaaTemp.split(";; ");
    
    for (int kk = 0; kk < temp.length; kk++) {
        //colocar aqui as tag que mostraram o link e de estrutura da pagina
%>

            <a href="<%=temp[kk].trim()%>" target="_new"><%=temp[kk].trim()%></a>

            <%
    } //fecha for

            %>
        </font>
    </td>
</tr>
<%
            }
%>
<!--Atributos do obaa-->
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <%
            if (dados.containsKey("obaaSupportedPlatforms")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td colspan="3">
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        <strong>Metadados OBAA</strong>
                    </font>
                </td>
            </tr>
            <%
                if (dados.containsKey("obaaSupportedPlatforms")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Plataformas suportadas:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            String obaaTemp = dados.get("obaaSupportedPlatforms").toString().trim();

            String temp[] = obaaTemp.split(";; ");
            for (int kk = 0; kk < temp.length; kk++) {
                //apos o primeiro elemento colocar um "<BR>"
                if (kk > 0) {
                    out.print("<BR>");
                }
                out.print(temp[kk].trim());
            }

                        %>

                    </font>
                </td>
            </tr>

            <%
                }

                if (dados.containsKey("obaaPerception")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Forma Sensorial (visual/auditiva):
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            String obaaTemp = dados.get("obaaPerception").toString().trim();

            String temp[] = obaaTemp.split(";; ");
            for (int kk = 0; kk < temp.length; kk++) {
                //apos o primeiro elemento colocar um "<BR>"
                if (kk > 0) {
                    out.print("<BR>");
                }
                out.print(temp[kk].trim());
            }

                        %>

                    </font>
                </td>
            </tr>

            <%
                }

                if (dados.containsKey("obaaHasVisual")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Informa&ccedil;&atilde;o visual:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            String obaaTemp = dados.get("obaaHasVisual").toString().trim();

            String temp[] = obaaTemp.split(";; ");
            for (int kk = 0; kk < temp.length; kk++) {
                //apos o primeiro elemento colocar um "<BR>"
                if (kk > 0) {
                    out.print("<BR>");
                }
                if (temp[kk].trim().equalsIgnoreCase("true")) {
                    out.print("Sim");
                } else if (temp[kk].trim().equalsIgnoreCase("false")) {
                    out.print("N&atilde;o");
                } else {
                    out.print(temp[kk].trim());
                }

            }

                        %>

                    </font>
                </td>
            </tr>

            <%
                }

                if (dados.containsKey("obaaHasAuditory")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Informa&ccedil;&atilde;o aud&iacute;vel:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            String obaaTemp = dados.get("obaaHasAuditory").toString().trim();

            String temp[] = obaaTemp.split(";; ");
            for (int kk = 0; kk < temp.length; kk++) {
                //apos o primeiro elemento colocar um "<BR>"
                if (kk > 0) {
                    out.print("<BR>");
                }
                if (temp[kk].trim().equalsIgnoreCase("true")) {
                    out.print("Sim");
                } else if (temp[kk].trim().equalsIgnoreCase("false")) {
                    out.print("N&atilde;o");
                } else {
                    out.print(temp[kk].trim());
                }

            }

                        %>

                    </font>
                </td>
            </tr>

            <%
                }

                if (dados.containsKey("obaaHasText")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Informa&ccedil;&atilde;o textual:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            String obaaTemp = dados.get("obaaHasText").toString().trim();

            String temp[] = obaaTemp.split(";; ");
            for (int kk = 0; kk < temp.length; kk++) {
                //apos o primeiro elemento colocar um "<BR>"
                if (kk > 0) {
                    out.print("<BR>");
                }
                if (temp[kk].trim().equalsIgnoreCase("true")) {
                    out.print("Sim");
                } else if (temp[kk].trim().equalsIgnoreCase("false")) {
                    out.print("N&atilde;o");
                } else {
                    out.print(temp[kk].trim());
                }

            }

                        %>

                    </font>
                </td>
            </tr>

            <%
                }

                if (dados.containsKey("obaaGraphicAlternative")) {
            %>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        Conte&uacute;do alternativo ao texto principal:
                    </font>
                </td>
                <td valign="top">
                    <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                        <%
            String obaaTemp = dados.get("obaaGraphicAlternative").toString().trim();

            String temp[] = obaaTemp.split(";; ");
            for (int kk = 0; kk < temp.length; kk++) {
                //apos o primeiro elemento colocar um "<BR>"
                if (kk > 0) {
                    out.print("<BR>");
                }
                if (temp[kk].trim().equalsIgnoreCase("true")) {
                    out.print("Sim");
                } else if (temp[kk].trim().equalsIgnoreCase("false")) {
                    out.print("N&atilde;o");
                } else {
                    out.print(temp[kk].trim());
                }

            }
                        %>

                    </font>
                </td>
            </tr>

            <%
                }


            }
            %>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
        </table>
        <!--?php include "botton.php"; ?-->
        <input class="BOTAO" type="button" value="< Voltar" onclick="javascript:history.back(-1);"/>
        <BR>

    </body>
</html>

