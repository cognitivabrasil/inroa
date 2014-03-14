$(function() {
    $('form').submit(function(){
        var consulta = $.trim($('#consulta').val().replace(/[^0-9A-Za-z]*/g,''));
        if(consulta === ''){
            return false;
        }
    });
});