$(function() {
    if($.trim($("#url").val()) != ""){
        if($("#federation").val()){
            validaOAI($("#url"), true);
        }else{
            validaOAI($("#url"), false);
        }
    }
    
    $("#url").blur(function(e){
        
        if($("#federation").val()){
            validaOAI($(this), true);
        }else{
            validaOAI($(this), false);
        }
    });
    
});

/**
 * Verifica a consistencia do link OAI-PMH
 * @param input input text onde o link foi escrito
 * @param federation boolean informando se é uma federação ou não 
*/
function validaOAI(input, federation){
    var $this = input;
    var urlOAI = $.trim($("#url").val());
    if(urlOAI != ""){
        var img = "<img src='"+rootUrl+"imagens/ajax-loader.gif' border='0' alt='Verificando' align='middle'>";
        $("#resultadoTesteOAI").html(img);
        $.getJSON(rootUrl+"admin/verificaLinkOAI?federation="+federation+"&link="+urlOAI, "", function(resultado){
                
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