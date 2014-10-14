
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="imgLogo" value="/imagens/logo.png" />
<c:url var="index" value="/" />

<div id="barra-brasil" style="background:#7F7F7F; height: 20px; padding:0 0 0 10px;display:block;"> 
    <ul id="menu-barra-temp" style="list-style:none;">
        <li style="display:inline; float:left;padding-right:10px; margin-right:10px; border-right:1px solid #EDEDED">
            <a href="http://brasil.gov.br" 
               style="font-family:sans,sans-serif; text-decoration:none; color:white;">Portal do Governo Brasileiro</a>
        </li> 

        <li>
            <a style="font-family:sans,sans-serif; text-decoration:none; color:white;" 
               href="http://epwg.governoeletronico.gov.br/barra/atualize.html">Atualize sua Barra de Governo</a>
        </li>
    </ul>
</div>
<!-- /#barra-brasil -->

<div class="container">
    <div class="row text-center">
        <div class="col-md-12">
            <div class="intro-message">
                <a href="${index}">
                    <img id="logo" src="${imgLogo}" class="img-responsive" alt=""/>
                </a>
            </div>
            <!--/.intro-message-->
        </div>
        <!--/.col-md-12-->
    </div>
    <!--/.row-->
</div>
