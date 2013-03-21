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
                    if(_this.text() == "deletar"){
                        rmLineTag(_this);
                    }
                    else{
                        rmAllTags(_this);
                    }
                    $("#msgTagCloud").addClass(unescape(resultado["type"]))
                    .text(unescape(resultado["message"]));
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

    });
    
    var rmLineTag = function(btClick){
        console.log("deletando a linha");
        btClick.parents("tr").remove();
    }
    
    var rmAllTags = function(btClick){
        btClick.siblings("table").remove();
        btClick.remove();
    }
    
});