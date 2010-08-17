package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;
import java.sql.*;
import mysql.Conectar;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(2);
    _jspx_dependants.add("/conexaoBD.jsp");
    _jspx_dependants.add("/googleAnalytics");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
      out.write("    \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("\n");
      out.write('\n');
      out.write('\n');


            Conectar conect = new Conectar();
            //chama metodo que conecta no mysql
            Connection con = conect.conectaBD();

            Statement stm = con.createStatement();
            
    
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>GT-FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>\n");
      out.write("        <link rel=\"StyleSheet\" href=\"css/padrao.css\" type=\"text/css\">\n");
      out.write("        <link href=\"imagens/favicon.ico\" rel=\"shortcut icon\" type=\"image/x-icon\" />\n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("    <body id=\"bodyMenor\">\n");
      out.write("        <!-- incluir um arquivo %@ include file=\"top.html\" %> -->\n");
      out.write("        <div id=\"page\">\n");
      out.write("\n");
      out.write("                <div class=\"logoBusca\"><img src=\"imagens/Logo FEB_reduzido.png\" width=\"11%\" height=\"10%\" alt=\"Logo FEB_reduzido\"/></div>\n");
      out.write("                \n");
      out.write("          \n");
      out.write("            <div class=\"clear\"> </div>\n");
      out.write("            \n");
      out.write("            <div class=\"EspacoPequeno\">&nbsp;</div>\n");
      out.write("            <div class=\"subTituloBusca-center\">&nbsp;Consulta de Objetos Educacionais</div>\n");
      out.write("<div class=\"linkCantoDireito\"><a href=\"./adm.jsp\">Ferramenta Administrativa</a></div>\n");
      out.write("            <div class=\"Espaco\">&nbsp;</div>\n");
      out.write("            <form name=\"consulta\" action=\"consulta.jsp\" method=\"post\">\n");
      out.write("\n");
      out.write("                <div class=\"LinhaEntrada\">\n");
      out.write("                    <div class=\"EspacoAntes\">&nbsp;</div>\n");
      out.write("                    <div class=\"Label\">\n");
      out.write("                        Servidor:\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"Value\">\n");
      out.write("                        <select name=\"repositorio\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\">\n");
      out.write("                            <option selected value=\"0\">Federa&ccedil;&atilde;o\n");
      out.write("                                ");

                //Carrega do banco de dados os repositorios cadastrados
                ResultSet res = stm.executeQuery("SELECT nome, id FROM repositorios ORDER BY nome ASC");
                while (res.next()) {
                    if (!res.getString("nome").equalsIgnoreCase("todos")) {
                        out.println("<option value=" + res.getInt("id") + ">" + res.getString("nome").toUpperCase());
                    }

                }
                                
      out.write("\n");
      out.write("\n");
      out.write("                        </select>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("<div class=\"clear\"> </div>\n");
      out.write("                <div id=\"modificavel\">\n");
      out.write("                    <div class=\"LinhaEntrada\">\n");
      out.write("                        <div class=\"Label\">\n");
      out.write("                            Texto para a busca:\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"Value\">\n");
      out.write("                            <input type=\"text\" name=\"key\" value=\"\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\"/>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("<div class=\"clear\"> </div>\n");
      out.write("                <div class=\"LinhaEntrada\">\n");
      out.write("                    <div class=\"Buttons\">\n");
      out.write("                        <input class=\"BOTAO\" type=\"submit\" value=\"Consultar\"/>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </form>\n");
      out.write("        </div>\n");
      out.write("        ");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");\r\n");
      out.write("document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));\r\n");
      out.write("</script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("try {\r\n");
      out.write("var pageTracker = _gat._getTracker(\"UA-15028081-2\");\r\n");
      out.write("pageTracker._trackPageview();\r\n");
      out.write("} catch(err) {}</script>");
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");

            con.close(); //fechar conexao com mysql

    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
