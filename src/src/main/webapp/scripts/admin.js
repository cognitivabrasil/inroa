$(function() {

    var $dialog = $( ".dialog-confirm" ).dialog({
        resizable: true,
        width:440,
        autoOpen: false,
        modal: true,
        buttons: {
            "Apagar": function() {
                _thisDialog = $(this);
                jQuery.ajax($(this).data('url'), {
                    dataType: 'text',
                    type: 'post',
                    statusCode: {
                        200: function() {
                            _thisDialog.dialog("close");
                            location.reload();
                        },
                        403 : function(jqXHR, textStatus, errorThrown){
                            _thisDialog.dialog('close');
                            $("#textStatus").text(textStatus);
                            $("#errorThrown").text("Não é possível apagar o último usuário do sistema");
                            $("#dialog-error").dialog('open');
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        if(jqXHR.status != 403){
                            _thisDialog.dialog('close');
                            $("#textStatus").text(textStatus);
                            $("#errorThrown").text(errorThrown);
                            $("#dialog-error").dialog('open');
                        }
                    }
                });                            
                $( ".dialog-confirm" ).siblings(".ui-dialog-buttonpane").hide();
                $( ".dialog-confirm" ).html("<div class='margin-top'><img src='"+rootUrl+"/imagens/ajax-loader.gif' border='0' alt='Atualizando' align='middle'> Deletando... Por favor aguarde.<p class='textoErro center'>Esta operação pode levar alguns minutos.</p><div>");
            },
            "Cancelar": function() {
                $( this ).dialog( "close" );
            }
        }
    });

    $( "#dialog-error" ).dialog({
        modal: true,
        autoOpen: false,
        buttons: {
            Ok: function() {
                $( this ).dialog( "close" );
                location.reload();
            }
        }
    });

    $('.delete_link').click(function(e) {
        e.preventDefault();
        $(".dialog-confirm")
        .data('url', $(this).attr('href')) // sends url to the dialog
        .dialog('open'); // opens the dialog
        $("#msgApagar").text($(this).attr('title'));
    });
                
    $('#estatisticas a').click(function (e) {
        $("#loading").removeClass("hidden");
    });
                

});