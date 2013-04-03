$(function() {
    $('form').submit(function(){
        var consulta = $.trim($('#consulta').val());
        if(consulta == ''){
            return false;
        }
    });
});