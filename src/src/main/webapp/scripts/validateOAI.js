$(function() {
    var url = $.trim($("#url").val());
    if(url != "" && url !="http://"){
        if($("#federation").val()){
            validaOAI($("#url"), true);
        }else{
            validaOAI($("#url"), false);
        }
    }
    
    $("#url").blur(function(){
        if($(this).val().match("^http://")){
            
            if($("#federation").val()){
                validaOAI($(this), true);
            }else{
                validaOAI($(this), false);
            }
        }else{
            setUrlError($(this));
            $(".msgError").html("A url deve iniciar com http://");
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
    if(urlOAI != "" && urlOAI !="http://"){
        var img = "<img src='"+rootUrl+"imagens/ajax-loader.gif' border='0' alt='Verificando' align='middle'>";
        $("#resultadoTesteOAI").html(img);
        $.getJSON(rootUrl+"admin/verificaLinkOAI?federation="+federation+"&link="+urlOAI, "", function(resultado){
            if(resultado){
                setUrlSuccess($this);                    
            }else{
                setUrlError($this);
            }
        });
    }else{
        setUrlError($this);
    }
}

function setUrlSuccess(input){
    input.removeClass("bordaVermelha");
    input.addClass("bordaVerde");
    $("#resultadoTesteOAI").html("");
    $("#resultadoTesteOAI").removeClass("textoErro");
    $('input[type="submit"]').removeAttr('disabled');
}

function setUrlError(input){
    input.removeClass("bordaVerde");
    input.addClass("bordaVermelha");
    $("#resultadoTesteOAI").html("Link inv&aacute;lido!");
    $("#resultadoTesteOAI").addClass("textoErro");
    $('input[type="submit"]').attr('disabled','disabled');
}