$(function() {
    var thisUrl = document.location.href;
    thisUrl = thisUrl.replace("/buscaAvancada", "/");
    if (thisUrl == '${confederacao}') {
        $("#confederacao").hide();
    }
});