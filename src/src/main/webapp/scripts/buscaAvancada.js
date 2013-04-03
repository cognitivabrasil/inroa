function ocultar(id, idLink){
    var valor = document.getElementById(idLink);
    valor.innerHTML="+";
    valor.onclick=function(){
        tornarVisivel(idLink,id, "Interno")
        };
    var obj = document.getElementById(id);
    obj.className='hidden';
}
function tornarVisivel(idLink, id, css){
    var valor = document.getElementById(idLink);
    valor.innerHTML="-";
    valor.onclick=function(){
        ocultar(id, idLink)
        };
    var obj = document.getElementById(id);
    obj.className=css;
}

$(function() {
    $('form').submit(function(){
        var consulta = $.trim($('#consulta').val());
        var autor = $.trim($('#autor').val());
        console.log("autor: '"+autor+"' consulta:'"+consulta+"'");
        if(consulta == '' && autor == ''){
            return false;
        }
    });
});