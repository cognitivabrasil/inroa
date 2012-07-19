$(document).ready(function(){

$('.zebraTable tbody tr').hover(function() {
        $(this).addClass('barra-escura');
    }, function() {
        $(this).removeClass('barra-escura');
    });
    $('.zebraTable tbody tr:even').addClass('zebraEven');
    $('.zebraTable tbody th').css('background-color','#AEC9E3');

    
});