$(function () {
    $("#padraoMetadados").click(function () {
        var divResult = $("#resultadoMap");

        divResult.html("Aguarde...");

        var urlMap = $(this).attr('url') + $(this).val();

        $.ajax(urlMap, {
            dataType: 'html',
            type: 'get'
        }).done(function (msg) {
            divResult.html(msg);
        }).fail(function () {
            divResult.text("Erro nas funções do Ajax");
        });

    });
});