<%--
    Document   : infoDetalhada2
    Created on : 29/09/2009, 18:09:20
    Author     : Marcos Nunes

modelo de tópico:
<div class="topicosObj">
    <div class="titulo">Informa&ccedil;&otilde;es xxx</div>
    <div class="nome">Atributo:</div>
    <div class="valor">&nbsp; valor</div>
</div>

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="operacoesLdap.Consultar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federação de Repositórios Educa Brasil</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>
    <body id="bodyLetraMaior">


        <div id="page">
            <%
            ArrayList<HashMap> resultHash = new ArrayList<HashMap>(); //instancia um ArrayList de HashMaps
            String ipServidor = request.getParameter("ip"); //coleta o ip informado
            String id = request.getParameter("id"); //coleta o id informado
            String dn = request.getParameter("dn"); //coleta o dn informado
            String consulta = "(obaaEntry=" + id + ")"; //cria o criterio de busca. Busca no atrubuto Entry o id informado

            Consultar busca = new Consultar(ipServidor, consulta, dn, "", "", 389, null); //efetua a busca no ldap
            resultHash = busca.getResultado(); //adicona no ArreyList o resultado da busca

            HashMap dados = new HashMap(); //instancia um HashMap


            dados = (HashMap) resultHash.get(0); //coloca no HashMap dados do primeiro resultado recebido


            if (resultHash.size() > 1) { //se retornar mais de um objeto com o mesmo identificador imprime um erro na tela
                out.println("<div class='TextoDivAlerta'>ERRO no repositorio: Retornou mais de um resultado com o mesmo identificador</div>");
            }

            %>

            <div class="tituloPrincipal">
                <div class="tituloObj"><%=dados.get("obaaTitle")%></div>
                <div class="identificadorObj"> Objeto 
                    <%
            if (dados.containsKey("obaaIdentifier")) {
                String obaaIdentifier[] = dados.get("obaaIdentifier").toString().split(";; ");
                out.print(obaaIdentifier[0]);
            } else if (dados.containsKey("obaaEntry")) {
                out.print(dados.get("obaaEntry"));
            }
                    %>
                </div>
            </div>

            <%
            if (dados.containsKey("obaaLanguage") ||
                    dados.containsKey("obaaDescription") ||
                    dados.containsKey("obaaKeyword")) {
            %>
            <!--Informacoes gerais-->
            <div class="topicosObj">
                <div class="titulo">Informa&ccedil;&otilde;es Gerais</div>

                <%
                if (dados.containsKey("obaaLanguage")) {
                %>
                <div class="nome">Idioma:</div>
                <div class="valor">&nbsp;
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
                    } else if (lingua.equalsIgnoreCase("PT")) {
                        out.print("Portugu&ecirc;s");
                    } else if (lingua.equalsIgnoreCase("EN")) {
                        out.print("Ingl&ecirc;s");
                    } else {
                        out.print(lingua);
                    }
                    %>
                </div>
                <%
                }
                if (dados.containsKey("obaaDescription")) {
                %>

                <div class="nome">Descrição:</div>
                <div class="valor">&nbsp;
                    <%
                    String[] tempObaa = dados.get("obaaDescription").toString().split(";; ");
                    for (int kk = 0; kk < tempObaa.length; kk++) {
                        //apos o primeiro elemento colocar um "<BR>"
                        if (kk > 0) {
                            out.print("<p>&nbsp; ");
                        }

                        out.print("+ " + tempObaa[kk].trim());
                    }
                    %>
                </div>
                <%
                }
                if (dados.containsKey("obaaKeyword")) {
                %>
                <div class="nome">Palavra-chave:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaKeyword").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                %>

            </div>
            <%
            } //fim if informacoes gerais

            if (dados.containsKey("obaaVersion") || dados.containsKey("obaaStatus") || dados.containsKey("obaaEntity") || dados.containsKey("obaaDate") || dados.containsKey("obaaRole")) {
            %>
            <!--Ciclo de Vida do Objeto-->
            <div class="topicosObj">
                <div class="titulo">Ciclo de Vida</div>
                <%
                if (dados.containsKey("obaaVersion")) {
                %>
                <div class="nome">Vers&atilde;o:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaVersion").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }//fim obaaVersion
                if (dados.containsKey("obaaStatus")) {
                %>
                <div class="nome">Status:</div>
                <div class="valor">&nbsp;

                    <%
                    String[] tempObaa = dados.get("obaaStatus").toString().split(";; ");
                    for (int kk = 0; kk < tempObaa.length; kk++) {
                        //apos o primeiro elemento colocar um "<BR>"
                        if (kk > 0) {
                            out.print("<br>&nbsp; ");
                        }


                        if (tempObaa[kk].trim().equalsIgnoreCase("draft")) {
                            out.print("Rascunho");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("final")) {
                            out.print("Final");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("revised")) {
                            out.print("Revisado");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("unavailable")) {
                            out.print("Indispon&iacute;vel");
                        } else {
                            out.print(tempObaa[kk].trim());
                        }
                    } //fim for

                    %>
                </div>
                <%
                }
                %>
            </div>
            <%
            } //fim version e status
            if (dados.containsKey("obaaEntity") || dados.containsKey("obaaDate") || dados.containsKey("obaaRole")) {
            %>
            <!--Contribuicoes Para o Objeto-->            
            <div class="subTopicosObj">
                <div class="titulo"> - Contribui&ccedil;&otilde;es</div>
                <%
                if (dados.containsKey("obaaRole")) {
                %>
                <div class="nome">Tipo de contribui&ccedil;&atilde;o:</div>
                <div class="valor">&nbsp;

                    <%
                    String[] tempObaa = dados.get("obaaRole").toString().split(";; ");
                    for (int kk = 0; kk < tempObaa.length; kk++) {
                        //apos o primeiro elemento colocar uma quebra de linha e um espaco
                        if (kk > 0) {
                            out.print("<BR>&nbsp; ");
                        }
                        if (tempObaa[kk].trim().equalsIgnoreCase("creator")) {
                            out.print("Cria&ccedil;&atilde;o");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("validator")) {
                            out.print("Valida&ccedil;&atilde;o");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("unknown")) {
                            out.print("Desconhecido");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("initiator")) {
                            out.print("Inicializador");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("terminator")) {
                            out.print("Finalizador");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("editor")) {
                            out.print("Editor");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("graphical designer")) {
                            out.print("Designer gr&aacute;fico");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("technical implementer")) {
                            out.print("Implementador t&eacute;cnico");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("content provider")) {
                            out.print("Provedor de conte&uacute;do");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("educational validator")) {
                            out.print("Validador Educacional");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("script writer")) {
                            out.print("Roteirista");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("instructional designer")) {
                            out.print("Designer instrucional");
                        } else if (tempObaa[kk].trim().equalsIgnoreCase("subject matter expert")) {
                            out.print("Especialista em conte&uacute;do");
                        } else {
                            out.print(tempObaa[kk].trim());
                        }
                    } //fim for

                    %>
                </div>
                <%
                }

                if (dados.containsKey("obaaEntity")) {
                %>

                <div class="nome">Entidade que contribuiu:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaEntity").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                %>
                <div class="nome">Data da contribui&ccedil;&atilde;o:</div>
                <div class="valor">&nbsp;
                    <%
                if (dados.containsKey("obaaDate")) {
                    if (!dados.get("obaaDate").toString().equalsIgnoreCase("Ano-Mês-Dia")) {

                        String[] tempObaa = dados.get("obaaDate").toString().split(";; ");
                        for (int kk = 0; kk < tempObaa.length; kk++) {
                            //apos o primeiro elemento colocar um "<BR>"
                            if (kk > 0) {
                                out.print("<BR>&nbsp; ");
                            }
                            out.print(tempObaa[kk].replaceAll("[A-Z,a-z,ê,Ê]", " ").replaceAll(" -", ""));
                        } //fim for

                    } else
                        out.print("N&atilde;o documentada");
                } else{
                    out.print("N&atilde;o documentada");
                    }
                    %>
                </div>
            </div>
            <%
            }

            if (dados.containsKey("obaaFormat") || dados.containsKey("obaaSize") || dados.containsKey("obaaLocation") || dados.containsKey("obaaResourceEntry") || dados.containsKey("obaaIdentifier")) {
            %>
            <!--Informacoes tecnicas-->
            <div class="topicosObj">
                <div class="titulo">Informa&ccedil;&otilde;es T&eacute;cnicas Gerais</div>
                
                <%
                if (dados.containsKey("obaaFormat")) {
                %>
                
                <div class="nome">Formato:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaFormat").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                if (dados.containsKey("obaaSize")) {
                %>
                <div class="nome">Tamanho:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaSize").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                if (dados.containsKey("obaaSupportedPlatforms")) {
                %>
                <div class="nome">Plataformas suportadas:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaSupportedPlatforms").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                
                <%
                } //fim teste contem obaaSupportedPlatforms

            
            
            if (dados.containsKey("obaaLocation") || dados.containsKey("obaaResourceEntry") || dados.containsKey("obaaIdentifier")) {
                %>

                <div class="nome">Localiza&ccedil;&atilde;o: </div>
                <%
                String obaaTemp = "";
                if (dados.containsKey("obaaLocation")) {
                    obaaTemp = dados.get("obaaLocation").toString().trim(); //se existir obaaLocation adiciona o valor dele para obaaTemp
                } else if (dados.containsKey("obaaResourceEntry")) { //se nao
                    obaaTemp = dados.get("obaaResourceEntry").toString().trim(); //adiciona o valor de obaaResourceEntry para obaaTemp
                } if (dados.containsKey("obaaIdentifier")) {
                    if(dados.get("obaaIdentifier").toString().contains("http://")){
                    String temp[] = dados.get("obaaIdentifier").toString().split(";; "); //adiciona o valor de obaaIdentifier para temp[]


                    for (int i = 0; i < temp.length; i++) { //percorre o array temp
                        if (temp[i].contains("http")) { //se existir a palavra http significa q eh um link
                            if (obaaTemp.isEmpty()) {
                                obaaTemp = temp[i]; //se a string tiver vazia apenas adiciona a string para obaaTemp
                            } else {
                                obaaTemp = obaaTemp + ";; " + temp[i]; //se ja possui algo na string contena o valor com ";; "
                            } //fim else
                        } //fim if http
                    } //fim for temp.length

                }} //fim id se obaaIdentifier

                String temp[] = obaaTemp.split(";;");
                for (int kk = 0; kk < temp.length; kk++) {
                    out.print("<div class=\"valor\">&nbsp;<a href='" + temp[kk].trim() + "' target='_new'>" + temp[kk].trim() + "</a></div>");

                } //fecha for

            } //fim teste principal do obj localizacao
%>
            </div>
            <%
            } //fim if informacoes tecnicas gerais
            
            if (dados.containsKey("obaaType") || dados.containsKey("obaaName") || dados.containsKey("obaaMinimumVersion") || dados.containsKey("obaaMaximumVersion")) {
            %>

            <!--Requisitos Tecnicos-->
            <div class="subTopicosObj">
                <div class="titulo"> - Requisitos T&eacute;cnicos Para Funcionalidade</div>
                <%
                if (dados.containsKey("obaaType")) {
                %>
                <div class="nome">Tipo da tecnologia:</div>
                <div class="valor">&nbsp;
                    <%
                    String[] tempObaa = dados.get("obaaType").toString().split(";; ");
                    for (int kk = 0; kk < tempObaa.length; kk++) {
                        if (kk > 0) {
                            out.print("<BR>&nbsp; ");
                        }
                        String obaaType = tempObaa[kk];
                        if (obaaType.equalsIgnoreCase("operational_system")) {
                            out.print("Sistema Operational");
                        } else if (obaaType.equalsIgnoreCase("browser")) {
                            out.print("Navegador");
                        } else {
                            out.print(obaaType);
                        }
                    }
                    %>
                </div>
                <%
                }
                if (dados.containsKey("obaaName")) {
                %>
                <div class="nome">Nome da tecnologia:</div>
                <div class="valor">&nbsp; 
                    <%
                    String[] tempObaa = dados.get("obaaName").toString().split(";; ");
                    for (int kk = 0; kk < tempObaa.length; kk++) {
                        if (kk > 0) {
                            out.print("<BR>&nbsp; ");
                        }
                        String obaaName = tempObaa[kk];
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
                    }
                    %>
                </div>
                <%
                } //fim teste se existe obaaName

                if (dados.containsKey("obaaMinimumVersion")) {
                %>
                <div class="nome">Vers&atilde;o m&iacute;nima:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaMinimumVersion").toString().replaceAll(";; ", "<BR>&nbsp; ").trim()%>
                </div>
                <%
                }
                if (dados.containsKey("obaaMaximumVersion")) {
                %>
                <div class="nome">Vers&atilde;o m&aacute;xima:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaMaximumVersion").toString().trim()%>
                </div>
                <%
                }

                %>
            </div>
            <%
            } //fim requisitos tecnicos
            if (dados.containsKey("obaaDuration")) {
            %>
            <!--Duracao do objeto-->
            <div class="subTopicosObj">
                <div class="titulo"> - Dura&ccedil;&atilde;o</div>
                <div class="nome">Tempo de dura&ccedil;&atilde;o:</div>
                <div class="valor">&nbsp; <%=dados.get("obaaDuration").toString().replaceAll(";; ", "<BR>&nbsp; ")%></div>
            </div>
            <%
            } //fim duracao


            if (dados.containsKey("obaaPlatformType") || dados.containsKey("obaaSpecificFormat") || dados.containsKey("obaaSpecificSize") || dados.containsKey("obaaSpecificLocation") || dados.containsKey("obaaSpecificRequirement") || dados.containsKey("obaaSpecificOrComposite") || dados.containsKey("obaaSpecificType") || dados.containsKey("obaaSpecificName") || dados.containsKey("obaaSpecificMinimumVersion") || dados.containsKey("obaaSpecificMaximumVersion") || dados.containsKey("obaaSpecificInstallationRemarks") || dados.containsKey("obaaSpecificOtherPlatformRequirements")) {
            %>
            <div class="topicosObj">
                <div class="titulo">Características para uma plataforma específica</div>

                <%
                if (dados.containsKey("obaaPlatformType")) {
                %>
                <div class="nome">Tipo de Plataforma:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaPlatformType").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                if (dados.containsKey("obaaSpecificFormat")) {
                %>
                <div class="nome">Formato:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaSpecificFormat").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                if (dados.containsKey("obaaSpecificSize")) {
                %>
                <div class="nome">Tamanho:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaSpecificSize").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                if (dados.containsKey("obaaSpecificLocation")) {
                %>
                <div class="nome">Localiza&ccedil;&atilde;o:</div>                

                <%
                    String[] tempObaa = dados.get("obaaSpecificLocation").toString().split(";; ");
                    for (int kk = 0; kk < tempObaa.length; kk++) {
                        //apos o primeiro elemento colocar uma quebra de linha e um espaco
                        if (kk > 0) {
                            out.print("<BR>&nbsp; ");
                        }
                        out.print("<div class=\"valor\">&nbsp;<a href='" + tempObaa[kk].trim() + "' target='_new'>" + tempObaa[kk].trim() + "</a></div>");
                    }
                %>

                <%
                }
                if (dados.containsKey("obaaSpecificOrComposite") || dados.containsKey("obaaSpecificType") || dados.containsKey("obaaSpecificName") || dados.containsKey("obaaSpecificMinimumVersion") || dados.containsKey("obaaSpecificMaximumVersion") || dados.containsKey("obaaSpecificInstallationRemarks") || dados.containsKey("obaaSpecificOtherPlatformRequirements")) {
                %>

                <div class="subTopicosObj">
                    <div class="titulo"> - Requisitos</div>
                    <%

                        if (dados.containsKey("obaaSpecificOrComposite")) {
                    %>
                    <div class="nome">XXX:</div>
                    <div class="valor">&nbsp;
                        <%=dados.get("obaaSpecificOrComposite").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                    </div>
                    <%
                        }
                        if (dados.containsKey("obaaSpecificType")) {
                    %>
                    <div class="nome">Tipo:</div>
                    <div class="valor">&nbsp;
                        <%=dados.get("obaaSpecificType").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                    </div>
                    <%
                        }
                        if (dados.containsKey("obaaSpecificName")) {
                    %>
                    <div class="nome">Nome:</div>
                    <div class="valor">&nbsp;
                        <%=dados.get("obaaSpecificName").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                    </div>
                    <%
                        }
                        if (dados.containsKey("obaaSpecificMinimumVersion")) {
                    %>
                    <div class="nome">Vers&atilde;o m&iacute;nima:</div>
                    <div class="valor">&nbsp;
                        <%=dados.get("obaaSpecificMinimumVersion").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                    </div>
                    <%
                        }
                        if (dados.containsKey("obaaSpecificMaximumVersion")) {
                    %>
                    <div class="nome">Vers&atilde;o m&aacute;xima:</div>
                    <div class="valor">&nbsp;
                        <%=dados.get("obaaSpecificMaximumVersion").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                    </div>
                    <%
                        }
                        if (dados.containsKey("obaaSpecificInstallationRemarks")) {
                    %>
                    <div class="nome">Instru&ccedil;&otilde;es de Instala&ccedil;&atilde;o da vers&atilde;o espec&iacute;fica:</div>
                    <div class="valor">&nbsp;
                        <%=dados.get("obaaSpecificInstallationRemarks").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                    </div>
                    <%
                        }
                        if (dados.containsKey("obaaSpecificOtherPlatformRequirements")) {
                    %>
                    <div class="nome">XXX:</div>
                    <div class="valor">&nbsp;
                        <%=dados.get("obaaSpecificOtherPlatformRequirements").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                    }
                %>
            </div>
            <%
            }

            if (dados.containsKey("obaaInteractivityType") || dados.containsKey("obaaLearningResourceType") || dados.containsKey("obaaInteractivityLevel") || dados.containsKey("obaaIntendedEndUserRole") || dados.containsKey("obaaContext") || dados.containsKey("obaaTypicalAgeRange") || dados.containsKey("obaaEducationalDescription")) {
            %>
            <div class="topicosObj">
                <div class="titulo">Caracter&iacute;sticas Pedag&oacute;gicas e Educacionais</div>
                <%
                if (dados.containsKey("obaaInteractivityType")) {
                %>
                <div class="nome">Tipo de interatividade:</div>
                <div class="valor">&nbsp;
                    <%
                    String[] tempObaa = dados.get("obaaInteractivityType").toString().split(";; ");
                    for (int kk = 0; kk < tempObaa.length; kk++) {
                        //apos o primeiro elemento colocar uma quebra de linha e um espaco
                        if (kk > 0) {
                            out.print("<BR>&nbsp; ");
                        }
                        String obaaInteractivityType = tempObaa[kk].trim();

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
                    }
                    %>
                </div>
                <%
                }

                if (dados.containsKey("obaaLearningResourceType")) {
                %>

                <div class="nome">Tipo de recurso educacional:</div>
                <div class="valor">&nbsp;
                    <%
                    String obaaLearningresourceType = null;
                    obaaLearningresourceType = dados.get("obaaLearningResourceType").toString().trim();
                    String temp[] = obaaLearningresourceType.split(";; ");
                    for (int kk = 0; kk < temp.length; kk++) {
                        //apos o primeiro elemento colocar uma quebra de linha e um espaco
                        if (kk > 0) {
                            out.print("<BR>&nbsp; ");
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
                    %>
                </div>
                <%
                }
                if (dados.containsKey("obaaInteractivityLevel")) {//if nivel de iteratividade
%>
                <div class="nome">N&iacute;vel de Interatividade:</div>
                <div class="valor">&nbsp;
                    <%
                    String obaaInteractivityLevel = null;
                    obaaInteractivityLevel = dados.get("obaaInteractivityLevel").toString().trim();
                    String temp[] = obaaInteractivityLevel.split(";; ");
                    for (int kk = 0; kk < temp.length; kk++) {
                        //apos o primeiro elemento colocar uma quebra de linha e um espaco
                        if (kk > 0) {
                            out.print("<BR>&nbsp; ");
                        }

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
                    }
                    %>
                </div>
                <%
                } //fim if nivel de iteratividade

                if (dados.containsKey("obaaIntendedEndUserRole")) {//if usuario final esperado
%>
                <div class="nome">Usu&aacute;rio final esperado:</div>
                <div class="valor">&nbsp;
                    <%
                    String obaaTemp = dados.get("obaaIntendedEndUserRole").toString().trim();

                    String temp[] = obaaTemp.split(";; ");
                    for (int kk = 0; kk < temp.length; kk++) {
                        //apos o primeiro elemento colocar uma quebra de linha e um espaco
                        if (kk > 0) {
                            out.print("<BR>&nbsp; ");
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
                    %>
                </div>
                <%
                }
                if (dados.containsKey("obaaContext")) {
                %>
                <div class="nome">Ambiente de utiliza&ccedil;&atilde;o:</div>
                <div class="valor">&nbsp;
                    <%
                    String obaaTemp = dados.get("obaaContext").toString().trim();

                    String temp[] = obaaTemp.split(";; ");
                    for (int kk = 0; kk < temp.length; kk++) {
                        //apos o primeiro elemento colocar um "; "
                        if (kk > 0) {
                            out.print("<BR>&nbsp; ");
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
                    %>
                </div>
                <%
                }

                if (dados.containsKey("obaaTypicalAgeRange")) {
                %>
                <div class="nome">Faixa et&aacute;ria:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaTypicalAgeRange").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                if (dados.containsKey("obaaEducationalDescription")) {
                %>
                <div class="nome">Descri&ccedil;&atilde;o:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaEducationalDescription").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                %>
            </div>

            <%
            } //fim teste principal do obj Caracteristicas Pedagogicas e Educacionais do Objeto

            if (dados.containsKey("obaaCost") || dados.containsKey("obaaCopyrightAndOtherRestrictions") || dados.containsKey("obaaRightsDescription")) {
            %>
            <div class="topicosObj">
                <div class="titulo">Propriedade Intelectual</div>
                <%
                if (dados.containsKey("obaaCost")) { //testa se existe o obaaCost
%>
                <div class="nome">Requer pagamento:</div>
                <div class="valor">&nbsp;
                    <%
                    String obaaTemp = dados.get("obaaCost").toString().trim();
                    if (obaaTemp.equalsIgnoreCase("yes")) {
                        out.print("Sim");
                    } else if (obaaTemp.equalsIgnoreCase("no")) {
                        out.print("N&atilde;o");
                    } else {
                        out.print(obaaTemp.replaceAll(";; ", "<BR>&nbsp; "));
                    }
                    %>
                </div>
                <%
                }
                if (dados.containsKey("obaaCopyrightAndOtherRestrictions")) {
                %>
                <div class="nome">Possui direito autoral:</div>
                <div class="valor">&nbsp;
                    <%
                    String obaaTemp = dados.get("obaaCopyrightAndOtherRestrictions").toString().trim();
                    if (obaaTemp.equalsIgnoreCase("yes")) {
                        out.print("Sim");
                    } else if (obaaTemp.equalsIgnoreCase("no")) {
                        out.print("N&atilde;o");
                    } else {
                        out.print(obaaTemp.replaceAll(";; ", "<BR>&nbsp; "));
                    }
                    %>
                </div>
                <%
                }
                if (dados.containsKey("obaaRightsDescription")) {
                %>
                <div class="nome">Condi&ccedil;&otilde;es de uso:</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaRightsDescription").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                }
                %>
            </div>

            <%
            } //fim teste principal do Propriedade Intelectual do Objeto


            if (dados.containsKey("obaaPerception") || dados.containsKey("obaaHasVisual") || dados.containsKey("obaaHasAuditory") || dados.containsKey("obaaHasText") || dados.containsKey("obaaGraphicAlternative")) {
            %>

            <!--Atributos do obaa-->

            <div class="topicosObj">
                <div class="titulo">Metadados OBAA</div>
                <%
                if (dados.containsKey("obaaPerception")) {
                %>
                <div class="nome">Forma Sensorial (visual/auditiva):</div>
                <div class="valor">&nbsp;
                    <%=dados.get("obaaPerception").toString().replaceAll(";; ", "<BR>&nbsp; ")%>
                </div>
                <%
                } //fim teste contem atributo
                if (dados.containsKey("obaaHasVisual")) {
                %>
                <div class="nome">Informa&ccedil;&atilde;o visual:</div>
                <div class="valor">&nbsp;
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

                    } //fim for temp.legth
                    %>
                </div>
                <%
                } //fim teste contem atributo
                if (dados.containsKey("obaaHasAuditory")) {
                %>
                <div class="nome">Informa&ccedil;&atilde;o aud&iacute;vel:</div>
                <div class="valor">&nbsp;
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
                </div>
                <%
                } //fim teste contem atributo
                if (dados.containsKey("obaaHasText")) {
                %>
                <div class="nome">Informa&ccedil;&atilde;o textual:</div>
                <div class="valor">&nbsp;
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

                    } //fim for
                    %>
                </div>
                <%
                } //fim teste contem atributo
                if (dados.containsKey("obaaGraphicAlternative")) {
                %>
                <div class="nome">Conte&uacute;do alternativo ao texto principal:</div>
                <div class="valor">&nbsp;
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

                    } //fim for
%>
                </div>
                <%
                } //fim teste contem atributo
                %>
            </div>
            <%
            }
            %>
            <input class="BOTAO" type="button" value="< Voltar" onclick="javascript:history.back(-1);"/>



        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
