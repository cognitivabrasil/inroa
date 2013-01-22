$(function() {
    if($.trim($("#url").val()) != ""){
        validaOAI($("#url"));
    }
    
    $("#url").blur(function(e){
        validaOAI($(this));
    });
    
});

function validaOAI(input){
    var $this = input;
    var urlOAI = $.trim($("#url").val());
    if(urlOAI != ""){
        var img = "<img src='"+rootUrl+"imagens/ajax-loader.gif' border='0' alt='Verificando' align='middle'>";
        $("#resultadoTesteOAI").html(img);
        $.getJSON(rootUrl+"admin/verificaLinkOAI?link="+urlOAI, "", function(resultado){
                
            if(resultado){
                $this.removeClass("bordaVermelha");
                $this.addClass("bordaVerde");
                $("#resultadoTesteOAI").html("");
                $("#resultadoTesteOAI").removeClass("textoErro");
                $('input[type="submit"]').removeAttr('disabled');
                    
            }else{
                $this.removeClass("bordaVerde");
                $this.addClass("bordaVermelha");
                $("#resultadoTesteOAI").html("Link inv&aacute;lido!");
                $("#resultadoTesteOAI").addClass("textoErro");
                $('input[type="submit"]').attr('disabled','disabled');
            }
        });
    }else{
        $this.removeClass("bordaVerde");
        $this.addClass("bordaVermelha");
        $("#resultadoTesteOAI").html("Link inv&aacute;lido!");
        $("#resultadoTesteOAI").addClass("textoErro");
        $('input[type="submit"]').attr('disabled','disabled');
    }
}