$(function() {
    $(".btSemTexto").button({
        text: false
    });
    
    $( ".deleteTag" ).button({
        icons: {
            primary: "ui-icon-trash"
        }
    }).click(function(ev){
        
        ev.preventDefault();
        var url = $(this).attr('href');
        _this = $(this);
        
        $.post(url,"",function(resultado){
            console.log(unescape(resultado));
            if(resultado["type"]){
                if(unescape(resultado["type"])=="error"){
                    $("#textStatus").text(unescape(resultado["type"]));
                    $("#errorThrown").text(unescape(resultado["message"]));
                    $("#dialog-error").dialog('open');
                }else{
                    //ação ao excluir
                    console.log(unescape(resultado["message"]));
                    unescape(resultado);
                }
            }else{
                $("#errorThrown").text("Não foi possível executar a operação!");
                $("#dialog-error").dialog('open');
            }
        },"json")
        .error(function() {
            $("#errorThrown").text("Não foi possível executar a operação!");
            $("#dialog-error").dialog('open');
        });

        $( ".dialog-confirm" ).siblings(".ui-dialog-buttonpane").hide();
        $( ".dialog-confirm" ).html("<p> Excluindo... Por favor aguarde.</p>");
    });
    
});